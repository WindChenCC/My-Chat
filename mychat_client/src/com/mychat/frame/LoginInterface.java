package com.mychat.frame;

import com.mychat.config.ColorInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * 登录界面，获取用户登录信息使用户登录
 */
public final class LoginInterface extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField userId;
    private JLabel rememberPasswd, autoLogin, headPortrait;
    private JLabel textLogoLabel;
    private JPasswordField passwd;
    private JButton login, findPasswd, register, close, minimize;
    private JCheckBox rememberPasswdCheckBox, autoLoginCheckBox;
    private JPanel upPanel, downPanel, textFiledPanel;
    private LoginListener loginListener;
    private String username, userPasswd;

    /**
     * 初始化界面组件
     */
    private void init() {
        /*
         * panel up
         */
        upPanel = new JPanel();
        upPanel.setLayout(null);
        upPanel.setBounds(0, 0, 430, 120);
        upPanel.setBackground(ColorInfo.MENU_COLOR);
        textLogoLabel = new JLabel();
        textLogoLabel.setIcon(new ImageIcon("./resource/loginIcon/mychat_textLogo.png"));
        textLogoLabel.setBounds(10, 10, 60, 15);
        upPanel.add(textLogoLabel);

        /*
         * button close
         */
        close = new JButton();
        close.setMargin(new Insets(0, 0, 0, 0));
        close.setBounds(390, 0, 40, 40);
        close.setContentAreaFilled(false);
        close.setBorderPainted(false);
        close.setFocusPainted(false);
        close.setToolTipText("关闭");
        close.setIcon(new ImageIcon("./resource/button/closebutton_normal.png"));
        close.setRolloverIcon(new ImageIcon("./resource/button/closebutton_hover.png"));
        close.setPressedIcon(new ImageIcon("./resource/button/closebutton_down.png"));
        ExitListener closeListener = new ExitListener();
        close.addActionListener(closeListener);

        /*
         * button minimize
         */
        minimize = new JButton();
        minimize.setMargin(new Insets(0, 0, 0, 0));
        minimize.setBounds(350, 0, 40, 40);
        minimize.setContentAreaFilled(false);
        minimize.setBorderPainted(false);
        minimize.setFocusPainted(false);
        minimize.setToolTipText("最小化");
        minimize.setIcon(new ImageIcon("./resource/button/minbutton_normal.png"));
        minimize.setRolloverIcon(new ImageIcon("./resource/button/minbutton_hover.png"));
        minimize.setPressedIcon(new ImageIcon("./resource/button/minbutton_down.png"));
        minimize.addActionListener(e -> setExtendedState(JFrame.ICONIFIED));

        /*
         * panel down
         */
        downPanel = new JPanel();
        downPanel.setLayout(null);
        downPanel.setBounds(0, 120, 430, 200);
        downPanel.setBackground(Color.white);
        /*
         * label headPortrait
         */
        headPortrait = new JLabel();
        headPortrait.setBounds(20, 20, 85, 85);
        String headPortraitPosition = "resource/default_profile.jpg";
        Image headPic = (new ImageIcon(headPortraitPosition)).getImage().getScaledInstance(82, 83, Image.SCALE_DEFAULT);
        headPortrait.setIcon(new ImageIcon(headPic));
        /*
         * panel textFieldPanel
         */
        textFiledPanel = new JPanel();
        textFiledPanel.setBounds(135, 29, 195, 67);
        textFiledPanel.setBackground(Color.white);
        textFiledPanel.setLayout(null);
        /*
         * TextField userId
         */
        userId = new JTextField("账号");
        userId.setBounds(0, 0, 195, 32);
        userId.setForeground(Color.GRAY);
        userId.setFont(new Font("黑体", Font.PLAIN, 18));
        userId.addFocusListener(new FocusListener() {
            @Override
            public void focusLost(FocusEvent e) {
                if (userId.getText().trim().equals("")) {
                    userId.setForeground(Color.GRAY);
                    userId.setText("账号");
                }
            }

            @Override
            public void focusGained(FocusEvent arg0) {
                if (userId.getText().trim().equals("账号")) {
                    userId.setText("");
                    userId.setForeground(Color.BLACK);
                }
            }
        });
        /*
         * PasswordField passwd
         */
        passwd = new JPasswordField("密码");
        passwd.setBounds(0, 35, 195, 32);
        passwd.setEchoChar((char) 0);
        passwd.setForeground(Color.GRAY);
        passwd.setFont(new Font("", Font.PLAIN, 18));
        passwd.addFocusListener(new FocusListener() {
            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(passwd.getPassword()).trim().equals("")) {
                    passwd.setEchoChar((char) 0);
                    passwd.setForeground(Color.GRAY);
                    passwd.setText("密码");
                }
            }

            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(passwd.getPassword()).trim().equals("密码")) {
                    passwd.setEchoChar('•');
                    passwd.setForeground(Color.BLACK);
                    passwd.setText("");
                }
            }
        });
        /*
         * Button register
         */
        // 点击注册账号按钮，打开注册账号的网页(目前还未实现)
        register = new JButton();
        register.setMargin(new Insets(0, 0, 0, 0));
        register.setBounds(340, 37, 51, 16);
        register.setContentAreaFilled(false);
        register.setBorderPainted(false);
        register.setIcon(new ImageIcon("./resource/loginIcon/signUp.png"));
        register.setRolloverIcon(new ImageIcon("./resource/loginIcon/signUp_hover.png"));
        register.setPressedIcon(new ImageIcon("./resource/loginIcon/signUp_press.png"));
        register.addActionListener(arg -> {
            try {
                URI uri = new URI("https://www.cnblogs.com/WindChenCC/");
                Desktop.getDesktop().browse(uri);
            } catch (URISyntaxException | IOException e) {
                e.printStackTrace();
            }
        });
        /*
         * Button findPasswd
         */
        // 点击找回密码的按钮，打开找回密码的网页
        findPasswd = new JButton();
        findPasswd.setMargin(new Insets(0, 0, 0, 0));
        findPasswd.setBounds(340, 72, 51, 16);
        findPasswd.setContentAreaFilled(false);
        findPasswd.setBorderPainted(false);
        findPasswd.setIcon(new ImageIcon("./resource/loginIcon/findPsw.png"));
        findPasswd.setRolloverIcon(new ImageIcon("./resource/loginIcon/findPsw_hover.png"));
        findPasswd.setPressedIcon(new ImageIcon("./resource/loginIcon/findPsw_press.png"));
        findPasswd.addActionListener(e -> {
            try {
                URI uri = new URI("https://www.cnblogs.com/WindChenCC/");
                Desktop.getDesktop().browse(uri);
            } catch (URISyntaxException | IOException e1) {
                e1.printStackTrace();
            }
        });
        /*
         * JCheckBox isRememberPasswd
         */
        rememberPasswdCheckBox = new JCheckBox();
        rememberPasswdCheckBox.setOpaque(true);
        rememberPasswdCheckBox.setBackground(Color.white);
        rememberPasswdCheckBox.setMargin(new Insets(0, 0, 0, 0));
        rememberPasswdCheckBox.setBounds(133, 110, 17, 17);
        rememberPasswdCheckBox.setIcon(new ImageIcon("./resource/loginIcon/checkbox_normal.png"));
        rememberPasswdCheckBox.setRolloverIcon(new ImageIcon("./resource/loginIcon/checkbox_hover.png"));
        rememberPasswdCheckBox.setPressedIcon(new ImageIcon("./resource/loginIcon/checkbox_press.png"));
        rememberPasswdCheckBox.setSelectedIcon(new ImageIcon("./resource/loginIcon/checkbox_tick_normal1.png"));
        rememberPasswdCheckBox.setRolloverSelectedIcon(new ImageIcon("./resource/loginIcon/checkbox_tick_highlight1.png"));
        /*
         * JCheckBox isAutoLogin
         */
        autoLoginCheckBox = new JCheckBox();
        autoLoginCheckBox.setOpaque(true);
        autoLoginCheckBox.setBackground(Color.white);
        autoLoginCheckBox.setMargin(new Insets(0, 0, 0, 0));
        autoLoginCheckBox.setBounds(258, 110, 17, 17);
        autoLoginCheckBox.setIcon(new ImageIcon("./resource/loginIcon/checkbox_normal.png"));
        autoLoginCheckBox.setRolloverIcon(new ImageIcon("./resource/loginIcon/checkbox_hover.png"));
        autoLoginCheckBox.setPressedIcon(new ImageIcon("./resource/loginIcon/checkbox_press.png"));
        autoLoginCheckBox.setSelectedIcon(new ImageIcon("./resource/loginIcon/checkbox_tick_normal1.png"));
        autoLoginCheckBox.setRolloverSelectedIcon(new ImageIcon("./resource/loginIcon/checkbox_tick_highlight1.png"));
        /*
         * label rememberPasswd
         */
        rememberPasswd = new JLabel("记住密码");
        rememberPasswd.setForeground(Color.GRAY);
        rememberPasswd.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        rememberPasswd.setForeground(new Color(166, 166, 166));
        rememberPasswd.setBounds(155, 112, 53, 15);
        /*
         * label autoLogin
         */
        autoLogin = new JLabel("自动登录");
        autoLogin.setForeground(Color.GRAY);
        autoLogin.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        autoLogin.setForeground(new Color(166, 166, 166));
        autoLogin.setBounds(280, 112, 52, 15);
        /*
         * Button login
         */
        login = new JButton("登录");
        login.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        login.setForeground(Color.white);
        login.setMargin(new Insets(0, 0, 0, 0));
        login.setBounds(135, 145, 195, 40);
        login.setFocusPainted(false);
        login.setBorderPainted(false);
        login.setBackground(ColorInfo.LOGIN_BUTTON_COLOR);
        /*
         * init actionListener
         */
        loginListener = new LoginListener();
        loginListener.setNow(this);
        loginListener.setUserId(userId);
        loginListener.setPasswd(passwd);
        loginListener.setIsRememberPasswd(rememberPasswdCheckBox);
        loginListener.setIsAutoLogin(autoLoginCheckBox);
        userId.addActionListener(loginListener);
        passwd.addActionListener(loginListener);

        login.addActionListener(loginListener);
        try {
            FileInputStream in = new FileInputStream("./Data/UserInfo.uif");
            int t;
            username = "";
            userPasswd = "";
            while ((t = in.read()) != -1) {
                if (t == '\n') {
                    break;
                }
                t ^= 'I';
                username = username + (char) t;
            }
            if (!username.equals("")) {
                while ((t = in.read()) != -1) {
                    if (t == '\n') {
                        break;
                    }
                    t ^= 'P';
                    userPasswd = userPasswd + (char) t;
                }
                userId.setForeground(Color.BLACK);
                userId.setText(username);
                passwd.setEchoChar('•');
                passwd.setForeground(Color.BLACK);
                passwd.setText(userPasswd);
                t = (char) in.read();
                rememberPasswdCheckBox.setSelected(true);
                if (t == '1') {
                    autoLoginCheckBox.setSelected(true);
                }
            }
            in.close();
        } catch (Exception ignored) {
        }
    }

    public LoginInterface() {
        setLayout(null);
        // 更改显示的小图标
        setIconImage(Toolkit.getDefaultToolkit().createImage("./resource/mainpanel/mychat_logo.png"));
        setTitle("登录窗口");
        init();
        upPanel.add(close);
        upPanel.add(minimize);

        downPanel.add(headPortrait);
        textFiledPanel.add(userId);
        textFiledPanel.add(passwd);
        downPanel.add(textFiledPanel);
        downPanel.add(register);
        downPanel.add(findPasswd);
        downPanel.add(rememberPasswdCheckBox);
        downPanel.add(autoLoginCheckBox);
        downPanel.add(rememberPasswd);
        downPanel.add(autoLogin);
        downPanel.add(login);
        add(upPanel);
        add(downPanel);
        LoginMousemove adapter = new LoginMousemove();
        addMouseMotionListener(adapter);
        addMouseListener(adapter);
        setSize(430, 320);
        setUndecorated(true);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
}
