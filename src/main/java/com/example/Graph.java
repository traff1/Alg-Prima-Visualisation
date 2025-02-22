package com.example;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class Graph implements Serializable {
    private ArrayList<Vertex> vertices = new ArrayList<>();
    private ArrayList<Edge> edges = new ArrayList<>();

    public Graph(ArrayList<Vertex>verticesIn, ArrayList<Edge>edgesIn) {
        this.vertices = verticesIn;
        this.edges = edgesIn;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public boolean isConnected() {
        if (vertices.isEmpty()) {
            return true;
        }

        Set<Vertex> visited = new HashSet<>();
        Stack<Vertex> stack = new Stack<>();

        stack.push(vertices.get(0));
        while (!stack.isEmpty()) {
            Vertex current = stack.pop();
            if (!visited.contains(current)) {
                visited.add(current);
                for (Edge edge : edges) {
                    if (edge.getStartVertex().equals(current) && !visited.contains(edge.getEndVertex())) {
                        stack.push(edge.getEndVertex());
                    } else if (edge.getEndVertex().equals(current) && !visited.contains(edge.getStartVertex())) {
                        stack.push(edge.getStartVertex());
                    }
                }
            }
        }
        return visited.size() == vertices.size();
    }

    public ArrayList<Edge> runPrimsAlgorithm() {
        if (vertices.isEmpty()) {
            return new ArrayList<>();
        }

        if (vertices.isEmpty() || !this.isConnected()) {
            return new ArrayList<>();
        }

        ArrayList<Edge> mst = new ArrayList<>();
        Set<Vertex> visited = new HashSet<>();
        ArrayList<Edge> availableEdges = new ArrayList<>();

        Vertex startVertex = vertices.get(0);
        visited.add(startVertex);

        while (visited.size() < vertices.size()) {
            availableEdges.clear(); // Очищаем список доступных рёбер для каждой итерации

            for (Vertex vertex : visited) {
                for (Edge edge : edges) {
                    if ((edge.getStartVertex().equals(vertex) && !visited.contains(edge.getEndVertex())) ||
                            (edge.getEndVertex().equals(vertex) && !visited.contains(edge.getStartVertex()))) {
                        availableEdges.add(edge);
                    }
                }
            }

            Edge minEdge = null;
            for (Edge edge : availableEdges) {
                if (minEdge == null || edge.getWeight() < minEdge.getWeight()) {
                    minEdge = edge;
                }
            }

            if (minEdge != null) {
                mst.add(minEdge);
                Vertex newVertex = visited.contains(minEdge.getStartVertex()) ? minEdge.getEndVertex() : minEdge.getStartVertex();
                visited.add(newVertex);
            } else {
                break; // Если нет доступных рёбер, алгоритм завершен
            }
        }

        return mst;
    }
}
