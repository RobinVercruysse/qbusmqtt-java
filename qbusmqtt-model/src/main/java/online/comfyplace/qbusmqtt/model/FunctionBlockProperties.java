package online.comfyplace.qbusmqtt.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FunctionBlockProperties {
    private final Object value;

    public FunctionBlockProperties() {
        this.value = null;
    }
}
