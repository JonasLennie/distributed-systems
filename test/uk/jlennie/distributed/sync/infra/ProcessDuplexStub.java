package uk.jlennie.distributed.sync.infra;

import java.util.ArrayList;
import java.util.List;

public class ProcessDuplexStub extends ProcessDuplex<Integer, Integer> {

    public ProcessDuplexStub(int pid) {
        super(pid);
    }

    public List<DuplexConnection<Integer>> getDuplexConnections() {
        return new ArrayList<>(duplexLinks);
    }

    @Override
    public void sendMessages() {
        terminate(0);
    }

    @Override
    public void readMessages() {

    }
}
