package uk.jlennie.distributed.sync.ringLeaderElection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RingMessageTest {
    RingMessage messagePID;
    RingMessage messageBool;
    int PID = 5;

    @BeforeEach
    void setup() {
        messagePID = new RingMessage(PID);
        messageBool = new RingMessage(true);
    }

    @Test
    void constructMessageWithIntPopulatesPID() {
        assertEquals(5, messagePID.getPid());

        assertFalse(messagePID.isLeaderDeclared());
    }

    @Test
    void constructMessageWithTrueGivesLeaderDeclared() {
        assertTrue(messageBool.isLeaderDeclared());
    }

    @Test
    void constructMessageFalseThrows() {
        assertThrows(RuntimeException.class, () -> new RingMessage(false));
    }

    @Test
    void inspectPIDWhenLeaderDeclaerdThrows() {
        assertTrue(messageBool.isLeaderDeclared());

        assertThrows(RuntimeException.class, () -> messageBool.getPid());
    }
}