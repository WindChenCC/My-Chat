package com.mychat.client;

import com.mychat.frame.LoginInterface;

/**
 * 客户端使用了多线程处理机制。<br>
 * 登录界面输入登录信息点击登录按钮后，会与服务端验证端口建立连接，
 * 发送一个序列化的ChatVerify对象到服务端。接收处理结果，如果验证
 * 成功则进入主界面，并接入聊天端口，发送获取个人资料请求，服务端
 * 返回信息后将信息更改到主界面。打开聊天窗口后，首先会向服务端请
 * 求聊天记录，接收到以后显示聊天记录。发送消息是，会对消息添加标
 * 志，然后通过输出流发出。更改个性签名向验证端发送请求。
 */
public class StartClient {
    public static void main(String[] args) {
        new LoginInterface();
    }
}
