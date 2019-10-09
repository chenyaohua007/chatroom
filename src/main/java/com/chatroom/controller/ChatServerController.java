package com.chatroom.controller;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.Set;

public class ChatServerController extends WebSocketServer {
    public ChatServerController(int port) {
        super(new InetSocketAddress(port));
    }

    public ChatServerController(InetSocketAddress address) {
        super(address);
    }

    /**
     * 有新的连接建立
     */
    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println("link statr");
    }

    /**
     * 断开连接时候触发
     */
    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        userLeave(conn);
        //向所有用户更新登陆状态信息
        StringBuffer nameList = new StringBuffer("");
        Set<WebSocket> set = ChatUserController.wsUserMap.keySet();
        for (WebSocket s:set) {
            nameList.append(ChatUserController.wsUserMap.get(s)+";");
        }
        ChatUserController.sendMessageToAll("nameList:"+nameList.toString());
    }

    /**
     * 接收到的消息
     */
    @Override
    public void onMessage(WebSocket conn, String message) {

        if(null != message && message.indexOf("login:")!=-1){
            //登录操作
            String userName=message.replaceFirst("login:", "");
            if (ChatUserController.getUserByWs(conn)==null) {
                userJoin(conn,userName);//用户加入
            }
            //向所有用户更新登陆状态信息
            StringBuffer nameList = new StringBuffer("");
            Set<WebSocket> set = ChatUserController.wsUserMap.keySet();
            for (WebSocket s:set) {
                nameList.append(ChatUserController.wsUserMap.get(s)+";");
            }
            ChatUserController.sendMessageToAll("nameList:"+nameList.toString());
        }else if (null != message && message.indexOf("message:")!=-1) {
            String text=message.replaceFirst("message:", "");
            System.out.println("进来了，我要发给别人的：");
            System.out.println(text);
            ChatUserController.sendMessageToOtherUser(conn,text);
        }
    }

    /**
     * 错误时候触发的代码
     */
    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.out.println("on error");
        ex.printStackTrace();
    }

    /**
     * 去除掉失效的websocket链接
     * @param conn
     */
    private void userLeave(WebSocket conn){
        String userName = ChatUserController.getUserByWs(conn);
        System.out.println("用户 "+userName + "登出");
        ChatUserController.removeUser(conn);
    }

    /**
     * 将websocket加入用户池
     * @param conn
     * @param userName
     */
    private void userJoin(WebSocket conn,String userName){
        ChatUserController.addUser(userName, conn);
    }
}
