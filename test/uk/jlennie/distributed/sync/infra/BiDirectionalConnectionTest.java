package uk.jlennie.distributed.sync.infra;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BiDirectionalConnectionTest {
    Connection<Integer> baseConnectionOut;
    Connection<Integer> baseConnectionIn;
    ConnectionRead<Integer> connectionRead;
    ConnectionSend<Integer> connectionSend;
    int idTo = 1;
    int idFrom = 2;
    int messageOut = 17;
    int messageIn = 13;

    DuplexConnection<Integer> sut;

    @BeforeEach
    void setup() {
        baseConnectionOut = new Connection<>(idFrom, idTo);
        baseConnectionIn = new Connection<>(idTo, idFrom);
        connectionRead = new ConnectionRead<>(baseConnectionIn);
        connectionSend = new ConnectionSend<>(baseConnectionOut);
        sut = new DuplexConnection<>(connectionSend, connectionRead);
    }

    @Test
    void createBiDirectionalConnection() {
        assertDoesNotThrow(() -> new DuplexConnection<>(connectionSend, connectionRead));
    }

    @Test
    void sendFromBiDirectionalConnectionReadInUnderlying() {
        sut.send(messageOut);

        assertEquals(messageOut, baseConnectionOut.readMessage());
    }

    @Test
    void readByBiDirectionalWhenSentInUnderlying() {
        baseConnectionIn.sendMessage(messageIn);

        assertEquals(messageIn, sut.read());
    }

    @Test
    void canGetIDOfOtherDevice() {
        assertEquals(sut.getID(), idTo);
    }

    @Test
    void throwsIfIdsDoNotMakeLink() {
        baseConnectionOut = new Connection<>(idFrom, idTo);
        baseConnectionIn = new Connection<>(idFrom, idTo);
        connectionRead = new ConnectionRead<>(baseConnectionIn);
        connectionSend = new ConnectionSend<>(baseConnectionOut);

        assertThrows(RuntimeException.class, () -> new DuplexConnection<>(connectionSend, connectionRead));
    }
}