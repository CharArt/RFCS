package com.wb.amr.robot.flotilla.control.system.tools.astar;

import com.wb.amr.robot.flotilla.control.system.map.dxf.NeighborPoint;
import com.wb.amr.robot.flotilla.control.system.map.dxf.PointFromDXF;

import java.util.*;

public class WorkMap {

    private List<PointFromDXF> points;
    private List<Node> nodes;
    private List<Node> racks;
    private List<Edge> edges;
    private NavigableMap<Integer, List<Node>> graph;

    public WorkMap(List<PointFromDXF> points) {
        this.points = points;
    }

    public NavigableMap<Integer, List<Node>> getMap() throws IllegalArgumentException {
        List<Node> nodes = new ArrayList<>();
        if (Objects.nonNull(this.points)) {

            points.forEach(point -> {
                Node node = getNode(point);
                nodes.add(node);
            });

            nodes.forEach(node -> {
                int x = (int) Math.round(node.getX());
                this.graph.computeIfAbsent(x, key -> new ArrayList<>()).add(node);
            });
            setNeighbors();

            return graph;
        } else {
            throw new IllegalArgumentException("List of points couldn't be empty");
        }
    }

    private Node getNode(PointFromDXF point) throws IllegalArgumentException {
        if (Objects.nonNull(point)) {
            Node node = new Node(point.getName(), point.getId(), point.getX(), point.getY());
            node.setType(point.getType());
            node.setBusinessName(point.getBusinessName());
            return node;
        } else {
            throw new IllegalArgumentException("Points couldn't be empty");
        }
    }

    private void setNeighbors() {
        Map<Node, Edge> neighbors = new HashMap<>();
        if (!this.nodes.isEmpty()) {
            for (Node startNode : this.nodes) {
                PointFromDXF pointDxf = points.stream()
                        .filter(point -> startNode.getId().equals(point.getId()))
                        .findFirst().orElseThrow();

                for (NeighborPoint neighborPoint : pointDxf.getNeighbors()) {

                }
            }
        }
    }
}