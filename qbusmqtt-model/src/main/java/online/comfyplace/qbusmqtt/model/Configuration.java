package online.comfyplace.qbusmqtt.model;

import lombok.*;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Configuration {
    private String app;
    private Device[] devices;
    private String version;
}
