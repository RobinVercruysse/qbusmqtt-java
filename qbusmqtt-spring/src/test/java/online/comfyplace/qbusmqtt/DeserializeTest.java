package online.comfyplace.qbusmqtt;

import com.fasterxml.jackson.databind.ObjectReader;
import online.comfyplace.qbusmqtt.model.Configuration;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeserializeTest {
    @Test
    void testDeserializeConfiguration_onlyController() throws Exception {
        final Configuration actualConfiguration = getReader().readValue(TestUtil.CONFIGURATION_ONLY_CONTROLLER_JSON);

        assertEquals(TestUtil.CONFIGURATION_ONLY_CONTROLLER, actualConfiguration);
    }

    @Test
    void testDeserializeConfiguration_withFunctionBlocks() throws Exception {
        final Configuration actualConfiguration = getReader().readValue(TestUtil.CONFIGURATION_WITH_FUNCTIONBLOCKS_JSON);

        assertEquals(TestUtil.CONFIGURATION_WITH_FUNCTIONBLOCKS, actualConfiguration);
    }

    private static ObjectReader getReader() throws NoSuchFieldException, IllegalAccessException {
        final Field readerField = InboundMessageHandler.class.getDeclaredField("READER");
        readerField.setAccessible(true);
        return (ObjectReader) readerField.get(null);
    }


}
