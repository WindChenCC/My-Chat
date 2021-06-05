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
    private String fProfile;
    private String fName;
    private String fSignature;
    private final String fid;
    private final JLabel fProfileLabel;
    private final JLabel fNameLabel;
    private final JLabel fSignatureLabel;

    public GroupModel(String fProfile, String fName, String fSignature, String fid, String profilePath) {
        this.fProfile = fProfile;
        this.fName = fName;
        this.fSignature = fSignature;
        this.fid = fid;
        this.setMargin(new Insets(0, 0, 0, 0));
        this.setIcon(new ImageIcon("./resource/mainpanel/friend_normal.png"));
        this.setRolloverIcon(new ImageIcon("./resource/mainpanel/friend_hover.png"));
        this.setPressedIcon(new ImageIcon("./resource/mainpanel/friend_selected.png"));
        this.setSelectedIcon(new ImageIcon("./resource/mainpanel/friend_selected.png"));
        this.setLayout(null);
        fProfileLabel = new JLabel();
        fProfileLabel.setIcon(new ImageIcon((GetProfile.getProfileImage(fid, profilePath, fProfile)).getImage()
                .getScaledInstance(41, 41, Image.SCALE_DEFAULT)));
        fProfileLabel.setBounds(8, 4, 41, 41);
        this.add(fProfileLabel);
        // 名字显示
        fNameLabel = new JLabel(fName);
        fNameLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 18));
        fNameLabel.setBounds(60, 14, 176, 22);
        this.add(fNameLabel);

        fSignatureLabel = new JLabel(fSignature);
        fSignatureLabel.setBounds(56, 56, 218, 19);
        fSignatureLabel.setToolTipText(fSignature);
        this.add(fSignatureLabel);
    }

    public String getfProfile() {
        return fProfile;
    }

    public String getfName() {
        return fName;
    }

    public String getfSignature() {
        return fSignature;
    }

    public String getFid() {
        return fid;
    }

    public void setfProfile(String fProfile) {
        this.fProfile = fProfile;
        fProfileLabel.setIcon(new ImageIcon((GetProfile.getProfileImage(fid, "./Data/Profile/Group/",
                fProfile)).getImage().getScaledInstance(41, 41, Image.SCALE_DEFAULT)));
    }

    public void setfName(String fName) {
        this.fName = fName;
        fNameLabel.setText(fName);
    }

    public void setfSignature(String fSignature) {
        this.fSignature = fSignature;
        fSignatureLabel.setText(fSignature);
    }
}
