package online.comfyplace.qbusmqtt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tools.jackson.databind.json.JsonMapper;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class QbusMqttApiTest {
    @Mock
    private MqttGateway mqttGateway;

    @Mock
    private TopicFactory topicFactory;

    private QbusMqttApi qbusMqttApi;

    @BeforeEach
    void setUp() {
        qbusMqttApi = new QbusMqttApi(JsonMapper.builder().build(), mqttGateway, topicFactory);
    }

    @Test
    public void testTurnOnSwitch_SendsCorrectMessage() {
        final String outputStateTopic = "topic";
        final String deviceId = "myDevice";
        final String entityId = "myEntity";
        when(topicFactory.getConfigTopic()).thenReturn("configTopic");
        when(topicFactory.getOutputStateTopic(deviceId, entityId)).thenReturn(outputStateTopic);

        qbusMqttApi.turnOnSwitch(deviceId, entityId);

        verify(mqttGateway).sendToMqtt(outputStateTopic, "{\"id\":\"" + entityId + "\",\"properties\":{\"connected\":true},\"type\":\"event\"}");

        // todo implement test
    }
}
