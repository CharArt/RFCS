package com.wb.amr.robot.flotilla.control.system.tools.astar;

import java.util.*;

public class AStarSearch {

    public PathResult findPath(Node start, Node goal) {
        Map<Node, Node> nodeFrom = new HashMap<>();
        Map<Node, Edge> edgeFrom = new HashMap<>();
        Map<Node, Double> gScore = new HashMap<>();
        Map<Node, Double> fScore = new HashMap<>();
        PriorityQueue<Node> queueForTreatment = new PriorityQueue<>(Comparator.comparingDouble(node -> fScore.getOrDefault(node, Double.MAX_VALUE)));

        gScore.put(start, 0.0);
        double euclideanHeuristicScore = this.euclideanHeuristic(start, goal);
        fScore.put(start, euclideanHeuristicScore);
        queueForTreatment.add(start);

        while (!queueForTreatment.isEmpty()) {
            Node currentNode = queueForTreatment.poll();

            if (currentNode == goal)
                return reconstructPath(nodeFrom, edgeFrom, currentNode);

            for (Map.Entry<Node, Edge> entry : currentNode.getNeighbors().entrySet()) {
                Node neighborNode = entry.getKey();
                Edge edgeToNeighborNode = entry.getValue();
                double weightToNeighbor = gScore.get(currentNode) + edgeToNeighborNode.getLength();

                if (weightToNeighbor < gScore.getOrDefault(neighborNode, Double.MAX_VALUE)) { //if we don't know node, it has ∞
                    nodeFrom.put(neighborNode, currentNode);
                    edgeFrom.put(neighborNode, edgeToNeighborNode);
                    gScore.put(neighborNode, weightToNeighbor);
                    double eh = euclideanHeuristic(neighborNode, goal);
                    double score = weightToNeighbor + eh;
                    fScore.put(neighborNode, score);
                    if (!queueForTreatment.contains(neighborNode)) {
                        queueForTreatment.add(neighborNode);
                    }
                }
            }   
        }
        return new PathResult(Collections.emptyList(), Collections.emptyList());
    }

    private double euclideanHeuristic(Node n, Node target) {
        return Math.sqrt(Math.pow(Math.abs(target.getX() - n.getX()), 2) + Math.pow(Math.abs(target.getY() - n.getY()), 2));
    }

    private PathResult reconstructPath(Map<Node, Node> nodeFrom, Map<Node, Edge> edgeFrom, Node current) {
        List<Node> nodes = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();
        while (current != null) {
            nodes.add(current);
            Optional.ofNullable(edgeFrom.get(current)).ifPresent(edges::add);
            current = nodeFrom.get(current);
        }
        Collections.reverse(nodes);
        Collections.reverse(edges);
        return new PathResult(nodes, edges);
    }
}