package online.comfyplace.qbusmqtt;

import com.fasterxml.jackson.databind.ObjectReader;
import online.comfyplace.qbusmqtt.model.Configuration;
import online.comfyplace.qbusmqtt.model.Device;
import org.junit.jupiter.api.Test;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeserializeTest {
    @Test
    void testDeserializeConfiguration() throws Exception {
        final Configuration expectedConfiguration = new Configuration()
                .setApp("myApp")
                .setDevices(new Device[]{})
                .setVersion("myVersion");

        final JSONObject json = new JSONObject(Map.of(
                "app", "myApp",
                "devices", new JSONObject[]{},
                "version", "myVersion"
        ));

        final Configuration actualConfiguration = getReader().readValue(json.toString());

        assertEquals(expectedConfiguration, actualConfiguration);
    }

    private static ObjectReader getReader() throws NoSuchFieldException, IllegalAccessException {
        final Field readerField = InboundMessageHandler.class.getDeclaredField("READER");
        readerField.setAccessible(true);
        return (ObjectReader) readerField.get(null);
    }
}
