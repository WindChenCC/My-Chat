package com.mychat.frame;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * @author wind chen
 * @date 2021-5-28
 * 绘制边框投影
 */
public class DropShadowPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    //最大阴影透明度
    private static final int TOP_OPACITY = 20;
    //阴影大小像素
    public int pixels;

    public DropShadowPanel(int pix) {
        this.pixels = pix;
        Border border = BorderFactory.createEmptyBorder(pixels, pixels, pixels, pixels);
        this.setBorder(BorderFactory.createCompoundBorder(this.getBorder(), border));
        this.setLayout(new BorderLayout());
    }

    @Override
    protected void paintComponent(Graphics g) {
        int shade = 0;
        for (int i = 0; i < pixels; i++) {
            double opacity = TOP_OPACITY * ((double) 1 / (double) (i == 0 ? 1 : i));
            drawShadow(g, shade, pixels - i, (int) opacity);
        }
    }

    /**
     * 绘制阴影
     *
     * @param g     绘图对象
     * @param shade 颜色值
     * @param i     边框层次
     */
    private void drawShadow(Graphics g, int shade, int i, int opacity) {
        System.out.println(opacity);
        g.setColor(new Color(shade, shade, shade, opacity));
        g.fillRoundRect(i + 1, i + 1, this.getWidth() - ((i * 2) + 1), this.getHeight() - ((i * 2) + 1), pixels, pixels);
    }
}
