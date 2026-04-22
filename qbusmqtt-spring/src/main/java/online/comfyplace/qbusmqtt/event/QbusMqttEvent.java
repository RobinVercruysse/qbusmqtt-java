package online.comfyplace.qbusmqtt.event;

public record QbusMqttEvent(String mqttTopic, String mqttContent, EventType type) {
    public enum EventType {
        CONFIGURATION_UPDATED,
        OTHER
    }
}
