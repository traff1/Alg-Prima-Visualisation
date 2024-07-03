package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.io.*;

public class RoundedRectangleCalculatorComponent extends JComponent {

    private ArrayList<Vertex> vertices = new ArrayList<>();
    private ArrayList<Edge> edges = new ArrayList<>();
    private Vertex hoveredVertex = null;
    private Vertex selectedVertex = null;
    private Edge hoveredEdge = null;
    private CalculatorNavigation navigation;
    private ToolBarItems toolBarItems;
    private int vertexCounter = 0;
    private static final int VERTEX_RADIUS = 25;
    private static final double EDGE_ADJUSTMENT_FACTOR = 1.1;

    public RoundedRectangleCalculatorComponent(CalculatorNavigation navigation, ToolBarItems toolBarItems) {
        this.navigation = navigation;
        this.toolBarItems = toolBarItems;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleMousePressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                handleMouseReleased(e);
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                handleMouseDragged(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                handleMouseMoved(e);
            }
        });

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                handleResize();
            }
        });
    }

    private void handleMousePressed(MouseEvent e) {
        int screenWidth = getWidth();
        int screenHeight = getHeight();

        if (navigation.isPlusButtonPressed()) {
            if (e.getX() > (int) (getWidth() * 5 / 100) && e.getY() > (int) (19.5 * getHeight() / 100) && (e.getX() < (int) (getWidth() * 62.5 / 100) && e.getY() < (int) (79.5 * getHeight() / 100))) {
                Vertex vertex = new Vertex(e.getPoint(), ++vertexCounter, screenWidth, screenHeight);
                vertices.add(vertex);
                repaint();
            }
        } else if (navigation.isUrnButtonPressed()) {
            Vertex vertexToRemove = null;
            for (Vertex vertex : vertices) {
                if (vertex.contains(e.getPoint(), screenWidth, screenHeight)) {
                    vertexToRemove = vertex;
                    break;
                }
            }
            if (vertexToRemove != null) {
                final Vertex finalVertexToRemove = vertexToRemove; // Делаем final
                vertices.remove(finalVertexToRemove);
                edges.removeIf(edge -> edge.start.equals(finalVertexToRemove) || edge.end.equals(finalVertexToRemove));
                repaint();
            } else {
                Edge edgeToRemove = null;
                for (Edge edge : edges) {
                    if (isPointOnEdge(e.getPoint(), edge)) {
                        edgeToRemove = edge;
                        break;
                    }
                }
                if (edgeToRemove != null) {
                    edges.remove(edgeToRemove);
                    repaint();
                }
            }
        } else if (navigation.isHandButtonPressed()) {
            for (Vertex vertex : vertices) {
                if (vertex.contains(e.getPoint(), screenWidth, screenHeight)) {
                    selectedVertex = vertex;
                    break;
                }
            }
        } else if (navigation.isArrowButtonPressed()) {
            boolean vertexClicked = false;
            for (Vertex vertex : vertices) {
                if (vertex.contains(e.getPoint(), screenWidth, screenHeight)) {
                    if (selectedVertex == null) {
                        selectedVertex = vertex;
                        vertexClicked = true; // Указываем, что была найдена вершина
                        break;
                    } else {
                        int weight = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter weight for the edge:", "Edge Weight", JOptionPane.PLAIN_MESSAGE));
                        edges.add(new Edge(selectedVertex, vertex, weight, screenWidth, screenHeight));
                        selectedVertex = null;
                        repaint();
                        vertexClicked = true; // Указываем, что была найдена вершина
                        break;
                    }
                }
            }

            // Если не было найдено ни одной вершины при клике, просто выходим из условия
            if (!vertexClicked) {
                selectedVertex = null;
            }
        } else if (toolBarItems.isDownloadButtonPressed()) {
            deserializeGraph("./src/main/resources/graph_data.ser");
        } else if (toolBarItems.isUploadButtonPressed()) {
            System.out.println("gsadg");
            serializeGraph(vertices, edges, "./src/main/resources/graph_data.ser");
        } else {
            selectedVertex = null;
        }
    }

    private void handleMouseReleased(MouseEvent e) {
        if (navigation.isHandButtonPressed()) {
            selectedVertex = null;
        }
    }

    private void handleMouseDragged(MouseEvent e) {
        int screenWidth = getWidth();
        int screenHeight = getHeight();

        if (selectedVertex != null && navigation.isHandButtonPressed()
                && e.getX() > (getWidth() * 5 / 100) && e.getY() > (int) (19.5 * getHeight() / 100)
                && (e.getX() < (int) (getWidth() * 62.5 / 100) && e.getY() < (int) (79.5 * getHeight() / 100))) {

            selectedVertex.setLocation(e.getPoint(), screenWidth, screenHeight);

            // Обновляем только координаты рёбер, которые связаны с перемещаемой вершиной
            for (Edge edge : edges) {
                if (edge.start.equals(selectedVertex) || edge.end.equals(selectedVertex)) {
                    edge.updateAbsoluteCoordinates(screenWidth, screenHeight);
                }
            }
            repaint();
        }
    }


    private void handleMouseMoved(MouseEvent e) {
        int screenWidth = getWidth();
        int screenHeight = getHeight();

        hoveredVertex = null;
        hoveredEdge = null;

        for (Vertex vertex : vertices) {
            if (vertex.contains(e.getPoint(), screenWidth, screenHeight)) {
                hoveredVertex = vertex;
                break;
            }
        }

        if (hoveredVertex == null) {
            for (Edge edge : edges) {
                if (isPointOnEdge(e.getPoint(), edge)) {
                    hoveredEdge = edge;
                    break;
                }
            }
        }

        repaint();
    }

    private void handleResize() {
        int screenWidth = getWidth();
        int screenHeight = getHeight();

        for (Vertex vertex : vertices) {
            vertex.updateAbsoluteCoordinates(screenWidth, screenHeight);
        }

        for (Edge edge : edges) {
            edge.updateAbsoluteCoordinates(screenWidth, screenHeight);
        }

        repaint();
    }


    private boolean isPointOnEdge(Point p, Edge edge) {
        double distance = edge.ptSegDist(p);
        return distance < 5.0;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setFont(new Font("Kodchasan", Font.BOLD, 18));

        int width_screen = getWidth();
        int height_screen = getHeight();

        int x = (2 * width_screen) / 100;
        int y = (2 * height_screen) / 100;

        int width = (64 * width_screen) / 100;
        int height = (80 * height_screen) / 100;

        int arcWidth = 45;
        int arcHeight = 45;

        g2d.setColor(new Color(102, 101, 101)); // Полупрозрачный цвет фона
        g2d.fillRoundRect(x, y, width, height, arcWidth, arcHeight);

        for (Edge edge : edges) {
            Point start = calculateIntersection(edge.start, edge.end);
            Point end = calculateIntersection(edge.end, edge.start);

            if (edge.equals(hoveredEdge)) {
                g2d.setColor(Color.LIGHT_GRAY);
            } else {
                g2d.setColor(Color.BLACK);
            }
            g2d.setStroke(new BasicStroke(3));
            g2d.drawLine(start.x, start.y, end.x, end.y);

            if (edge.weight > 0) {
                int rectWidth = calculateRectWidth(edge.weight);
                int rectHeight = 20;
                int rectX = (start.x + end.x - rectWidth) / 2;
                int rectY = (start.y + end.y - rectHeight) / 2;

                double angle = Math.atan2(end.y - start.y, end.x - start.x);

                if (angle > Math.PI / 2 || angle < -Math.PI / 2) {
                    angle += Math.PI;
                }

                AffineTransform originalTransform = g2d.getTransform();

                g2d.rotate(angle, rectX + rectWidth / 2, rectY + rectHeight / 2);

                g2d.setColor(Color.BLACK);
                g2d.fillRect(rectX, rectY, rectWidth, rectHeight);

                g2d.setColor(Color.WHITE);
                FontMetrics fm = g2d.getFontMetrics();
                String weightStr = String.valueOf(edge.weight);
                int textWidth = fm.stringWidth(weightStr);
                int textHeight = fm.getHeight();
                g2d.drawString(weightStr, rectX + (rectWidth - textWidth) / 2, rectY + (rectHeight + textHeight) / 2 - 5);

                g2d.setTransform(originalTransform);
            }
        }

        for (Vertex vertex : vertices) {
            Point absoluteLocation = vertex.getAbsoluteLocation(getWidth(), getHeight());

            if (vertex.equals(hoveredVertex) || vertex.equals(selectedVertex)) {
                g2d.setColor(Color.LIGHT_GRAY);
            } else {
                g2d.setColor(Color.BLACK);
            }
            g2d.setStroke(new BasicStroke(3));
            g2d.drawOval(absoluteLocation.x - VERTEX_RADIUS, absoluteLocation.y - VERTEX_RADIUS, VERTEX_RADIUS * 2, VERTEX_RADIUS * 2);

            g2d.setColor(Color.WHITE);
            FontMetrics fm = g2d.getFontMetrics();
            String numberStr = String.valueOf(vertex.number);
            int textWidth = fm.stringWidth(numberStr);
            int textHeight = fm.getHeight();
            g2d.drawString(numberStr, absoluteLocation.x - (textWidth / 2), absoluteLocation.y + (textHeight / 4));
        }
    }

    private Point calculateIntersection(Vertex v1, Vertex v2) {
        Point v1Loc = v1.getAbsoluteLocation(getWidth(), getHeight());
        Point v2Loc = v2.getAbsoluteLocation(getWidth(), getHeight());

        double dx = v2Loc.x - v1Loc.x;
        double dy = v2Loc.y - v1Loc.y;
        double dist = Math.sqrt(dx * dx + dy * dy);
        double scale = VERTEX_RADIUS * EDGE_ADJUSTMENT_FACTOR / dist;
        return new Point((int) (v1Loc.x + dx * scale), (int) (v1Loc.y + dy * scale));
    }

    private int calculateRectWidth(int weight) {
        int digits = String.valueOf(weight).length();
        return digits * 10 + 25;
    }

    public void serializeGraph(ArrayList<Vertex> vertices, ArrayList<Edge> edges, String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(vertices);
            out.writeObject(edges);
            System.out.println("Граф успешно сохранён в файл " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deserializeGraph(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            ArrayList<Vertex> vertices = (ArrayList<Vertex>) in.readObject();
            ArrayList<Edge> edges = (ArrayList<Edge>) in.readObject();
            System.out.println("Граф успешно загружен из файла " + filename);
            // Используйте загруженные vertices и edges для восстановления состояния вашего графа
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
