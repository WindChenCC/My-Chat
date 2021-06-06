package com.mychat.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import com.mychat.database.DataBaseConnection;
import com.mychat.database.DataQuery;

/**
 * 客户成功登录之后会接入聊天端口，在这里主要处理客户端发送的消息，并将其转发给目标用户或群
 */
public final class DataStream implements Runnable {
    /**
     * 当前连接用户的ID
     */
    private final String userId;
    /**
     * 当前连接用户好友列表
     */
    private final Vector<String> friends;
    /**
     * 为当前连接用户所创建的数据库连接对象
     */
    private final DataBaseConnection con;
    /**
     * 利用与客户端连接套接字Socket对象创建的数据输入流
     */
    private DataInputStream in;
    /**
     * 利用与客户端连接套接字Socket对象创建的数据输出流
     */
    private DataOutputStream out;

    /**
     * 利用与客户端连接的Socket对象创建数据输入输出流
     */
    public DataStream(Socket clientSocket, String userId) {
        this.userId = userId;
        con = new DataBaseConnection();
        friends = DataQuery.getFriendMember(userId);
        try {
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            System.out.println("创建聊天数据传输流失败：" + e.getMessage());
        }
        sendToAllFriends("OnlineSituation```在线```" + userId);
    }

    /**
     * 因为数据输入流读取时候会阻塞，所以将其单独分配在一个线程里面，成功读取信息之后执行处理函数<br>
     * 与客户端连接中断之后抛出该异常，此时从服务端在线用户表中删除该用户
     *
     * @see java.lang.Runnable#run() 接收客户端发送来的请求
     */
    @Override
    public void run() {
        String scMessage;
        try {
            while (true) {
                scMessage = in.readUTF();
                execute(scMessage);
            }
        } catch (IOException e) {
            // 离线后删除用户在线信息
            ChatServer.getClientUser().remove(userId);
            // 通知所有好友离线情况
            sendToAllFriends("OnlineSituation```离线```" + userId);
            System.out.println("用户：" + userId + " 已下线");
            System.out.println("MyChat在线用户：" + ChatServer.getClientUser().size());
            System.out.println("*********************************************");
            // 关闭为该用户创建的数据库连接
            con.close();
        }
    }

    /**
     * 处理客户端所发送来的信息，将它转发给其他用户或群
     */
    private void execute(String message) {
        String[] res = message.split("```", 4);
        if (res.length == 4) {
            /*
             * res[0]：发送标识 、res[1]：fromId 、res[2]：toId 、res[3]：消息内容
             */
            String type = res[0];
            String toId = res[2];
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            message = dateFormat.format(new Date()) + "```" + message;
            if (type.equals("toFriend")) {
                if (ChatServer.getClientUser().containsKey(toId)) {
                    ChatServer.getClientUser().get(toId).send(message);
                }
                printToDatabase(res[1], res[2], res[3], false);
            } else if (type.equals("toGroup")) {
                Vector<String> groups = DataQuery.getGroupMemberId(toId);
                for (String group : groups) {
                    if (!group.equals(userId)
                            && ChatServer.getClientUser().containsKey(group)) {
                        ChatServer.getClientUser().get(group).send(message);
                    }
                }
                printToDatabase(res[1], res[2], res[3], true);
            }
        } else {
            System.out.println("收到的信息不规范：" + message);
        }
    }

    /**
     * @param message 经过特殊编码之后的信息内容
     *                通知该用户的所有好友(一般为上线信息或离线信息)
     */
    private void sendToAllFriends(String message) {
        for (String friend : friends) {
            if (ChatServer.getClientUser().containsKey(friend)) {
                ChatServer.getClientUser().get(friend).send(message);
            }
        }
    }

    /**
     * @param message 转发内容
     *                发送消息到连接的客户端
     */
    private void send(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            System.out.println(userId + "发送到客户端失败：" + e.getMessage());
        }
    }

    /**
     * 聊天记录写入数据库
     *
     * @param fromId  发送者ID
     * @param toId    接收ID
     * @param message 消息内容
     * @param isGroup 是否为群聊天
     */
    private void printToDatabase(String fromId, String toId, String message, boolean isGroup) {
        String sql;
        if (!isGroup) {
            sql = "insert into dw_userchat (uchat_fromid,uchat_toid,uchat_message) values(" + fromId + "," + toId + ",'"
                    + message + "')";
        } else {
            sql = "insert into dw_groupchat (gchat_uid,gchat_gid,gchat_message) VALUES (" + fromId + "," + toId + ",'"
                    + message + "')";
        }
        con.putToDatabase(sql);
    }
}
