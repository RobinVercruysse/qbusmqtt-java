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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QbusMqttApiTest {
    @Mock
    private MqttGateway gatewayMock;

    @Mock
    private TopicFactory topicFactoryMock;

    @InjectMocks
    private QbusMqttApi qbusMqttApi;

    private ListAppender<ILoggingEvent> logAppender;

    @BeforeEach
    void setUp() {
        final Logger logger = (Logger) LoggerFactory.getLogger(QbusMqttApi.class);
        logAppender = new ListAppender<>();
        logAppender.start();
        logger.addAppender(logAppender);
    }

    @AfterEach
    void tearDown() {
        final Logger logger = (Logger) LoggerFactory.getLogger(QbusMqttApi.class);
        logger.detachAppender(logAppender);
    }

    @Test
    void init_requestsConfig() {
        final String getConfigTopic = "testGetConfig";
        when(topicFactoryMock.getGetConfigTopic()).thenReturn(getConfigTopic);

        qbusMqttApi.init();

        verify(gatewayMock, times(1)).sendToMqtt(getConfigTopic, "");
        verifyNoMoreInteractions(gatewayMock);

        Assertions.assertThat(logAppender.list)
                .hasSize(1)
                .first()
                .extracting(ILoggingEvent::getMessage)
                .isEqualTo("Requesting Qbus configuration...");
    }
}
