package com.mychat.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.mychat.config.ChatVerify;
import com.mychat.database.DataBaseConnection;
import com.mychat.database.DataCheck;

/**
 * 主要接收客户端发送来的验证请求(登录验证、获取个人资料、获取好友列表等)，然后并处理该请求返回处理结果
 */
public final class VerifyConnection implements Runnable {
    /**
     * 用户Socket对象
     */
    private final Socket userSocket;

    /**
     * 初始化套接字对象
     */
    public VerifyConnection(Socket userSocket) {
        this.userSocket = userSocket;
    }

    /**
     * 选择当前处理的事件类型并执行不同操作
     *
     * @param obj 从客户端发送来的验证请求
     * @return 返回处理结果
     */
    public Object switchCon(Object obj) {
        // 创建 Data_Check 来完成这些工作
        DataCheck check = new DataCheck();
        // 获取对象类型
        String objName = obj.getClass().getSimpleName();
        // 返回结果对象
        Object result = null;
        System.out.println("当前处理对象类型为 " + objName);
        switch (objName) {
            // 登录验证
            case "ChatVerify":
                ChatVerify loginVerify = (ChatVerify) obj;
                // 该用户已登录
                if (ChatServer.getClientUser().containsKey(loginVerify.getUserId())) {
                    result = "Repeat_login";
                } else {
                    result = check.isLoginSuccess(loginVerify.getUserId(), loginVerify.getUserPassword());
                    // 为登录成功的用户创建聊天线程
                    if (result != null && (Boolean) result) {
                        System.out.println("登录成功，为该用户创建一个聊天线程");
                        new Thread(new ChatThread(loginVerify.getUserId())).start();
                    }
                }
                break;
            case "String":
                // 获取字符串内容
                String field = obj.toString();
                // 如果为获取信息
                if (field.startsWith("getUserInfo")) {
                    field = field.replace("getUserInfo", "");
                    result = check.getUserInfo(field);
                } else if (field.startsWith("getChatRecord")) {
                    // 获取聊天记录
                    String[] res = field.split("```", 4);
                    if (res.length == 4) {
                        /*
                         * res[0]：getChatRecord、res[1]：fromId、res[2]：toId、
                         * res[3]：isGroup
                         */
                        result = check.getChatRecord(res[1], res[2], res[3]);
                    }
                } else if (field.startsWith("getGroupMembers")) {
                    field = field.replace("getGroupMembers", "");
                    result = DataCheck.getGroupMember(field);
                } else if (field.startsWith("setMySignature")) {
                    // 替换前缀
                    String[] res = field.split("```", 3);
                    if (res.length == 3) {
                        /*
                         * res[0]：setMySignature、res[1]：myId、res[2]：newTrades
                         */
                        DataBaseConnection con = new DataBaseConnection();
                        String sql = "UPDATE dw_user SET user_signature = '" + res[2] + "' WHERE user_id = " + res[1];
                        con.putToDatabase(sql);
                        con.close();
                    }
                }
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * 利用与客户端建立的连接创建对象输入输出流，读取到客户端的请求，处理完之后利用输出流反馈给客户端
     *
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        try {
            // 对象输入输出流
            ObjectInputStream in = new ObjectInputStream(userSocket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(userSocket.getOutputStream());
            while (true) {
                // 接收一个对象流
                Object obj = in.readObject();
                // 获取处理结果
                Object result = switchCon(obj);
                // 返回给客户端这个处理结果
                out.writeObject(result);
                // 关闭所有流
                in.close();
                userSocket.close();
            }
        } catch (IOException | ClassNotFoundException e) {
//            System.out.println("VerifyConnection 与服务器失去联系 ：" + e.getMessage());
        }
    }
}
