package com.couture.store.controller;

import com.couture.store.entity.User;
import com.couture.store.service.IUserService;
import com.couture.store.service.ex.InsertException;
import com.couture.store.service.ex.UsernameDuplicatedException;
import com.couture.store.util.JsonResult;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author Couture
 * @data: 2022/8/7
 * @description:
 */
@RestController
@RequestMapping("users")
public class UserController extends BaseController{
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
}
