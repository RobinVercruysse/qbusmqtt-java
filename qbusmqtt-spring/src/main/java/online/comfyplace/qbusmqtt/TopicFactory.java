package online.comfyplace.qbusmqtt;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TopicFactory {
    private final String topicPrefix;

    public String getConfigTopic() {
        return String.format("%s/config", topicPrefix);
    }

    public String getGetConfigTopic() {
        return String.format("%s/getConfig", topicPrefix);
    }

    public String getGatewayStateTopic() {
        return String.format("%s/state", topicPrefix);
    }

    public String getGetGatewayStateTopic() {
        return String.format("%s/getState", topicPrefix);
    }

    public String getDeviceStateTopic(String deviceId) {
        return String.format("%s/%s/state", topicPrefix, deviceId);
    }

    public String getDeviceCommandTopic(String deviceId) {
        return String.format("%s/%s/setState", topicPrefix, deviceId);
    }

    public String getOutputStateTopic(String deviceId, String entityId) {
        return String.format("%s/%s/%s/state", topicPrefix, deviceId, entityId);
    }

    public String getOutputCommandTopic(String deviceId, String entityId) {
        return String.format("%s/%s/%s/setState", topicPrefix, deviceId, entityId);
    }
}
