package uk.jlennie.distributed.sync.infra;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import uk.jlennie.distributed.util.GraphEdge;

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

        Assertions.assertEquals(result.size(), 2);
        assert(result.containsKey(1) && result.containsKey(2));
        Assertions.assertEquals(result.get(1), false);
        Assertions.assertEquals(result.get(2), true);
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

        Assertions.assertEquals(result.size(), 2);
        assert(result.containsKey(1) && result.containsKey(2));
        Assertions.assertEquals(result.get(1), true);
        Assertions.assertEquals(result.get(2), true);
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