package com.wb.amr.robot.flotilla.control.system.map.xml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Value {

    private static final Logger LOGGER = LogManager.getLogger(Value.class.getName());

    @JacksonXmlProperty(isAttribute = true, localName = "isChargeable")
    private int isChargeable;

    @JacksonXmlText
    private String content;

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