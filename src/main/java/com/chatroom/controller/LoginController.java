package com.chatroom.controller;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/login")
public class LoginController {

    @RequestMapping("/index")
    @ResponseBody
    public String index(HttpServletRequest request, HttpServletResponse response) {
        List<String> userList = (List<String>)request.getSession().getAttribute("username");
        if(userList != null && !userList.get(0).equals("login")){
            return "success";
        }
        return "fail";
    }

    @RequestMapping("/logout")
    @ResponseBody
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute("username");
        request.getSession().invalidate();
        return "success";
    }
}
