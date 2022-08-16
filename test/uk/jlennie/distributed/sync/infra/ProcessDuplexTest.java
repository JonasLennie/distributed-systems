package uk.jlennie.distributed.sync.infra;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProcessDuplexTest {
    ProcessDuplexStub sut;

    private void addNewPair(int pid) {
        addNewIncoming(pid);
        addNewOutgoing(pid);
    }

    private void addNewOutgoing(int pid) {
        sut.addNewOutgoing(new ConnectionSend<>(new Connection<>(0, pid)));
    }

    private void addNewIncoming(int pid) {
        sut.addNewIncoming(new ConnectionRead<>(new Connection<>(pid, 0)));
    }

    @BeforeEach
    void setup() {
        sut = new ProcessDuplexStub(0);
    }

    @Test
    void testWithNoConnections() {
        sut.setup();

        assert sut.getDuplexConnections().isEmpty();
    }

    @Test
    void testWithNoGoodConnections() {
        addNewIncoming(27);
        addNewOutgoing(17);
        addNewIncoming(45);
        addNewOutgoing(87);

        sut.setup();

        assert sut.getDuplexConnections().isEmpty();
    }

    @Test
    void testWithTwoHappyConnections() {
        addNewPair(13);

        sut.setup();

        assertEquals(1, sut.getDuplexConnections().size());
        assertEquals(13, sut.getDuplexConnections().get(0).getID());
    }

    @Test
    void testWithMixOfPairs() {
        addNewPair(17);
        addNewPair(23);
        addNewPair(13);

        addNewIncoming(46);
        addNewOutgoing(98);
        addNewIncoming(104);


        sut.setup();


        assertEquals(3, sut.getDuplexConnections().size());
    }

}