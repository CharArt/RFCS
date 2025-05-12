package com.wb.amr.robot.flotilla.control.system;

import com.wb.amr.robot.flotilla.control.system.map.json.vda.Order;
import com.wb.amr.robot.flotilla.control.system.map.json.vda.Status;
import com.wb.amr.robot.flotilla.control.system.tools.parser.JsonParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest(classes = JsonParser.class)
public class JsonParserTest {

    private JsonParser jsonParser;

    @BeforeEach
    public void setUp() {
        jsonParser = new JsonParser();
    }

    @Test
    public void testGetOrderFromString_shouldReturnMapCfg_WhenGetString() throws IOException {
        String validJson = "{\"headerId\": 154829630012,\"orderId\": \"154829630012\"}";
        Order result = jsonParser.getOrderFromString(validJson);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("154829630012", result.getOrderId());
    }

    @Test
    public void testGetOrderFromString_shouldThrowError_WhenDoesNotGetString() {
        String invalidJson = "not-such-valid-json";
        String emptyJson = "";

        Executable executableNotValidXml = new Executable() {
            @Override
            public void execute() throws Throwable {
                jsonParser.getOrderFromString(invalidJson);
            }
        };

        Executable executableEmptyXml = new Executable() {
            @Override
            public void execute() throws Throwable {
                jsonParser.getOrderFromString(emptyJson);
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
    public void testGetOrderFromPath_shouldThrowError_WhenDoesNotGetFile() {
        Executable executable = new Executable() {
            @Override
            public void execute() throws Throwable {
                jsonParser.getOrderFromPath("no-such-file.xml");
            }
        };
        IOException ioException = Assertions.assertThrows(IOException.class, executable);
        Assertions.assertNotNull(ioException);
        Assertions.assertInstanceOf(IOException.class, ioException);
        Assertions.assertTrue(ioException.getMessage().contains("no-such-file.xml"));
    }

    @Test
    public void testGetStatusFromString_shouldReturnMapCfg_WhenGetString() throws IOException {
        String validJson = "{ \"headerId\": 1450,\"timestamp\": \"2024-10-27T13:04:10.159Z\"}";
        Status result = jsonParser.getStatusFromString(validJson);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("2024-10-27T13:04:10.159Z", result.getTimestamp());
    }

    @Test
    public void testGetStatusFromString_shouldThrowError_WhenDoesNotGetString() {
        String invalidJson = "not-such-valid-json";
        String emptyJson = "";

        Executable executableNotValidXml = new Executable() {
            @Override
            public void execute() throws Throwable {
                Status resultNotValidJson = jsonParser.getStatusFromString(invalidJson);
            }
        };

        Executable executableEmptyXml = new Executable() {
            @Override
            public void execute() throws Throwable {
                jsonParser.getStatusFromString(emptyJson);
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
    public void testGetStatusFromPath_shouldThrowError_WhenDoesNotGetFile() {
        Executable executable = new Executable() {
            @Override
            public void execute() throws Throwable {
                jsonParser.getStatusFromPath("no-such-file.xml");
            }
        };
        IOException ioException = Assertions.assertThrows(IOException.class, executable);
        Assertions.assertNotNull(ioException);
        Assertions.assertInstanceOf(IOException.class, ioException);
        Assertions.assertTrue(ioException.getMessage().contains("no-such-file.xml"));
    }
}
