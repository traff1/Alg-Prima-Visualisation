package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.RescaleOp;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class CalculatorNavigation extends JComponent {

    private BufferedImage handImage;
    private BufferedImage plusImage;
    private BufferedImage urnImage;
    private BufferedImage arrowImage;

    private BufferedImage handImageHovered;
    private BufferedImage plusImageHovered;
    private BufferedImage urnImageHovered;
    private BufferedImage arrowImageHovered;

    private BufferedImage handImagePressed;
    private BufferedImage plusImagePressed;
    private BufferedImage arrowImagePressed;
    private BufferedImage urnImagePressed;

    public JButton handButton;
    public JButton plusButton;
    public JButton urnButton;
    public JButton arrowButton;

    private boolean handButtonPressed;
    private boolean plusButtonPressed;
    private boolean urnButtonPressed;
    private boolean arrowButtonPressed;
    private boolean handButtonHovered;
    private boolean plusButtonHovered;
    private boolean urnButtonHovered;
    private boolean arrowButtonHovered;


    public CalculatorNavigation() {
        try {
            handImage = ImageIO.read(new File("./src/main/resources/images/hand.png"));
            plusImage = ImageIO.read(new File("./src/main/resources/images/plus.png"));
            urnImage = ImageIO.read(new File("./src/main/resources/images/urn.png"));
            arrowImage = ImageIO.read(new File("./src/main/resources/images/arrow.png"));

            handImageHovered = ImageIO.read(new File("./src/main/resources/images/handgray.png"));
            plusImageHovered = ImageIO.read(new File("./src/main/resources/images/plusgray.png"));
            urnImageHovered = ImageIO.read(new File("./src/main/resources/images/urngray.png"));
            arrowImageHovered = ImageIO.read(new File("./src/main/resources/images/arrowgray.png"));

            handImagePressed = ImageIO.read(new File("./src/main/resources/images/handwhite.png"));
            plusImagePressed = ImageIO.read(new File("./src/main/resources/images/pluswhite.png"));
            urnImagePressed = ImageIO.read(new File("./src/main/resources/images/urnwhite.png"));
            arrowImagePressed = ImageIO.read(new File("./src/main/resources/images/arrowwhite.png"));

        } catch (IOException e) {
            System.out.println("Failed to load image: " + e.getMessage());
        }

        setLayout(null); // Use null layout for absolute positioning

        // Create buttons
        handButton = new JButton();
        plusButton = new JButton();
        urnButton = new JButton();
        arrowButton = new JButton();

        // Set the buttons to be transparent
        handButton.setOpaque(false);
        handButton.setContentAreaFilled(false);
        handButton.setBorderPainted(false);
        handButton.setFocusPainted(false);
        handButton.setBorder(BorderFactory.createEmptyBorder());

        plusButton.setOpaque(false);
        plusButton.setContentAreaFilled(false);
        plusButton.setBorderPainted(false);
        plusButton.setFocusPainted(false);
        plusButton.setBorder(BorderFactory.createEmptyBorder());

        urnButton.setOpaque(false);
        urnButton.setContentAreaFilled(false);
        urnButton.setBorderPainted(false);
        urnButton.setFocusPainted(false);
        urnButton.setBorder(BorderFactory.createEmptyBorder());

        arrowButton.setOpaque(false);
        arrowButton.setContentAreaFilled(false);
        arrowButton.setBorderPainted(false);
        arrowButton.setFocusPainted(false);
        arrowButton.setBorder(BorderFactory.createEmptyBorder());

        // Add action listeners to buttons
        ButtonActionListener buttonListener = new ButtonActionListener();
        handButton.addActionListener(buttonListener);
        plusButton.addActionListener(buttonListener);
        urnButton.addActionListener(buttonListener);
        arrowButton.addActionListener(buttonListener);

        // Add mouse listeners to buttons for hover effect
        ButtonHoverListener hoverListener = new ButtonHoverListener();
        handButton.addMouseListener(hoverListener);
        plusButton.addMouseListener(hoverListener);
        urnButton.addMouseListener(hoverListener);
        arrowButton.addMouseListener(hoverListener);



        // Add buttons to the component
        add(handButton);
        add(plusButton);
        add(urnButton);
        add(arrowButton);
    }

    private class ButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == handButton) {
                handButtonPressed = true;
                plusButtonPressed = false;
                urnButtonPressed = false;
                arrowButtonPressed = false;
            } else if (e.getSource() == plusButton) {
                handButtonPressed = false;
                plusButtonPressed = true;
                urnButtonPressed = false;
                arrowButtonPressed = false;
            } else if (e.getSource() == urnButton) {
                handButtonPressed = false;
                urnButtonPressed = true;
                arrowButtonPressed = false;
                plusButtonPressed = false;
            } else if (e.getSource() == arrowButton) {
                handButtonPressed = false;
                arrowButtonPressed = true;
                plusButtonPressed = false;
                urnButtonPressed = false;
            }
            repaint();
        }
    }

    private class ButtonHoverListener extends MouseAdapter {
        @Override
        public void mouseEntered(MouseEvent e) {
            if (e.getSource() == handButton) {
                handButtonHovered = true;
            } else if (e.getSource() == plusButton) {
                plusButtonHovered = true;
            } else if (e.getSource() == urnButton) {
                urnButtonHovered = true;
            } else if (e.getSource() == arrowButton) {
                arrowButtonHovered = true;
            }
            repaint();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (e.getSource() == handButton) {
                handButtonHovered = false;
            } else if (e.getSource() == plusButton) {
                plusButtonHovered = false;
            } else if (e.getSource() == urnButton) {
                urnButtonHovered = false;
            } else if (e.getSource() == arrowButton) {
                arrowButtonHovered = false;
            }
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

        int width_screen = getWidth();
        int height_screen = getHeight();

        int handWidth = handImage.getWidth();
        int handHeight = handImage.getHeight();

        int plusWidth = plusImage.getWidth();
        int plusHeight = plusImage.getHeight();

        int urnWidth = urnImage.getWidth();
        int urnHeight = urnImage.getHeight();

        int arrowWidth = arrowImage.getWidth();
        int arrowHeight = arrowImage.getHeight();

        int newHandWidth = (8 * width_screen/100);
        int newHandHeight = (10*height_screen/100);

        int newPlusWidth = (8*width_screen/100);
        int newPlusHeight = (12*height_screen / 100);


        int newUrnWidth = (8 * width_screen/100);
        int newUrnHeight = (10* height_screen/100);

        int newArrowWidth = (8 * width_screen/100);
        int newArrowHeight = (12 * height_screen/ 100);



        int xHand = (27 * width_screen) / 100;
        int yHand = (3*height_screen) / 100;

        int xPlus = (4 * width_screen) / 100;
        int yPlus = (2 * height_screen) / 100;

        int xArrow = (12 * width_screen) / 100;
        int yArrow = (2 * height_screen) / 100;

        int xUrn = (20 * width_screen) / 100;
        int yUrn = (3*height_screen) / 100;

        // Масштабируем изображение с помощью AffineTransform
        AffineTransform atHand = new AffineTransform();
        atHand.translate(xHand, yHand);
        atHand.scale((double) newHandWidth / handWidth, (double) newHandHeight / handHeight);

        AffineTransform atPlus = new AffineTransform();
        atPlus.translate(xPlus, yPlus);
        atPlus.scale((double) newPlusWidth / plusWidth, (double) newPlusHeight / plusHeight);

        AffineTransform atArrow = new AffineTransform();
        atArrow.translate(xArrow, yArrow);
        atArrow.scale((double) newArrowWidth / arrowWidth, (double) newArrowHeight / arrowHeight);

        AffineTransform atUrn = new AffineTransform();
        atUrn.translate(xUrn, yUrn);
        atUrn.scale((double) newUrnWidth / urnWidth, (double) newUrnHeight / urnHeight);

        BufferedImage handImageToDraw = handImage;
        BufferedImage plusImageToDraw = plusImage;
        BufferedImage arrowImageToDraw = arrowImage;
        BufferedImage urnImageToDraw = urnImage;

        if (handButtonPressed) {
            handImageToDraw = handImagePressed;
        } else if (handButtonHovered) {
            handImageToDraw = handImageHovered;
        }

        if (plusButtonPressed) {
            plusImageToDraw = plusImagePressed;
        } else if (plusButtonHovered) {
            plusImageToDraw = plusImageHovered;
        }

        if (arrowButtonPressed) {
            arrowImageToDraw = arrowImagePressed;
        } else if (arrowButtonHovered) {
            arrowImageToDraw = arrowImageHovered;
        }

        if (urnButtonPressed) {
            urnImageToDraw = urnImagePressed;
        } else if (urnButtonHovered) {
            urnImageToDraw = urnImageHovered;
        }

        g2d.drawRenderedImage(plusImageToDraw, atPlus);
        g2d.drawRenderedImage(urnImageToDraw, atUrn);
        g2d.drawRenderedImage(arrowImageToDraw, atArrow);
        g2d.drawRenderedImage(handImageToDraw, atHand);

        // Update button positions and sizes
        handButton.setBounds(xHand, yHand, newHandWidth, newHandHeight);
        plusButton.setBounds(xPlus, yPlus, newPlusWidth, newPlusHeight);
        arrowButton.setBounds(xArrow, yArrow, newArrowWidth, newArrowHeight);
        urnButton.setBounds(xUrn, yUrn, newUrnWidth, newUrnHeight);
    }

//    private static BufferedImage changeImageColor(BufferedImage src, Color color) {
//        BufferedImage result = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_ARGB);
//
//
//        // Rescale operation to change the image color
//        float[] scales = { color.getRed()/255f, color.getGreen()/255f, color.getBlue()/255f, color.getAlpha()/255f};
//        float[] offsets = {255f, 255f, 255f, 0f};
//
//
//        RescaleOp op = new RescaleOp(scales, offsets, null);
//        op.filter(src, result);
//
//        return result;
//    }

    public boolean isHandButtonPressed() {
        return handButtonPressed;
    }

    public boolean isPlusButtonPressed() {
        return plusButtonPressed;
    }

    public boolean isUrnButtonPressed() {
        return urnButtonPressed;
    }

    public boolean isArrowButtonPressed() {
        return arrowButtonPressed;
    }

}
