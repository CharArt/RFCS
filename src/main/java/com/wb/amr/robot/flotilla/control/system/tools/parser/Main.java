package com.wb.amr.robot.flotilla.control.system.tools.parser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.wb.amr.robot.flotilla.control.system.map.xml.MapCfg;
import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import javax.swing.text.html.parser.Parser;

public class Main {
    public static void main(String[] args) {
        try {
            XmlMapper mapper = new XmlMapper();
            InputStream rawXml = new ClassPathResource("xml/map_section1.xml").getInputStream();
            MapCfg mapCfg = mapper.readValue(rawXml, MapCfg.class);

            System.out.println(mapCfg.getPointInfo().get(0).toString());

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


//        JsonParser parser = new JsonParser();
//        try {
//            ObjectMapper mapper = new ObjectMapper();
//            InputStream rawOrder = new ClassPathResource("json/OrderExample.json").getInputStream();
//            InputStream rawStatus = new ClassPathResource("json/StatusExample.json").getInputStream();
//            Order order = parser.getOrder(rawOrder);
//            System.out.println(order.toString());
//            Order order1 = mapper.readValue(rawOrder, Order.class);
//            System.out.println(order1.toString());
//            Status status = parser.getStatus(rawStatus);
//            System.out.println(status1.toString());
//            Status status1 = mapper.readValue(rawStatus, Status.class);
//            System.out.println(status1.toString());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }
}
