package online.comfyplace.qbusmqtt.model;

public record DeviceProperty(
        boolean read,
        String type,
        boolean write
) {}
