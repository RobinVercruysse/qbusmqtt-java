package online.comfyplace.qbusmqtt;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import online.comfyplace.qbusmqtt.model.Configuration;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

@AllArgsConstructor
@Slf4j
public class InboundMessageHandler implements MessageHandler {
    private static final ObjectReader READER = createObjectReader();

    private final TopicFactory topicFactory;
    private final QbusConfigurationHolder configurationHolder;

    @Override
    public void handleMessage(@NonNull Message<?> message) throws MessagingException {
        if (message.getHeaders().containsKey(MqttHeaders.RECEIVED_TOPIC)
                && topicFactory.getConfigTopic().equals(message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC, String.class))) {
            try {
                log.info("Qbus configuration received");
                configurationHolder.setConfiguration(READER.readValue((String) message.getPayload()));
            } catch (JsonProcessingException e) {
                log.error("Failed to set Qbus configuration from json", e);
            }
        }
    }

    private static ObjectReader createObjectReader() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
        return mapper.readerFor(Configuration.class);
    }
}
