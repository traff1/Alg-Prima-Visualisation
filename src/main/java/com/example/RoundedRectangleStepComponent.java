package com.example;

import javax.swing.*;
import java.awt.*;

public class RoundedRectangleStepComponent extends JComponent {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width_screen = getWidth();
        int height_screen = getHeight();

        int x = (67 * width_screen) / 100;
        int y = (2 * height_screen) / 100;

        int width = (30 * width_screen) / 100;
        int height = (80 * height_screen) / 100;

        int arcWidth = 45;
        int arcHeight = 45;

        g2d.setColor(new Color(102, 101, 101)); // Semi-transparent fill color
        g2d.fillRoundRect(x, y, width, height, arcWidth, arcHeight);


        g2d.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
    }
}