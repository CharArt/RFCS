package com.wb.amr.robot.flotilla.control.system.tools.parser;

import com.wb.amr.robot.flotilla.control.system.map.dxf.PointXYZ;
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

    public static void main(String[] args) throws IOException {
        try (InputStream rawDxf = new ClassPathResource("dxf/R9-5.dxf").getInputStream()) {
            Parser parser = ParserBuilder.createDefaultParser();
            parser.parse(rawDxf, "UTF-8");
            DXFDocument dxfDocument = parser.getDocument();

            String racks = "точки движения роботов 2";
            String ways = "сетка стеллажей";

            List<DXFLayer> layers = new ArrayList<>(2);
            Set<PointXYZ> allPoints = new HashSet<>();

            @SuppressWarnings("unchecked")
            Iterator<DXFLayer> iterator = (Iterator<DXFLayer>) dxfDocument.getDXFLayerIterator();

            while (iterator.hasNext() && layers.size() != 2) {
                DXFLayer layer = (DXFLayer) iterator.next();
                if (layer.getName().equals(racks) || layer.getName().equals(ways)) {
                    layers.add(layer);
                }
            }

            for (DXFLayer layer : layers) {
                for (Object dxfEntity : layer.getDXFEntities(DXFConstants.ENTITY_TYPE_INSERT)) {
                    if (dxfEntity != null) {
                        if (dxfEntity instanceof DXFInsert) {
                            DXFInsert insert = (DXFInsert) dxfEntity;
                            Point p = insert.getPoint();
                            allPoints.add(new PointXYZ(p.getX(), p.getY(), p.getZ()));
                        }
                    }
                }
            }
            List<PointXYZ> pointXYZList = new ArrayList<>(allPoints.stream().toList());
            pointXYZList.sort(Comparator.comparingDouble(PointXYZ::getX));
            for (PointXYZ pointXYZ : pointXYZList) {
                System.out.println(pointXYZ.toString());
            }
            System.out.println(pointXYZList.getFirst().toString());
            System.out.println("allPoints.size(): " + pointXYZList.size());

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


    }

}
