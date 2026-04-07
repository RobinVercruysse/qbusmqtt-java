package online.comfyplace.qbusmqtt.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Map;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = FunctionBlock.OnOffFunctionBlock.class, name = "onoff")
})
// aka Output
public abstract class FunctionBlock<T extends FunctionBlockValue> {
        private String id;
        private String name;
        private String originalName;
        private String refId;
        private FunctionBlockProperties properties;
        private Map<String, Object> actions;
        private String location; // TODO model locationId + location as Location object?
        private int locationId;
        private String variant; // TODO improve model

    public abstract String getType();

    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class AnalogFunctionBlock extends FunctionBlock<FunctionBlockValue.AnalogValue> {
        @Override
        public String getType() {
            return "analog";
        }
    }

    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class OnOffFunctionBlock extends FunctionBlock<FunctionBlockValue.OnOffValue> {
        @Override
        public String getType() {
            return "onoff";
        }
    }

    // TODO search for additional FunctionBlock types
}
