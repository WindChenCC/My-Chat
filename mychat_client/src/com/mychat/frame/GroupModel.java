/*
  存放GroupModel类
 */
package com.mychat.frame;

import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

/**
 * 用于主界面显示群的相关信息
 */
public class GroupModel extends JRadioButton {
    private static final long serialVersionUID = 1L;
    private String fAvatar;
    private String fName;
    private String fTrades;
    private final String fid;
    private final JLabel fAvatarLabel;
    private final JLabel fNameLabel;
    private final JLabel fTradesLabel;

    public GroupModel(String fAvatar, String fName, String fTrades, String fid, String avatarPath) {
        this.fAvatar = fAvatar;
        this.fName = fName;
        this.fTrades = fTrades;
        this.fid = fid;
        this.setMargin(new Insets(0, 0, 0, 0));
        this.setIcon(new ImageIcon("./resource/mainpanel/friend_normal.png"));
        this.setRolloverIcon(new ImageIcon("./resource/mainpanel/friend_hover.png"));
        this.setPressedIcon(new ImageIcon("./resource/mainpanel/friend_selected.png"));
        this.setSelectedIcon(new ImageIcon("./resource/mainpanel/friend_selected.png"));
        this.setLayout(null);
        fAvatarLabel = new JLabel();
        fAvatarLabel.setIcon(new ImageIcon((GetProfile.getAvatarImage(fid, avatarPath, fAvatar)).getImage()
                .getScaledInstance(41, 41, Image.SCALE_DEFAULT)));
        fAvatarLabel.setBounds(8, 4, 41, 41);
        this.add(fAvatarLabel);
        // 名字显示
        fNameLabel = new JLabel(fName);
        fNameLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 18));
        fNameLabel.setBounds(60, 14, 176, 22);
        this.add(fNameLabel);

        fTradesLabel = new JLabel(fTrades);
        fTradesLabel.setBounds(56, 56, 218, 19);
        fTradesLabel.setToolTipText(fTrades);
        this.add(fTradesLabel);
    }

    public String getfAvatar() {
        return fAvatar;
    }

    public String getfName() {
        return fName;
    }

    public String getfTrades() {
        return fTrades;
    }

    public String getFid() {
        return fid;
    }

    public void setfAvatar(String fAvatar) {
        this.fAvatar = fAvatar;
        fAvatarLabel.setIcon(new ImageIcon((GetProfile.getAvatarImage(fid, "./Data/Avatar/Group/", fAvatar)).getImage()
                .getScaledInstance(41, 41, Image.SCALE_DEFAULT)));
    }

    public void setfName(String fName) {
        this.fName = fName;
        fNameLabel.setText(fName);
    }

    public void setfTrades(String fTrades) {
        this.fTrades = fTrades;
        fTradesLabel.setText(fTrades);
    }
}
