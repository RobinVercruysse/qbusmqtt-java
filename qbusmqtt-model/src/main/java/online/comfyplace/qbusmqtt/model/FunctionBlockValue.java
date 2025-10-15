package online.comfyplace.qbusmqtt.model;

import java.math.BigDecimal;

public abstract class FunctionBlockValue {
    public static class AnalogValue extends FunctionBlockValue {
        private short max;
        private short min;
        private boolean read;
        private BigDecimal step;
        private String type;
        private boolean write;
    }

    public static class OnOffValue extends FunctionBlockValue {
        private boolean read;
        private String type;
        private boolean write;
    }
}
