package com.mychat.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mychat.config.ServerInfo;

import java.sql.PreparedStatement;

/**
 * 创建数据库连接，支持通过sql查询数据库内容或更新
 */
public final class DataBaseConnection {
    /**
     * 数据库连接对象
     */
    private Connection conn = null;
    private PreparedStatement psql = null;
    /**
     * 最终结果集
     */
    private ResultSet resultSet = null;

    /**
     * 与数据库Mysql建立联系，设置默认连接编码为UTF-8
     * 获取连接对象失败抛出该异常,ClassNotFoundException
     * 加载驱动失败抛出该异常.
     */
    public DataBaseConnection() {
        // 数据库驱动名
        String dbDriver = "com.mysql.jdbc.Driver";
        // 数据库所在域
        String dbUrl = "jdbc:mysql://" + ServerInfo.MYSQL_IP + ":" + ServerInfo.MYSQL_PORT + "/" + ServerInfo.DB_NAME
                + "?useUnicode=true&characterEncoding=UTF-8" + "&useSSL=false";
        try {
            // 加载驱动
            Class.forName(dbDriver);
            // 获取连接对象
            conn = DriverManager.getConnection(dbUrl, ServerInfo.DB_USER_NAME, ServerInfo.DB_USER_PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("无法连接到数据库：" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new DataBaseConnection();
    }

    /**
     * 利用当前的数据连接查询，通过一条SQL语句
     * 查询失败或参数化sql失败抛出该异常
     *
     * @return ResultSet 查询结果集
     */
    public ResultSet getFromDatabase(String sql) {
        try {
            // 创建对象参数化 sql
            psql = conn.prepareStatement(sql);
            // 开始查询
            resultSet = psql.executeQuery();
        } catch (SQLException e) {
            System.out.println("数据库查询失败：" + e.getMessage());
        }
        return resultSet;
    }

    /**
     * 通过SQL语句更新数据库
     * 执行update失败抛出该异常
     */
    public void putToDatabase(String sql) {
        try {
            psql = conn.prepareStatement(sql);
            psql.executeUpdate();
        } catch (SQLException e) {
            System.out.println("更新数据库异常：" + e.getMessage());
        }
    }

    /**
     * 关闭所有打开的连接
     * 关闭失败抛出该异常
     */
    public void close() {
        try {
            if (resultSet != null && !resultSet.isClosed()) {
                resultSet.close();
            }
        } catch (SQLException e) {
            System.out.println("结果集关闭异常：" + e.getMessage());
        }
        try {
            if (psql != null && !psql.isClosed()) {
                psql.close();
            }
        } catch (SQLException e) {
            System.out.println("更新集关闭异常：" + e.getMessage());
        }
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("数据库连接关闭异常：" + e.getMessage());
        }
    }
}
