package online.comfyplace.qbusmqtt.model;

import lombok.Builder;

@Builder
public class FunctionBlockProperties<T extends FunctionBlockValue> {
    private final T value;
}
