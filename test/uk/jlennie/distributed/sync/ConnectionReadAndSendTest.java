package uk.jlennie.distributed.sync;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ConnectionReadAndSendTest {
    ConnectionSend<Integer> sender;
    ConnectionRead<Integer> reader;
    Connection<Integer> defaultConnection;
    int message = 5;

    @BeforeEach
    void setup() {
        defaultConnection = new Connection<>();
        sender = new ConnectionSend<>(defaultConnection);
        reader = new ConnectionRead<>(defaultConnection);
    }

    @Test
    void writeAndReadConnection() {
        sender.send(message);

        assertEquals(message, reader.read());
    }

    @Test
    void testReadBeforeAnythingWrittenGivesNull() {
        assertNull(reader.read());
    }

    @Test
    void justSendingDoesNotCrash() {
        assertDoesNotThrow(() -> sender.send(message));
    }

}
