package com.mychat.frame;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.ImageIcon;

/**
 * 利用头像URL获取ImageIcon对象
 */
public final class GetProfile {
    private static void download(String urlString, String fileName, String savePath) throws Exception {
        // 构造URL
        URL url = new URL(urlString);
        // 打开连接
        URLConnection con = url.openConnection();
        // 设置请求超时为5s
        con.setConnectTimeout(5 * 1000);
        // 输入流
        InputStream in = con.getInputStream();
        // 1K的数据缓冲
        byte[] bs = new byte[1024];
        // 读取到的数据长度
        int len;
        // 输出的文件流
        File file = new File(savePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        OutputStream out = new FileOutputStream(file.getPath() + "\\" + fileName);
        // 开始读取
        while ((len = in.read(bs)) != -1) {
            out.write(bs, 0, len);
        }
        // 完毕，关闭所有链接
        out.close();
        in.close();
    }

    public static ImageIcon getProfileImage(String id, String sourcePath, String profileUrl) {
        ImageIcon profile;
        try {
            String path = sourcePath + id + ".jpg";
            if (!new File(path).exists()) {
                download(profileUrl, id + ".jpg", sourcePath);
            }
            profile = new ImageIcon(path);
        } catch (Exception e) {
            profile = new ImageIcon("./resource/default_profile.jpg");
            System.out.println("URL: " + profileUrl);
            System.out.println("用户：" + id + " 获取头像失败，改为默认头像");
            System.out.println(" *************************************");
        }
        return profile;
    }
}
