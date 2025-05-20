package com.wb.amr.robot.flotilla.control.system;

import com.wb.amr.robot.flotilla.control.system.map.dxf.PointFromDXF;
import com.wb.amr.robot.flotilla.control.system.tools.parser.DxfParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kabeja.dxf.helpers.Point;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class DxfParserTest {

    private DxfParser parser;

    @BeforeEach
    void setUp() {
        String layer1 = "точки движения роботов 2";
        String layer2 = "сетка стеллажей";
        Map<Integer, Double> AXIS_X_STEPS = Map.of(
                555, 555.0,
                660, 660.0,
                780, 780.0,
                1110, 1110.0,
                1440, 1440.0,
                1560, 1560.0
        );

        Map<Integer, Double> AXIS_Y_STEPS = Map.of(
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
    public void testDxfParser_withDXFMap() throws IOException {
        List<PointFromDXF> points = parser.getMap("AA");
        Assertions.assertNotNull(points);
        Assertions.assertEquals(4424, points.size());
        Assertions.assertEquals(2216, points.stream().filter(p -> p.getType().equals("rack")).toList().size());
        for (PointFromDXF point : points) {
            if (Math.round(point.getX()) == 96867 && Math.round(point.getY()) == 689969) {
                Assertions.assertEquals(3, point.getNeighbors().size());
                Assertions.assertTrue(point.getNeighbors().stream().noneMatch(n -> n.getLengthForNeighbor().equals(1560.0)));
            }
        }
    }

    @Test
    public void testSetRackNumber_forListRack() throws IOException {
        List<PointFromDXF> points = parser.getMap("AA");
        List<PointFromDXF> racks = points.stream().filter(p -> p.getType().equals("rack")).toList();
        parser.setRackNumber(racks);
        for (PointFromDXF rack : racks) {
            System.out.println(rack.getBusinessName());
        }
    }
}
