package online.comfyplace.qbusmqtt.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Device {
    private FunctionBlock<?>[] functionBlocks;
    private String id;
    private String ip;
    private Location[] locations;
    private String mac;
    private String name;
    private DeviceProperties properties;
    private String serialNr;
    private String type;
    private String version;
}
