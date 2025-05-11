package com.wb.amr.robot.flotilla.control.system;

import com.wb.amr.robot.flotilla.control.system.map.xml.MapCfg;
import com.wb.amr.robot.flotilla.control.system.tools.parser.XmlParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = XmlParser.class)
public class XmlParserTest {

    private XmlParser xmlParser;
    private ListAppender appender;
    private Logger coreLogger;

    @BeforeEach
    public void setUp() {
        xmlParser = new XmlParser();
    }

    @BeforeEach
    public void attachAppender() {
        coreLogger = (Logger) LogManager.getLogger(XmlParser.class);
        appender = new ListAppender("XmlParserTestAppender");
        appender.start();
        coreLogger.addAppender(appender);
    }

    @AfterEach
    public void detachAppender() {
        coreLogger.removeAppender(appender);
        appender.stop();
    }

    @Test
    public void should_GetMapFromString_validXml() {
        String validXml = "<MapCfg><MapName>TestName</MapName></MapCfg>";
        MapCfg result = xmlParser.getMapFromString(validXml);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("TestName", result.getMapName());
    }

    @Test
    public void testGetMapFromPath_validResource() {
        MapCfg result = xmlParser.getMapFromPath("test/valid_xml.xml");

        Assertions.assertNotNull(result);
        Assertions.assertEquals("TestName", result.getMapName());
    }

}