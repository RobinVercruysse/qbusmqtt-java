package online.comfyplace.qbusmqtt;

import org.junit.jupiter.api.Test;

import static online.comfyplace.qbusmqtt.TestUtil.CONFIGURATION_WITH_FUNCTIONBLOCKS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class QbusConfigurationHolderTest {
    @Test
    void testQbusConfigurationHolder() {
        final QbusConfigurationHolder configHolder = new QbusConfigurationHolder();

        assertNull(configHolder.getConfiguration());

        configHolder.setConfiguration(CONFIGURATION_WITH_FUNCTIONBLOCKS);

        assertEquals(CONFIGURATION_WITH_FUNCTIONBLOCKS, configHolder.getConfiguration());
    }
}
