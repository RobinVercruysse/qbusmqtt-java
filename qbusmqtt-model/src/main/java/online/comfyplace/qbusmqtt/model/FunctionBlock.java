package online.comfyplace.qbusmqtt.model;

import java.util.Map;

// aka Output
public record FunctionBlock(
        String id,
        String type,
        String name,
        String originalName,
        String refId,
        FunctionBlockProperties properties,
        Map<String, Object> actions,
        String location, // TODO model locationId + location as Location object?
        int locationId,
        String variant // TODO improve model
) {}
