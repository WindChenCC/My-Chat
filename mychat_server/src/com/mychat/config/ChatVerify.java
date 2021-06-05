package com.mychat.config;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 用作登录验证所要发送的对象
 */
public final class ChatVerify implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -4490443980607193791L;

    /**
     * 用户名
     */
    private final String userId;
    /**
     * 密码
     */
    private final String userPassword;

    /**
     * 创建一个存储用户ID、密码经过MD5加密后的对象
     *
     * @param userId       用户ID
     * @param userPassword 原密码
     */
    public ChatVerify(String userId, String userPassword) {
        this.userId = userId;
        this.userPassword = getMd5(userPassword);
    }

    /**
     * 返回用户ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 返回MD5加密后的密码
     *
     * @return String 加密后的密码
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * 对一个字符串进行MD5加密并返回加密后的串
     *
     * @return String 加密后的密码
     */
    private String getMd5(String str) {
        String mdPassword = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // 保留16位
            mdPassword = new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("MD5加密失败：" + e.getMessage());
        }
        return mdPassword;
    }
}