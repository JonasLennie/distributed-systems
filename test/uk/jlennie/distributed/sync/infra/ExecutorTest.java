package uk.jlennie.distributed.sync.infra;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ExecutorTest {
    // run

    @Test
    void basicTestWithSingleProcess() {
        Process<Integer, Boolean> process = new BasicProcess(1);
        List<Process<Integer, Boolean>> processes = new ArrayList<>();
        processes.add(process);

        Executor<Integer, Boolean> sut = new Executor<>(processes);
        Map<Integer, Boolean> results = sut.run();

        assertEquals(1, results.size());
        assertEquals(false, results.get(1));
    }

    @Test
    void basicProcessReadsOne() {
        // Setup
        Connection<Integer> testConnection = new Connection<>();
        testConnection.sendMessage(1);
        ConnectionRead<Integer> testReader = new ConnectionRead<>(testConnection);

        Process<Integer, Boolean> process = new BasicProcess(1);

        process.addNewIncoming(testReader);

        List<Process<Integer, Boolean>> processes = new ArrayList<>();
        processes.add(process);


        // Execute
        Executor<Integer, Boolean> sut = new Executor<>(processes);
        Map<Integer, Boolean> results = sut.run();



        //Assert
        assertEquals(1, results.size());
        assertEquals(true, results.get(1));
    }

}