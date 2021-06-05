/*
  存放各种监听类
 */
package com.mychat.frame;

import com.mychat.client.ChatThread;
import com.mychat.client.InteractWithServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileOutputStream;
import java.util.Date;

/**
 * 用于登录界面和主界面的退出按钮
 */
class ExitListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }
}

/**
 * 用于推出当前frame窗口
 */
class ExitNowFrameListener implements ActionListener {
    private final JFrame now;

    public ExitNowFrameListener(JFrame now) {
        this.now = now;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        now.dispose();
    }
}

/**
 * 用于使窗口可以点击任意位置移动
 */
class LoginMousemove extends MouseAdapter {
    private int offsetX, offsetY;
    private boolean isCanMove;

    public LoginMousemove() {
        isCanMove = true;
    }

    public void setCanMove(boolean isCanMove) {
        this.isCanMove = isCanMove;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (isCanMove) {
            SwingUtilities.getRoot((Component) e.getSource()).setLocation(e.getXOnScreen() - offsetX,
                    e.getYOnScreen() - offsetY);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        offsetX = e.getX();
        offsetY = e.getY();
    }
}

/**
 * 获得登录信息并创建主界面
 */
class LoginListener implements ActionListener {
    JFrame now;
    JTextField userId;
    JPasswordField passwd;
    JCheckBox isRememberPasswd, isAutoLogin;

    public void setNow(JFrame now) {
        this.now = now;
    }

    public void setUserId(JTextField userId) {
        this.userId = userId;
    }

    public void setPasswd(JPasswordField passwd) {
        this.passwd = passwd;
    }

    public void setIsRememberPasswd(JCheckBox isRememberPasswd) {
        this.isRememberPasswd = isRememberPasswd;
    }

    public void setIsAutoLogin(JCheckBox isAutoLogin) {
        this.isAutoLogin = isAutoLogin;
    }

    /**
     * 点击登录按钮
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        new Thread(() -> {
            // 获取文本框内容
            String userIdString = userId.getText().trim();
            String userPasswordString = String.valueOf(passwd.getPassword()).trim();
            // 获取登录设置
            boolean isAutoLog = isAutoLogin.isSelected();
            boolean isRemember = isRememberPasswd.isSelected();
            if (isAutoLog) {
                isRemember = true;
            }
            // 验证用户或密码是否正确
            Object isLoginSuccess = InteractWithServer.isLogin(userIdString, userPasswordString);

            System.out.println("当前登录状态：" + isLoginSuccess);
            if (isLoginSuccess != null) {
                String loginResult = isLoginSuccess.toString();
                if (loginResult.equals("true")) {
                    if (isRemember) {
                        try {
                            FileOutputStream out = new FileOutputStream("./Data/UserInfo.uif");
                            for (int i = 0; i < userIdString.length(); i++) {
                                char t = userIdString.charAt(i);
                                t ^= 'I';
                                out.write(t);
                            }
                            out.write('\n');
                            for (int i = 0; i < userPasswordString.length(); i++) {
                                char t = userPasswordString.charAt(i);
                                t ^= 'P';
                                out.write(t);
                            }
                            out.write('\n');
                            if (isAutoLog) {
                                out.write('1');
                            } else {
                                out.write('0');
                            }
                            out.close();
                        } catch (Exception e1) {
                            System.out.println("ListenterClass/actionPerformed Error " + e1);
                        }
                    }
                    now.dispose();
                    // 创建线程接入聊天端口
                    new Thread(new ChatThread(userIdString)).start();
                    new MainInterface(userIdString);
                } else if (loginResult.equals("Repeat_login")) {
                    JOptionPane.showMessageDialog(now, "不能重复登录");
                } else {
                    JOptionPane.showMessageDialog(now, "用户名或密码错误", "登陆失败", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(now, "登陆超时，请检查您的网络或防火墙设置");
            }
        }).start();
    }
}

/**
 * 对好友发送信息
 */
class SendFriend implements ActionListener {
    private JTextArea message;
    private final String mid;
    private final String mName;
    private final String fid;
    private ChatWithFriend now;
    private final boolean isGroup;

    public SendFriend(String mid, String mName, String fid, boolean isGroup) {
        this.mName = mName;
        this.fid = fid;
        this.isGroup = isGroup;
        this.mid = mid;
    }

    private void sendMes() {
        if (this.message.getText().trim().length() != 0) {
            now.addMessage(mid, mName, new Date().toString(), this.message.getText(), false);
            ChatThread.getDataStream().send(this.message.getText(), fid, isGroup);
            this.message.setText("");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 发送消息
        sendMes();
    }

    public void setMessage(JTextArea message) {
        this.message = message;
    }

    public void setNow(ChatWithFriend now) {
        this.now = now;
    }


}

class PressEnter implements KeyListener {
    private JTextArea message;
    private final String mid;
    private final String mName;
    private final String fid;
    private ChatWithFriend now;
    private final boolean isGroup;

    public PressEnter(String mid, String mName, String fid, boolean isGroup) {
        this.mName = mName;
        this.fid = fid;
        this.isGroup = isGroup;
        this.mid = mid;
    }

    private void sendMes() {
        if (this.message.getText().trim().length() != 0) {
            now.addMessage(mid, mName, new Date().toString(), this.message.getText(), false);
            ChatThread.getDataStream().send(this.message.getText(), fid, isGroup);
            this.message.setText("");
        }
    }

    public void setMessage(JTextArea message) {
        this.message = message;
    }

    public void setNow(ChatWithFriend now) {
        this.now = now;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // shift+enter 换行
        if (e.getKeyCode() == KeyEvent.VK_ENTER && e.isShiftDown()) {
            message.append(System.lineSeparator());
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            /* 按下回车发送消息 */
            // 屏蔽textArea默认回车换行
            e.consume();
            sendMes();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}