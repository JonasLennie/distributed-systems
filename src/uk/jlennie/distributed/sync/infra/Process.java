package uk.jlennie.distributed.sync.infra;

import java.util.ArrayList;
import java.util.List;

public abstract class Process<M, R> {
    private final int pid;
    private final List<ConnectionRead<M>> incomingConnections;
    private final List<ConnectionSend<M>> outgoingConnections;
    private boolean terminated;
    private R result;

    public Process(int pid) {
        this.pid = pid;

        incomingConnections = new ArrayList<>();
        outgoingConnections = new ArrayList<>();
        terminated = false;
    }

    protected final List<ConnectionRead<M>> getIncomingConnections() {
        return new ArrayList<>(incomingConnections);
    }

    protected final List<ConnectionSend<M>> getOutgoingConnections() {
        return new ArrayList<>(outgoingConnections);
    }

    public void addNewIncoming(ConnectionRead<M> newConnection) {
        incomingConnections.add(newConnection);
    }

    public void addNewOutgoing(ConnectionSend<M> newConnection) {
        outgoingConnections.add(newConnection);
    }

    public abstract void sendMessages();

    public abstract void readMessages();

    protected final List<ConnectionRead<M>> _internal_getIncomingConnections() {
        return incomingConnections;
    }

    protected final List<ConnectionSend<M>> _internal_getOutgoingConnections() {
        return outgoingConnections;
    }

    public void setup() {

    }

    public final boolean isTerminated() {
        return terminated;
    }

    public final R getResult() throws RuntimeException {
        if (!terminated)
            throw new RuntimeException("getResult Called Illegally");

        return result;
    }

    public final Integer getPid() {
        return pid;
    }

    protected final void terminate(R terminatingValue) {
        terminated = true;
        result = terminatingValue;
    }
}
