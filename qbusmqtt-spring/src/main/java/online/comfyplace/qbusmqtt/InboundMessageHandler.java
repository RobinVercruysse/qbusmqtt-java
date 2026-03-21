package online.comfyplace.qbusmqtt;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import online.comfyplace.qbusmqtt.model.Configuration;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

@AllArgsConstructor
@Slf4j
class InboundMessageHandler implements MessageHandler {
    private static final ObjectReader READER = createObjectReader();

    private final TopicFactory topicFactory;
    private final QbusConfigurationHolder configurationHolder;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void handleMessage(@NonNull Message<?> message) throws MessagingException {
        final String topic = message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC, String.class);
        final String content = String.valueOf(message.getPayload());
        // TODO filter out non-qbus topics
        if (topicFactory.getConfigTopic().equals(topic)) {
            try {
                configurationHolder.setConfiguration(READER.readValue(content));
            } catch (JsonProcessingException e) {
                throw new MessagingException(message, "Failed to parse Qbus configuration message", e);
            } finally {
                applicationEventPublisher.publishEvent(new MqttMessage(topic, content));
            }
        } else {
            applicationEventPublisher.publishEvent(new MqttMessage(topic, content));
        }
    }

    private static ObjectReader createObjectReader() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
        return mapper.readerFor(Configuration.class);
    }
}
