package com.mychat.server;

/**
 * 服务端采用多线程处理机制。<br>
 * 服务端启动后，同时监听聊天端口10001和验证端口10002。当用户登陆时，会向验证端口发送一个已序列化的ChatVerify对象，其中包括用户ID和经过MD5加密后的密码。服务端查询查询数据库验证是否正确，验证成功则允许用户接入聊天端口。
 * 聊天时，用户的聊天内容经过特定标志处理（包括发送人、接收人等）通过聊天端口被服务端接收，服务端对消息识别后转发给接收者。<br>
 * 在服务端有一个以用户的ID为键，数据流对象为值的静态HashMap。此HashMap可以通过ID找到该用户相应的输出流。用户登录成功时服务端将该用户的数据流对象加入到HashMap中，用户离线时将其从HashMap中删除。
 * 一个用户上线或者离线时，服务器会发送一个标识字符串给该用户的所有好友，以更新其上线离线状态。<br>
 * 验证端口除了实现登陆验证功能外，还要处理用户信息的更新，比如个性签名。更新个性签名后，用户向验证端发送一个标识字符串来请求更新数据库对应字段。
 */
public class StartServer {
    public static void main(String[] args) {
        String str = "                   _ooOoo_\n" +
                "                  o8888888o\n" +
                "                  88\" . \"88\n" +
                "                  (| -_- |)\n" +
                "                  O\\  =  /O\n" +
                "               ____/`---'\\____\n" +
                "             .'  \\\\|     |//  `.\n" +
                "            /  \\\\|||  :  |||//  \\ \n" +
                "           /  _||||| -:- |||||-  \\ \n" +
                "           |   | \\\\\\  -  /// |   |\n" +
                "           | \\_|  ''\\---/''  |   |\n" +
                "           \\  .-\\__  `-`  ___/-. /\n" +
                "         ___`. .'  /--.--\\  `. . __\n" +
                "      .\"\" '<  `.___\\_<|>_/___.'  >'\"\".\n" +
                "     | | :  `- \\`.;`\\ _ /`;.`/ - ` : | |\n" +
                "     \\  \\ `-.   \\_ __\\ /__ _/   .-` /  /\n" +
                "======`-.____`-.___\\_____/___.-`____.-'======\n" +
                "                   `=---='\n" +
                "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\n"+
                "              MyChat服务端已启动              \n" +
                "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\n" +
                "            佛祖保佑       永无BUG            ";
        new Thread(new VerifyThread()).start();
        System.out.println("*********************************************");
        System.out.println(str);
        System.out.println("*********************************************");
    }
}

/* 随便写的简陋图像界面 */
//public class StartServer extends JFrame implements ActionListener {
//    private final JPanel jp;
//    private final JButton jb;
//    private boolean isRunning = false;
//    private final VerifyThread server;
//    private Thread serverThread;
//
//    public static void main(String[] args) {
//        new StartServer();
//
//    }
//
//    public StartServer() {
//        server = new VerifyThread();
//        jp = new JPanel();
//        jp.setLayout(null);
//        jb = new JButton("启动");
//        jb.setFocusPainted(false);
//        jb.setBounds(110, 30, 80, 50);
//        jb.addActionListener(this);
//        jp.add(jb);
//        this.add(jp);
//        this.setBounds(800, 450, 300, 200);
//        this.setVisible(true);
//
//        addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowClosing(WindowEvent e) {
//                System.out.println("服务器已停止");
//                System.exit(0);
//            }
//        });
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if (!isRunning) {
//            serverThread = new Thread(server);
//            serverThread.start();
//            isRunning = true;
//            jb.setText("关闭");
//            System.out.println("服务端已成功创建,当前在线人数：0");
//        } else {
//            serverThread.stop();
//            isRunning = false;
//            jb.setText("启动");
//            System.out.println("服务器已停止");
//        }
//    }
//}




