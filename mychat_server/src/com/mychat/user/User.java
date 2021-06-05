/*
  用户信息对象
 */

package com.mychat.user;

import com.mychat.config.UserInfo;

import java.util.Vector;

public final class User extends UserInfo {

    private static final long serialVersionUID = -2844611810327524136L;

    /**
     * 初始化用户信息内容
     */
    public User(String userId, String userName, String userSex, String userBirthday,
                String userProfile, String userSignature, String userRegisterTime, Vector<FriendsGroups> friends,
                Vector<FriendsGroups> groups) {
        this.userId = userId;
        this.userName = userName;

        this.userEmail = userId + "@mychat.com";
        this.userSex = userSex;
        this.userBirthday = userBirthday;
        this.userProfile = userProfile;
        this.userSignature = userSignature;
        this.userRegisterTime = userRegisterTime;
        this.friends = friends;
        this.groups = groups;
    }

    /**
     * 获取用户ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 获取用户昵称
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 获取用户Email
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * 获取用户性别
     */
    public String getUserSex() {
        return userSex;
    }

    /**
     * 获取用户生日
     */
    public String getUserBirthday() {
        return userBirthday;
    }

    /**
     * 获取用户头像链接(url)
     */
    public String getUserProfile() {
        return userProfile;
    }

    /**
     * 获取用户个性签名
     */
    public String getUserSignature() {
        return userSignature;
    }

    /**
     * 获取用户注册时间
     */
    public String getUserRegisterTime() {
        return userRegisterTime;
    }

    /**
     * 获取用户好友列表ID
     */
    public Vector<FriendsGroups> getFriends() {
        return friends;
    }

    /**
     * 获取用户群列表ID
     */
    public Vector<FriendsGroups> getGroups() {
        return groups;
    }
}
