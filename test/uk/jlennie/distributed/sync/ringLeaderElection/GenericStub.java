package uk.jlennie.distributed.sync.ringLeaderElection;

import uk.jlennie.distributed.sync.infra.Process;
import uk.jlennie.distributed.sync.infra.Sendable;

final public class GenericStub extends RingProcessGeneric {

    public GenericStub(int pid) {
        super(pid);
    }

    @Override
    public Process<RingMessage, Boolean> newInstance(int pid) {
        return new GenericStub(pid);
    }

    @Override
    public void readMessages() {
        readIncoming();

        readDuplexed();
    }

    private void readIncoming() {
        for (var connection : getIncomingConnections())
            readMessage(connection.read());
    }

    private void readDuplexed() {
        for (var connection : getDuplexLinks())
            readMessage(connection.read());
    }

    @Override
    protected void sendFromCache() {
        for (var connection : getOutgoingConnections())
            sendPID(connection);

        for (var connection : getDuplexLinks())
            sendPID(connection);
    }

    @Override
    protected void processMessage(RingMessage message) {
        if ((message.getPid() + getPid()) % 5 == 0)
            declareLeader();
    }

    private void sendPID(Sendable<RingMessage> connection) {
        connection.send(new RingMessage(getPid()));
    }
}
