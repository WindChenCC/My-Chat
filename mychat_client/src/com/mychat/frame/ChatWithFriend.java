package com.mychat.frame;

import com.mychat.client.InteractWithServer;
import com.mychat.config.ColorInfo;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/**
 * 显示聊天窗口
 */
public final class ChatWithFriend extends JFrame {
    private static final long serialVersionUID = 1L;
    private JButton minimize, close, maximize;
    private JButton closeButton, sendButton;
    private JButton emotionButton, shakeButton;
    private LoginMousemove adapter;
    private JPanel upPanel, inputPanel, functionPanel;
    private JPanel groupMemberPanel;
    private JLabel friendName, friendSignature;
    private final String friendNameString;
    private final String friendSignatureString;
    private final String fid;
    private final String mid;
    private final String mName;
    private Box showBox;
    private Box groupMemberBox;
    private JScrollPane showPanel, inputScroll, groupMemberScrollPanel;
    private JTextArea inputText;
    private final Image profilePic;
    private int messageNum = 0;
    private final boolean isGroup;

    public ChatWithFriend(String mid, String mName, String fid, String friendProfileString, String friendNameString,
                          String friendSignatureString, boolean isGroup) {
        this.mid = mid;
        this.mName = mName;
        this.fid = fid;
        this.friendNameString = friendNameString;
        this.friendSignatureString = friendSignatureString;
        this.isGroup = isGroup;

        setTitle(friendNameString);
        setLayout(null);
        // 更改显示的小图标
        profilePic = (GetProfile.getProfileImage(fid, isGroup ? "./Data/Profile/Group/" : "./Data/Profile/User/",
                friendProfileString)).getImage().getScaledInstance(41, 41, Image.SCALE_DEFAULT);
        setIconImage(profilePic);
        setSize(800, 620);
        init();
        this.add(close);
        this.add(maximize);
        this.add(minimize);
        this.add(upPanel);
        this.add(showPanel);
        this.add(inputPanel);
        this.add(functionPanel);
        if (isGroup) {
            add(groupMemberScrollPanel);
        }
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void init() {
        // 关闭按钮
        close = new JButton("");
        close.setMargin(new Insets(0, 0, 0, 0));
        close.setBounds(760, 0, 40, 40);
        close.setContentAreaFilled(false);
        close.setBorderPainted(false);
        close.setFocusPainted(false);
        close.setToolTipText("关闭");
        close.setIcon(new ImageIcon("./resource/button/closebutton_normal.png"));
        close.setRolloverIcon(new ImageIcon("./resource/button/closebutton_hover.png"));
        close.setPressedIcon(new ImageIcon("./resource/button/closebutton_down.png"));
        close.addActionListener(new ExitNowFrameListener(this));

        // 最大化按钮，界面最大化
        maximize = new JButton();
        maximize.setMargin(new Insets(0, 0, 0, 0));
        maximize.setBounds(720, 0, 40, 40);
        maximize.setContentAreaFilled(false);
        maximize.setFocusPainted(false);
        maximize.setBorderPainted(false);
        maximize.setToolTipText("最大化");
        maximize.setIcon(new ImageIcon("./resource/button/maxbutton_normal.png"));
        maximize.setRolloverIcon(new ImageIcon("./resource/button/maxbutton_hover.png"));
        maximize.setPressedIcon(new ImageIcon("./resource/button/maxbutton_down.png"));

        // 最小化按钮
        minimize = new JButton();
        minimize.setMargin(new Insets(0, 0, 0, 0));
        minimize.setBounds(680, 0, 40, 40);
        minimize.setContentAreaFilled(false);
        minimize.setBorderPainted(false);
        minimize.setFocusPainted(false);
        minimize.setToolTipText("最小化");
        minimize.setIcon(new ImageIcon("./resource/button/minbutton_normal.png"));
        minimize.setRolloverIcon(new ImageIcon("./resource/button/minbutton_hover.png"));
        minimize.setPressedIcon(new ImageIcon("./resource/button/minbutton_down.png"));
        minimize.addActionListener(e -> setExtendedState(JFrame.ICONIFIED));

        upPanel = new JPanel();
        upPanel.setLayout(null);
        upPanel.setBounds(0, 0, 800, 60);
        upPanel.setBackground(ColorInfo.MENU_COLOR);
        adapter = new LoginMousemove();
        upPanel.addMouseMotionListener(adapter);
        upPanel.addMouseListener(adapter);

        // 昵称
        friendName = new JLabel(friendNameString, JLabel.CENTER);
        friendName.setFont(new Font("微软雅黑", Font.PLAIN, 22));
        friendName.setForeground(Color.white);
        friendName.setBounds(210, 12, 200, 22);
        upPanel.add(friendName);

        // 个性签名
        friendSignature = new JLabel(friendSignatureString, JLabel.CENTER);
        friendSignature.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        friendSignature.setForeground(Color.white);
        friendSignature.setBounds(60, 40, 500, 18);
        upPanel.add(friendSignature);

        // 聊天信息面板
        showBox = Box.createVerticalBox();
        // 设置为不透明，否则背景色设置无效
        showBox.setOpaque(true);
        showBox.setBackground(ColorInfo.NORMAL_COLOR);
        showPanel = new JScrollPane(showBox);
        showPanel.setBorder(BorderFactory.createLineBorder(ColorInfo.BORDER_COLOR, 1));
        showPanel.getVerticalScrollBar().setUI(new MyScrollBarUI());
        showPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        // 设置滚动速率
        showPanel.getVerticalScrollBar().setUnitIncrement(20);
        showPanel.setBounds(0, 60, 620, 400);

        // 获取聊天记录
        Vector<String> record = InteractWithServer.getChatRecord(mid, fid, isGroup);
        for (String s : record) {
            /*
             * resource[0] 消息发送时间
             * resource[1] 发送人id
             * resource[2] 接收人id
             * resource[3] 信息
             */
            String[] resource = s.split("```", 4);
            // 聊天面板显示用户昵称
//            String fromName;
//            if (mid.equals(resource[1])) {
//                fromName = mName;
//            } else {
//                fromName =
//            }
            String fromName = resource[1].equals(mid) ? mName
                    : (MainInterface.getFriend().containsKey(resource[1]) ? MainInterface.getFriend().get(resource[1]).getfName()
                    : ("陌生人:" + resource[1]));
            if (resource.length == 4) {
                addMessage(resource[1], fromName, resource[0], resource[3], true);
            }
        }
        // 将滚动条拉到最下方
        showPanel.getViewport().setViewPosition(new Point(0, showPanel.getHeight() + 999999));

        // 中间功能面板(表情、窗口抖动等)
        functionPanel = new JPanel();
        functionPanel.setLayout(null);
        functionPanel.setBackground(ColorInfo.NORMAL_COLOR);
        functionPanel.setBounds(0, 460, 620, 35);
        // 表情按钮
        emotionButton = new JButton("");
        emotionButton.setMargin(new Insets(0, 0, 0, 0));
        emotionButton.setContentAreaFilled(false);
        emotionButton.setBorderPainted(false);
        emotionButton.setFocusPainted(false);
        emotionButton.setMargin(new Insets(0, 0, 0, 0));
        emotionButton.setBounds(0, 0, 35, 35);
        emotionButton.setIcon(new ImageIcon("./resource/chatwindow/emotion_normal.png"));
        emotionButton.setRolloverIcon(new ImageIcon("./resource/chatwindow/emotion_hover.png"));
        emotionButton.setPressedIcon(new ImageIcon("./resource/chatwindow/emotion_down.png"));
        functionPanel.add(emotionButton);
        // 抖动按钮
        shakeButton = new JButton("");
        shakeButton.setMargin(new Insets(0, 0, 0, 0));
        shakeButton.setContentAreaFilled(false);
        shakeButton.setBorderPainted(false);
        shakeButton.setFocusPainted(false);
        shakeButton.setBounds(35, 0, 40, 35);
        shakeButton.setIcon(new ImageIcon("./resource/chatwindow/shake_normal.png"));
        shakeButton.setRolloverIcon(new ImageIcon("./resource/chatwindow/shake_hover.png"));
        shakeButton.setPressedIcon(new ImageIcon("./resource/chatwindow/shake_down.png"));
        // 非群聊窗口添加抖动
        if (!isGroup) {
            functionPanel.add(shakeButton);
        }

        // 聊天输入框
        inputPanel = new JPanel();
        inputPanel.setLayout(null);
        inputPanel.setBounds(0, 495, 620, 125);
        inputPanel.setBackground(ColorInfo.NORMAL_COLOR);
        inputText = new JTextArea();
        inputText.setBounds(0, 0, 620, 85);
        inputText.setLineWrap(true);
        inputText.setBackground(ColorInfo.NORMAL_COLOR);
        inputText.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 18));
        PressEnter pressEnter = new PressEnter(mid, mName, fid, isGroup);
        pressEnter.setMessage(inputText);
        pressEnter.setNow(this);
        inputText.addKeyListener(pressEnter);

