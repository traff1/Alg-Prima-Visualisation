package com.example;

import javax.swing.*;
import java.awt.*;

public class RoundedRectangleTollBarComponent extends JComponent {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width_screen = getWidth();
        int height_screen = getHeight();

        int x = (2 * width_screen) / 100;
        int y = (85 * height_screen) / 100;
        int width = (95 * width_screen) / 100;
        int height = (12 * height_screen) / 100;
        int arcWidth = 40;
        int arcHeight = 40;

        g2d.setColor(new Color(102, 101, 101)); // Semi-transparent fill color
        g2d.fillRoundRect(x, y, width, height, arcWidth, arcHeight);


        g2d.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
    }
}