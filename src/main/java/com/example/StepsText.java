package com.example;

import javax.swing.*;
import java.awt.*;

public class StepsText extends JComponent {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width_screen = getWidth();
        int height_screen = getHeight();

        g2d.setColor(Color.WHITE);
        int fontSize = Math.min(width_screen, height_screen) / 15;
        g2d.setFont(new Font("Kodchasan", Font.BOLD, fontSize));
        g2d.drawString("steps", (76*width_screen)/100, (12*height_screen)/100);
    }
}
