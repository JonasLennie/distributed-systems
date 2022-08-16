package uk.jlennie.distributed.sync;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    BasicController sut;

    @Test
    void testSingleNodeNoConnections() {
        int singlePID = 1;
        sut = new BasicController(List.of(singlePID), new ArrayList<>());

        Map<Integer, Boolean> result = sut.run();

        assertEquals(result.size(), 1);
        assert(result.containsKey(singlePID));
        assertEquals(result.get(singlePID), false);
    }

    @Test
    void testTwoNodesOneConnectionTerminates() {
        sut = new BasicController(
                Arrays.asList(1, 2),
                List.of(new GraphEdge(1, 2))
        );

        var result = sut.run();

        assertEquals(result.size(), 2);
        assert(result.containsKey(1) && result.containsKey(2));
        assertEquals(result.get(1), false);
        assertEquals(result.get(2), true);
    }

    @Test
    void testTwoNodesBiDirectionalTerminates() {
        sut = new BasicController(
                Arrays.asList(1, 2),
                Arrays.asList(
                        new GraphEdge(1, 2),
                        new GraphEdge(2, 1)
                ));

        var result = sut.run();

        assertEquals(result.size(), 2);
        assert(result.containsKey(1) && result.containsKey(2));
        assertEquals(result.get(1), true);
        assertEquals(result.get(2), true);
    }

    @Test
    void testLargeNetworkCommunicatesTerminates() {
        sut = getRingNetwork(10);

        var result = sut.run();

        assertEquals(result.size(), 10);
        for (int i = 0; i < 10; i ++) {
            assert(result.containsKey(i));
            assertEquals(result.get(i), true);
        }
    }

    private BasicController getRingNetwork(int numNodes) {
        List<Integer> nodes = new ArrayList<>();
        for (int i = 0; i < numNodes; i ++) {
            nodes.add(i);
        }

        List<GraphEdge> connections = new ArrayList<>();
        for (int i = 0; i < numNodes; i ++) {
            addBidirectional(connections, i, (i + 1) % numNodes);
        }

        return new BasicController(nodes, connections);
    }

    private static void addBidirectional(List<GraphEdge> connections, int first, int second) {
        connections.add(new GraphEdge(first, second));
        connections.add(new GraphEdge(second, first));
    }

    @Test
    void testPIDsNotUnique() {
        List<Integer> PIDs = Arrays.asList(1, 1, 2, 3);

        assertThrows(RuntimeException.class
                , () -> sut = new BasicController(
                        PIDs, new ArrayList<>()
                ));
    }

    @Test
    void testBadPIDsForConnection() {
        List<Integer> PIDs = Arrays.asList(1, 2, 3);
        GraphEdge connection = new GraphEdge(3, 4);

        assertThrows(RuntimeException.class,
                () -> sut = new BasicController(
                        PIDs, List.of(connection)
                ));
    }

    @Test
    void testSamePIDforConnection(){
        List<Integer> PIDs = Arrays.asList(1, 2, 3);
        GraphEdge connection = new GraphEdge(3, 3);

        assertThrows(RuntimeException.class,
                () -> sut = new BasicController(
                        PIDs, List.of(connection)
                ));
    }
}