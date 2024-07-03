package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.RenderedImage;
import java.awt.image.RescaleOp;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.AffineTransformOp;


public class ToolBarItems extends JComponent {
    private BufferedImage menuImage;
    private BufferedImage runImage;
    private BufferedImage stepsImage;
    private BufferedImage downloadImage;
    private BufferedImage uploadImage;
    private BufferedImage rightArrowImage;
    private BufferedImage leftArrowImage;

    private BufferedImage menuImageHovered;
    private BufferedImage runImageHovered;
    private BufferedImage stepsImageHovered;
    private BufferedImage downloadImageHovered;
    private BufferedImage uploadImageHovered;
    private BufferedImage rightArrowImageHovered;
    private BufferedImage leftArrowImageHovered;


    private BufferedImage menuImagePressed;
    private BufferedImage runImagePressed;
    private BufferedImage stepsImagePressed;
    private BufferedImage downloadImagePressed;
    private BufferedImage uploadImagePressed;
    private BufferedImage rightArrowImagePressed;
    private BufferedImage leftArrowImagePressed;

    private JButton menuButton;
    private JButton runButton;
    private JButton stepsButton;
    private JButton downloadButton;
    private JButton uploadButton;
    private JButton rightArrowButton;
    private JButton leftArrowButton;

    private boolean menuButtonPressed = false;
    private boolean runButtonPressed = false;
    private boolean stepsButtonPressed = false;
    private boolean downloadButtonPressed = false;
    private boolean uploadButtonPressed = false;
    private boolean rightArrowButtonPressed = false;
    private boolean leftArrowButtonPressed = false;

    private boolean menuButtonHovered = false;
    private boolean runButtonHovered = false;
    private boolean stepsButtonHovered = false;
    private boolean downloadButtonHovered = false;
    private boolean uploadButtonHovered = false;
    private boolean rightArrowButtonHovered = false;
    private boolean leftArrowButtonHovered = false;

    public ToolBarItems() {
        try {
            menuImage = ImageIO.read(new File("./src/main/resources/images/menu.png"));
            runImage = ImageIO.read(new File("./src/main/resources/images/run.png"));
            stepsImage = ImageIO.read(new File("./src/main/resources/images/steps.png"));
            downloadImage = ImageIO.read(new File("./src/main/resources/images/download.png"));
            uploadImage = ImageIO.read(new File("./src/main/resources/images/upload.png"));
            rightArrowImage = ImageIO.read(new File("./src/main/resources/images/arrow.png"));
            leftArrowImage = ImageIO.read(new File("./src/main/resources/images/arrow.png"));

            menuImageHovered = ImageIO.read(new File("./src/main/resources/images/menugray.png"));
            runImageHovered = ImageIO.read(new File("./src/main/resources/images/rungray.png"));
            stepsImageHovered = ImageIO.read(new File("./src/main/resources/images/stepsgray.png"));
            downloadImageHovered = ImageIO.read(new File("./src/main/resources/images/downloadgray.png"));
            uploadImageHovered = ImageIO.read(new File("./src/main/resources/images/uploadgray.png"));
            rightArrowImageHovered = ImageIO.read(new File("./src/main/resources/images/arrowgray.png"));
            leftArrowImageHovered = ImageIO.read(new File("./src/main/resources/images/arrowgray.png"));

            menuImagePressed = ImageIO.read(new File("./src/main/resources/images/menuwhite.png"));
            runImagePressed = ImageIO.read(new File("./src/main/resources/images/runwhite.png"));
            stepsImagePressed = ImageIO.read(new File("./src/main/resources/images/stepswhite.png"));
            downloadImagePressed = ImageIO.read(new File("./src/main/resources/images/downloadwhite.png"));
            uploadImagePressed = ImageIO.read(new File("./src/main/resources/images/uploadwhite.png"));
            rightArrowImagePressed = ImageIO.read(new File("./src/main/resources/images/arrowwhite.png"));
            leftArrowImagePressed = ImageIO.read(new File("./src/main/resources/images/arrowwhite.png"));



            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-leftArrowImage.getWidth(null), 0);

            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            leftArrowImage = op.filter(leftArrowImage, null);

            AffineTransform tx2 = AffineTransform.getScaleInstance(-1, 1);
            tx2.translate(-leftArrowImageHovered.getWidth(null), 0);

            AffineTransform tx3 = AffineTransform.getScaleInstance(-1, 1);
            tx3.translate(-leftArrowImagePressed.getWidth(null), 0);

            AffineTransformOp op2 = new AffineTransformOp(tx2, AffineTransformOp.TYPE_BILINEAR);
            leftArrowImageHovered = op2.filter(leftArrowImageHovered, null);

            AffineTransformOp op3 = new AffineTransformOp(tx3, AffineTransformOp.TYPE_BILINEAR);
            leftArrowImagePressed = op3.filter(leftArrowImagePressed, null);



            // Creating different colors:



        } catch (IOException e){
            System.out.println("Failed to load image: " + e.getMessage());
        }

