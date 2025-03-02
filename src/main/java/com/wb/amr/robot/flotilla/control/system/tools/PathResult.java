package com.wb.amr.robot.flotilla.control.system.tools;

import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Getter
public class PathResult {
    private final List<Node> nodes;
    private final List<Edge> edges;
    private final Map<Integer, Object> path;

    public PathResult(List<Node> nodes, List<Edge> edges) {
        this.nodes = nodes;
        this.edges = edges;
        this.path = addHolePath(nodes, edges);
    }

    private Map<Integer, Object> addHolePath(List<Node> nodes, List<Edge> edges) {
        Map<Integer, Object> p = new HashMap<>();
        for (int i = 0; i < nodes.size(); i++) {
            if (i == 0) {
                p.put(i, nodes.get(i));
            } else if (i % 2 == 0) {
                p.put(i, nodes.get(i));
            } else {
                p.put(i, edges.get(i - 1));
            }
        }
        return p;
    }


    @Override
    public int hashCode() {
        int result = 0;
        result = result * 31 + (!nodes.isEmpty() ? nodes.hashCode() : 0);
        result = result * 31 + (!edges.isEmpty() ? edges.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof PathResult pathResult)) return false;
        if (!Objects.equals(pathResult.getEdges().hashCode(), this.getEdges().hashCode())) return false;
        if (!Objects.equals(pathResult.getNodes().hashCode(), this.getNodes().hashCode())) return false;
        return !Objects.equals(pathResult.getPath().hashCode(), this.getPath().hashCode());
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
