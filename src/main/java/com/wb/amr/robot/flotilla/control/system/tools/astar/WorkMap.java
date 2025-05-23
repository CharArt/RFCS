package com.wb.amr.robot.flotilla.control.system.tools.astar;

import com.wb.amr.robot.flotilla.control.system.map.dxf.NeighborPoint;
import com.wb.amr.robot.flotilla.control.system.map.dxf.PointFromDXF;
import lombok.Getter;

import java.util.*;

public class WorkMap {

    private final List<PointFromDXF> points;
    private final List<Node> nodes;
    @Getter
    private final NavigableMap<Integer, List<Node>> graph;
    @Getter
    private final NavigableMap<Integer, List<Node>> rack;
    @Getter
    private final NavigableMap<Integer, List<Node>> way;

    public WorkMap(List<PointFromDXF> points) throws IllegalArgumentException {
        this.points = points;
        this.nodes = new ArrayList<>(points.size());
        this.graph = new TreeMap<>();
        this.rack = new TreeMap<>();
        this.way = new TreeMap<>();

        points.forEach(point -> {
            Node node = getNode(point);
            nodes.add(node);
        });

        nodes.forEach(node -> {
            int x = (int) Math.round(node.getX());
            this.graph.computeIfAbsent(x, key -> new ArrayList<>()).add(node);

            if (node.getType().equals("rack"))
                this.rack.computeIfAbsent(x, key -> new ArrayList<>()).add(node);

            if (node.getType().equals("way"))
                this.way.computeIfAbsent(x, key -> new ArrayList<>()).add(node);
        });
        setNeighbors();

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
                    Node neighborNode = this.graph.get((int) Math.round(neighborPoint.getX())).stream()
                            .filter(n -> n.getY().equals(neighborPoint.getY()))
                            .findFirst().orElseThrow();

                    Edge edge = new Edge(startNode, neighborNode);
                    startNode.addNeighbor(neighborNode, edge);
                }
            }
        }
    }
}