package online.comfyplace.qbusmqtt;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import online.comfyplace.qbusmqtt.event.QbusMqttEvent;
import online.comfyplace.qbusmqtt.model.Configuration;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.ObjectReader;

@AllArgsConstructor
@Slf4j
class InboundMessageHandler implements MessageHandler {
    private static final ObjectReader READER = new ObjectMapper().reader(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES).forType(Configuration.class);

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
            } catch (JacksonException e) {
                throw new MessagingException(message, "Failed to parse Qbus configuration message", e);
            } finally {
                applicationEventPublisher.publishEvent(new QbusMqttEvent(topic, content, QbusMqttEvent.EventType.CONFIGURATION_UPDATED));
            }
        } else {
            applicationEventPublisher.publishEvent(new QbusMqttEvent(topic, content, QbusMqttEvent.EventType.OTHER));
        }
    }
}
