package online.comfyplace.qbusmqtt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestPropertySource(properties = """
        mqtt.topic-prefix=testPrefix
        """)
@ContextConfiguration(classes = TopicFactory.class)
@SpringBootTest
class TopicFactoryTest {
    private static final String TOPIC_PREFIX = "testPrefix";

    @Autowired
    private TopicFactory topicFactory;

    @Test
    void testConfigTopic() {
        assertEquals(TOPIC_PREFIX + "/config", topicFactory.getConfigTopic());
    }

    @Test
    void testGetConfigTopic() {
        assertEquals(TOPIC_PREFIX + "/getConfig", topicFactory.getGetConfigTopic());
    }

    @Test
    void testGatewayStateTopic() {
        assertEquals(TOPIC_PREFIX + "/state", topicFactory.getGatewayStateTopic());
    }

    @Test
    void testGetGatewayStateTopic() {
        assertEquals(TOPIC_PREFIX + "/getState", topicFactory.getGetGatewayStateTopic());
    }

    @Test
    void testDeviceStateTopic() {
        final String deviceId = "testDeviceId";
        assertEquals(TOPIC_PREFIX + "/" + deviceId + "/state", topicFactory.getDeviceStateTopic(deviceId));
    }

    @Test
    void testDeviceCommandTopic() {
        final String deviceId = "testDeviceId";
        assertEquals(TOPIC_PREFIX + "/" + deviceId + "/" + "setState", topicFactory.getDeviceCommandTopic(deviceId));
    }

    @Test
    void testOutputStateTopic() {
        final String deviceId = "testDeviceId";
        final String entityId = "testEntityId";
        assertEquals(TOPIC_PREFIX + "/" + deviceId + "/" + entityId + "/state", topicFactory.getOutputStateTopic(deviceId, entityId));
    }

    @Test
    void testOutputCommandTopic() {
        final String deviceId = "testDeviceId";
        final String entityId = "testEntityId";
        assertEquals(TOPIC_PREFIX + "/" + deviceId + "/" + entityId + "/setState", topicFactory.getOutputCommandTopic(deviceId, entityId));
    }
}
