package com.chatroom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/chatroom")
public class PageController {

    @RequestMapping("/login")
    public String login(HashMap<String, Object> map) {
        return "login";
    }

    @RequestMapping("/chat")
    public ModelAndView chat(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("chat");
        String curusername = (String)request.getSession().getAttribute("curusername");
        mv.addObject("curusername", curusername);
        return mv;
    }

    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("index");
        StringBuilder menuBuilder = new StringBuilder();
        List<String> userList = (List<String>)request.getSession().getAttribute("username");
        userList.forEach(username -> {
            menuBuilder.append("<dd><a href='javascript:;' id='"+username+"'><i class='fa fa-share-square' aria-hidden='true'></i> <span>"+username+"</span></a></dd>");
        });
        
        mv.addObject("indexmenu", menuBuilder);
        return mv;
    }



}
