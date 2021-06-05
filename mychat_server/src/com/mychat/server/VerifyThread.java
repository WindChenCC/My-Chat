package com.mychat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.mychat.config.ServerInfo;

/**
 * 在服务器创建验证端口的ServerSocket对象，并等待用户连接，连接成功后为该用户分配一个线程单独处理他所发出的请求
 */
public final class VerifyThread implements Runnable {
    /**
     * serverSocket : 验证端口监听者对象
     */
    private static final ServerSocket serverSocket;

    static {
        serverSocket = new ChatServer(ServerInfo.VERIFY_PORT).getServerSocket();
    }

    /**
     * 等待用户连接，如果连接成功为其单独分配线程处理
     */
    @Override
    public void run() {
        try {
            while (true) {
                // 等待用户连接
                Socket userSocket = serverSocket.accept();
                System.out.println(userSocket.getInetAddress() + " 发送来的新的验证");
                // 为用户接入创建一个验证线程
                new Thread(new VerifyConnection(userSocket)).start();
            }
        } catch (IOException e) {
            System.out.println("验证端口服务异常 ：" + e.getMessage());
        }
    }
}
