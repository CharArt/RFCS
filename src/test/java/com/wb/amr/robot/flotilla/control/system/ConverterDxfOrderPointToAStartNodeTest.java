package com.wb.amr.robot.flotilla.control.system;

import com.wb.amr.robot.flotilla.control.system.dto.comand.OrderPoint;
import com.wb.amr.robot.flotilla.control.system.map.dxf.PointFromDXF;
import com.wb.amr.robot.flotilla.control.system.tools.astar.AStarSearch;
import com.wb.amr.robot.flotilla.control.system.tools.astar.WorkMap;
import com.wb.amr.robot.flotilla.control.system.tools.astar.Node;
import com.wb.amr.robot.flotilla.control.system.tools.astar.PathResult;
import com.wb.amr.robot.flotilla.control.system.tools.parser.DxfParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kabeja.dxf.helpers.Point;

import java.io.IOException;
import java.util.List;
import java.util.NavigableMap;

public class ConverterDxfOrderPointToAStartNodeTest {
    private DxfParser parser;

    @BeforeEach
    void setUp() {
        String layer1 = "точки движения роботов 2";
        String layer2 = "сетка стеллажей";
        java.util.Map<Integer, Double> AXIS_X_STEPS = java.util.Map.of(
                555, 555.0,
                660, 660.0,
                780, 780.0,
                1110, 1110.0,
                1440, 1440.0,
                1560, 1560.0
        );

        java.util.Map<Integer, Double> AXIS_Y_STEPS = java.util.Map.of(
                990, 990.0,
                1340, 1340.0,
                1400, 1400.0,
                1440, 1440.0,
                1558, 1558.0,
                1640, 1640.0,
                1733, 1733.0
        );

        Point fromPoint = new Point(88647.5, 664380.4, 0.0);
        Point toPoint = new Point(173307.5, 747649.4, 0.0);
        String path = "dxf/R9-5.dxf";
        parser = new DxfParser(layer1, layer2, AXIS_X_STEPS, AXIS_Y_STEPS, fromPoint, toPoint, path);
    }

    @Test
    public void converterWithValidMap() throws IOException {
        List<PointFromDXF> points = parser.getMap("AA");
        WorkMap converter = new WorkMap(points);
        NavigableMap<Integer, List<Node>> map = converter.getMap();

        OrderPoint startPoint = new OrderPoint("90087AA686649");
        OrderPoint endPoint = new OrderPoint("90867AA665780");
        Node startNode = startPoint.getNode(map);
        Node endNode = endPoint.getNode(map);

        AStarSearch aStart = new AStarSearch();
        PathResult path = aStart.findPath(startNode, endNode);

        System.out.println(path.getNodes().size());
        System.out.println(path.getEdges().size());

        for (Node node : path.getNodes()) {
            System.out.println(node.toString());
        }
    }
}
