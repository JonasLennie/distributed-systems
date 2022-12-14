package uk.jlennie.distributed.sync.infra;

import uk.jlennie.distributed.util.GraphEdge;

import java.util.*;

public class Parser<M, R> {
    private final Executor<M, R> executor;
    private final Map<Integer, Process<M, R>> pidToProcess;
    private final Process<M, R> processPrototype;

    public Parser(List<Integer> processIDs, List<GraphEdge> connections, Process<M, R> processType) {
        this.processPrototype = processType;

        pidToProcess = new HashMap<>();

        populateProcesses(processIDs);
        populateEdges(connections);

        executor = getExecutor();
    }

    private Executor<M, R> getExecutor() {
        final Executor<M, R> executor;
        executor = new Executor<>(getProcesses());
        return executor;
    }

    private List<Process<M, R>> getProcesses() {
        return pidToProcess.values().stream().toList();
    }

    private void populateEdges(List<GraphEdge> connections) {
        for (var edge : connections)
            addConnections(edge);
    }

    private void addConnections(GraphEdge edge) {
        throwIfConnectionHasBadPIDs(edge);
        throwIfConnectionHasEqualPIDs(edge);

        addConnectionSafe(edge);
    }

    private void throwIfConnectionHasEqualPIDs(GraphEdge edge) {
        if(edge.getReader() == edge.getSender())
            throw new RuntimeException("PIDs cannot be equal in connection");
    }

    private void throwIfConnectionHasBadPIDs(GraphEdge edge) {
        if (eitherEdgeVertexNotInProcessList(edge))
            throw new RuntimeException("Bad ID in connection");
    }

    private boolean eitherEdgeVertexNotInProcessList(GraphEdge edge) {
        return !pidToProcess.containsKey(edge.getSender()) || !pidToProcess.containsKey(edge.getReader());
    }

    private void addConnectionSafe(GraphEdge edge) {
        Connection<M> newConnection = new Connection<>(
                edge.getSender(), edge.getReader()
        );

        addOutgoingConnection(edge, newConnection);
        addIncomingConnection(edge, newConnection);
    }

    private void addIncomingConnection(GraphEdge edge, Connection<M> newConnection) {
        pidToProcess.get(edge.getReader()).addNewIncoming(new ConnectionRead<>(newConnection));
    }

    private void addOutgoingConnection(GraphEdge edge, Connection<M> newConnection) {
        pidToProcess.get(edge.getSender()).addNewOutgoing(new ConnectionSend<>(newConnection));
    }

    private void populateProcesses(List<Integer> processIDs) {
        throwIfDuplicatePIDs(processIDs);

        addNewProcessForAllPIDs(processIDs);
    }

    private void addNewProcessForAllPIDs(List<Integer> processIDs) {
        for (var pid : processIDs)
            addNewProcessForPID(pid);
    }

    private void addNewProcessForPID(Integer pid) {
        pidToProcess.put(pid, constructDefaultProcess(pid));
    }

    private void throwIfDuplicatePIDs(List<Integer> processIDs) {
        if (duplicateIDs(processIDs))
            throw new RuntimeException("Duplicate IDs in process ID list");
    }

    private boolean duplicateIDs(List<Integer> processIDs) {
        return !processIDs.equals(processIDs.stream().distinct().toList());
    }

    private Process<M, R> constructDefaultProcess(int pid) {
        return processPrototype.newInstance(pid);
    }

    public final Map<Integer, R> run() {
        return executor.run();
    }
}
