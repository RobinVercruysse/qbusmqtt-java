package online.comfyplace.qbusmqtt;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
        assertNotNull(config.mqttErrorFlow());
    }
}
