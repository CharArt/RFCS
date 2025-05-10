package com.wb.amr.robot.flotilla.control.system.tools.parser;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.wb.amr.robot.flotilla.control.system.map.xml.MapCfg;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

public class XmlParser {

    private static final Logger LOGGER = LogManager.getLogger(XmlParser.class);

    public MapCfg getMapFromPath(String path) {
        MapCfg mapCfg = new MapCfg();
        try (InputStream rawXml = new ClassPathResource(path).getInputStream()) {
            mapCfg = parse(rawXml);
        } catch (IOException e) {
            LOGGER.error("Get exception in getMap method {}", e.getMessage(), e);
        }
        return mapCfg;
    }

    public MapCfg getMapFromUrl(String url) {
        MapCfg mapCfg = new MapCfg();
        try (InputStream rawXml = URI.create(url).toURL().openStream()) {
            mapCfg = parse(rawXml);
        } catch (IOException e) {
            LOGGER.error("Get exception in getMap method {}", e.getMessage(), e);
        }
        return mapCfg;
    }

    public MapCfg getMapFromString(String string) {
        MapCfg mapCfg = new MapCfg();
        try (InputStream rawXml = new ByteArrayInputStream(string.getBytes())) {
            mapCfg = parse(rawXml);
        } catch (IOException e) {
            LOGGER.error("Get exception in getMap method {}", e.getMessage(), e);
        }
        return mapCfg;
    }

    private MapCfg parse(InputStream inputStream) throws IOException {
        XmlMapper mapper = new XmlMapper();
        return mapper.readValue(inputStream, MapCfg.class);
    }
}
