package com.chatroom.logininterrupter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 拦截器
 */
@Component
public class MyInterceptor extends HandlerInterceptorAdapter {

    //在请求处理之前进行调用（Controller方法调用之前
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        HttpSession session = httpServletRequest.getSession();
        String username = httpServletRequest.getParameter("username");
        List<String> userList = (List<String>)session.getAttribute("username");
        String curusername = (String)session.getAttribute("curusername");

        if(userList == null){
            List<String> login = new ArrayList<>();
            login.add("login");
            session.setAttribute("username",login);
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/chatroom/login");
        }else{
            userList.remove("login");
            if(!StringUtils.isEmpty(username)){
                userList.add(username);
            }
            if(!StringUtils.isEmpty(username)){
                curusername = username;
                session.setAttribute("curusername",curusername);
            }
            session.setAttribute("username",userList);
            return true;
        }
        return false;
    }

    //请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    //在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        System.out.println("afterCompletion被调用\n");
    }
}