        setLayout(null);

        menuButton = new JButton();
        runButton = new JButton();
        stepsButton = new JButton();
        downloadButton = new JButton();
        uploadButton = new JButton();
        rightArrowButton = new JButton();
        leftArrowButton = new JButton();

        menuButton.setOpaque(false);
        menuButton.setContentAreaFilled(false);
        menuButton.setBorderPainted(false);
        menuButton.setFocusPainted(false);
        menuButton.setBorder(BorderFactory.createEmptyBorder());

        runButton.setOpaque(false);
        runButton.setContentAreaFilled(false);
        runButton.setBorderPainted(false);
        runButton.setFocusPainted(false);
        runButton.setBorder(BorderFactory.createEmptyBorder());

        stepsButton.setOpaque(false);
        stepsButton.setContentAreaFilled(false);
        stepsButton.setBorderPainted(false);
        stepsButton.setFocusPainted(false);
        stepsButton.setBorder(BorderFactory.createEmptyBorder());

        downloadButton.setOpaque(false);
        downloadButton.setContentAreaFilled(false);
        downloadButton.setBorderPainted(false);
        downloadButton.setFocusPainted(false);
        downloadButton.setBorder(BorderFactory.createEmptyBorder());

        rightArrowButton.setOpaque(false);
        rightArrowButton.setContentAreaFilled(false);
        rightArrowButton.setBorderPainted(false);
        rightArrowButton.setFocusPainted(false);
        rightArrowButton.setBorder(BorderFactory.createEmptyBorder());

        leftArrowButton.setOpaque(false);
        leftArrowButton.setContentAreaFilled(false);
        leftArrowButton.setBorderPainted(false);
        leftArrowButton.setFocusPainted(false);
        leftArrowButton.setBorder(BorderFactory.createEmptyBorder());

        uploadButton.setOpaque(false);
        uploadButton.setContentAreaFilled(false);
        uploadButton.setBorderPainted(false);
        uploadButton.setFocusPainted(false);
        uploadButton.setBorder(BorderFactory.createEmptyBorder());

        ButtonActionListener buttonListener = new ButtonActionListener();
        menuButton.addActionListener(buttonListener);
        runButton.addActionListener(buttonListener);
        stepsButton.addActionListener(buttonListener);
        downloadButton.addActionListener(buttonListener);
        uploadButton.addActionListener(buttonListener);
        rightArrowButton.addActionListener(buttonListener);
        leftArrowButton.addActionListener(buttonListener);

        ButtonHoverListener hoverListener = new ButtonHoverListener();
        menuButton.addMouseListener(hoverListener);
        runButton.addMouseListener(hoverListener);
        stepsButton.addMouseListener(hoverListener);
        downloadButton.addMouseListener(hoverListener);
        uploadButton.addMouseListener(hoverListener);
        rightArrowButton.addMouseListener(hoverListener);
        leftArrowButton.addMouseListener(hoverListener);


