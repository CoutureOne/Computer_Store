package com.couture.store.controller;

import com.couture.store.service.ex.*;
import com.couture.store.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

/**
 * @author Couture
 * @data: 2022/8/7
 * @description: 控制层类的基类
 */
public class BaseController {
    // 操作状态码
    public static final int OK = 200;

    // 用于统一处理抛出的异常
    @ExceptionHandler(ServiceException.class)
    public JsonResult<Void> HandleException(Throwable e) {
        JsonResult<Void> result = new JsonResult<>(e);
        if (e instanceof UsernameDuplicatedException) {
            result.setState(4000);
            result.setMessage("用户名被占用");
        } else if (e instanceof UserNotFoundException) {
            result.setState(5001);
            result.setMessage("用户数据不存在异常");
        } else if (e instanceof PasswordNotMatchException) {
            result.setState(5002);
            result.setMessage("用户名密码错误异常");

        } else if (e instanceof InsertException) {
            result.setState(5000);
            result.setMessage("插入数据时产生未知的异常");
        } else if (e instanceof UpdateException) {
            result.setState(5001);
            result.setMessage("更新数据时产生未知的异常");
        }
        return result;
    }

        /**
         * 获取 session对象中的 uid
         *
         * @param session session对象
         * @return 当前登录的用户 uid
         */
        protected final Integer getuidFromSession (HttpSession session){
            return Integer.valueOf(session.getAttribute("uid").toString());
        }

        /**
         * 获取当前登录用户的username
         *
         * @param session session对象
         * @return 当前登录的用户名称
         */
        protected final String getUsernameFromSession (HttpSession session){
            return session.getAttribute("username").toString();
        }
}