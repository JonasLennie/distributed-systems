package uk.jlennie.distributed.sync;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionTest {
    Connection<Integer> sut;
    int message = 5;
    int readerID = 17;
    int senderID = 12;
    int defaultID = 0;

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
    void readBeforeGivesNullWithAltConstructor() {
        // This was a bug :)
        sut = new Connection<>(senderID, readerID);

        var testMessage = sut.readMessage();

        assertNull(testMessage);
    }

    @Test
    void justSendingDoesNotCrash() {
        assertDoesNotThrow(() -> sut.sendMessage(message));
    }

    @Test
    void testConstructsWithReaderAndSenderIDs() {
        assertDoesNotThrow(() -> sut = new Connection<>(senderID, readerID));
    }

    @Test
    void canGetReaderIDConnection() {
        sut = new Connection<>(senderID, readerID);

        assertEquals(sut.getReaderID(), readerID);
    }

    @Test
    void canGetSenderIDConnection() {
        sut = new Connection<>(senderID, readerID);

        assertEquals(sut.getSenderID(), senderID);
    }

    @Test
    void emptyConstructorGivesDefaultSenderID() {
        int testID = sut.getSenderID();

        assertEquals(testID, defaultID);
    }

    @Test
    void emptyConstructorGivesDefaultReaderID() {
        int testID = sut.getReaderID();

        assertEquals(testID, defaultID);
    }

    @Test
    void getReaderIDFromConnectionSend() {
        sut = new Connection<>(senderID, readerID);
        ConnectionSend<Integer> test = new ConnectionSend<>(sut);

        int testID = test.getReaderID();

        assertEquals(testID, readerID);
    }

    @Test
    void getSenderIDFromConnectionRead() {
        sut = new Connection<>(senderID, readerID);
        ConnectionRead<Integer> test = new ConnectionRead<>(sut);

        int testID = test.getSenderID();

        assertEquals(testID, senderID);
    }

    @Test
    void ifReadTwiceFromConnectionThenClears() {
        sut.sendMessage(message);

        int firstRead = sut.readMessage();

        // Use non-primitive type to be able to test for null
        // Otherwise just test for 0
        Integer secondRead = sut.readMessage();

        assertEquals(firstRead, message);
        assertNull(secondRead);
    }
}