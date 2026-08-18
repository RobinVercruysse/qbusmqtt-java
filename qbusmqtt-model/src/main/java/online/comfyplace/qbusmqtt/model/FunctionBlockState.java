package online.comfyplace.qbusmqtt.model;

import java.util.HashMap;
import java.util.Map;

public abstract class FunctionBlockState {
    private final String id;
    private final String type;
    private final Map<String, String> properties = new HashMap<>();

    public FunctionBlockState(String id, String type) {
        this.id = id;
        this.type = type;
    }

    void setProperty(final String key, final String value) {
        this.properties.put(key, value);
    }

    String getProperty(final String key) {
        return this.properties.get(key);
    }

    public static class OnOffState extends FunctionBlockState {
        public OnOffState(String id, String type, String value) {
            super(id, type);
            setProperty("value", value);
        }
    }
}
