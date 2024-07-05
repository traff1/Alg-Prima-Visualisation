package com.example;

import java.awt.*;
import java.io.Serializable;

public class Edge {
    Vertex start;
    Vertex end;
    String colorE = "Black";
    int weight;
    int screenWidth;
    int screenHeight;
    private boolean isInMst = false;

    Edge(Vertex start, Vertex end, int weight, int screenWidth, int screenHeight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    void updateAbsoluteCoordinates(int screenWidth, int screenHeight) {
        // Обновляем только координаты стартовой и конечной вершины
        this.start.updateAbsoluteCoordinates(screenWidth, screenHeight);
        this.end.updateAbsoluteCoordinates(screenWidth, screenHeight);
    }

    Point getStart() {
        return start.getAbsoluteLocation(screenWidth, screenHeight);
    }

    Point getEnd() {
        return end.getAbsoluteLocation(screenWidth, screenHeight);
    }

    double ptSegDist(Point p) {
        Point startLoc = start.getAbsoluteLocation(screenWidth, screenHeight);
        Point endLoc = end.getAbsoluteLocation(screenWidth, screenHeight);

        double dx = endLoc.x - startLoc.x;
        double dy = endLoc.y - startLoc.y;
        double len = Math.sqrt(dx * dx + dy * dy);
        if (len == 0.0) {
            return p.distance(startLoc);
        }
        double t = ((p.x - startLoc.x) * dx + (p.y - startLoc.y) * dy) / (len * len);
        if (t < 0.0) {
            return p.distance(startLoc);
        }
        if (t > 1.0) {
            return p.distance(endLoc);
        }
        double closestX = startLoc.x + t * dx;
        double closestY = startLoc.y + t * dy;
        return p.distance(closestX, closestY);
    }
    Vertex getStartVertex() {
        return start;
    }
    Vertex getEndVertex() {
        return end;
    }
    int getWeight() {
        return weight;
    }

    String getColorE() {
        return colorE;
    }

    void setColorE(String colorE) {
        this.colorE = colorE;
    }

    public void setInMst(boolean isInMst) {
        this.isInMst = isInMst;
    }

    public boolean isInMst() {
        return isInMst;
    }

}
