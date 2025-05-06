package com.wb.amr.robot.flotilla.control.system.map.xml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import lombok.Data;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.EqualsAndHashCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


@Data
@EqualsAndHashCode(callSuper = false)
@JacksonXmlRootElement(localName = "mapCfg")
@JsonIgnoreProperties(ignoreUnknown = true)
public class MapCfg {

    private static final Logger LOGGER = LogManager.getLogger(MapCfg.class.getName());

    @JacksonXmlProperty(localName = "MapName")
    private String mapName;

    @JacksonXmlProperty(localName = "MapQRCode")
    private String mapQRCode;

    @JacksonXmlProperty(localName = "MapType")
    private int mapType;

    @JacksonXmlProperty(localName = "Row")
    private int row;

    @JacksonXmlProperty(localName = "Col")
    private int col;

    @JacksonXmlProperty(localName = "Width")
    private int width;

    @JacksonXmlProperty(localName = "Height")
    private int height;
    
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "PointInfo")
    private List<PointInfo> pointInfo = new ArrayList<>();

    @Override
    public String toString() {
        try {
            XmlMapper mapper = new XmlMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
            return mapper.writeValueAsString(this);
        } catch (Exception e) {
            LOGGER.error(e.toString());
            throw new IllegalArgumentException("Empty MapCfg");
        }
    }
}
