package online.comfyplace.qbusmqtt.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FunctionBlockProperties<T extends FunctionBlockValue> {
    private T value;
}
