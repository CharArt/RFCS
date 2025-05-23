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

    /**
     * @param wayLayer  Layer where robots will move.
     * @param rackLayer Layer where rack be.
     * @param x_steps   Steps on X axis.
     * @param y_steps   Steps on Y axis.These steps create a grid of points.
     * @param fromPoint It is min x, y.
     * @param toPoint   It is max x, y. These points set restriction of grid by points, min x, y and max x, y.
     * @Note This is parser get points from dxf file.
     */
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

    /**
     * @param nameIndex Every point have name, every name has try part {@code x + nameIndex + y},
     *                  for correction work we have to put index, for example: {@code "AA"}.
     *                  Map also can separate on a part, and for every part have to has index.
     * @return {@code List<PointFromDXF>} It is Array of points from DXF file ready for transform into internal type for processing.
     * @throws IOException              This exception throws because there is {@link InputStream}
     * @throws IllegalArgumentException This exception throws because code has unchecked type conversion
     *                                  <pre>
     *                                                                                                                                                                                                                                                                                                                                                                                                                                                  {@code Iterator<?> iterator = dxfDocument.getDXFLayerIterator();}
     *                                                                                                                                                                                                                                                                                                                                                                                                                                              </pre>
     */
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

    /**
     * This internal method for extract points from layers and set neighbors into every point.
     * <p> Also used static parameters {@code LAYER_WAYS, LAYER_RACKS} and {@code TYPE_OF_POINT_WAY, TYPE_OF_POINT_RACK}.
     * Where LAYER_ is static name of layer, TYPE_OF_ it is type of point, it set into point.</p>
     *
     * @param layers    It is list of layers were we have points.
     * @param nameIndex It is index of naming for points.
     * @return {@code List<PointFromDXF>} list of points with neighbors.
     */
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
                            pointFromDXF.setId((long) id++);
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

    /**
     * Internal method set neighbors into every point. Method filters points with stay close of target point and add in neighbors list.
     *
     * @param points       List of points without neighbors.
     * @param AXIS_X_STEPS Steps for grid by axis X.
     * @param AXIS_Y_STEPS Steps for grid by axis Y.
     */
    private void setPointWithNeighbors(List<PointFromDXF> points,
                                       Map<Integer, Double> AXIS_X_STEPS,
                                       Map<Integer, Double> AXIS_Y_STEPS) {
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
                                point.getNeighbors()
                                        .add(new NeighborPoint(neighbor.getId(),
                                                1340.0, neighbor.getX(),
                                                neighbor.getY()));
                            } else {
                                Optional.ofNullable(AXIS_Y_STEPS.get(deferenceY))
                                        .ifPresent(len -> point.getNeighbors()
                                                .add(new NeighborPoint(neighbor.getId(),
                                                        len,
                                                        neighbor.getX(),
                                                        neighbor.getY())));
                            }
                        }
                        if (deferenceY == 0) {
                            Optional.ofNullable(AXIS_X_STEPS.get(deferenceX)).ifPresent(len -> point.getNeighbors()
                                    .add(new NeighborPoint(neighbor.getId(),
                                            len,
                                            neighbor.getX(),
                                            neighbor.getY())));
                        }
                    });
            checkAfterAddingNeighbors(point);
        }
    }

    /**
     * Point already has neighbors, but method {@code setPointWithNeighbors} has collision. It is filter witch cleans collision neighbors.
     *
     * @param point Point already has neighbors.
     */
    private void checkAfterAddingNeighbors(PointFromDXF point) {
        boolean has780 = point.getNeighbors().stream().anyMatch(n -> Objects.equals(780.0, n.getLengthForNeighbor()));
        boolean has1440 = point.getNeighbors().stream().anyMatch(n -> Objects.equals(1440.0, n.getLengthForNeighbor()));
        if (has780) {
            point.getNeighbors().removeIf(n -> Objects.equals(1560.0, n.getLengthForNeighbor()));
            point.getNeighbors().removeIf(n -> Objects.equals(1440.0, n.getLengthForNeighbor()));
        }
        if (has1440) {
            point.getNeighbors().removeIf(n -> Objects.equals(1560.0, n.getLengthForNeighbor()));
        }
    }

    /**
     * Internal method with set restriction by axis x and y
     *
     * @param p - point, that checked for the occurrence of the coordinate range.
     * @return true if point into range, else return false.
     */
    private boolean checkFilter(Point p) {
        if (p.getX() >= FILTER_POINTS.getFirst().getX() && p.getY() >= FILTER_POINTS.getFirst().getY()) {
            return p.getX() <= FILTER_POINTS.getLast().getX() && p.getY() <= FILTER_POINTS.getLast().getY();
        } else {
            return false;
        }
    }

    /**
     * @param racks List of racks
     */
    public void setRackNumber(List<PointFromDXF> racks) {
        NavigableMap<Integer, List<PointFromDXF>> bucket = new TreeMap<>();
        for (PointFromDXF point : racks) {
            int x = (int) Math.round(point.getX());
            bucket.computeIfAbsent(x, key -> new ArrayList<>()).add(point);
        }
        int line = 1;
        int number = 1;
        for (List<PointFromDXF> listRacks : bucket.values()) {
            String lineNumber = String.format("%03d", line++);
            for (PointFromDXF point : listRacks) {
                String sequenceNumber = String.format("%03d", number++);
                point.setBusinessName(lineNumber + sequenceNumber);
            }
            number = 1;
        }
    }
}