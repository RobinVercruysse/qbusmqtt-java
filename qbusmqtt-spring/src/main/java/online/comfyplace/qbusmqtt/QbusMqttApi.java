package online.comfyplace.qbusmqtt;

import lombok.AllArgsConstructor;
import online.comfyplace.qbusmqtt.model.FunctionBlockState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
@AllArgsConstructor
public class QbusMqttApi {
    @Autowired
    private ObjectMapper mapper;

    private final MqttGateway gateway;
    private final TopicFactory topicFactory;

    public void turnOnSwitch(final String deviceId, final String entityId) {
        final String topic = topicFactory.getOutputStateTopic(deviceId, entityId);
        final FunctionBlockState state = new FunctionBlockState.OnOffState(entityId, "state", Boolean.TRUE.toString());
        gateway.sendToMqtt(topic, mapper.writeValueAsString(state));
    }
}
