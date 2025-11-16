package online.comfyplace.qbusmqtt;

import online.comfyplace.qbusmqtt.model.Configuration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InboundMessageHandlerTest {
    private static final String CONFIG_TOPIC = "testPrefix/config";

    @Mock
    private TopicFactory mockTopicFactory;

    @Mock
    private QbusConfigurationHolder mockConfigurationHolder;

    @InjectMocks
    private InboundMessageHandler handler;

    @Test
    void testHandleMessage_ParsesAndStoresConfiguration() {
        final ArgumentCaptor<Configuration> configurationCaptor = ArgumentCaptor.forClass(Configuration.class);
        when(mockTopicFactory.getConfigTopic()).thenReturn(CONFIG_TOPIC);
        doNothing().when(mockConfigurationHolder).setConfiguration(configurationCaptor.capture());
        final Message<String> message = new GenericMessage<>(TestUtil.CONFIGURATION_JSON, Map.of(MqttHeaders.RECEIVED_TOPIC, CONFIG_TOPIC));

        handler.handleMessage(message);

        verify(mockConfigurationHolder, times(1)).setConfiguration(any(Configuration.class));
        assertEquals(TestUtil.CONFIGURATION, configurationCaptor.getValue());
    }
}
