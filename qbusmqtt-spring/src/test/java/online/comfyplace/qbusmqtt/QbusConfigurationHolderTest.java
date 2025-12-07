package online.comfyplace.qbusmqtt;

import org.junit.jupiter.api.Test;

import static online.comfyplace.qbusmqtt.TestUtil.CONFIGURATION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class QbusConfigurationHolderTest {
    @Test
    void testQbusConfigurationHolder() {
        final QbusConfigurationHolder configHolder = new QbusConfigurationHolder();

        assertNull(configHolder.getConfiguration());

        configHolder.setConfiguration(CONFIGURATION);

        assertEquals(CONFIGURATION, configHolder.getConfiguration());
    }
}
