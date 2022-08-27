package uk.jlennie.distributed.sync.ringLeaderElection;

import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import uk.jlennie.distributed.sync.infra.*;
import uk.jlennie.distributed.util.GraphEdge;


class RingProcessGenericTest {
    // If receives RM with true, terminates
    // At termination, all outgoing connections populated with leadership declaration
    // Can use declareLeader to terminate (send message & terminate)

    int pid = 5;
    DuplexConnection<RingMessage> connection;
    Parser<RingMessage, Boolean> sut;


    @BeforeEach
    void setup() {
        List<Integer> processes = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        List<GraphEdge> links = new ArrayList<>(Arrays.asList(
                new GraphEdge(1, 2),
                new GraphEdge(2, 3),
                new GraphEdge(3, 4),
                new GraphEdge(4, 5),
                new GraphEdge(5, 1)
        ));

        sut = new Parser<>(processes, links, new GenericStub(0));
    }

    @Test
    void whenRunThreeElectsSelfAsLeader() {
        Map<Integer, Boolean> result = sut.run();

        assertTrue(result.containsKey(3));

        assertTrue(result.get(3));

        for (int i = 4; i != 3; i = ((i + 1) % 5) + 1)
            assertFalse(result.get(i));
    }
}