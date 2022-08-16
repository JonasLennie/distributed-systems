package uk.jlennie.distributed.sync.ringLeaderElection;

import org.junit.jupiter.api.Test;
import uk.jlennie.distributed.util.GraphEdge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CreateRingUniTest {
    CreateRingUni sut;

    @Test
    void failsForNegative() {
        assertThrows(RuntimeException.class,
                () -> new CreateRingUni(-5)
        );
    }

    @Test
    void failsForZero() {
        assertThrows(RuntimeException.class
                , () -> new CreateRingUni(0)
        );
    }

    @Test
    void oneHasNoConnections() {
        sut = new CreateRingUni(1);

        assertEquals(0, sut.getConnection().size());
    }

    @Test
    void oneReturnsSinglePIDZero() {
        sut = new CreateRingUni(1);

        assert (sut.getPIDs().equals(List.of(0)));
    }

    @Test
    void twoPIDsAsExpected() {
        sut = new CreateRingUni(2);

        assert sut.getPIDs().equals(Arrays.asList(0, 1));
    }

    @Test
    void twoConnectionsAsExpected() {
        sut = new CreateRingUni(2);

        assertEquals(2, sut.getConnection().size());
    }

    @Test
    void fourNetworkHasExactlyExpectedLinks() {
        sut = new CreateRingUni(4);
        List<GraphEdge> connections = sut.getConnection();

        List<String> expectedListOfPairs = new ArrayList<>(Arrays.asList(
                "1,0", "2,1", "3,2", "0,3"));

        List<String> actualPiarList = new ArrayList<>();
        for (var connection: connections) {
            actualPiarList.add(connection.getReader() + "," + connection.getSender());
        }

        Collections.sort(expectedListOfPairs);
        Collections.sort(actualPiarList);

        assert expectedListOfPairs.equals(actualPiarList);
    }

}