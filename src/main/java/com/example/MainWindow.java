package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainWindow {
    private static final Logger LOGGER = Logger.getLogger(MainWindow.class.getName());

    public static void run() {
        JFrame frame = new JFrame();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize(); // Getting screen size

        Color backgroundColor = new Color(37, 37, 37);  // background color
        // frame size and bounds
        frame.setBounds(screenSize.width / 2 - (screenSize.width * 55) / 200, screenSize.height / 2 - (screenSize.height * 58) / 200, (screenSize.width * 55) / 100, (screenSize.height * 58) / 100);
        frame.setMinimumSize(new Dimension(800, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // Path to the font file
        String fontPath = "./src/main/resources/fonts/Kodchasan/Kodchasan-Bold.ttf";

        try {
            // Load the custom font
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File(fontPath)).deriveFont(24f);

            // Register the font
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);

        } catch (FontFormatException | IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load custom font", e);
        }

        // Create a panel with OverlayLayout to hold the components
        JLayeredPane overlayPanel = new JLayeredPane();
        overlayPanel.setLayout(new OverlayLayout(overlayPanel));

        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setBackground(backgroundColor);
        overlayPanel.add(backgroundPanel, JLayeredPane.DEFAULT_LAYER);

        CalculatorNavigation calculatorNavigation = new CalculatorNavigation();
        ToolBarItems toolBarItems = new ToolBarItems();
        RoundedRectangleCalculatorComponent roundedRectangleCalculatorComponent = new RoundedRectangleCalculatorComponent(calculatorNavigation, toolBarItems);
        RoundedRectangleStepComponent roundedRectangleStepComponent = new RoundedRectangleStepComponent();
        RoundedRectangleTollBarComponent roundedRectangleTollBarComponent = new RoundedRectangleTollBarComponent();
        DelimiterLineCalculator delimiterLineCalculatorLine = new DelimiterLineCalculator();
        DelimiterLineSteps delimiterLineSteps = new DelimiterLineSteps();
        DelimiterLineToolBar delimiterLineToolBar = new DelimiterLineToolBar();
        StepsText stepsText = new StepsText();

        overlayPanel.add(roundedRectangleCalculatorComponent, JLayeredPane.PALETTE_LAYER);
        overlayPanel.add(roundedRectangleStepComponent, JLayeredPane.PALETTE_LAYER);
        overlayPanel.add(roundedRectangleTollBarComponent, JLayeredPane.PALETTE_LAYER);
        overlayPanel.add(delimiterLineCalculatorLine, JLayeredPane.MODAL_LAYER);
        overlayPanel.add(delimiterLineSteps, JLayeredPane.MODAL_LAYER);
        overlayPanel.add(delimiterLineToolBar, JLayeredPane.MODAL_LAYER);
        overlayPanel.add(calculatorNavigation, JLayeredPane.MODAL_LAYER);
        overlayPanel.add(toolBarItems, JLayeredPane.MODAL_LAYER);
        overlayPanel.add(stepsText, JLayeredPane.MODAL_LAYER);


        // Add the panel to the frame
        frame.add(overlayPanel);

        frame.setVisible(true);
    }


}