        add(menuButton);
        add(runButton);
        add(stepsButton);
        add(downloadButton);
        add(uploadButton);
        add(rightArrowButton);
        add(leftArrowButton);
    }

    private class ButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == menuButton) {
                menuButtonPressed = true;
                runButtonPressed = false;
                stepsButtonPressed = false;
                downloadButtonPressed = false;
                uploadButtonPressed = false;
                rightArrowButtonPressed = false;
                leftArrowButtonPressed = false;
            } else if (e.getSource() == runButton) {
                menuButtonPressed = false;
                runButtonPressed = true;
                stepsButtonPressed = false;
                downloadButtonPressed = false;
                uploadButtonPressed = false;
                rightArrowButtonPressed = false;
                leftArrowButtonPressed = false;
            } else if (e.getSource() == stepsButton) {
                menuButtonPressed = false;
                runButtonPressed = false;
                stepsButtonPressed = true;
                downloadButtonPressed = false;
                uploadButtonPressed = false;
                rightArrowButtonPressed = false;
                leftArrowButtonPressed = false;
            } else if (e.getSource() == downloadButton) {
                menuButtonPressed = false;
                runButtonPressed = false;
                stepsButtonPressed = false;
                downloadButtonPressed = true;
                uploadButtonPressed = false;
                rightArrowButtonPressed = false;
                leftArrowButtonPressed = false;
            } else if (e.getSource() == uploadButton) {
                menuButtonPressed = false;
                runButtonPressed = false;
                stepsButtonPressed = false;
                downloadButtonPressed = false;
                uploadButtonPressed = true;
                rightArrowButtonPressed = false;
                leftArrowButtonPressed = false;
            } else if (e.getSource() == rightArrowButton) {
                menuButtonPressed = false;
                runButtonPressed = false;
                stepsButtonPressed = false;
                downloadButtonPressed = false;
                uploadButtonPressed = false;
                rightArrowButtonPressed = true;
                leftArrowButtonPressed = false;
            } else if (e.getSource() == leftArrowButton) {
                menuButtonPressed = false;
                runButtonPressed = false;
                stepsButtonPressed = false;
                downloadButtonPressed = false;
                uploadButtonPressed = false;
                rightArrowButtonPressed = false;
                leftArrowButtonPressed = true;
            }
            repaint();


            Timer timer = new Timer(300, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    menuButtonPressed = false;
                    runButtonPressed = false;
                    stepsButtonPressed = false;
                    downloadButtonPressed = false;
                    uploadButtonPressed = false;
                    rightArrowButtonPressed = false;
                    leftArrowButtonPressed = false;
                    repaint();
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    private class ButtonHoverListener extends MouseAdapter {

        @Override
        public void mouseEntered(MouseEvent e) {
            if (e.getSource() == menuButton) {
                menuButtonHovered = true;
            } else if (e.getSource() == runButton) {
                runButtonHovered = true;
            } else if (e.getSource() == stepsButton) {
                stepsButtonHovered = true;
            } else if (e.getSource() == downloadButton) {
                downloadButtonHovered = true;
            } else if (e.getSource() == uploadButton) {
                uploadButtonHovered = true;
            } else if (e.getSource() == rightArrowButton) {
                rightArrowButtonHovered = true;
            } else if (e.getSource() == leftArrowButton) {
                leftArrowButtonHovered = true;
            }
            repaint();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (e.getSource() == menuButton) {
                menuButtonHovered = false;
            } else if (e.getSource() == runButton) {
                runButtonHovered = false;
            } else if (e.getSource() == stepsButton) {
                stepsButtonHovered = false;
            } else if (e.getSource() == downloadButton) {
                downloadButtonHovered = false;
            } else if (e.getSource() == uploadButton) {
                uploadButtonHovered = false;
            } else if (e.getSource() == rightArrowButton) {
                rightArrowButtonHovered = false;
            } else if (e.getSource() == leftArrowButton) {
                leftArrowButtonHovered = false;
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

        int menuWidth = menuImage.getWidth();
        int menuHeight = menuImage.getHeight();

        int runWidth = runImage.getWidth();
        int runHeight = runImage.getHeight();

        int stepsWidth = stepsImage.getWidth();
        int stepsHeight = stepsImage.getHeight();

        int downloadWidth = downloadImage.getWidth();
        int downloadHeight = downloadImage.getHeight();

        int uploadWidth = uploadImage.getWidth();
        int uploadHeight = uploadImage.getHeight();

        int rightArrowWidth = rightArrowImage.getWidth();
        int rightArrowHeight = rightArrowImage.getHeight();

        int leftArrowWidth = leftArrowImage.getWidth();
        int leftArrowHeight = leftArrowImage.getHeight();

        int newMenuWidth = (6*width_screen)/100;
        int newMenuHeight = (8*height_screen)/100;

        int newRunWidth = (6*width_screen)/100;
        int newRunHeight = (8*height_screen)/100;

        int newStepsWidth = (6*width_screen)/100;
        int newStepsHeight = (8*height_screen)/100;

        int newDownloadWidth = (6*width_screen)/100;
        int newDownloadHeight = (8*height_screen)/100;

        int newUploadWidth = (int)(5.9*width_screen)/100;
        int newUploadHeight = (int)(9.7*height_screen)/100;

        int newRightArrowWidth = (int)(6.5*width_screen)/100;
        int newRightArrowHeight = (int)(10.2*height_screen)/100;

        int newLeftArrowWidth = (int)(6.5*width_screen)/100;
        int newLeftArrowHeight = (int)(10.2*height_screen)/100;

        int xMenu = (6*width_screen)/100;
        int yMenu = (87*height_screen)/100;

        int xRun = (14*width_screen)/100;
        int yRun = (87*height_screen)/100;

        int xSteps = (22*width_screen)/100;
        int ySteps = (87*height_screen)/100;

        int xDownload = (30*width_screen)/100;
        int yDownload = (87*height_screen)/100;

        int xUpload = (38*width_screen)/100;
        int yUpload = (int)(86.25*height_screen)/100;

        int xRightArrow = (int)(86*width_screen)/100;
        int yRightArrow = (int)(86.5*height_screen)/100;

        int xLeftArrow = (int)(75*width_screen)/100;
        int yLeftArrow = (int)(86.5*height_screen)/100;

        AffineTransform atMenu = new AffineTransform();
        atMenu.translate(xMenu, yMenu);
        atMenu.scale((double) newMenuWidth/menuWidth, (double) newMenuHeight/menuHeight);

        AffineTransform atRun = new AffineTransform();
        atRun.translate(xRun, yRun);
        atRun.scale((double) newRunWidth/runWidth, (double) newRunHeight/runHeight);

        AffineTransform atSteps = new AffineTransform();
        atSteps.translate(xSteps, ySteps);
        atSteps.scale((double) newStepsWidth/stepsWidth, (double) newStepsHeight/stepsHeight);

        AffineTransform atDownload = new AffineTransform();
        atDownload.translate(xDownload, yDownload);
        atDownload.scale((double) newDownloadWidth/downloadWidth, (double) newDownloadHeight/downloadHeight);

        AffineTransform atUpload = new AffineTransform();
        atUpload.translate(xUpload, yUpload);
        atUpload.scale((double) newUploadWidth/uploadWidth, (double) newUploadHeight/uploadHeight);

        AffineTransform atRightArrow = new AffineTransform();
        atRightArrow.translate(xRightArrow, yRightArrow);
        atRightArrow.scale((double) newRightArrowWidth/rightArrowWidth, (double) newRightArrowHeight/rightArrowHeight);

        AffineTransform atLeftArrow = new AffineTransform();
        atLeftArrow.translate(xLeftArrow, yLeftArrow);
        atLeftArrow.scale((double) newLeftArrowWidth/leftArrowWidth, (double) newLeftArrowHeight/leftArrowHeight);

        BufferedImage menuImageToDraw = menuImage;
        BufferedImage runImageToDraw = runImage;
        BufferedImage stepsImageToDraw = stepsImage;
        BufferedImage downloadImageToDraw = downloadImage;
        BufferedImage uploadImageToDraw = uploadImage;
        BufferedImage rightArrowImageToDraw = rightArrowImage;
        BufferedImage leftArrowImageToDraw = leftArrowImage;


        if (menuButtonPressed) {
            menuImageToDraw = menuImagePressed;
        } else if (menuButtonHovered) {
            menuImageToDraw = menuImageHovered;
        }
        if (runButtonPressed) {
            runImageToDraw = runImagePressed;
        } else if (runButtonHovered) {
            runImageToDraw = runImageHovered;
        }
        if (stepsButtonPressed) {
            stepsImageToDraw = stepsImagePressed;
        } else if (stepsButtonHovered) {
            stepsImageToDraw = stepsImageHovered;
        }
        if (downloadButtonPressed) {
            downloadImageToDraw = downloadImagePressed;
        } else if (downloadButtonHovered) {
            downloadImageToDraw = downloadImageHovered;
        }
        if (uploadButtonPressed) {
            uploadImageToDraw = uploadImagePressed;
        } else if (uploadButtonHovered) {
            uploadImageToDraw = uploadImageHovered;
        }
        if (rightArrowButtonPressed) {
            rightArrowImageToDraw = rightArrowImagePressed;
        } else if (rightArrowButtonHovered) {
            rightArrowImageToDraw = rightArrowImageHovered;
        }
        if (leftArrowButtonPressed) {
            leftArrowImageToDraw = leftArrowImagePressed;
        } else if (leftArrowButtonHovered) {
            leftArrowImageToDraw = leftArrowImageHovered;
        }

        g2d.drawRenderedImage(menuImageToDraw, atMenu);
        g2d.drawRenderedImage(runImageToDraw, atRun);
        g2d.drawRenderedImage(stepsImageToDraw, atSteps);
        g2d.drawRenderedImage(downloadImageToDraw, atDownload);
        g2d.drawRenderedImage(uploadImageToDraw, atUpload);
        g2d.drawRenderedImage(rightArrowImageToDraw, atRightArrow);
        g2d.drawRenderedImage(leftArrowImageToDraw, atLeftArrow);

        menuButton.setBounds(xMenu, yMenu, newMenuWidth, newMenuHeight);
        runButton.setBounds(xRun, yRun, newRunWidth, newRunHeight);
        stepsButton.setBounds(xSteps, ySteps, newStepsWidth, newStepsHeight);
        downloadButton.setBounds(xDownload, yDownload, newDownloadWidth, newDownloadHeight);
        uploadButton.setBounds(xUpload, yUpload, newUploadWidth, newUploadHeight);
        rightArrowButton.setBounds(xRightArrow,yRightArrow, newRightArrowWidth, newRightArrowHeight);
        leftArrowButton.setBounds(xLeftArrow,yLeftArrow, newLeftArrowWidth, newLeftArrowHeight);
    }

    boolean isDownloadButtonPressed(){
        return downloadButtonPressed;
    }

    boolean isUploadButtonPressed(){
        return uploadButtonPressed;
    }

}