        inputScroll = new JScrollPane(inputText);
        inputScroll.setBorder(BorderFactory.createLineBorder(ColorInfo.BORDER_COLOR, 0));
        inputScroll.setBounds(0, 0, 620, 85);
        inputScroll.getVerticalScrollBar().setUI(new MyScrollBarUI());
        inputScroll.getVerticalScrollBar().setUnitIncrement(15);
        inputPanel.add(inputScroll);


        closeButton = new JButton("<html><span color=black>关闭(<u>C</u>)</span></html>");
        closeButton.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        closeButton.setFocusPainted(false);
        closeButton.setMargin(new Insets(0, 0, 0, 0));
        closeButton.setBackground(ColorInfo.CLOSE_BUTTON_COLOR);
        closeButton.addActionListener(new ExitNowFrameListener(this));
        closeButton.setBounds(460, 85, 70, 30);
        closeButton.setBorder(BorderFactory.createLineBorder(ColorInfo.BORDER_COLOR, 1));
        inputPanel.add(closeButton);

        sendButton = new JButton("<html><span color=white>发送(<u>S</u>)</span></html>");
        sendButton.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        sendButton.setBorderPainted(false);
        sendButton.setFocusPainted(false);
        sendButton.setMargin(new Insets(0, 0, 0, 0));
        sendButton.setBackground(ColorInfo.MENU_COLOR);
        sendButton.setBounds(540, 85, 70, 30);
        SendFriend sendFriend = new SendFriend(mid, mName, fid, isGroup);
        sendFriend.setMessage(inputText);
        sendFriend.setNow(this);
        sendButton.addActionListener(sendFriend);

