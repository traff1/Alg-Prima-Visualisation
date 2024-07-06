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
    private ArrayList<String> steps = new ArrayList<>();
    public Graph graph;
    public ArrayList<Edge> mst = new ArrayList<>();;
    private Vertex hoveredVertex = null;
    private Vertex selectedVertex = null;
    private Edge selectedEdge = null;
    private Edge hoveredEdge = null;
    private CalculatorNavigation navigation;
    private ToolBarItems toolBarItems;
    private int vertexCounter = 0;
    private static final int VERTEX_RADIUS = 25;
    private static final double EDGE_ADJUSTMENT_FACTOR = 1.1;
    public boolean actionMenu = false;
    public boolean actionRun = false;
    public boolean actionSteps = false;
    public boolean actionDownload = false;
    public boolean actionSave = false;
    public boolean actionPreviousStep = false;
    public boolean actionNextStep = false;

    private boolean algPrimaSelected = false;

    public RoundedRectangleCalculatorComponent(CalculatorNavigation navigation) {
        this.navigation = navigation;


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

        if (e.getSource() == navigation.plusButton || e.getSource() == navigation.urnButton || e.getSource() == navigation.arrowButton) {
            resetMst();
        }

        if (navigation.isPlusButtonPressed()) {
            if (e.getX() > (int) (getWidth() * 5 / 100) && e.getY() > (int) (19.5 * getHeight() / 100) && (e.getX() < (int) (getWidth() * 62.5 / 100) && e.getY() < (int) (79.5 * getHeight() / 100))) {
                Vertex vertex = new Vertex(e.getPoint(), ++vertexCounter, screenWidth, screenHeight);
                vertices.add(vertex);
                repaint();
            }
        } else if (navigation.isUrnButtonPressed()) {
            resetMst();
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
            resetMst();
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
        } else {
            selectedVertex = null;
            resetMst(); // Сбрасываем MST при нажатии на пустое место
        }
    }



    private void handleMouseReleased(MouseEvent e) {
        if (navigation.isHandButtonPressed()) {
            selectedVertex = null;
        } else {
            resetMst(); // Сбрасываем MST при отпускании мыши, если не выбрана кнопка "Рука"
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
        Point start = calculateIntersection(edge.start, edge.end);
        Point end = calculateIntersection(edge.end, edge.start);

        double distance = ptSegDist(start.x, start.y, end.x, end.y, p.x, p.y);
        return distance <= 3.0;
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
            } else if (edge.isInMst()) {
                g2d.setColor(Color.WHITE); // Рисуем MST рёбра белым цветом
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
                if (edge.equals(hoveredEdge)) {
                    g2d.setColor(Color.LIGHT_GRAY);
                    g2d.fillRect(rectX, rectY, rectWidth, rectHeight);

                    g2d.setColor(Color.BLACK);
                    FontMetrics fm = g2d.getFontMetrics();
                    String weightStr = String.valueOf(edge.weight);
                    int textWidth = fm.stringWidth(weightStr);
                    int textHeight = fm.getHeight();
                    g2d.drawString(weightStr, rectX + (rectWidth - textWidth) / 2, rectY + (rectHeight + textHeight) / 2 - 5);
                } else if (edge.isInMst()) {
                    g2d.setColor(Color.WHITE);
                    g2d.fillRect(rectX, rectY, rectWidth, rectHeight);

                    g2d.setColor(Color.BLACK);
                    FontMetrics fm = g2d.getFontMetrics();
                    String weightStr = String.valueOf(edge.weight);
                    int textWidth = fm.stringWidth(weightStr);
                    int textHeight = fm.getHeight();
                    g2d.drawString(weightStr, rectX + (rectWidth - textWidth) / 2, rectY + (rectHeight + textHeight) / 2 - 5);
                } else {
                    g2d.setColor(Color.BLACK);
                    g2d.fillRect(rectX, rectY, rectWidth, rectHeight);

                    g2d.setColor(Color.WHITE);
                    FontMetrics fm = g2d.getFontMetrics();
                    String weightStr = String.valueOf(edge.weight);
                    int textWidth = fm.stringWidth(weightStr);
                    int textHeight = fm.getHeight();
                    g2d.drawString(weightStr, rectX + (rectWidth - textWidth) / 2, rectY + (rectHeight + textHeight) / 2 - 5);
                }

                g2d.setTransform(originalTransform);
            }
        }

        for (Vertex vertex : vertices) {
            Point absoluteLocation = vertex.getAbsoluteLocation(getWidth(), getHeight());
            if (vertex.equals(hoveredVertex) || vertex.equals(selectedVertex)) {
                g2d.setColor(Color.LIGHT_GRAY);
            } else if (isVertexInMst(vertex)) {
                g2d.setColor(Color.WHITE);
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

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Kodchasan-Bold", Font.PLAIN, 18));
        int yPosition = 20*height_screen/100;  // Начальная вертикальная позиция для текста
        for (String step : steps) {
            g2d.drawString(step, (int) (68.5*width_screen/100), yPosition);  // Рисование строки на экране
            yPosition += 20;  // Смещение вниз для следующей строки
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

    public void showAlgorithmSelectionDialog() {
        String[] algorithms = {"Prim's Algorithm"};
        String selectedAlgorithm = (String) JOptionPane.showInputDialog(
                null,
                "Select the algorithm to use:",
                "Algorithm Selection",
                JOptionPane.QUESTION_MESSAGE,
                null,
                algorithms,
                algorithms[0]
        );

        if (selectedAlgorithm != null) {
            JOptionPane.showMessageDialog(null, "You selected: " + selectedAlgorithm);
            // Process the selected algorithm (for now just Prim's Algorithm)
            if (selectedAlgorithm.equals("Prim's Algorithm")) {
                // Call the function that runs Prim's Algorithm
                algPrimaSelected = true;
            }
        }
    }

    public void saveGraphToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            // Сохраняем количество вершин
            writer.write(vertices.size() + "\n");
            for (Vertex vertex : vertices) {
                writer.write(vertex.number + " " + vertex.relativeX + " " + vertex.relativeY + "\n");
            }
            // Сохраняем количество рёбер
            writer.write(edges.size() + "\n");
            for (Edge edge : edges) {
                writer.write(edge.start.number + " " + edge.end.number + " " + edge.weight + "\n");
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadGraphFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            vertices.clear();
            edges.clear();

            // Считываем количество вершин
            int vertexCount = Integer.parseInt(reader.readLine().trim());
            for (int i = 0; i < vertexCount; i++) {
                String[] vertexData = reader.readLine().split(" ");
                int number = Integer.parseInt(vertexData[0]);
                double relativeX = Double.parseDouble(vertexData[1]);
                double relativeY = Double.parseDouble(vertexData[2]);
                Vertex vertex = new Vertex(new Point((int)(relativeX * getWidth()), (int)(relativeY * getHeight())), number, getWidth(), getHeight());
                vertices.add(vertex);
            }

            // Считываем количество рёбер
            int edgeCount = Integer.parseInt(reader.readLine().trim());
            for (int i = 0; i < edgeCount; i++) {
                String[] edgeData = reader.readLine().split(" ");
                int startNumber = Integer.parseInt(edgeData[0]);
                int endNumber = Integer.parseInt(edgeData[1]);
                int weight = Integer.parseInt(edgeData[2]);
                Vertex startVertex = findVertexByNumber(startNumber);
                Vertex endVertex = findVertexByNumber(endNumber);
                if (startVertex != null && endVertex != null) {
                    edges.add(new Edge(startVertex, endVertex, weight, getWidth(), getHeight()));
                }
            }

            repaint();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Vertex findVertexByNumber(int number) {
        for (Vertex vertex : vertices) {
            if (vertex.number == number) {
                return vertex;
            }
        }
        return null;
    }

    public Graph getGraph(){
        return graph = new Graph(this.vertices, this.edges);
    }

    void setMst(ArrayList<Edge> edges){
        mst = edges;
        steps.clear();
        for (Edge edge : this.edges) {
            edge.setInMst(false); // Сначала сбросим поле для всех рёбер
        }
        for (Edge edge : mst) {
            edge.setInMst(true); // Установим поле для рёбер из MST
        }
        String start = String.format("Algorithm started!");
        steps.add(start);
        for (int i = 0; i < mst.size(); i++) {
            String step = String.format("Step №%d: Edge: %d - %d", i+1, mst.get(i).getStartVertex().getNumber(), mst.get(i).getEndVertex().getNumber());
            steps.add(step);  // Сохраняем шаги
        }
        if (mst.size() == vertices.size()-1){
            String end = String.format("Algorithm ended!");
            steps.add(end);
        }
        repaint(); // Перерисовать компонент для отображения изменений
    }

    private boolean isVertexInMst(Vertex vertex) {
        for (Edge edge : mst) {
            if (edge.start.equals(vertex) || edge.end.equals(vertex)) {
                return true;
            }
        }
        return false;
    }

    public void resetMst() {
        mst.clear();
        for (Edge edge : edges) {
            edge.setInMst(false);
        }
        repaint();
    }


    public static double ptSegDist(double x1, double y1, double x2, double y2, double px, double py) {
        double x2x1 = x2 - x1;
        double y2y1 = y2 - y1;
        double x1px = x1 - px;
        double y1py = y1 - py;
        double dotprod = x1px * x2x1 + y1py * y2y1;
        double projlenSq = dotprod * dotprod / (x2x1 * x2x1 + y2y1 * y2y1);
        double lenSq = x1px * x1px + y1py * y1py - projlenSq;
        if (projlenSq < 0.0) {
            return Math.sqrt(x1px * x1px + y1py * y1py);
        } else if (projlenSq > x2x1 * x2x1 + y2y1 * y2y1) {
            double x2px = x2 - px;
            double y2py = y2 - py;
            return Math.sqrt(x2px * x2px + y2py * y2py);
        }
        return Math.sqrt(lenSq);
    }

}
