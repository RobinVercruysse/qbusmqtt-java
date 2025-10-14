package online.comfyplace.qbusmqtt.model;

public record Device(
        FunctionBlock[] functionBlocks,
        String id,
        String ip,
        Location[] locations,
        String mac,
        String name,
        Properties properties,
        String serialNr,
        String type,
        String version) {
}
