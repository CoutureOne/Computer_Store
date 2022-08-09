package com.couture.store.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Couture
 * @data: 2022/8/9
 * @description: 定义一个拦截器
 */

public class LoginInterceptor implements HandlerInterceptor {
    /**
     * 检测全局session对象中是否有uid数据，如果有则放行，入股没有则重定向回到页面
     *
     * @param request 请求对象
     * @param response 响应对象
     * @param handler 处理对象
     * @return 如果返回为true表示放行当前的请求，如果返回值为false则表示拦截当前的请求
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        Object obj = request.getSession().getAttribute("uid");
        if (obj == null) {
            response.sendRedirect("/web/login.html");
            return false;
        }
        return false;
    }

}
