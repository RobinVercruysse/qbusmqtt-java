package online.comfyplace.qbusmqtt;

import com.fasterxml.jackson.databind.ObjectReader;
import online.comfyplace.qbusmqtt.model.*;
import online.comfyplace.qbusmqtt.model.Configuration;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeserializeTest {
    @Test
    void testDeserializeConfiguration() throws Exception {
        final Configuration actualConfiguration = getReader().readValue(TestUtil.CONFIGURATION_JSON);

        assertEquals(TestUtil.CONFIGURATION, actualConfiguration);
    }

    private static ObjectReader getReader() throws NoSuchFieldException, IllegalAccessException {
        final Field readerField = InboundMessageHandler.class.getDeclaredField("READER");
        readerField.setAccessible(true);
        return (ObjectReader) readerField.get(null);
    }
}
