package com.wb.amr.robot.flotilla.control.system.tools.parser;

import com.wb.amr.robot.flotilla.control.system.map.dxf.NeighborPoint;
import com.wb.amr.robot.flotilla.control.system.map.dxf.PointFromDXF;
import org.kabeja.dxf.DXFConstants;
import org.kabeja.dxf.DXFDocument;
import org.kabeja.dxf.DXFInsert;
import org.kabeja.dxf.DXFLayer;
import org.kabeja.dxf.helpers.Point;
import org.kabeja.parser.ParseException;
import org.kabeja.parser.Parser;
import org.kabeja.parser.ParserBuilder;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class DxfParser {

    private final static int AXIS_Y_STEP_990 = 990;
    private final static int AXIS_Y_STEP_1340 = 1340;
    private final static int AXIS_Y_STEP_1440 = 1440;
    private final static int AXIS_Y_STEP_1558 = 1558;
    private final static int AXIS_Y_STEP_1640 = 1640;
    private final static int AXIS_Y_STEP_1733 = 1733;

    private final static int AXIS_X_STEP_555 = 555;
    private final static int AXIS_X_STEP_660 = 660;
    private final static int AXIS_X_STEP_780 = 780;
    private final static int AXIS_X_STEP_1110 = 1110;
    private final static int AXIS_X_STEP_1440 = 1440;
    private final static int AXIS_X_STEP_1560 = 1560;

    private final static String LAYER_WAYS = "точки движения роботов 2";
    private final static String LAYER_RACKS = "сетка стеллажей";
    private final static String TYPE_OF_POINT_WAY = "way";
    private final static String TYPE_OF_POINT_RACK = "rack";


    public static void main(String[] args) throws IOException {

        try (InputStream rawDxf = new ClassPathResource("dxf/R9-5.dxf").getInputStream()) {
            Parser parser = ParserBuilder.createDefaultParser();
            parser.parse(rawDxf, "UTF-8");
            DXFDocument dxfDocument = parser.getDocument();

            List<DXFLayer> layers = new ArrayList<>(2);
            Set<PointFromDXF> allPoints = new HashSet<>();

            Iterator<DXFLayer> iterator = (Iterator<DXFLayer>) dxfDocument.getDXFLayerIterator();

            while (iterator.hasNext() && layers.size() != 2) {
                DXFLayer layer = (DXFLayer) iterator.next();
                if (layer.getName().equals(LAYER_RACKS) || layer.getName().equals(LAYER_WAYS)) layers.add(layer);
            }

            for (DXFLayer layer : layers) {
                for (Object dxfEntity : layer.getDXFEntities(DXFConstants.ENTITY_TYPE_INSERT)) {
                    if (dxfEntity != null) {
                        if (dxfEntity instanceof DXFInsert insert) {
                            Point p = insert.getPoint();
                            PointFromDXF pointFromDXF = new PointFromDXF(p.getX(), p.getY(), p.getZ(), "AA");
                            if (layer.getName().equals(LAYER_WAYS)) pointFromDXF.setType(TYPE_OF_POINT_WAY);
                            if (layer.getName().equals(LAYER_RACKS)) pointFromDXF.setType(TYPE_OF_POINT_RACK);
                            allPoints.add(pointFromDXF);
                        }
                    }
                }
            }

            List<PointFromDXF> pointFromDXFList = new ArrayList<>(allPoints.stream().toList());
            pointFromDXFList.sort(Comparator.comparingDouble(PointFromDXF::getX));

            int id = 0;
            for (PointFromDXF pointFromDXF : pointFromDXFList) {
                pointFromDXF.setId(id++);
            }

            List<PointFromDXF> fullPointsList = getListOfPoints(pointFromDXFList);

            for (int i = 0; i < 3000; i++) {
                System.out.println(fullPointsList.get(i).toString());
            }
//            for (PointFromDXF pointFromDXF : fullPointsList) {
//                if (Math.round(pointFromDXF.getX()) == 95307 && Math.round(pointFromDXF.getY()) == 670629) {
//                    System.out.println(pointFromDXF);
//                }
//                if (Math.round(pointFromDXF.getX()) == 95307 && Math.round(pointFromDXF.getY()) == 671969) {
//                    System.out.println(pointFromDXF);
//                }
//            }
//            System.out.println("First:" + pointFromDXFList.get(10534));

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<PointFromDXF> getListOfPoints(List<PointFromDXF> pointFromDXFList) {
        List<PointFromDXF> fullPointsList = new ArrayList<>();
        for (PointFromDXF targetPoint : pointFromDXFList) {
            for (PointFromDXF potentialNeighbor : pointFromDXFList) {
                if (Math.abs(potentialNeighbor.getX() - targetPoint.getX()) < 1.0
                        && !potentialNeighbor.equals(targetPoint)) {
                    int differenceY = (int) Math.abs(Math.round(targetPoint.getY() - potentialNeighbor.getY()));
                    NeighborPoint neighborPointY = checkDeferenceOnAxisY(differenceY, potentialNeighbor);
                    if (Objects.nonNull(neighborPointY.getLengthForNeighbor())) {
                        targetPoint.getNeighbors().add(neighborPointY);
                    }
                }
                if (Math.abs(potentialNeighbor.getY() - targetPoint.getY()) < 1.0
                        && !potentialNeighbor.equals(targetPoint)) {
                    int differenceX = (int) Math.abs(Math.round(targetPoint.getX() - potentialNeighbor.getX()));
                    NeighborPoint neighborPointX = checkDeferenceOnAxisX(differenceX, potentialNeighbor);
                    if (Objects.nonNull(neighborPointX.getLengthForNeighbor())) {
                        targetPoint.getNeighbors().add(neighborPointX);
                    }
                }
            }
            checkAfterAddingNeighbors(targetPoint);
            fullPointsList.add(targetPoint);
        }
        return fullPointsList;
    }


    public static NeighborPoint checkDeferenceOnAxisX(int differenceX, PointFromDXF potentialNeighbor) {
        NeighborPoint neighbor = new NeighborPoint(potentialNeighbor.getId());
        switch (differenceX) {
            case (AXIS_X_STEP_555):
                neighbor.setLengthForNeighbor((double) AXIS_X_STEP_555);
                break;

            case (AXIS_X_STEP_660):
                neighbor.setLengthForNeighbor((double) AXIS_X_STEP_660);
                break;

            case (AXIS_X_STEP_780):
                neighbor.setLengthForNeighbor((double) AXIS_X_STEP_780);
                break;

            case (AXIS_X_STEP_1110):
                neighbor.setLengthForNeighbor((double) AXIS_X_STEP_1110);
                break;

            case (AXIS_X_STEP_1440):
                neighbor.setLengthForNeighbor((double) AXIS_X_STEP_1440);
                break;

            case (AXIS_X_STEP_1560):
                neighbor.setLengthForNeighbor((double) AXIS_X_STEP_1560);
                break;
        }
        return neighbor;
    }

    public static NeighborPoint checkDeferenceOnAxisY(int differenceY, PointFromDXF potentialNeighbor) {
        NeighborPoint neighbor = new NeighborPoint(potentialNeighbor.getId());
        switch (differenceY) {
            case (AXIS_Y_STEP_990):
                neighbor.setLengthForNeighbor((double) AXIS_Y_STEP_990);
                break;

            case (AXIS_Y_STEP_1340):
                if (!potentialNeighbor.getType().equals(TYPE_OF_POINT_RACK)) {
                    neighbor.setLengthForNeighbor((double) AXIS_Y_STEP_1340);
                }
                break;

            case (AXIS_Y_STEP_1440):
                neighbor.setLengthForNeighbor((double) AXIS_Y_STEP_1440);
                break;

            case (AXIS_Y_STEP_1558):
                neighbor.setLengthForNeighbor((double) AXIS_Y_STEP_1558);
                break;

            case (AXIS_Y_STEP_1640):
                neighbor.setLengthForNeighbor((double) AXIS_Y_STEP_1640);
                break;

            case (AXIS_Y_STEP_1733):
                neighbor.setLengthForNeighbor((double) AXIS_Y_STEP_1733);
                break;
        }
        return neighbor;
    }

    public static void checkAfterAddingNeighbors(PointFromDXF point) {
        boolean has780 = point.getNeighbors().stream()
                .anyMatch(n -> Objects.equals((double) AXIS_X_STEP_780, n.getLengthForNeighbor()));
        if (has780) {
            point.getNeighbors().removeIf(n -> Objects.equals((double) AXIS_X_STEP_1560, n.getLengthForNeighbor()));
        }
    }
}
