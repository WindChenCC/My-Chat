package com.mychat.user;
import java.util.Vector;
import com.mychat.config.UserInfo;

/**
 * 用户信息对象
 */
public final class User extends UserInfo {


	private static final long serialVersionUID = -2844611810327524136L;

	/**
	 * 初始化用户信息内容
	 */
	public User(String userId, String userName, String userEmail, String userSex, String userBirthday,
			String userProfile, String userSignature, String userRegistertime, Vector<FriendsGroups> friends,
			Vector<FriendsGroups> groups) {
		this.userId = userId;
		this.userName = userName;
		this.userEmail = userEmail;
		this.userSex = userSex;
		this.userBirthday = userBirthday;
		this.userProfile = userProfile;
		this.userSignature = userSignature;
		this.userRegisterTime = userRegistertime;
		this.friends = friends;
		this.groups = groups;
	}

	/**
	 * 获取用户ID
	 * @return userId String对象
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * 获取用户昵称
	 * @return userName String对象
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 *
	 * @return userEmail String对象
	 */
	public String getUserEmail() {
		return userEmail;
	}

	/**
	 * 获取用户性别
	 * @return userSex String对象
	 */
	public String getUserSex() {
		return userSex;
	}

	/**
	 * 获取用户生日
	 * @return userBirthday String对象
	 */
	public String getUserBirthday() {
		return userBirthday;
	}

	/**
	 * 获取用户头像链接(url)
	 * @return userProfile String对象
	 */
	public String getUserProfile() {
		return userProfile;
	}

	/**
	 *  获取用户个性签名
	 * @return userSignature String对象
	 */
	public String getUserSignature() {
		return userSignature;
	}

	/**
	 * 获取用户注册时间
	 * @return userRegisterTime String对象
	 */
	public String getUserRegistertime() {
		return userRegisterTime;
	}

	/**
	 * 获取用户好友列表ID
	 * @return friends String对象
	 */
	public Vector<FriendsGroups> getFriends() {
		return friends;
	}

	/**
	 * 获取用户群列表ID
	 * @return groups String对象
	 */
	public Vector<FriendsGroups> getGroups() {
		return groups;
	}
}
