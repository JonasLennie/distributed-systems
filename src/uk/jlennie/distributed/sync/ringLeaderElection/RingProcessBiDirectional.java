package uk.jlennie.distributed.sync.ringLeaderElection;

import uk.jlennie.distributed.sync.infra.DuplexConnection;
import uk.jlennie.distributed.sync.infra.Process;

public class RingProcessBiDirectional<M> extends Process<M, Boolean> {
    DuplexConnection<M> leftConnection;
    DuplexConnection<M> rightConnection;

    M rightCache;
    M leftCache;

    public RingProcessBiDirectional(int pid) {
        super(pid);
    }

    @Override
    public void sendMessages() {

    }

    @Override
    public void readMessages() {

    }
}
