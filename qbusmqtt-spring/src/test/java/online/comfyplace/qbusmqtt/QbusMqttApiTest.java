package online.comfyplace.qbusmqtt;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class QbusMqttApiTest {
    @Mock
    private MqttGateway mqttGateway;

    @Mock
    private TopicFactory topicFactory;

    @InjectMocks
    private QbusMqttApi qbusMqttApi;

    @Test
    public void testTurnOnSwitch_SendsCorrectMessage() {
        // todo implement test
    }
}
