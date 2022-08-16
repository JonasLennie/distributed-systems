package uk.jlennie.distributed.sync.infra;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProcessTest {

    Process<Integer, Boolean> sut;
    int pid = 1;
    Connection<Integer> testConnection;
    ConnectionRead<Integer> connectionIn;
    ConnectionSend<Integer> connectionOut;

    @BeforeEach
    void setup() {
        sut = new BasicProcess(pid);
        testConnection = new Connection<>();
        connectionIn = new ConnectionRead<>(testConnection);
        connectionOut = new ConnectionSend<>(testConnection);
    }

    @Test
    void canGetPID() {
        assertEquals(1, sut.getPid());
    }

    @Test
    void addNewIncomingWorks() {
        assertDoesNotThrow(() -> sut.addNewIncoming(connectionIn));
    }

    @Test
    void addNewOutgoingNotCrash() {
        assertDoesNotThrow(() -> sut.addNewOutgoing(connectionOut));
    }

    @Test
    void newOutgoingSendsOne() {
        sut.addNewOutgoing(connectionOut);

        executeCycle();

        assertEquals(1, connectionIn.read());
    }

    private void executeCycle() {
        sut.sendMessages();
        sut.readMessages();
    }

    @Test
    void notTerminatedAfterOneRound() {
        executeCycle();
        assertFalse(sut.isTerminated());
    }

    @Test
    void terminatesFalseAfterTwoRounds() {
        // Round 1
        executeCycle();

        // Round 2
        executeCycle();

        assert(sut.isTerminated());
        assertFalse(sut.getResult());
    }

    @Test
    void newIncomingReadZeroNotTerminate() {
        sut.addNewIncoming(connectionIn);
        connectionOut.send(0);

        executeCycle();

        assertFalse(sut.isTerminated());
    }

    @Test
    void terminatesFalseAfterTwoRoundsWithZeroRead() {
        sut.addNewIncoming(connectionIn);
        connectionOut.send(0);

        executeCycle();

        assertFalse(sut.isTerminated());

        executeCycle();

        assert(sut.isTerminated());
        assertFalse(sut.getResult());
    }

    @Test
    void newIncomingReadOneTerminatesTrue() {
        sut.addNewIncoming(connectionIn);
        connectionOut.send(1);

        executeCycle();

        assert(sut.isTerminated());
        assertEquals(sut.getResult(), true);
    }

    @Test
    void isTerminatedFalseAtRest() {
        assertFalse(sut.isTerminated());
    }

    @Test
    void readResultBeforeTerminatedThrows() {
        assertThrows(RuntimeException.class, sut::getResult);
    }

    @Test
    void readResultBeforeTerminatedAfterOneIterationThrows() {
        executeCycle();

        assertThrows(RuntimeException.class, sut::getResult);
    }




}