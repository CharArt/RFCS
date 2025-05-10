package com.wb.amr.robot.flotilla.control.system;

import com.wb.amr.robot.flotilla.control.system.map.xml.MapCfg;
import com.wb.amr.robot.flotilla.control.system.tools.parser.XmlParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class XmlParserTest {

    private XmlParser xmlParser;

    @BeforeEach
    public void setUp() {
        xmlParser = new XmlParser();
    }

    @Test
    public void should_GetMapFromString_validXml() {
        String validXml = "<MapCfg><MapName>TestName</name></MapCfg>";
        MapCfg result = xmlParser.getMapFromString(validXml);
        Assertions.assertNotNull(result);
        Assertions.assertEquals("TestName", result.getMapName());
    }

    @Test
    public void should_GetMapFromString_invalidXml_returnsDefault() {
        String invalidXml = "<MapCfg><name>Broken</MapCfg>"; // не закрыт тег

        MapCfg result = xmlParser.getMapFromString(invalidXml);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(new MapCfg(), result);
    }

    @Test
    public void testGetMapFromPath_validResource() throws Exception {
        String validXml = "<MapCfg><MapName>FileTest</MapName></MapCfg>";
        InputStream inputStream = new ByteArrayInputStream(validXml.getBytes());

        try (MockedStatic<ClassPathResource> mockedStatic = Mockito.mockStatic(ClassPathResource.class)) {
            ClassPathResource mockResource = mock(ClassPathResource.class);
            when(mockResource.getInputStream()).thenReturn(inputStream);
            mockedStatic.when(() -> new ClassPathResource("test.xml")).thenReturn(mockResource);

            MapCfg result = xmlParser.getMapFromPath("test.xml");

            Assertions.assertNotNull(result);
            Assertions.assertEquals("FileTest", result.getMapName());
        }
    }

    @Test
    public void testGetMapFromUrl_validUrl() throws Exception {
        String validXml = "<MapCfg><MapName>UrlTest</MapName></MapCfg>";
        InputStream inputStream = new ByteArrayInputStream(validXml.getBytes());

        URI mockUri = mock(URI.class);
        URL mockUrl = mock(URL.class);

        try (MockedStatic<URI> mockedUriStatic = Mockito.mockStatic(URI.class)) {
            mockedUriStatic.when(() -> URI.create("http://example.com")).thenReturn(mockUri);
            when(mockUri.toURL()).thenReturn(mockUrl);
            when(mockUrl.openStream()).thenReturn(inputStream);

            MapCfg result = xmlParser.getMapFromUrl("http://example.com");

            Assertions.assertNotNull(result);
            Assertions.assertEquals("UrlTest", result.getMapName());
        }
    }

}
