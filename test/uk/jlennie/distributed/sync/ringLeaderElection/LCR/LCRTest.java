package uk.jlennie.distributed.sync.ringLeaderElection.LCR;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LCRTest {
    // Assuming non-equal PIDs
    // Assuming no failures

    LCRController sut;

    @Test
    void singletonRingElectsSelf() {
        sut = new LCRController(1);

        var result = sut.run();

        assertEquals(1, result.size());
        assertTrue(result.containsKey(0));
        assertTrue(result.get(0));
    }

    @Test
    void pairLargerPIDLeader() {
        sut = new LCRController(2);

        var result = sut.run();

        assertEquals(2, result.size());
        assertTrue(result.containsKey(1));
        assertTrue(result.get(1));
    }

    @Test
    void pairSmallerPIDNotLeader() {
        sut = new LCRController(2);

        var result = sut.run();

        assertEquals(2, result.size());
        assertTrue(result.containsKey(0));
        assertFalse(result.get(0));
    }

    @Test
    void manyPIDLargestLeader() {
        sut = new LCRController(10);

        var result = sut.run();

        assertEquals(10, result.size());
        assertTrue(result.containsKey(9));
        assertTrue(result.get(9));
    }

    @Test
    void manyPIDAllSmallerNotLeader() {
        sut = new LCRController(10);

        var result = sut.run();

        assertEquals(10, result.size());

        for (int i = 0; i < 9; i ++) {
            assertTrue(result.containsKey(i));
            assertFalse(result.get(i));
        }
    }
}