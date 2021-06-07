package com.mychat.client;

import java.util.HashMap;

import com.mychat.frame.ChatWithFriend;
import com.mychat.frame.MainInterface;

/**
 * 处理从服务端发送来的消息
 */
public final class ChatExecute {
    /**
     * 接收到的消息内容
     */
    private static String message;
    /**
     * 发送该消息的用户ID，用作显示对方信息
     */
    private static String fromId;
    /**
     * 发送目标方ID，主要在群聊天中有效，用作消息应该展示在哪个群里面
     */
    private static String toId;
    /**
     * 接收消息内容的标识信息
     */
    private static String type;

    /**
     * 开始处理服务端发送已封装的消息内容
     *
     * @param scMessage 接收到的消息内容
     */
    public static void execute(String scMessage) {
        // 对接收到的消息内容进行解码
        String[] res = scMessage.split("```", 5);
        // 从服务端发送的内容解码之后长度为5，代表该消息为聊天内容
        if (res.length == 5) {
            type = res[1];
            fromId = res[2];
            toId = res[3];
            message = res[4];
            // 以ID为键，对应聊天面板为值的哈希映射
            HashMap<String, ChatWithFriend> model;
            // 接收到的消息是从好友发送来的
            if (type.equals("toFriend")) {
                model = MainInterface.getFriendChat();
                // 展示在对应好友聊天面板中
                if (model.containsKey(fromId)) {
                    model.get(fromId).addMessage(fromId, MainInterface.getFriend().get(fromId).getfName(), res[0], message,
                            false);
                }
            }
            // 接收到的消息是从某个群发送来的
            else if (type.equals("toGroup")) {
                model = MainInterface.getGroupChat();
                if (model.containsKey(toId)) {
                    // 聊天面板显示用户昵称
                    String fromString = MainInterface.getFriend().containsKey(fromId)
                            ? MainInterface.getFriend().get(fromId).getfName() :
                            MainInterface.getGroupMembers().get(fromId).getName();
                    model.get(toId).addMessage(fromId, fromString, res[0], message, false);
                }
            }
        } // 接收的内容是为了改变用户状态（在线/离线）
        else if (res.length == 3) {
            // res[0]:验证标识、res[1]:状态信息、res[2]:好友ID
            if (res[0].equals("OnlineSituation")) {
                if (MainInterface.getFriend().containsKey(res[2])) {
                    MainInterface.getFriend().get(res[2]).setfOnline(res[1]);
                }
            }
        }
    }
}
