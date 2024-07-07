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

    Vertex getStartVertex() {
        return start;
    }
    Vertex getEndVertex() {
        return end;
    }
    int getWeight() {
        return weight;
    }


    public void setInMst(boolean isInMst) {
        this.isInMst = isInMst;
    }

    public boolean isInMst() {
        return isInMst;
    }

}
