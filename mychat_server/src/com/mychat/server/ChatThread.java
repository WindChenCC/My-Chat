package com.mychat.server;

import java.io.IOException;
import java.net.Socket;

import com.mychat.config.ServerInfo;

/**
 * 服务器聊天端口的监听，等待用户连接
 */
public final class ChatThread implements Runnable {
    /**
     * 聊天端口监听者对象
     */
    private static final ChatServer CHAT_SERVER_INFO;

    static {
        CHAT_SERVER_INFO = new ChatServer(ServerInfo.CHAT_PORT);
    }

    /**
     * 该线程内用户ID
     */
    private final String userId;

    /**
     * 初始化用户ID
     */
    public ChatThread(String userId) {
        this.userId = userId;
    }

    /**
     * 等待客户端连接，成功连接之后为其创建一个聊天数据流对象
     *
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        // 先与客户端建立聊天端口的连接
        /*
         该用户连接所生成的Socket对象
         */
        Socket userSocket;
        try {
            userSocket = CHAT_SERVER_INFO.getServerSocket().accept();
        } catch (IOException e) {
            System.out.println("未能与客户端成功建立连接：" + e.getMessage());
            return;
        }
        // 成功接入之后建立数据流
        DataStream dataStream = new DataStream(userSocket, userId);
        // 加入到在线人员映射里面
        ChatServer.getClientUser().put(userId, dataStream);
        System.out.println("用户：" + userId + " 登陆成功 IP: " + userSocket.getInetAddress());
        System.out.println("已为该用户创建线程");
        System.out.println("MyChat在线用户：" + ChatServer.getClientUser().size());
        System.out.println("*********************************************");
        dataStream.run();
    }
}
