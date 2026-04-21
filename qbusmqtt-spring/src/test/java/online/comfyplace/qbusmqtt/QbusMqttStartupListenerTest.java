package online.comfyplace.qbusmqtt;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
import org.springframework.integration.mqtt.event.MqttSubscribedEvent;
import org.springframework.integration.support.context.NamedComponent;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QbusMqttStartupListenerTest {
    @Mock
    private MqttGateway gatewayMock;

    @Mock
    private TopicFactory topicFactoryMock;

    @InjectMocks
    private QbusMqttStartupListener startupListener;

    private ListAppender<ILoggingEvent> logAppender;

    @BeforeEach
    void setUp() {
        final Logger logger = (Logger) LoggerFactory.getLogger(QbusMqttStartupListener.class);
        logAppender = new ListAppender<>();
        logAppender.start();
        logger.addAppender(logAppender);
    }

    @AfterEach
    void tearDown() {
        final Logger logger = (Logger) LoggerFactory.getLogger(QbusMqttStartupListener.class);
        logger.detachAppender(logAppender);
    }

    @Test
    void onMqttSubscribed_requestsConfigOnlyOnce() {
        final String getConfigTopic = "testGetConfig";
        when(topicFactoryMock.getGetConfigTopic()).thenReturn(getConfigTopic);
        final NamedComponent source = mock(NamedComponent.class);
        when(source.getComponentName()).thenReturn("inbound");
        final MqttSubscribedEvent event = new MqttSubscribedEvent(source, "subscribed");

        startupListener.onMqttSubscribed(event);
        startupListener.onMqttSubscribed(event);

        verify(gatewayMock, times(1)).sendToMqtt(getConfigTopic, "");
        verifyNoMoreInteractions(gatewayMock);

        Assertions.assertThat(logAppender.list)
                .hasSize(1)
                .first()
                .extracting(ILoggingEvent::getMessage)
                .isEqualTo("Requesting Qbus configuration...");
    }

    @Test
    void onMqttSubscribed_ignoresOtherComponents() {
        final NamedComponent source = mock(NamedComponent.class);
        when(source.getComponentName()).thenReturn("otherInbound");
        final MqttSubscribedEvent event = new MqttSubscribedEvent(source, "subscribed");

        startupListener.onMqttSubscribed(event);

        verifyNoInteractions(gatewayMock);
        Assertions.assertThat(logAppender.list).isEmpty();
    }
}
