package online.comfyplace.qbusmqtt.model;

public record DeviceProperties(
        DeviceProperty connectable,
        DeviceProperty connected
) {}
