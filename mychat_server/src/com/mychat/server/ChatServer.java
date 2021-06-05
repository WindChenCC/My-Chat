package com.mychat.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.HashMap;

/**
 * 创建一个监听某端口的ServerSocket对象，并存储连接到该端口的客户端信息
 */
public final class ChatServer {
    /**
     * 用户ID与其连接对象数据流之间的哈希映射
     */
    private static final HashMap<String, DataStream> clientUser;

    // 静态初始化块
    static {
        clientUser = new HashMap<>();
    }

    /**
     * 服务器Socket对象
     */
    private ServerSocket serverSocket;
    /**
     * 本地信息
     */
    private InetAddress localHostAddress;
    /**
     * 监听端口
     */
    private int serverPort;

    /**
     * 监听计算机某个端口(创建ServerSocket对象)
     */
    public ChatServer(int port) {
        try {
            // 监听端口
            serverPort = port;
            // 创建监听端口的ServerSocket对象
            serverSocket = new ServerSocket(serverPort);
            // 获取本机地址
            localHostAddress = serverSocket.getInetAddress();
        } catch (IOException e) {
            System.out.println("错误信息 ：" + e.getMessage());
        }
    }

    /**
     * 获取用户ID与其连接对象数据流之间的哈希映射
     */
    public static HashMap<String, DataStream> getClientUser() {
        return clientUser;
    }

    /**
     * 获取监听的端口号
     */
    public int getServerPort() {
        return serverPort;
    }

    /**
     * 获取本机名称
     */
    public String getLocalHostName() {
        return localHostAddress.getHostName();
    }

    /**
     * 获取本机IP(当前计算机所在局域网的IP信息)
     */
    public String getLocalHostAddress() {
        return localHostAddress.getHostAddress();
    }

    /**
     * 获取已经成功连接到该ChatServer对象的人数
     */
    public int getClientNum() {
        return clientUser.size();
    }

    /**
     * 获取该端口的监听者ServerSocket对象
     */
    public ServerSocket getServerSocket() {
        return serverSocket;
    }
}