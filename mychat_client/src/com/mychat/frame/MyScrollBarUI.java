package com.mychat.frame;

import com.mychat.config.ColorInfo;
import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

/**
 * 美化 JScrollPane 滚动条，为其添加自定义样式
 */
public final class MyScrollBarUI extends BasicScrollBarUI {

    /**
     * 更改滑道颜色与滚动条宽度
     */
    @Override
    protected void configureScrollBarColors() {
        // 滑道
        trackColor = ColorInfo.NORMAL_COLOR;
        // 滚动条宽度
        scrollBarWidth = 10;
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {

        super.paintTrack(g, c, trackBounds);
    }

    /**
     * 更改滚动条内部样式(滑块颜色,滑块宽度,把手颜色等)
     */
    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        // 重写父类方法，如果不加这一句无法拖动滑动条
        g.translate(thumbBounds.x, thumbBounds.y);
        // 设置把手颜色
        g.setColor(ColorInfo.SCROLL_THUMB_COLOR);
        // 消除锯齿
        Graphics2D g2 = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.addRenderingHints(rh);
        // 填充圆角矩形
        g2.fillRoundRect(0, 0, 10, thumbBounds.height - 1, 15, 15);
    }

    /**
     * 隐藏向下点击的按钮
     */
    @Override
    protected JButton createIncreaseButton(int orientation) {
        JButton button = new JButton();
        button.setBorder(null);
        return button;
    }

    /**
     * 隐藏向上点击的按钮
     */
    @Override
    protected JButton createDecreaseButton(int orientation) {
        JButton button = new JButton();
        button.setBorder(null);
        return button;
    }
}
