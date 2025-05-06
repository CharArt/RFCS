package com.wb.amr.robot.flotilla.control.system.tools.astar;

import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Node A = new Node("A", "1", 0.0, 0.0);
        Node B = new Node("B", "2", 1.0, 2.0);
        Node C = new Node("C", "3", 3.0, 1.0);
        Node D = new Node("D", "4", 4.0, 3.0);
        Node E = new Node("E", "5", 5.0, 5.0);

        A.addNeighbor(C, new Edge(A, C));
        A.addNeighbor(B, new Edge(A, B));

        B.addNeighbor(A, new Edge(B, A));
        B.addNeighbor(C, new Edge(B, C));
        B.addNeighbor(E, new Edge(B, E));

        C.addNeighbor(A, new Edge(C, A));
        C.addNeighbor(B, new Edge(C, B));
        C.addNeighbor(D, new Edge(C, D));

        D.addNeighbor(E, new Edge(D, E));
        D.addNeighbor(C, new Edge(D, C));

        E.addNeighbor(D, new Edge(E, D));
        E.addNeighbor(B, new Edge(E, B));


        AStarSearch aStart = new AStarSearch();
        PathResult pathResult = aStart.findPath(A, E); 
        System.out.println(pathResult.toString());
    }
}
