package uk.jlennie.distributed.sync.infra;

import java.util.ArrayList;
import java.util.List;

public abstract class Process<M, R> {
    protected final int pid;
    protected List<ConnectionRead<M>> incomingConnections;
    protected List<ConnectionSend<M>> outgoingConnections;
    protected boolean terminated;
    protected R result;

    public Process(int pid) {
        this.pid = pid;

        incomingConnections = new ArrayList<>();
        outgoingConnections = new ArrayList<>();
        terminated = false;
    }

    public void addNewIncoming(ConnectionRead<M> newConnection) {
        incomingConnections.add(newConnection);
    }

    public void addNewOutgoing(ConnectionSend<M> newConnection) {
        outgoingConnections.add(newConnection);
    }

    public abstract void sendMessages();

    public abstract void readMessages();

    public void setup() {

    }

    public boolean isTerminated() {
        return terminated;
    }

    public R getResult() throws RuntimeException {
        if (!terminated)
            throw new RuntimeException("getResult Called Illegally");

        return result;
    }

    public Integer getPid() {
        return pid;
    }

    protected void terminate(R terminatingValue) {
        terminated = true;
        result = terminatingValue;
    }
}
