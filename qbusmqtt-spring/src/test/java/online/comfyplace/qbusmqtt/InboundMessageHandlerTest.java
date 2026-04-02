package online.comfyplace.qbusmqtt;

import online.comfyplace.qbusmqtt.model.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.GenericMessage;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InboundMessageHandlerTest {
    private static final String CONFIG_TOPIC = "testPrefix/config";

    @Mock
    private TopicFactory mockTopicFactory;

    @Mock
    private QbusConfigurationHolder mockConfigurationHolder;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @InjectMocks
    private InboundMessageHandler handler;

    @BeforeEach
    void setUp() {
        lenient().when(mockTopicFactory.getConfigTopic()).thenReturn(CONFIG_TOPIC);
    }

    @Test
    void testHandleMessage_ParsesAndStoresConfiguration() {
        final ArgumentCaptor<Configuration> configurationCaptor = ArgumentCaptor.forClass(Configuration.class);
        doNothing().when(mockConfigurationHolder).setConfiguration(configurationCaptor.capture());
        final Message<String> message = new GenericMessage<>(TestUtil.CONFIGURATION_WITH_FUNCTIONBLOCKS_JSON, Map.of(MqttHeaders.RECEIVED_TOPIC, CONFIG_TOPIC));
        final MqttMessage expectedMqttMessage = new MqttMessage(CONFIG_TOPIC, TestUtil.CONFIGURATION_WITH_FUNCTIONBLOCKS_JSON);

        handler.handleMessage(message);

        verify(mockConfigurationHolder, times(1)).setConfiguration(any(Configuration.class));
        assertEquals(TestUtil.CONFIGURATION_WITH_FUNCTIONBLOCKS, configurationCaptor.getValue());
        verify(applicationEventPublisher, times(1)).publishEvent(expectedMqttMessage);
    }

    @Test
    void testHandleMessage_ThrowsMessagingExceptionWhenJsonParsingFails() {
        final String payload = "<xml></xml>";
        final Message<String> message = new GenericMessage<>(payload, Map.of(MqttHeaders.RECEIVED_TOPIC, CONFIG_TOPIC));
        final MqttMessage expectedMqttMessage = new MqttMessage(CONFIG_TOPIC, payload);

        Assertions.assertThrows(MessagingException.class, () -> handler.handleMessage(message));
        verify(applicationEventPublisher, times(1)).publishEvent(expectedMqttMessage);
    }

    @Test
    void testHandleMessage_DoesNotStoreMessagesWithoutTopicInConfig() {
        final Message<String> message = new GenericMessage<>("bla", Collections.emptyMap());
        final MqttMessage expectedMqttMessage = new MqttMessage(null, "bla");

        handler.handleMessage(message);

        verifyNoInteractions(mockConfigurationHolder);
        verify(applicationEventPublisher, times(1)).publishEvent(expectedMqttMessage);
    }

    @Test
    void testHandleMessage_DoesNotStoreNonConfigMessagesInConfig() {
        final Message<String> message = new GenericMessage<>("bla", Map.of(MqttHeaders.RECEIVED_TOPIC, "something"));
        final MqttMessage expectedMqttMessage = new MqttMessage("something", "bla");

        handler.handleMessage(message);

        verifyNoInteractions(mockConfigurationHolder);
        verify(applicationEventPublisher, times(1)).publishEvent(expectedMqttMessage);
    }
}
