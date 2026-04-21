package online.comfyplace.qbusmqtt;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

@EnableIntegration
@IntegrationComponentScan(includeFilters = @ComponentScan.Filter(classes = MessagingGateway.class))
@Import({TopicFactory.class, QbusConfigurationHolder.class, QbusMqttApi.class})
@Configuration
class AppConfiguration {
    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer inbound(
            @Value("${mqtt.url}") String url,
            TopicFactory topicFactory
    ) {
        final MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(url, "pahoInbound",
                topicFactory.getGatewayStateTopic(),
                topicFactory.getConfigTopic());
        adapter.setManualAcks(false);
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputChannel());
        adapter.setErrorChannel(mqttErrorChannel());
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler inboundHandler(TopicFactory topicFactory, QbusConfigurationHolder configurationHolder, ApplicationEventPublisher publisher) {
        return new InboundMessageHandler(topicFactory, configurationHolder, publisher);
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
            TopicFactory topicFactory,
            MqttPahoClientFactory mqttPahoClientFactory) {
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler("pahoOutbound", mqttPahoClientFactory);
        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic(topicFactory.getGatewayStateTopic());
        return messageHandler;
    }

    @Bean
    public MessageChannel deadLetterChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel mqttErrorChannel() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow mqttErrorFlow(
            @Qualifier("mqttErrorChannel") MessageChannel mqttErrorChannel,
            @Qualifier("deadLetterChannel") MessageChannel deadLetterChannel) {
        return IntegrationFlow
                .from(mqttErrorChannel)
                .handle(errorFlowHandler(deadLetterChannel))
                .get();
    }

    MessageHandler errorFlowHandler(@Qualifier("deadLetterChannel") MessageChannel deadLetterChannel) {
        return message -> {
            if (message.getPayload() instanceof MessagingException &&
                    ((MessagingException) message.getPayload()).getFailedMessage() != null) {
                deadLetterChannel.send(((MessagingException) message.getPayload()).getFailedMessage());
            }
        };
    }

    @Bean
    @ServiceActivator(inputChannel = "deadLetterChannel")
    public MessageHandler deadLetter() {
        return new DeadLetterMessageHandler();
    }
}
