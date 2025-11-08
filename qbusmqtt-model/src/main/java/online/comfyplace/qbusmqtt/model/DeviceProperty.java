package online.comfyplace.qbusmqtt.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DeviceProperty {
    private boolean read;
    private String type;
    private boolean write;
}
