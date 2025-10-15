package online.comfyplace.qbusmqtt.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
// aka Output
public abstract class FunctionBlock<T extends FunctionBlockValue> {
        private String id;
        private String name;
        private String originalName;
        private String refId;
        private FunctionBlockProperties<T> properties;
        private Map<String, Object> actions;
        private String location; // TODO model locationId + location as Location object?
        private int locationId;
        private String variant; // TODO improve model

    public abstract String getType();

    public class AnalogFunctionBlock extends FunctionBlock<FunctionBlockValue.AnalogValue> {
        @Override
        public String getType() {
            return "analog";
        }
    }

    public class OnOffFunctionBlock extends FunctionBlock<FunctionBlockValue.OnOffValue> {
        @Override
        public String getType() {
            return "onoff";
        }
    }

    // TODO search for additional FunctionBlock types
}
