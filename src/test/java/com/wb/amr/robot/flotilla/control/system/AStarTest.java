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
        Node nodeA = new Node("nodeA", 1L, 0.0, 0.0);
        Node nodeB = new Node("nodeB", 2L, 1.0, 2.0);
        Node nodeC = new Node("nodeC", 3L, 3.0, 1.0);
        Node nodeD = new Node("nodeD", 4L, 4.0, 3.0);
        Node nodeE = new Node("nodeE", 5L, 5.0, 5.0);

        nodeA.addNeighbor(nodeC, new Edge(nodeA, nodeC));
        nodeA.addNeighbor(nodeB, new Edge(nodeA, nodeB));

        nodeB.addNeighbor(nodeA, new Edge(nodeB, nodeA));
        nodeB.addNeighbor(nodeC, new Edge(nodeB, nodeC));
        nodeB.addNeighbor(nodeE, new Edge(nodeB, nodeE));

        nodeC.addNeighbor(nodeA, new Edge(nodeC, nodeA));
        nodeC.addNeighbor(nodeB, new Edge(nodeC, nodeB));
        nodeC.addNeighbor(nodeD, new Edge(nodeC, nodeD));

        nodeD.addNeighbor(nodeE, new Edge(nodeD, nodeE));
        nodeD.addNeighbor(nodeC, new Edge(nodeD, nodeC));

        nodeE.addNeighbor(nodeD, new Edge(nodeE, nodeD));
        nodeE.addNeighbor(nodeB, new Edge(nodeE, nodeB));

        AStarSearch aStart = new AStarSearch();
        PathResult pathResult = aStart.findPath(nodeA, nodeE);
        Assertions.assertEquals(3, pathResult.getNodes().size());
        Assertions.assertEquals(2, pathResult.getEdges().size());
        Assertions.assertEquals(5, pathResult.getPath().size());
    }
}
