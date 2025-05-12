package com.wb.amr.robot.flotilla.control.system;

import com.wb.amr.robot.flotilla.control.system.map.xml.MapCfg;
import com.wb.amr.robot.flotilla.control.system.tools.parser.XmlParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest(classes = XmlParser.class)
public class XmlParserTest {

    private XmlParser xmlParser;

    @BeforeEach
    public void setUp() {
        xmlParser = new XmlParser();
    }


    @Test
    public void testGetMapFromString_shouldReturnMapCfg_WhenGetString() throws IOException {
        String validXml = "<MapCfg><MapName>TestName</MapName></MapCfg>";
        MapCfg result = xmlParser.getMapFromString(validXml);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("TestName", result.getMapName());
    }

    @Test
    public void testGetMapFromString_shouldThrowError_WhenDoesNotGetString() {
        String invalidXml = "not-such-valid-xml";
        String emptyXml = "";

        Executable executableNotValidXml = new Executable() {
            @Override
            public void execute() throws Throwable {
                xmlParser.getMapFromString(invalidXml);
            }
        };

        Executable executableEmptyXml = new Executable() {
            @Override
            public void execute() throws Throwable {
                xmlParser.getMapFromString(emptyXml);
            }
        };

        IOException ioException1 = Assertions.assertThrows(IOException.class, executableNotValidXml);
        IOException ioException2 = Assertions.assertThrows(IOException.class, executableEmptyXml);

        Assertions.assertNotNull(ioException1);
        Assertions.assertNotNull(ioException2);

        Assertions.assertInstanceOf(IOException.class, ioException1);
        Assertions.assertInstanceOf(IOException.class, ioException2);

    }

    @Test
    public void testGetMapFromPath_shouldThrowError_WhenDoesNotGetFile() {
        Executable executable = new Executable() {
            @Override
            public void execute() throws Throwable {
                xmlParser.getMapFromPath("no-such-file.xml");
            }
        };
        IOException ioException = Assertions.assertThrows(IOException.class, executable);
        Assertions.assertNotNull(ioException);
        Assertions.assertInstanceOf(IOException.class, ioException);
        Assertions.assertTrue(ioException.getMessage().contains("no-such-file.xml"));
    }
}