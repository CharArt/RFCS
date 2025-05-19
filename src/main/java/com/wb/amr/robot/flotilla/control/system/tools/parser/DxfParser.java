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

    private final static String TYPE_OF_POINT_WAY = "way";
    private final static String TYPE_OF_POINT_RACK = "rack";
    private final static List<Point> FILTER_POINTS = new ArrayList<>(2);

    private final String LAYER_WAYS;
    private final String LAYER_RACKS;
    private final Map<Integer, Double> AXIS_X_STEPS;
    private final Map<Integer, Double> AXIS_Y_STEPS;
    private final String PATH;

    public DxfParser(String wayLayer,
                     String rackLayer,
                     Map<Integer, Double> x_steps,
                     Map<Integer, Double> y_steps,
                     Point fromPoint,
                     Point toPoint,
                     String path) {
        this.LAYER_RACKS = rackLayer;
        this.LAYER_WAYS = wayLayer;
        this.AXIS_X_STEPS = x_steps;
        this.AXIS_Y_STEPS = y_steps;
        this.PATH = path;
        FILTER_POINTS.add(fromPoint);
        FILTER_POINTS.add(toPoint);
    }

    public List<PointFromDXF> getMap(String nameIndex) throws IOException, IllegalArgumentException {
        try (InputStream rawDxf = new ClassPathResource(PATH).getInputStream()) {
            Parser parser = ParserBuilder.createDefaultParser();
            parser.parse(rawDxf, "UTF-8");
            DXFDocument dxfDocument = parser.getDocument();
            List<DXFLayer> layers = new ArrayList<>(2);
            Iterator<?> iterator = dxfDocument.getDXFLayerIterator();
            while (iterator.hasNext() && layers.size() != 2) {
                Object next = iterator.next();
                if (next instanceof DXFLayer layer) {
                    if (layer.getName().equals(LAYER_RACKS) || layer.getName().equals(LAYER_WAYS)) {
                        layers.add(layer);
                    }
                } else {
                    throw new IllegalArgumentException("Unexpected type: " + next.getClass());
                }
            }
            return getListPointsWithNeighbors(layers, nameIndex);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private List<PointFromDXF> getListPointsWithNeighbors(List<DXFLayer> layers, String nameIndex) {
        Comparator<PointFromDXF> comparator = Comparator.comparingDouble(PointFromDXF::getX).thenComparing(PointFromDXF::getY);
        Set<PointFromDXF> points = new TreeSet<>(comparator);
        int id = 0;
        for (DXFLayer layer : layers) {
            for (Object dxfEntity : layer.getDXFEntities(DXFConstants.ENTITY_TYPE_INSERT)) {
                if (dxfEntity != null) {
                    if (dxfEntity instanceof DXFInsert insert) {
                        Point p = insert.getPoint();
                        if (checkFilter(p)) {
                            PointFromDXF pointFromDXF = new PointFromDXF(p.getX(), p.getY(), p.getZ(), nameIndex);
                            pointFromDXF.setId(id++);
                            if (layer.getName().equals(LAYER_WAYS)) pointFromDXF.setType(TYPE_OF_POINT_WAY);
                            if (layer.getName().equals(LAYER_RACKS)) pointFromDXF.setType(TYPE_OF_POINT_RACK);
                            points.add(pointFromDXF);
                        }
                    }
                }
            }
        }
        setPointWithNeighbors(points.stream().toList(), AXIS_X_STEPS, AXIS_Y_STEPS);
        return points.stream().toList();
    }


    private void setPointWithNeighbors(List<PointFromDXF> points, Map<Integer, Double> AXIS_X_STEPS, Map<Integer, Double> AXIS_Y_STEPS) {
        NavigableMap<Integer, List<PointFromDXF>> bucketsByX = new TreeMap<>();
        for (PointFromDXF point : points) {
            int x = (int) Math.round(point.getX());
            bucketsByX.computeIfAbsent(x, key -> new ArrayList<>()).add(point);
        }

        for (PointFromDXF point : points) {
            int currentPointX = (int) Math.round(point.getX());
            bucketsByX.subMap(currentPointX - 1560, true,
                            currentPointX + 1560, true)
                    .values().stream()
                    .flatMap(Collection::stream)
                    .filter(p -> !p.equals(point))
                    .forEach(neighbor -> {
                        int deferenceX = (int) Math.abs(Math.round(point.getX() - neighbor.getX()));
                        int deferenceY = (int) Math.abs(Math.round(point.getY() - neighbor.getY()));
                        if (deferenceX == 0) {
                            if (deferenceY == 1340 && !neighbor.getType().equals(TYPE_OF_POINT_RACK)) {
                                point.getNeighbors().add(new NeighborPoint(neighbor.getId(), 1340.0));
                            } else {
                                Optional.ofNullable(AXIS_Y_STEPS.get(deferenceY)).ifPresent(len -> point.getNeighbors()
                                        .add(new NeighborPoint(neighbor.getId(), len)));
                            }
                        }
                        if (deferenceY == 0) {
                            Optional.ofNullable(AXIS_X_STEPS.get(deferenceX)).ifPresent(len -> point.getNeighbors()
                                    .add(new NeighborPoint(neighbor.getId(), len)));
                        }
                    });
            checkAfterAddingNeighbors(point);
        }
    }

    private void checkAfterAddingNeighbors(PointFromDXF point) {
        boolean has780 = point.getNeighbors().stream().anyMatch(n -> Objects.equals(780.0, n.getLengthForNeighbor()));
        boolean has1440 = point.getNeighbors().stream().anyMatch(n -> Objects.equals(1440.0, n.getLengthForNeighbor()));
        if (has780) {
            point.getNeighbors().removeIf(n -> Objects.equals(1560.0, n.getLengthForNeighbor()));
            point.getNeighbors().removeIf(n -> Objects.equals(1400.0, n.getLengthForNeighbor()));
        }
        if (has1440) {
            point.getNeighbors().removeIf(n -> Objects.equals(1560.0, n.getLengthForNeighbor()));
        }
    }

    private boolean checkFilter(Point p) {
        if (p.getX() >= FILTER_POINTS.getFirst().getX() && p.getY() >= FILTER_POINTS.getFirst().getY()) {
            return p.getX() <= FILTER_POINTS.getLast().getX() && p.getY() <= FILTER_POINTS.getLast().getY();
        } else {
            return false;
        }
    }
}