        inputPanel.add(sendButton);

        // 获取群成员部分
        if (isGroup) {
            groupMemberBox = Box.createVerticalBox();
            groupMemberBox.setOpaque(true);
            groupMemberBox.setBackground(ColorInfo.NORMAL_COLOR);
            groupMemberPanel = new JPanel();
            groupMemberPanel.setLayout(null);
            groupMemberPanel.setBackground(ColorInfo.NORMAL_COLOR);
            groupMemberScrollPanel = new JScrollPane(groupMemberPanel);
            groupMemberScrollPanel.getVerticalScrollBar().setUI(new MyScrollBarUI());
            groupMemberScrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            groupMemberScrollPanel.getVerticalScrollBar().setUnitIncrement(16);
            groupMemberScrollPanel.setBounds(620, 60, 181, 620);
            groupMemberScrollPanel.setBorder(BorderFactory.createLineBorder(ColorInfo.BORDER_COLOR, 1));
            Vector<String> members = InteractWithServer.getGroupMembers(fid);

            JPanel myPanel = new JPanel();
            myPanel.setOpaque(true);
            myPanel.setBackground(ColorInfo.NORMAL_COLOR);
            myPanel.setLayout(null);
            myPanel.setBounds(10, 0, 170, 30);
            JLabel myProfileLabel = new JLabel(new ImageIcon(GetProfile.getProfileImage(mid, "./Data/Profile/User/", "").getImage()
                    .getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
            myProfileLabel.setBounds(0, 0, 30, 30);

            String myInfo = mName + "(" + mid + ")";
            JLabel myInfoLabel = new JLabel(myInfo);
            myInfoLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 13));
            myInfoLabel.setBounds(35, 0, 135, 30);
            myPanel.add(myProfileLabel);
            myPanel.add(myInfoLabel);
            groupMemberPanel.add(myPanel);
            // 记录成员个数 便于设置坐标值
            int num = 1;
            for (String i : members) {
                if (i.equals(mid)) {
                    continue;
                }
                JPanel memberPanel = new JPanel();
                memberPanel.setOpaque(true);
                memberPanel.setBackground(ColorInfo.NORMAL_COLOR);
                memberPanel.setLayout(null);
                memberPanel.setBounds(10, 40 * num++, 170, 30);

                ImageIcon icon = new ImageIcon("./resource/default_profile.jpg");
                String content = "陌生人(" + i + ")";
                if (MainInterface.getFriend().containsKey(i)) {
                    icon = GetProfile.getProfileImage(i, "./Data/Profile/User/",
                            MainInterface.getFriend().get(i).getfProfile());
                    content = MainInterface.getFriend().get(i).getfName() + "("
                            + MainInterface.getFriend().get(i).getFid() + ")";
                }
                icon = new ImageIcon(icon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
                JLabel memberInfoLabel = new JLabel(content);
                memberInfoLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 13));
                memberInfoLabel.setBounds(35, 0, 135, 30);
                JLabel memberProfileLabel = new JLabel(icon);
                memberProfileLabel.setBounds(0, 0, 30, 30);
                memberPanel.add(memberProfileLabel);
                memberPanel.add(memberInfoLabel);
                groupMemberPanel.add(memberPanel);
            }



        } else {
            close.setBounds(580, 0, 40, 40);
            maximize.setBounds(540, 0, 40, 40);
            minimize.setBounds(500, 0, 40, 40);
            setSize(620, 620);
        }

    }

    public void addMessage(String userId, String userName, String sendTime, String message, boolean isOld) {
        // 区分自己的消息和好友的消息
        if (userId.equals(mid)) {
            userName = "<html><p style =\"font-size:12px;color:#008F8C\">" + userName;
            sendTime = "<span style=\"color:#008F8C\"> " + sendTime + "</span></p></html>";
        } else {
            userName = "<html><p style =\"font-size:12px;color:#3494FC\">" + userName;
            sendTime = "<span style=\"color:#3494FC\"> " + sendTime + "</span></p></html>";
        }
        message = "<html><p style =\"font-size:14px;" + (isOld ? "color:#000000" : "") + "\">" + message
                + "</p><br/></html>";
        JLabel infoLabel = new JLabel(userName + sendTime);
        JLabel messageLabel = new JLabel(message);
        infoLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 18));
        messageLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 18));
        showBox.add(infoLabel);
        showBox.add(messageLabel);
        showPanel.getViewport().setViewPosition(new Point(0, showPanel.getHeight() + 999999));
    }
}