package com.mychat.database;

import com.mychat.config.UserInfo;
import com.mychat.config.UserInfo.FriendsGroups;
import com.mychat.server.ChatServer;
import com.mychat.user.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 * 针对数据库的一些查询与更新函数，并对每种操作提供相应的SQL语句
 */
public final class DataQuery {
    /**
     * 通过ID与SQL获取最终结果集
     *
     * @param sql SQL语句
     * @param row 选择哪一个属性
     * @return Vector<String> 最终查询到的结果
     */
    private static Vector<String> getMemberFromId(String sql, String row) {
        // 与数据库创建连接
        DataBaseConnection dataCon = new DataBaseConnection();
        // 最终结果Vector数组
        Vector<String> member = new Vector<>();
        // 利用该sql语句查询，返回ResultSet结果集
        ResultSet resultSet = dataCon.getFromDatabase(sql);
        try {
            while (resultSet.next()) {
                member.add(resultSet.getString(row));
            }
            // 关闭连接
            dataCon.close();
        } catch (SQLException e) {
            System.out.println("查询成员ID列表失败：" + e.getMessage());
        }
        return member;
    }

    /**
     * 查询用户的所有好友ID
     *
     * @param myselfId 用户ID
     * @return Vector<String> 好友列表Vector数组
     */
    public static Vector<String> getFriendMember(String myselfId) {
        String sqlString = "select myfriend from dw_useruser where myself = " + myselfId;
        return getMemberFromId(sqlString, "myfriend");
    }

    /**
     * 查询群中所有成员的ID
     *
     * @param groupId 群ID
     * @return Vector<String> 返回群成员列表Vector数组
     */
    public static Vector<String> getGroupMemberId(String groupId) {
        String sqlString = "select user_id from dw_usergroup where group_id = " + groupId;
        return getMemberFromId(sqlString, "user_id");
    }

