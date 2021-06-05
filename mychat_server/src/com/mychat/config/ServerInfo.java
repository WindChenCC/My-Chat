package com.mychat.config;

/**
 * 服务器相关信息
 *
 * @author wind chen
 */
public final class ServerInfo {
    /**
     * 聊天端口监听：{@value}
     */
    public final static int CHAT_PORT = 10001;

    /**
     * 验证端口监听：{@value}
     */
    public final static int VERIFY_PORT = 10002;

    /**
     * Mysql 服务器地址：{@value}
     */
    public final static String MYSQL_IP = "localhost";

    /**
     * Mysql 端口号：{@value}
     */
    public final static int MYSQL_PORT = 3306;

    /**
     * Mysql 数据库名称：{@value}
     */
    public final static String DB_NAME = "oicq";

    /**
     * Mysql 数据库连接用户名：{@value}
     */
    public final static String DB_USER_NAME = "root";

    /**
     * Mysql 数据库连接密码：{@value}
     */
    public final static String DB_USER_PASSWORD = "cchcch";
}
