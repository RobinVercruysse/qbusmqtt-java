package online.comfyplace.qbusmqtt;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.handler.annotation.Header;

@org.springframework.context.annotation.Configuration
public class Configuration {
    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer inbound(
            @Value("${mqtt.url}") String url,
            @Value("${mqtt.username}") String username,
            TopicFactory topicFactory
    ) {
        final MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(url, "pahoInbound",
                topicFactory.getGatewayStateTopic(),
                topicFactory.getConfigTopic());
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler inboundHandler(TopicFactory topicFactory, QbusConfigurationHolder configurationHolder) {
        return new InboundMessageHandler(topicFactory, configurationHolder);
    }

    @Bean
    public TopicFactory topicFactory(@Value("${mqtt.topic-prefix}") String topicPrefix) {
        return new TopicFactory(topicPrefix);
    }

    @Bean
    public MqttConnectOptions connectOptions(
            @Value("${mqtt.url}") String url,
            @Value("${mqtt.username}") String username,
            @Value("${mqtt.password}") String password
    ) {
        final MqttConnectOptions connectOptions = new MqttConnectOptions();
        connectOptions.setServerURIs(new String[] {url});
        connectOptions.setUserName(username);
        connectOptions.setPassword(password.toCharArray());
        return connectOptions;
    }

    @Bean
    public MqttPahoClientFactory mqttClientFactory(MqttConnectOptions connectOptions) {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(connectOptions);
        return factory;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler outbound(
            @Value("${mqtt.url}") String url,
            @Value("${mqtt.username}") String username,
            TopicFactory topicFactory,
            MqttPahoClientFactory mqttPahoClientFactory) {
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler("pahoOutbound", mqttPahoClientFactory);
        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic(topicFactory.getGatewayStateTopic());
        return messageHandler;
    }

    @MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
    public interface MqttGateway {
        void sendToMqtt(@Header(MqttHeaders.TOPIC) String topic, String data);
    }
}
