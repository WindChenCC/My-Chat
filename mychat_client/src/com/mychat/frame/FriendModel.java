/*
  存放GroupModel类
 */
package com.mychat.frame;

import javax.swing.JLabel;
import java.awt.*;

/**
 * 继承自GroupModel 用于主界面显示好友模块
 */
public class FriendModel extends GroupModel {
    private String fOnline;
    private final JLabel fOnlineLabel;
    private static final long serialVersionUID = 1L;

    public FriendModel(String fAvatar, String fName, String fTrades, String fid, String fOnline) {
        super(fAvatar, fName, fTrades, fid, "./Data/Avatar/User/");
        this.fOnline = fOnline;
        fOnlineLabel = new JLabel(fOnline);
        fOnlineLabel.setBounds(232, 14, 42, 22);
        fOnlineLabel.setFont(new Font("微软雅黑", Font.BOLD, 14));
        this.add(fOnlineLabel);
    }

    public String getOnline() {
        return fOnline;
    }

    public String getfOnline() {
        return fOnline;
    }

    public void setfOnline(String fOnline) {
        this.fOnline = fOnline;
        fOnlineLabel.setText(fOnline);
    }
}