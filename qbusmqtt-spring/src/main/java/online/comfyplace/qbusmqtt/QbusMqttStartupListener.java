package online.comfyplace.qbusmqtt;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.integration.mqtt.event.MqttSubscribedEvent;
import org.springframework.integration.support.context.NamedComponent;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
class QbusMqttStartupListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(QbusMqttStartupListener.class);
    private static final String INBOUND_ADAPTER_BEAN_NAME = "inbound";

    private final MqttGateway gateway;
    private final TopicFactory topicFactory;
    private final AtomicBoolean configRequested = new AtomicBoolean(false);

    @EventListener
    void onMqttSubscribed(MqttSubscribedEvent event) {
        final Object source = event.getSource();
        if (source instanceof NamedComponent namedComponent
                && INBOUND_ADAPTER_BEAN_NAME.equals(namedComponent.getComponentName())
                && configRequested.compareAndSet(false, true)) {
            LOGGER.info("Requesting Qbus configuration...");
            gateway.sendToMqtt(topicFactory.getGetConfigTopic(), "");
        }
    }
}
