package uk.jlennie.distributed.sync.ringLeaderElection;

import org.junit.jupiter.api.Test;
import uk.jlennie.distributed.util.GraphEdge;

import java.util.*;
import java.util.concurrent.LinkedTransferQueue;

import static org.junit.jupiter.api.Assertions.*;

class CreateRingTest {
    CreateRing sut;

    @Test
    void failsForNegative() {
        assertThrows(RuntimeException.class,
                () -> new CreateRing(-5)
        );
    }

    @Test
    void failsForZero() {
        assertThrows(RuntimeException.class
                , () -> new CreateRing(0)
        );
    }

    @Test
    void oneHasNoConnections() {
        sut = new CreateRing(1);

        assertEquals(0, sut.getConnection().size());
    }

    @Test
    void oneReturnsSinglePIDZero() {
        sut = new CreateRing(1);

        assert (sut.getPIDs().equals(List.of(0)));
    }

    @Test
    void twoPIDsAsExpected() {
        sut = new CreateRing(2);

        assert sut.getPIDs().equals(Arrays.asList(0, 1));
    }

    @Test
    void twoConnectionsAsExpected() {
        sut = new CreateRing(2);

        assertEquals(2, sut.getConnection().size());
    }

    @Test
    void fourNetworkHasExactlyExpectedLinks() {
        sut = new CreateRing(4);
        List<GraphEdge> connections = sut.getConnection();

        List<String> expectedListOfPairs = new ArrayList<>(Arrays.asList("0,1"
                , "1,2", "2,3", "3,0", "1,0", "2,1", "3,2", "0,3"));

        List<String> actualPiarList = new ArrayList<>();
        for (var connection: connections) {
            actualPiarList.add(connection.getReader() + "," + connection.getSender());
        }

        Collections.sort(expectedListOfPairs);
        Collections.sort(actualPiarList);

        assert expectedListOfPairs.equals(actualPiarList);
    }

}