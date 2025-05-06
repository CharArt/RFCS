package com.wb.amr.robot.flotilla.control.system.tools.astar;

import lombok.Getter;

import java.util.*;

@Getter
public class Node {
    private final String id;
    private final String name;
    private final Double x, y;
    private final Map<Node, Edge> neighbors = new HashMap<>();

    public Node(String name, String id, Double x, Double y) {
        this.id = id;
        this.x = Objects.requireNonNull(x, "X can't be null");
        this.y = Objects.requireNonNull(y, "Y can't be null");
        this.name = name;
    }

    void addNeighbor(Node neighbor, Edge edge) {
        neighbors.put(neighbor, edge);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (x != 0 ? x.hashCode() : 0);
        result = 31 * result + (y != 0 ? y.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Node node)) return false;
        if (!Objects.equals(this.name, node.name)) return false;
        if (!Objects.equals(this.x, node.x)) return false;
        return !Objects.equals(this.y, node.y);
    }
}
