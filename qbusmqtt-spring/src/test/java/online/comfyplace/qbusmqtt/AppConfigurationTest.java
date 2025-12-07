package online.comfyplace.qbusmqtt;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.GenericMessage;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class AppConfigurationTest {

    private AppConfiguration config;

    @BeforeEach
    void setUp() {
        config = new AppConfiguration();
    }

    @Test
    void testMqttInputChannel() {
        assertNotNull(config.mqttInputChannel());
    }

    @Test
    void testMqttOutboundChannel() {
        assertNotNull(config.mqttOutboundChannel());
    }

    @Test
    void testInbound() {
        final String url = "tcp://localhost:1883";
        final TopicFactory mockTopicFactory = mock(TopicFactory.class);
        when(mockTopicFactory.getConfigTopic()).thenReturn("config");
        when(mockTopicFactory.getGatewayStateTopic()).thenReturn("gatewayState");

        assertNotNull(config.inbound(url, mockTopicFactory));
    }

    @Test
    void testInboundHandler() {
        final TopicFactory mockTopicFactory = mock(TopicFactory.class);
        final QbusConfigurationHolder mockQbusConfigurationHolder = mock(QbusConfigurationHolder.class);

        assertNotNull(config.inboundHandler(mockTopicFactory, mockQbusConfigurationHolder));
    }

    @Test
    void testConnectOptions() {
        final String url = "tcp://localhost:1883";
        final String username = "username";
        final String password = "password";

        assertNotNull(config.connectOptions(url, username, password));
    }

    @Test
    void testMqttClientFactory() {
        final MqttConnectOptions connectOptions = mock(MqttConnectOptions.class);

        assertNotNull(config.mqttClientFactory(connectOptions));
    }

    @Test
    void testOutbound() {
        final TopicFactory mockTopicFactory = mock(TopicFactory.class);
        when(mockTopicFactory.getGatewayStateTopic()).thenReturn("gatewayState");
        final MqttPahoClientFactory mockClientFactory = mock(MqttPahoClientFactory.class);

        assertNotNull(config.outbound(mockTopicFactory, mockClientFactory));
    }

    @Test
    void testDeadLetterChannel() {
        assertNotNull(config.deadLetterChannel());
    }

    @Test
    void testMqttErrorChannel() {
        assertNotNull(config.mqttErrorChannel());
    }

    @Test
    void testMqttErrorFlow() {
        final MessageChannel mockErrorChannel = mock(MessageChannel.class);
        final MessageChannel mockDeadLetterChannel = mock(MessageChannel.class);

        assertNotNull(config.mqttErrorFlow(mockErrorChannel, mockDeadLetterChannel));
    }

    @Test
    void testErrorFlowHandler() {
        final MessageChannel mockDeadLetterChannel = mock(MessageChannel.class);
        final Message<?> failedMessage = new GenericMessage<>("failed");
        final MessagingException messagingException = new MessagingException(failedMessage, "test", new Exception("cause"));
        final Message<MessagingException> errorMessage = new GenericMessage<>(messagingException);
        final MessageHandler errorFlowHandler = config.errorFlowHandler(mockDeadLetterChannel);

        assertNotNull(errorFlowHandler);

        errorFlowHandler.handleMessage(errorMessage);

        verify(mockDeadLetterChannel, times(1)).send(failedMessage);
    }

    @Test
    void testErrorFlowHandlerIgnoresIrrelevantException() {
        final MessageChannel mockDeadLetterChannel = mock(MessageChannel.class);
        final Message<IllegalArgumentException> errorMessage = new GenericMessage<>(new IllegalArgumentException());
        final MessageHandler errorFlowHandler = config.errorFlowHandler(mockDeadLetterChannel);

        errorFlowHandler.handleMessage(errorMessage);

        verifyNoInteractions(mockDeadLetterChannel);
    }

    @Test
    void testErrorFlowHandlerIgnoresExceptionsWithoutMessage() {
        final MessageChannel mockDeadLetterChannel = mock(MessageChannel.class);
        final MessagingException messagingException = new MessagingException("no failed message");
        final Message<MessagingException> errorMessage = new GenericMessage<>(messagingException);
        final MessageHandler errorFlowHandler = config.errorFlowHandler(mockDeadLetterChannel);

        errorFlowHandler.handleMessage(errorMessage);

        verifyNoInteractions(mockDeadLetterChannel);
    }
}
