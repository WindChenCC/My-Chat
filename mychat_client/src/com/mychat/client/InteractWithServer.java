package com.mychat.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import com.mychat.config.ChatVerify;
import com.mychat.config.ServerInfo;
import com.mychat.user.User;

/**
 * 与服务器验证端口的连接，主要用于请求数据或者验证登录信息
 */
public final class InteractWithServer {
    /**
     * 与服务器验证端口建立连接，并使用对象流传输数据
     * 如果与服务器建立连接失败或创建对象输入输出流失败产生IOException.
     * 如果对象输入流读取异常产生ClassNotFoundException.
     *
     * @param obj 发送给服务器的对象
     * @return result 接收服务器返回的对象信息
     */
    private static Object postToServer(Object obj) {
        Object result = null;
        try {
            // 建立连接
            Socket sc = new Socket(ServerInfo.SERVER_IP, ServerInfo.VERIFY_PORT);
            // 创建对象输入输出流
            ObjectOutputStream out = new ObjectOutputStream(sc.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(sc.getInputStream());
            // 写对象到服务端
            out.writeObject(obj);
            // 获取返回信息
            result = in.readObject();
            // 关闭流
            sc.close();
            in.close();
            out.close();
        } catch (ClassNotFoundException | IOException e) {
            System.out.println("在与服务器验证交互中出现了异常：" + e.getMessage());
        }
        return result;
    }

    /**
     * 验证登录用户名或密码是否正确，发送构造的身份信息到服务器
     *
     * @param userId       用户ID
     * @param userPassword 经过MD5加密后的密码
     * @return Object 返回接收的回馈对象(Boolean)
     */
    public static Object isLogin(String userId, String userPassword) {
        // 构造身份信息
        ChatVerify userInfo = new ChatVerify(userId, userPassword);
        // 返回服务器产生的结果
        return postToServer(userInfo);
    }

    /**
     * 通过ID获取用户的信息，包括个人资料以及好友列表群列表
     *
     * @param userId 用户ID
     * @return userInfo 包含用户信息的对象
     */
    public static User getUserInfo(String userId) {
        User userInfo;
        String fieldString = "getUserInfo" + userId;
        userInfo = (User) postToServer(fieldString);
        return userInfo;
    }

    /**
     * 通过交互双方id获取历史聊天记录
     *
     * @param fromid  交互方1
     * @param toId    交互方2
     * @param isGroup 是否为群聊天记录
     * @return Vector<String> 返回聊天记录信息的Vector数组
     */
    @SuppressWarnings("unchecked")
    public static Vector<String> getChatRecord(String fromid, String toId, boolean isGroup) {
        String sendString = "getChatRecord```" + fromid + "```" + toId + "```" + isGroup;
        return (Vector<String>) postToServer(sendString);
    }

    /**
     * 通过群ID向服务器发送请求，获取群所有成员ID
     *
     * @param groupId 群ID
     * @return Vector<String> 返回群成员ID的Vector数组
     */
    @SuppressWarnings("unchecked")
    public static Vector<String> getGroupMembers(String groupId) {
        String send = "getGroupMembers" + groupId;
        return (Vector<String>) postToServer(send);
    }

    /**
     * 向服务器发送请求更改个性签名
     *
     * @param myId    需要修改的用户ID(只能是自己)
     * @param content 新的个性签名内容
     */
    public static void setMySignature(String myId, String content) {
        postToServer("setMySignature```" + myId + "```" + content);
    }
}
