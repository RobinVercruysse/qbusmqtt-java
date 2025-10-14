package online.comfyplace.qbusmqtt.model;

public record Location(
        int id,
        String name,
        Location[] locations
) {}
