package com.example;

import javax.swing.*;
import java.awt.*;

public class DelimiterLineToolBar extends JComponent {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.WHITE);

        int screen_width = getWidth();
        int screen_height = getHeight();


        g2d.setStroke(new BasicStroke(1));
        g2d.drawLine(66*getWidth()/100, 87*getHeight()/100, 66*getWidth()/100, 95*getHeight()/100);
    }
}
