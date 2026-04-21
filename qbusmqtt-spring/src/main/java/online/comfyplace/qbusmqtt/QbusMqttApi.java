package online.comfyplace.qbusmqtt;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class QbusMqttApi {
    private final MqttGateway gateway;
    private final TopicFactory topicFactory;
}
