package com.wb.amr.robot.flotilla.control.system;

import com.wb.amr.robot.flotilla.control.system.tools.astar.AStarSearch;
import com.wb.amr.robot.flotilla.control.system.tools.astar.Edge;
import com.wb.amr.robot.flotilla.control.system.tools.astar.Node;
import com.wb.amr.robot.flotilla.control.system.tools.astar.PathResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = AStarSearch.class)
public class AStarTest {
    private AStarSearch starSearch;

    @BeforeEach
    public void setUp() {
        starSearch = new AStarSearch();
    }

    @Test
    public void shoud_FindShorWay_WhenGetValidMap() {
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
        Assertions.assertEquals(3, pathResult.getNodes().size());
        Assertions.assertEquals(2, pathResult.getEdges().size());
        Assertions.assertEquals(5, pathResult.getPath().size());
    }
}
