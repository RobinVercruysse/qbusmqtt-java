package online.comfyplace.qbusmqtt;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class QbusMqttApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(QbusMqttApi.class);

    private final MqttGateway gateway;
    private final TopicFactory topicFactory;

    public void init() {
        LOGGER.info("Requesting Qbus configuration...");
        gateway.sendToMqtt(topicFactory.getConfigTopic(), "");
    }
}
