package online.comfyplace.qbusmqtt.model;

public record Configuration(
        String app,
        Device[] devices,
        String version
) {}
