package online.comfyplace.qbusmqtt;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;

@MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
interface MqttGateway {
    void sendToMqtt(@Header(MqttHeaders.TOPIC) String topic, String data);
}