    /**
     * 查询群成员 Id name profile
     */
    public static Vector<FriendsGroups> getGroupMembers(Vector<FriendsGroups> groups, DataBaseConnection dataCon) {
        if (groups.isEmpty()) {
            return null;
        }
        Vector<FriendsGroups> members = new Vector<>();
        StringBuilder sqlString = new StringBuilder("select distinct user_id, user_name, user_profile " +
                "from view_usergroup where group_id =").append(groups.get(0).getId());
        for (int i = 1; i < groups.size(); ++i) {
            sqlString.append(" or group_id = ").append(groups.get(i).getId());
        }
        ResultSet resultSet = dataCon.getFromDatabase(sqlString.toString());
        try {
            while (resultSet.next()) {
                String memberId = resultSet.getString("user_id");
                String memberName = resultSet.getString("user_name");
                String memberProfile = resultSet.getString("user_profile");
                members.add(new FriendsGroups(memberId, memberName, memberProfile, null, null));
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("DataQuery 获取群成员失败 " + e.getMessage());
        }
        return members;
    }

    /**
     * 利用客户端发送来的用户名与MD5加密后的密码查询该用户是否存在于数据库
     *
     * @param userId       用户名
     * @param userPassword 加密后的密码
     * @return Boolean 是否存在该用户
     */
    public Boolean isLoginSuccess(String userId, String userPassword) {
        Boolean isSuccess = Boolean.FALSE;
        try {
            // 查询该用户是否存在
            DataBaseConnection dataCon = new DataBaseConnection();
            String sql = "select * from dw_user where user_id = " + userId + " and user_password = '" + userPassword
                    + "'";
            // 如果存在该用户，返回true
            isSuccess = dataCon.getFromDatabase(sql).next();
            // 关闭与服务器连接对象
            dataCon.close();
        } catch (SQLException e) {
            System.out.println("身份验证信息查询失败:" + e.getMessage());
        }
        return isSuccess;
    }

    /**
     * @param userId  用户ID
     * @param dataCon 与数据库连接对象
     * @return Vector<FriendsGroups> 返回最终获取的好友信息列表Vector数组
     */
    public Vector<FriendsGroups> getUserFriends(String userId, DataBaseConnection dataCon) {
        Vector<FriendsGroups> friends = new Vector<>();
        // 查询好友信息
        String sqlString = "select * from view_useruser where myself = " + userId;
        ResultSet resultSet = dataCon.getFromDatabase(sqlString);
        try {
            while (resultSet.next()) {
                String fId = resultSet.getString("myfriend");
                String fName = resultSet.getString("user_name");
                String fProfile = resultSet.getString("user_profile");
                String fSignature = resultSet.getString("user_signature");
                String fStatus = ChatServer.getClientUser().containsKey(fId) ? "在线" : "离线";
                friends.add(new FriendsGroups(fId, fName, fProfile, fSignature, fStatus));
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("获取好友信息失败 " + e.getMessage());
        }
        return friends;
    }

    /**
     * @param userId  用户ID
     * @param dataCon 与数据库连接对象
     * @return Vector<FriendsGroups> 返回最终获取的群信息列表Vector数组
     */
    public Vector<FriendsGroups> getUserGroups(String userId, DataBaseConnection dataCon) {
        Vector<FriendsGroups> groups = new Vector<>();
        // 查询群信息
        String sqlString = "select * from view_usergroup where user_id = " + userId;
        ResultSet resultSet = dataCon.getFromDatabase(sqlString);
        try {
            while (resultSet.next()) {
                String gId = resultSet.getString("group_id");
                String gName = resultSet.getString("group_name");
                String gSignature = resultSet.getString("group_signature");
                String gProfile = resultSet.getString("group_profile");
                String gStatus = resultSet.getString("user_id");
                groups.add(new FriendsGroups(gId, gName, gProfile, gSignature, gStatus));
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("DataQuery 获取好友群失败 " + e.getMessage());
        }
        return groups;
    }

    /**
     * 获取用户信息(包括个人资料，群列表及资料，好友列表及资料)
     *
     * @param userId 需要获取的用户ID
     * @return UserInfo 返回UserInfo用户信息对象
     */
    public UserInfo getUserInfo(String userId) {
        // 用户个人信息
        String userName = "";
        String userSex = "";
        String userBirthday = "";
        String userProfile = "";
        String userSignature = "";
        String userRegistertime = "";
        Vector<FriendsGroups> friends;
        Vector<FriendsGroups> groups;
        Vector<FriendsGroups> groupMembers;
        UserInfo userInfo = null;
        try {
            // 创建数据库连接
            DataBaseConnection dataCon = new DataBaseConnection();
            // 查询个人信息
            String sqlString = "select * from dw_user where user_id = " + userId;
            ResultSet resultSet = dataCon.getFromDatabase(sqlString);
            while (resultSet.next()) {
                userName = resultSet.getString("user_name");
                userSex = resultSet.getString("user_sex");
                userBirthday = resultSet.getString("user_birthday");
                userProfile = resultSet.getString("user_Profile");
                userSignature = resultSet.getString("user_signature");
                userRegistertime = resultSet.getString("user_registertime");
            }
            resultSet.close();
            // 查询好友列表信息与群列表信息
            friends = getUserFriends(userId, dataCon);
            groups = getUserGroups(userId, dataCon);
            groupMembers = getGroupMembers(groups, dataCon);
            // 关闭数据库连接
            dataCon.close();
            // 创建对象
            userInfo = new User(userId, userName, userSex, userBirthday, userProfile, userSignature,
                    userRegistertime, friends, groups, groupMembers);
        } catch (SQLException e) {
            System.out.println("获取用户信息失败：" + e.getMessage());
        }
        return userInfo;
    }

    /**
     * 获取聊天记录
     *
     * @param fromId  交互方1
     * @param toId    交互方2
     * @param isGroup 是否获取群聊天记录
     * @return Vector<String> 返回聊天记录结果Vector数组
     */
    public Vector<String> getChatRecord(String fromId, String toId, String isGroup) {
        Vector<String> all = new Vector<>();
        // 创建数据库连接对象
        DataBaseConnection dataCon = new DataBaseConnection();
        String sqlString;
        if (isGroup.equals("true")) {
            sqlString = "select gchat_uid fromid,gchat_gid toid,gchat_message message,gchat_datetime timer " +
                    "from dw_groupchat where gchat_gid = " + toId;
        } else {
            sqlString = "select uchat_fromid fromid,uchat_toid toid,uchat_message message,uchat_datetime timer " +
                    "from dw_userchat where (uchat_fromid ="
                    + fromId + " and uchat_toid = " + toId + ") or (uchat_fromid = " + toId + " and uchat_toid = "
                    + fromId + ")";
        }
        ResultSet resultSet = dataCon.getFromDatabase(sqlString);
        try {
            String tmp;
            while (resultSet.next()) {
                tmp = resultSet.getString("timer") + "```";
                tmp += resultSet.getString("fromid") + "```";
                tmp += resultSet.getString("toid") + "```";
                tmp += resultSet.getString("message");
                all.add(tmp);
            }
            // 关闭连接
            dataCon.close();
        } catch (SQLException e) {
            System.out.println("获取聊天记录信息失败：" + e.getMessage());
        }
        return all;
    }
}
