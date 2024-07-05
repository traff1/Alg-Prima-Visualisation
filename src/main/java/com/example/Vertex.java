package com.example;

import java.awt.*;
import java.io.Serializable;
import java.util.UUID;

public class Vertex extends Point implements Serializable{
    int number;
    String colorV = "Black";
    double relativeX;
    double relativeY;
    private static final int VERTEX_RADIUS = 25;
    private static final double EDGE_ADJUSTMENT_FACTOR = 1.1;

    Vertex(Point point, int number, int screenWidth, int screenHeight) {
        super(point);
        this.number = number;
        this.relativeX = (double) point.x / screenWidth;
        this.relativeY = (double) point.y / screenHeight;
    }

    void setLocation(Point point, int screenWidth, int screenHeight) {
        this.x = point.x;
        this.y = point.y;
        this.relativeX = (double) point.x / screenWidth;
        this.relativeY = (double) point.y / screenHeight;
    }

    Point getAbsoluteLocation(int screenWidth, int screenHeight) {
        return new Point((int) (relativeX * screenWidth), (int) (relativeY * screenHeight));
    }

    boolean contains(Point p, int screenWidth, int screenHeight) {
        Point absoluteLocation = getAbsoluteLocation(screenWidth, screenHeight);
        double dx = p.x - absoluteLocation.x;
        double dy = p.y - absoluteLocation.y;
        return dx * dx + dy * dy <= VERTEX_RADIUS * VERTEX_RADIUS;
    }

    void updateAbsoluteCoordinates(int screenWidth, int screenHeight) {
        this.x = (int) (relativeX * screenWidth);
        this.y = (int) (relativeY * screenHeight);
    }

    int getNumber() {
        return number;
    }

    String getColorV() {
        return colorV;
    }

    void setColorV(String colorV) {
        this.colorV = colorV;
    }
}