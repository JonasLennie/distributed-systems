package uk.jlennie.distributed.sync;

import java.util.*;

public abstract class Controller<M, R> {
    private final Executor<M, R> executor;
    private final Map<Integer, Process<M, R>> pidToProcess;

    public Controller(List<Integer> processIDs, List<GraphEdge> connections) {
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

        addConnectionSafe(edge);
    }

    private void throwIfConnectionHasBadPIDs(GraphEdge edge) {
        if (eitherEdgeVertexNotInProcessList(edge))
            throw new RuntimeException("Bad ID in connection");
    }

    private boolean eitherEdgeVertexNotInProcessList(GraphEdge edge) {
        return !pidToProcess.containsKey(edge.getFrom()) || !pidToProcess.containsKey(edge.getTo());
    }

    private void addConnectionSafe(GraphEdge edge) {
        Connection<M> newConnection = new Connection<>();

        addOutgoingConnection(edge, newConnection);
        addIncomingConnection(edge, newConnection);
    }

    private void addIncomingConnection(GraphEdge edge, Connection<M> newConnection) {
        pidToProcess.get(edge.getTo()).addNewIncoming(new ConnectionRead<>(newConnection));
    }

    private void addOutgoingConnection(GraphEdge edge, Connection<M> newConnection) {
        pidToProcess.get(edge.getFrom()).addNewOutgoing(new ConnectionSend<>(newConnection));
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
        pidToProcess.put(pid, constructDefaultProcess());
    }

    private void throwIfDuplicatePIDs(List<Integer> processIDs) {
        if (duplicateIDs(processIDs))
            throw new RuntimeException("Duplicate IDs in process ID list");
    }

    private boolean duplicateIDs(List<Integer> processIDs) {
        return !processIDs.equals(processIDs.stream().distinct().toList());
    }

    abstract protected Process<M, R> constructDefaultProcess();

    public Map<Integer, R> run() {
        return executor.run();
    }
}
