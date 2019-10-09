package com.chatroom;

import com.chatroom.controller.ChatServerController;
import org.java_websocket.WebSocketImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@MapperScan(value = "com.chatroom.dao")
public class ChatroomApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(ChatroomApplication.class, args);
        WebSocketImpl.DEBUG = false;
        int port = 9999; // 端口
        ChatServerController s = new ChatServerController(port);
        s.start();
    }

    /**
     * 添加静态资源文件，外部可以直接访问地址
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        //需要配置1：----------- 需要告知系统，这是要被当成静态文件的！
        //第一个方法设置访问路径前缀，第二个方法设置资源路径
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
}
