package online.comfyplace.qbusmqtt.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Location {
    private int id;
    private String name;
    private Location[] locations;
}
