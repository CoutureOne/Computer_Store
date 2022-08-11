package com.couture.store.controller;

import com.couture.store.controller.ex.*;
import com.couture.store.entity.User;
import com.couture.store.service.IUserService;
import com.couture.store.service.ex.InsertException;
import com.couture.store.service.ex.UsernameDuplicatedException;
import com.couture.store.util.JsonResult;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Couture
 * @data: 2022/8/7
 * @description:
 */
@RestController
@RequestMapping("users")
public class UserController extends BaseController {
    @Autowired
    private IUserService userService;

    @RequestMapping("reg")
    public JsonResult<Void> reg(User user) {
        userService.reg(user);
        return new JsonResult<>(OK);
    }
/*
        try {
            userService.reg(user);
            result.setState(200);
            result.setMessage("用户名注册成功");
        } catch (UsernameDuplicatedException e) {
            result.setState(4000);
            result.setMessage("用户名被占用");
        } catch (InsertException e) {
            result.setState(5000);
            result.setMessage("注册时为产生异常");
        }
        return result;
    }*/

    @RequestMapping("login")
    public JsonResult<User> login(String username, String password, HttpSession session) {
        User data = userService.login(username, password);
        // 向session对象中完成数据的绑定
        session.setAttribute("uid", data.getUid());
        session.setAttribute("username", data.getUsername());

        // 获取session绑定数据
        System.out.println(getuidFromSession(session));
        System.out.println(getUsernameFromSession(session));
        return new JsonResult<>(OK, data);
    }

    @RequestMapping("change_password")
    public JsonResult<Void> changePassword(String oldPassword,
                                           String newPassword,
                                           HttpSession session) {
        Integer uid = getuidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changePassword(uid, username, oldPassword, newPassword);
        return new JsonResult<>(OK);
    }

    @RequestMapping("get_by_uid")
    public JsonResult<User> getByUid(HttpSession session) {
        User data = userService.getByUid(getuidFromSession(session));
        return new JsonResult<>(OK, data);
    }

    @RequestMapping("change_info")
    public JsonResult<Void> changeInfo(User user, HttpSession session) {
        Integer uid = getuidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changeInfo(uid, username, user);
        return new JsonResult<>(OK);
    }

    /**
     * 设置上传文件地最大值
     */
    public static final int AVATAR_MAX_SIZE = 10 * 1024 * 1024;

    // 限制文件上传的类型
    public static final List<String> AVATAR_TYPE = new ArrayList<>();

    static {
        AVATAR_TYPE.add("image/jpeg");
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/bmp");
        AVATAR_TYPE.add("image/gif");
    }

        /**
         * MultipartFile接口是SpringMVC提供的一个接口，这个接口为我们包装了获取文件类型的数据（任何类型的file都可以接收）
         * 获取文件类型的数据（任何数据的file都可以接收）
         *
         * @param session session
         * @param file file
         * @return 上传结果
         */
        @PostMapping("change_avatar")
        public JsonResult<String> changeAvatar (@RequestParam("file")   MultipartFile file, HttpSession session){
            // 判断上传的文件是否为空
            if (file.isEmpty()) {
                // 是：抛出异常
                throw new FileEmptyException("文件为空");
            }

            // 判断上传的文件大小是否超出限制值
            if (file.getSize() > AVATAR_MAX_SIZE) { // getSize()：返回文件的大小，以字节为单位
                // 是：抛出异常
                throw new FileSizeException("不允许上传超过" + (AVATAR_MAX_SIZE / 1024) + "KB的头像文件");
            }

            // 判断上传的文件类型是否超出限制
            String contentType = file.getContentType();
            // boolean contains(Object o)：当前列表若包含某元素，返回结果为true；若不包含该元素，返回结果为false
            if (!AVATAR_TYPE.contains(contentType)) {
                // 是：抛出异常
                throw new FileTypeException("不支持使用该类型的文件作为头像，允许的文件类型：" + AVATAR_TYPE);
            }

            // 获取当前项目的绝对磁盘路径
            String parent = session.getServletContext().getRealPath("upload");
            System.out.println(parent);
            // 保存头像文件的文件夹
            File dir = new File(parent);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 保存的头像文件的文件名
            String suffix = "";
            String originalFilename = file.getOriginalFilename();
            int beginIndex = originalFilename.lastIndexOf(".");
            if (beginIndex > 0) {
                suffix = originalFilename.substring(beginIndex);
            }
            String filename = UUID.randomUUID().toString() + suffix;

            // 创建文件对象，表示保存的头像文件
            File dest = new File(dir, filename);
            // 执行保存头像文件
            try {
                file.transferTo(dest);
            } catch (IllegalStateException e) {
                // 抛出异常
                throw new FileStateException("文件状态异常，可能文件已被移动或删除");
            } catch (IOException e) {
                // 抛出异常
                throw new FileUploadIOException("上传文件时读写错误，请稍后重新尝试");
            }

            // 头像路径
            String avatar = "/upload/" + filename;
            // 从Session中获取uid和username
            Integer uid = getuidFromSession(session);
            String username = getUsernameFromSession(session);
            // 将头像写入到数据库中
            userService.changeAvatar(uid, username, avatar);

            // 返回成功头像路径
            return new JsonResult<String>(OK, avatar);
        }
    }
}