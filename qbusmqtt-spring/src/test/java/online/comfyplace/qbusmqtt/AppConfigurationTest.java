package online.comfyplace.qbusmqtt;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestPropertySource(properties = """
        mqtt.topic-prefix=testPrefix
        mqtt.url=tcp://localhost:1883
        mqtt.username=username
        mqtt.passwords=password
        """)
@SpringBootTest(classes = AppConfiguration.class)
class AppConfigurationTest {

    @Autowired
    private MessageChannel mqttInputChannel;

    @Autowired
    private MessageChannel mqttOutboundChannel;

    @Autowired
    private MessageProducer inbound;

    @Autowired
    private MessageHandler inboundHandler;

    @Autowired
    private MqttConnectOptions connectOptions;

    @Autowired
    private MqttPahoClientFactory mqttClientFactory;

    @Autowired
    private MessageHandler outbound;

    @Test
    public void testBeans() {
        assertNotNull(mqttInputChannel);
        assertNotNull(mqttOutboundChannel);
        assertNotNull(inbound);
        assertNotNull(inboundHandler);
        assertNotNull(connectOptions);
        assertNotNull(mqttClientFactory);
        assertNotNull(outbound);
    }
}
