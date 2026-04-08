package online.comfyplace.qbusmqtt;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

@ExtendWith(MockitoExtension.class)
class DeadLetterHandlerTest {
    private DeadLetterMessageHandler handler;

    private ListAppender<ILoggingEvent> logAppender;

    @BeforeEach
    void setUp() {
        handler = new DeadLetterMessageHandler();
        final Logger logger = (Logger) LoggerFactory.getLogger(DeadLetterMessageHandler.class);
        logAppender = new ListAppender<>();
        logAppender.start();
        logger.addAppender(logAppender);
    }

    @AfterEach
    void tearDown() {
        final Logger logger = (Logger) LoggerFactory.getLogger(DeadLetterMessageHandler.class);
        logger.detachAppender(logAppender);
    }

    @Test
    void testHandleMessage_logsPayload() {
        final String payload = "Something went wrong";
        final Message<String> message = new GenericMessage<>(payload);

        handler.handleMessage(message);

        Assertions.assertThat(logAppender.list)
                .hasSize(1)
                .first()
                .extracting(ILoggingEvent::getMessage)
                .isEqualTo(payload);
    }
}
