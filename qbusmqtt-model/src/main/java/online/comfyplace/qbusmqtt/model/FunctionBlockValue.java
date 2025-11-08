package online.comfyplace.qbusmqtt.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public abstract class FunctionBlockValue {
    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class AnalogValue extends FunctionBlockValue {
        private short max;
        private short min;
        private boolean read;
        private BigDecimal step;
        private String type;
        private boolean write;
    }

    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class OnOffValue extends FunctionBlockValue {
        private boolean read;
        private String type;
        private boolean write;
    }
}
