package com.mychat.config;

import java.io.Serializable;
import java.util.Vector;

/**
 * 为服务端与客户端之间传输用户个人信息以及好友列表、群列表而创建的可序列化类
 */
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 4146085358128616967L;
    /**
     * 用户 ID
     */
    protected String userId;
    /**
     * 用户昵称
     */
    protected String userName;
    /**
     * 用户E-mail
     */
    protected String userEmail;
    /**
     * 用户性别
     */
    protected String userSex;
    /**
     * 用户生日
     */
    protected String userBirthday;
    /**
     * 用户头像
     */
    protected String userProfile;
    /**
     * 用户个性签名
     */
    protected String userSignature;
    /**
     * 用户注册时间
     */
    protected String userRegisterTime;
    /**
     * 用户好友列表
     */
    protected Vector<FriendsOrGroups> friends = new Vector<>();
    /**
     * 用户群列表
     */
    protected Vector<FriendsOrGroups> groups = new Vector<>();

    /**
     * 对群或者一个好友所记录的信息是一个FriendsOrGroups对象，对象中包含它的id,name,avatar,signature,status.
     */
    public static class FriendsOrGroups implements Serializable {
        private static final long serialVersionUID = -1855195980029629286L;
        private final String id;
        private final String name;
        private final String avatar;
        private final String signature;
        private final String status;

        /**
         * 创建一个群信息对象或好友信息对象
         */
        public FriendsOrGroups(String id, String name, String avatar, String signature, String status) {
            this.id = id;
            this.name = name;
            this.avatar = avatar;
            this.signature = signature;
            this.status = status;
        }

        /**
         * 获取好友/群id
         *
         * @return id String对象
         */
        public String getId() {
            return id;
        }

        /**
         * 获取好友/群Name
         *
         * @return name String对象
         */
        public String getName() {
            return name;
        }

        /**
         * 获取好友/群头像链接(url)
         *
         * @return avatar String对象
         */
        public String getAvatar() {
            return avatar;
        }

        /**
         * 获取好友/群个性签名
         *
         * @return signature String对象
         */
        public String getTrades() {
            return signature;
        }

        /**
         * 获取好友状态(在线/离线)
         *
         * @return status String对象
         */
        public String getStatus() {
            return status;
        }
    }
}
