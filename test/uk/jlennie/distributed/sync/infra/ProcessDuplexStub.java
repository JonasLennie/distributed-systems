package uk.jlennie.distributed.sync.infra;

import java.util.List;

public class ProcessDuplexStub extends ProcessDuplex<Integer, Integer> {

    public ProcessDuplexStub(int pid) {
        super(pid);
    }

    @Override
    public Process<Integer, Integer> newInstance(int pid) {
        return new ProcessDuplexStub(pid);
    }

    public List<DuplexConnection<Integer>> getDuplexConnections() {
        return getDuplexLinks();
    }

    public int getSizeOfIncomingPlusOutgoing() {
        return getIncomingConnections().size() + getOutgoingConnections().size();
    }

    @Override
    public void sendMessages() {
        terminate(0);
    }

    @Override
    public void readMessages() {

    }
}
