package com.mychat.server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * 服务端启动，同时监听两个端口(验证端口与聊天端口)，当用户登录时会发送
 * 一个已序列化的ChatVerify对象(其中包括用户名和加密后的密码)到验证端口，
 * 查询数据库如果该用户存在则返回登录成功，然后允许该用户接入聊天端口，在聊
 * 天时，用户所发送的聊天内容都会经过聊天端口被服务器接收，并且发送的内容
 * 经过某种特定标识处理(这样可以告诉服务端该消息是谁发出的，发送给谁等)，
 * 服务端对消息内容识别之后找到发送的目标，并转发给该用户。<br>
 * 关于如何找到发送的目标，在服务端有一个静态哈希映射，它是以用户的ID为键，
 * 和为该用户连接到服务器所创建的数据交换流对象为值。所以我们可以通过一个ID
 * 找到相应的输出流，然后就可以转发数据，对于群聊天来说，需要转发的目标则是
 * 这个群的所有成员。而在用户登录成功之后，服务端会自动将其所拥有的数据交换流
 * 对象加入到哈希映射中，用户离线后则删除它。<br>
 * 在一个用户登录或离线时，同时也会在服务端生成一个标识字串发送给该用户的所有
 * 好友以通知当前的上线/离线情况。<br>
 * 在验证端口所实现的功能主要是用户的登录验证，信息等数据的请求与更新，比如
 * 在更新用户个性签名时会发送来一个标识字串，其中包括一个验证标识(属于哪种操作)，
 * 需要修改的目标ID(固定，不可自定义)，与新的个性签名，然后针对这种标识字串处理
 * 之后得出所要执行的操作，完成任务。<br>
 * 整个服务端用到了多线程处理机制，在采用并行处理的同时也可以保证服务端的正常
 * 运作。
 */
public class StartServer {
    public static void main(String[] args) {
        new Thread(new VerifyThread()).start();
        System.out.println("服务端已成功创建,当前在线人数：0");
    }
}

/* 随便写的简陋图像界面 */
//public class StartServer extends JFrame implements ActionListener {
//    private final JPanel jp;
//    private final JButton jb;
//    private boolean isRunning = false;
//    private final VerifyThread server;
//    private Thread serverThread;
//
//    public static void main(String[] args) {
//        new StartServer();
//
//    }
//
//    public StartServer() {
//        server = new VerifyThread();
//        jp = new JPanel();
//        jp.setLayout(null);
//        jb = new JButton("启动");
//        jb.setFocusPainted(false);
//        jb.setBounds(110, 30, 80, 50);
//        jb.addActionListener(this);
//        jp.add(jb);
//        this.add(jp);
//        this.setBounds(800, 450, 300, 200);
//        this.setVisible(true);
//
//        addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowClosing(WindowEvent e) {
//                System.out.println("服务器已停止");
//                System.exit(0);
//            }
//        });
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if (!isRunning) {
//            serverThread = new Thread(server);
//            serverThread.start();
//            isRunning = true;
//            jb.setText("关闭");
//            System.out.println("服务端已成功创建,当前在线人数：0");
//        } else {
//            serverThread.stop();
//            isRunning = false;
//            jb.setText("启动");
//            System.out.println("服务器已停止");
//        }
//    }
//}




