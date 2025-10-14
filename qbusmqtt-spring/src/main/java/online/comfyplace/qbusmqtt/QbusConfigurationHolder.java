package online.comfyplace.qbusmqtt;

import online.comfyplace.qbusmqtt.model.Configuration;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicReference;

@Component
public class QbusConfigurationHolder {
    private final AtomicReference<Configuration> configuration = new AtomicReference<>();

    public Configuration getConfiguration() {
        return configuration.get();
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration.set(configuration);
    }
}
