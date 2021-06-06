package com.mychat.frame;

import com.mychat.client.InteractWithServer;
import com.mychat.config.ColorInfo;
import com.mychat.config.UserInfo;
import com.mychat.tool.ColorIcon;
import com.mychat.user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

/**
 * 用于显示主界面 用户的相关信息和好友等
 */
public final class MainInterface extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel upPanel, midPanel, menuPanel;
    private JPanel friendPanel, groupPanel;
    private JButton minimize, close;
    private JRadioButton friendButton, groupButton;
    private JButton signatureButton, friendButtonLeft, groupButtonLeft;
    private JButton searchButton;
    private JButton menuButton;
    private JLabel nameLabel;
    private JLabel searchLabel;
    private JLabel textLogoLabel;
    private JButton headPortrait;
    private Box nameBox;
    private JTextField signatureTextField;
    private JTextField searchTextField;
    private ButtonGroup peopleOrGroup;
    private final User userInfo;
    private JScrollPane friendScrollPane;
    private ButtonGroup friendButtonGroup, groupButtonGroup;
    // 用户信息部分
    private static final HashMap<String, FriendModel> friend;
    private static final HashMap<String, GroupModel> group;
    private static final HashMap<String, ChatWithFriend> withFriend;
    private static final HashMap<String, ChatWithFriend> withGroup;
    private static final HashMap<String, UserInfo.FriendsGroups> groupMembers;

    static {
        friend = new HashMap<>();
        group = new HashMap<>();
        withFriend = new HashMap<>();
        withGroup = new HashMap<>();
        groupMembers = new HashMap<>();
    }

    public MainInterface(String userId) {
        // 获取用户信息
        userInfo = InteractWithServer.getUserInfo(userId);
        for (UserInfo.FriendsGroups i : userInfo.getGroupMembers()) {
            groupMembers.put(i.getId(), i);
        }
        // 调试使用
        System.out.println("************** 个人信息 **************");
        System.out.println("ID：" + userInfo.getUserId());
        System.out.println("昵称：" + userInfo.getUserName());
        System.out.println("Email：" + userInfo.getUserEmail());
        System.out.println("性别：" + userInfo.getUserSex());
        System.out.println("生日：" + userInfo.getUserBirthday());
        System.out.println("个性签名：" + userInfo.getUserSignature());
        System.out.println("注册时间：" + userInfo.getUserRegistertime());
        System.out.println("*************************************");
        // 界面部分
        // 更改显示的小图标
        setIconImage(Toolkit.getDefaultToolkit().createImage("./resource/mainpanel/mychat_logo.png"));
        setTitle("MyChat " + userInfo.getUserName() + " 在线");
        init();
        setLayout(null);
        upPanel.add(close);
        upPanel.add(minimize);
        upPanel.add(headPortrait);
        upPanel.add(nameBox);
        upPanel.add(signatureButton);
        upPanel.add(signatureTextField);

        midPanel.add(searchLabel);
        midPanel.add(searchButton);
        midPanel.add(searchTextField);
        midPanel.add(friendButtonLeft);
        midPanel.add(friendButton);
        midPanel.add(groupButton);
        midPanel.add(groupButtonLeft);
        midPanel.add(friendScrollPane);

        add(upPanel);
        add(midPanel);
        add(menuPanel);
        LoginMousemove adapter = new LoginMousemove();
        addMouseMotionListener(adapter);
        addMouseListener(adapter);
        setSize(300, 640);
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void init() {
        // 上方个人信息面板，包括最小化 关闭按钮，个人头像、昵称、个性签名等
        upPanel = new JPanel();
        upPanel.setLayout(null);
        upPanel.setBounds(0, 0, 300, 115);
        upPanel.setBackground(ColorInfo.MENU_COLOR);
        // 文字Logo
        textLogoLabel = new JLabel();
        textLogoLabel.setIcon(new ImageIcon("./resource/mainpanel/mychat_textLogo.png"));
        textLogoLabel.setBounds(5, 5, 48, 12);
        upPanel.add(textLogoLabel);
        // 关闭按钮
        close = new JButton("");
        close.setMargin(new Insets(0, 0, 0, 0));
        close.setBounds(260, 0, 40, 40);
        close.setContentAreaFilled(false);
        close.setBorderPainted(false);
        close.setFocusPainted(false);
        close.setToolTipText("关闭");
        // 平时效果
        close.setIcon(new ImageIcon("./resource/button/closebutton_normal.png"));
        // 鼠标移上去效果
        close.setRolloverIcon(new ImageIcon("./resource/button/closebutton_hover.png"));
        // 按压效果
        close.setPressedIcon(new ImageIcon("./resource/button/closebutton_down.png"));
        ExitListener closeListener = new ExitListener();
        close.addActionListener(closeListener);
        // 最小化按钮
        minimize = new JButton();
        minimize.setMargin(new Insets(0, 0, 0, 0));
        minimize.setBounds(220, 0, 40, 40);
        minimize.setContentAreaFilled(false);
        minimize.setBorderPainted(false);
        minimize.setFocusPainted(false);
        minimize.setToolTipText("最小化");
        minimize.setIcon(new ImageIcon("./resource/button/minbutton_normal.png"));
        minimize.setRolloverIcon(new ImageIcon("./resource/button/minbutton_hover.png"));
        minimize.setPressedIcon(new ImageIcon("./resource/button/minbutton_down.png"));
        minimize.addActionListener(e -> setExtendedState(JFrame.ICONIFIED));

        // 用按钮显示头像
        headPortrait = new JButton();
        headPortrait.setBounds(15, 35, 65, 65);
        headPortrait.setVisible(true);
        Image userProfile = (GetProfile.getProfileImage(userInfo.getUserId(), "./Data/Profile/User/",
                userInfo.getUserProfile())).getImage().getScaledInstance(65, 65, Image.SCALE_DEFAULT);
        headPortrait.setIcon(new ImageIcon(userProfile));

        nameBox = Box.createHorizontalBox();
        nameBox.setBounds(90, 35, 158, 25);
        // 用户昵称
        String username = userInfo.getUserName();
        nameLabel = new JLabel(username);
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
        nameBox.add(nameLabel);

        // 个性签名
        signatureButton = new JButton();
        signatureButton.setHorizontalAlignment(SwingConstants.LEFT);
        signatureButton.setMargin(new Insets(0, 0, 0, 0));
        signatureButton.setBounds(88, 65, 155, 23);
        signatureButton.setContentAreaFilled(false);
        signatureButton.setBorderPainted(false);
        String signature = userInfo.getUserSignature();

        if (signature.equals("")) {
            signature = "编辑个性签名";
        }
        signatureButton.setText(signature);
        signatureButton.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        signatureButton.setForeground(Color.WHITE);
        signatureButton.setToolTipText(signature);
        // 点击签名之后的编辑文本框
        signatureTextField = new JTextField();
        signatureTextField.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        signatureTextField.setBounds(88, 65, 155, 23);
        signatureTextField.setVisible(false);
        // 点击个性签名之后变为可编辑的TextField
        signatureButton.addActionListener(e -> {
            signatureButton.setVisible(false);
            signatureTextField.setVisible(true);
            if (signatureButton.getText().equals("编辑个性签名")) {
                signatureTextField.setText("");
            } else {
                signatureTextField.setText(signatureButton.getText());
            }
            signatureTextField.requestFocus();
        });

        // TextField失去焦点之后变为Button并将更改后的内容传送给服务器
        signatureTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusLost(FocusEvent e) {
                signatureTextField.setVisible(false);
                if (signatureTextField.getText().equals("")) {
                    signatureButton.setText("编辑个性签名");
                    InteractWithServer.setMySignature(userInfo.getUserId(), "");
                } else {
                    signatureButton.setText(signatureTextField.getText());
                    // 更新服务端数据
                    InteractWithServer.setMySignature(userInfo.getUserId(), signatureTextField.getText());
                }
                signatureButton.setVisible(true);
            }

            @Override
            public void focusGained(FocusEvent e) {
            }
        });
        signatureTextField.addActionListener(e -> {
            signatureTextField.setVisible(false);
            if (signatureTextField.getText().equals("")) {
                signatureButton.setText("编辑个性签名");
            } else {
                signatureButton.setText(signatureTextField.getText());
            }
            signatureButton.setVisible(true);
        });


        // 中间面板
        midPanel = new JPanel();
        midPanel.setLayout(null);
        midPanel.setBounds(0, 115, 300, 485);
        midPanel.setBackground(ColorInfo.SEARCH_COLOR);
        // 搜索框
        searchLabel = new JLabel();
        searchLabel.setForeground(ColorInfo.SEARCH_COLOR);
        searchLabel.setIcon(new ImageIcon("./resource/mainpanel/icon_search.png"));
        searchLabel.setBounds(0, 0, 26, 26);

        searchButton = new JButton("搜索");
        searchButton.setHorizontalAlignment(SwingConstants.LEFT);
        searchButton.setFocusPainted(false);
        searchButton.setBorderPainted(false);
        searchButton.setContentAreaFilled(false);
        searchButton.setBounds(23, 0, 277, 26);
        searchButton.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        searchButton.setHorizontalTextPosition(SwingConstants.CENTER);
        searchButton.setForeground(new Color(186, 235, 242));

        searchTextField = new JTextField();
        searchTextField.setBounds(26, 1, 277, 25);
        searchTextField.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        searchTextField.setForeground(Color.BLACK);
        searchTextField.setVisible(false);
        searchTextField.setEditable(true);
        searchTextField.setBorder(null);
        searchButton.addActionListener(e -> {
            searchButton.setVisible(false);
            searchTextField.setVisible(true);
            searchTextField.requestFocus();
        });

        searchTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusLost(FocusEvent e) {
                searchTextField.setVisible(false);
                searchTextField.setText("");
                searchButton.setVisible(true);
            }

            @Override
            public void focusGained(FocusEvent e) {
            }
        });
        searchTextField.addActionListener(e -> {
            searchTextField.setVisible(false);
            searchButton.setVisible(true);
        });

        // 好友和群聊 按钮
        friendButtonLeft = new JButton();
        friendButtonLeft.setBounds(0, 26, 51, 36);
        friendButtonLeft.setIcon(new ColorIcon(ColorInfo.NORMAL_COLOR, 51, 36));
        friendButtonLeft.setContentAreaFilled(false);
        friendButtonLeft.setFocusPainted(false);
        friendButtonLeft.setBorderPainted(false);
        friendButtonLeft.addActionListener(arg0 -> {
            friendButton.setSelected(true);
            friendButton.requestFocus();
        });
        friendButton = new JRadioButton();
        friendButton.setBounds(51, 26, 99, 36);
        friendButton.setBackground(ColorInfo.NORMAL_COLOR);
        friendButton.setHorizontalTextPosition(SwingConstants.CENTER);
        friendButton.setIcon(new ImageIcon("./resource/mainpanel/icon_contacts_normal.png"));
        friendButton.setRolloverIcon(new ImageIcon("./resource/mainpanel/icon_contacts_hover.png"));
        friendButton.setSelectedIcon(new ImageIcon("./resource/mainpanel/icon_contacts_selected.png"));
        friendButton.setSelected(true);
        friendButton.addFocusListener(new FocusListener() {
            @Override
            public void focusLost(FocusEvent arg0) {
            }

            @Override
            public void focusGained(FocusEvent arg0) {
                friendScrollPane.setViewportView(friendPanel);
            }
        });
        groupButtonLeft = new JButton();
        groupButtonLeft.setBounds(150, 26, 51, 36);
        groupButtonLeft.setIcon(new ColorIcon(ColorInfo.NORMAL_COLOR, 51, 36));
        groupButtonLeft.setContentAreaFilled(false);
        groupButtonLeft.setFocusPainted(false);
        groupButtonLeft.setBorderPainted(false);
        groupButtonLeft.addActionListener(arg0 -> {
            groupButton.setSelected(true);
            groupButton.requestFocus();
        });
        groupButton = new JRadioButton();
        groupButton.setBounds(201, 26, 99, 36);
        groupButton.setBackground(ColorInfo.NORMAL_COLOR);
        groupButton.setIcon(new ImageIcon("./resource/mainpanel/icon_group_normal.png"));
        groupButton.setRolloverIcon(new ImageIcon("./resource/mainpanel/icon_group_hover.png"));
        groupButton.setSelectedIcon(new ImageIcon("./resource/mainpanel/icon_group_selected.png"));
        groupButton.addFocusListener(new FocusListener() {
            @Override
            public void focusLost(FocusEvent e) {
            }

            @Override
            public void focusGained(FocusEvent e) {
                friendScrollPane.setViewportView(groupPanel);
            }
        });
        peopleOrGroup = new ButtonGroup();
        peopleOrGroup.add(friendButton);
        peopleOrGroup.add(groupButton);

        // 好友列表
        int friendsNumber = userInfo.getFriends().size();
        friendPanel = new JPanel();
        friendPanel.setLayout(null);
        friendPanel.setBounds(0, 0, 300, friendsNumber * 51);
        friendPanel.setPreferredSize(new Dimension(282, friendsNumber * 51));
        friendPanel.setBackground(ColorInfo.NORMAL_COLOR);
        friendButtonGroup = new ButtonGroup();

        for (int i = 0; i < friendsNumber; i++) {
            UserInfo.FriendsGroups userFriend = userInfo.getFriends().get(i);
            String fProfile = userFriend.getProfile();
            String fName = userFriend.getName();
            String fSignature = userFriend.getSignature();
            String fid = userFriend.getId();
            String fOnline = userFriend.getStatus();
            friend.put(fid, new FriendModel(fProfile, fName, fSignature, fid, fOnline));
            friend.get(fid).setBounds(0, i * 51, 300, 51);
            friend.get(fid).addMouseListener(new MouseListener() {
                @Override
                public void mouseReleased(MouseEvent e) {
                }

                @Override
                public void mousePressed(MouseEvent e) {
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        withFriend.put(fid, new ChatWithFriend(userInfo.getUserId(), userInfo.getUserName(), fid,
                                fProfile, fName, fSignature, false));
                    }
                }
            });
            friendPanel.add(friend.get(fid));
            friendButtonGroup.add(friend.get(fid));
        }
        // 群组列表
        int groupsNumber = userInfo.getGroups().size();
        groupPanel = new JPanel();
        groupPanel.setLayout(null);
        groupPanel.setBounds(0, 0, 300, groupsNumber * 51);
        groupPanel.setPreferredSize(new Dimension(282, groupsNumber * 51));
        groupPanel.setBackground(ColorInfo.NORMAL_COLOR);
        groupButtonGroup = new ButtonGroup();
        for (int j = 0; j < groupsNumber; j++) {
            UserInfo.FriendsGroups userGroup = userInfo.getGroups().get(j);
            String gProfile = userGroup.getProfile(), gName = userGroup.getName(), gSignature = userGroup.getSignature(),
                    gid = userGroup.getId();
            group.put(gid, new GroupModel(gProfile, gName, gSignature, gid, "./Data/Profile/Group/"));
            group.get(gid).setBounds(0, j * 51, 300, 51);
            group.get(gid).addMouseListener(new MouseListener() {
                @Override
                public void mouseReleased(MouseEvent e) {
                }

                @Override
                public void mousePressed(MouseEvent e) {
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        withGroup.put(gid, new ChatWithFriend(userInfo.getUserId(), userInfo.getUserName(), gid,
                                gProfile, gName, gSignature, true));
                    }
                }
            });
            groupPanel.add(group.get(gid));
            groupPanel.setVisible(true);
            groupButtonGroup.add(group.get(gid));
        }
        friendScrollPane = new JScrollPane(friendPanel);
        // 设置滚动条样式
        friendScrollPane.getVerticalScrollBar().setUI(new MyScrollBarUI());
        // 设置框线颜色
        friendScrollPane.setBorder(BorderFactory.createLineBorder(ColorInfo.BORDER_COLOR, 1));
        // 设置滚动速率
        friendScrollPane.getVerticalScrollBar().setUnitIncrement(20);
        // -1 和 303 为了不显示左右框线
        friendScrollPane.setBounds(-1, 62, 303, 423);

        // 下方菜单面板
        menuPanel = new JPanel();
        menuPanel.setBorder(null);
        menuPanel.setLayout(null);
        menuPanel.setBounds(0, 600, 300, 40);
        menuPanel.setBackground(ColorInfo.NORMAL_COLOR);
        menuButton = new JButton();
        menuButton.setBorderPainted(false);
        menuButton.setFocusPainted(false);
        menuButton.setIcon(new ImageIcon("./resource/mainpanel/menu_normal.png"));
        menuButton.setRolloverIcon(new ImageIcon("./resource/mainpanel/menu_hover.png"));
        menuButton.setPressedIcon(new ImageIcon("./resource/mainpanel/menu_down.png"));
        menuButton.setBounds(5, 0, 30, 40);
        menuPanel.add(menuButton);
    }

    public static HashMap<String, FriendModel> getFriend() {
        return friend;
    }

    public static HashMap<String, GroupModel> getGroup() {
        return group;
    }

    public static HashMap<String, ChatWithFriend> getFriendChat() {
        return withFriend;
    }

    public static HashMap<String, ChatWithFriend> getGroupChat() {
        return withGroup;
    }

    public static HashMap<String, UserInfo.FriendsGroups> getGroupMembers() {
        return groupMembers;
    }

}