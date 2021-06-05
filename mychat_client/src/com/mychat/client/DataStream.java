package com.mychat.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * 与服务器聊天端口建立通讯，可以接收来自服务器的消息，也可以发送消息到服务器
 */
public final class DataStream implements Runnable {
    /**
     * 与服务器连接的Socket对象
     */
    private final Socket clientSocket;
    /**
     * 数据输入流，从服务器接收
     */
    private DataInputStream in;
    /**
     * 数据输出流，发送到服务器
     */
    private DataOutputStream out;
    /**
     * 用户ID
     */
    private final String userId;
    /**
     * 使用数据输入流所接收到的数据内容，可经过解析后展示到相应窗口
     */
    private String scMessage;

    /**
     * 初始化连接对象与用户ID，使用该连接对象创建数据输入输出流
     *
     * @param clientSocket 连接对象
     * @param userId       用户ID
     */
    public DataStream(Socket clientSocket, String userId) {
        this.clientSocket = clientSocket;
        this.userId = userId;
        try {
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            System.out.println("创建聊天数据流失败：" + e.getMessage());
        }
    }

    /**
     * 因为数据输入流读取时候会阻塞，所以将其单独分配在一个线程里面
     *
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        try {
            while (true) {
                // 读取消息内容
                scMessage = in.readUTF();
                // 解析处理消息
                ChatExecute.execute(scMessage);
            }
        } catch (IOException e) {
            /* 如果程序执行到这里，可能是因为与服务器断开连接，所以需要关闭这些流 */
            try {
                in.close();
            } catch (Exception e2) {
                System.out.println("数据输入流关闭失败：" + e.getMessage());
            }
            try {
                out.close();
            } catch (Exception e2) {
                System.out.println("数据输出流关闭失败：" + e.getMessage());
            }
            try {
                clientSocket.close();
            } catch (IOException e1) {
                System.out.println("服务器连接关闭失败：" + e.getMessage());
            }
            System.out.println("与服务端失去联系 " + e.getMessage());
        }
    }

    /**
     * 发送消息到服务器
     *
     * @param message 消息内容
     * @param toId    发送对象ID
     * @param isGroup 是否发送给群
     */
    public void send(String message, String toId, boolean isGroup) {
        // 对发送内容进行特定编码
        message = (isGroup ? "toGroup```" : "toFriend```") + userId + "```" + toId + "```" + message;
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            System.out.println("发送消息失败：" + e.getMessage());
        }
    }
}
