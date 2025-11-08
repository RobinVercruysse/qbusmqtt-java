package online.comfyplace.qbusmqtt.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DeviceProperties {
    private DeviceProperty connectable;
    private DeviceProperty connected;
}
