package com.wb.amr.robot.flotilla.control.system.tools.parser;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.wb.amr.robot.flotilla.control.system.map.xml.MapCfg;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class XmlParser {

    public MapCfg getMapFromPath(String path) throws IOException {
        try (InputStream rawXml = new ClassPathResource(path).getInputStream()) {
            return parse(rawXml);
        }
    }

    public MapCfg getMapFromString(String string) throws IOException {
        try (InputStream rawXml = new ByteArrayInputStream(string.getBytes())) {
            return parse(rawXml);
        }
    }

    private MapCfg parse(InputStream rawXml) throws IOException {
        XmlMapper mapper = new XmlMapper();
        return mapper.readValue(rawXml, MapCfg.class);
    }
}
