package uk.jlennie.distributed.sync;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionTest {
    Connection<Integer> sut;
    int message = 5;

    @BeforeEach
    void setup() {
         sut = new Connection<>();
    }

    @Test
    void testSendMessageThenReadMatchesSent() {
        sut.sendMessage(message);

        assertEquals(message, sut.readMessage());
    }

    @Test
    void testReadBeforeAnythingWrittenGivesNull() {
        assertNull(sut.readMessage());
    }

    @Test
    void justSendingDoesNotCrash() {
        assertDoesNotThrow(() -> sut.sendMessage(message));
    }
}