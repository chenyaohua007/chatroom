package com.chatroom.controller;

import org.java_websocket.WebSocket;

import java.util.*;

public class ChatUserController {
    public static final Map<WebSocket, String> wsUserMap = new HashMap<>();

    /**
     * 通过websocket连接获取其对应的用户
     *
     * @param conn
     * @return
     */
    public static String getUserByWs(WebSocket conn) {
        return wsUserMap.get(conn);
    }

    /**
     * 根据userName获取WebSocket,这是一个list,此处取第一个
     * @param userName
     */
    public static WebSocket getWsByUser(String userName) {
        Set<WebSocket> keySet = wsUserMap.keySet();
        synchronized (keySet) {
            for (WebSocket conn : keySet) {
                String cuser = wsUserMap.get(conn);
                if (cuser.equals(userName)) {
                    return conn;
                }
            }
        }
        return null;
    }

    /**
     * 向连接池中添加连接
     *
     */
    public static void addUser(String userName, WebSocket conn) {
        wsUserMap.put(conn, userName);
    }

    /**
     * 获取所有连接池中的用户
     *
     * @return
     */
    public static Collection<String> getOnlineUser() {
        List<String> setUsers = new ArrayList<String>();
        Collection<String> setUser = wsUserMap.values();
        for (String u : setUser) {
            setUsers.add(u);
        }
        return setUsers;
    }

    /**
     * 移除连接池中的连接
     *
     * @param conn
     */
    public static boolean removeUser(WebSocket conn) {
        if (wsUserMap.containsKey(conn)) {
            wsUserMap.remove(conn); // 移除连接
            return true;
        } else {
            return false;
        }
    }

    /**
     * 向特定的用户发送数据
     *
     * @param conn
     * @param message
     */
    public static void sendMessageToUser(WebSocket conn, String message) {
        if (null != conn && null != wsUserMap.get(conn)) {
            conn.send(message);
        }
    }

    /**
     * 向所有的用户发送消息
     *
     * @param message
     */
    public static void sendMessageToAll(String message) {
        Set<WebSocket> keySet = wsUserMap.keySet();
        synchronized (keySet) {
            for (WebSocket conn : keySet) {
                String user = wsUserMap.get(conn);
                if (user != null) {
                    conn.send(message);
                }
            }
        }
    }

    /**
     * 向其他人发送信息
     *
     * @param message
     */
    public static void sendMessageToOtherUser(WebSocket conn , String message) {
        Set<WebSocket> keySet = wsUserMap.keySet();
        for (WebSocket connTeam : keySet) {
            sendMessageToUser(connTeam,wsUserMap.get(conn)+":"+message);
        }
    }
}
