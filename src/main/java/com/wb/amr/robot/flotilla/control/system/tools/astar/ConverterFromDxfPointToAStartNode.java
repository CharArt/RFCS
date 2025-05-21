package com.wb.amr.robot.flotilla.control.system.tools.astar;

import com.wb.amr.robot.flotilla.control.system.map.dxf.NeighborPoint;
import com.wb.amr.robot.flotilla.control.system.map.dxf.PointFromDXF;

import java.util.*;

public class ConverterFromDxfPointToAStartNode {

    private List<PointFromDXF> points;

    public ConverterFromDxfPointToAStartNode(List<PointFromDXF> points) {
        this.points = points;
    }

    public NavigableMap<Integer, List<Node>> getMap() throws IllegalArgumentException {
        NavigableMap<Integer, List<Node>> graph = new TreeMap<>();
        List<Node> nodes = new ArrayList<>();
        if (Objects.nonNull(this.points)) {

            points.forEach(point -> {
                Node node = getNode(point);
                node.setNeighbors(setNeighbors(point));
                nodes.add(node);
            });

            for (Node node : nodes) {
                int x = (int) Math.round(node.getX());
                graph.computeIfAbsent(x, key -> new ArrayList<>()).add(node);
            }

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

    private Map<Node, Edge> setNeighbors(PointFromDXF point) {
        Map<Node, Edge> neighbors = new HashMap<>();
        if (Objects.nonNull(point)) {
            List<NeighborPoint> neighborPoints = point.getNeighbors().stream().toList();
            Node startNode = getNode(point);
            for (NeighborPoint np : neighborPoints) {
                PointFromDXF pointFromDXF = points.stream()
                        .filter(pt -> np.getPointId().equals(pt.getId())).findFirst().orElseThrow();
                Node endNode = getNode(pointFromDXF);
                Edge edge = new Edge(startNode, endNode);
                neighbors.put(endNode, edge);
            }
            return neighbors;
        } else {
            throw new IllegalArgumentException("Points couldn't be empty");
        }
    }
}
