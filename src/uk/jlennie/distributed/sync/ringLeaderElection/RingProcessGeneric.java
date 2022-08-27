package uk.jlennie.distributed.sync.ringLeaderElection;

import uk.jlennie.distributed.sync.infra.ProcessDuplex;

public abstract class RingProcessGeneric extends ProcessDuplex<RingMessage, Boolean> {
    private boolean shouldTerminate;
    private boolean amLeader;

    public RingProcessGeneric(int pid) {
        super(pid);

        shouldTerminate = false;
        amLeader = false;
    }

    @Override
    public void setup() {
        super.setup();
    }

    @Override
    public final void sendMessages() {
        if (shouldTerminate) {
            runTermination();
        } else {
            sendFromCache();
        }
    }

    protected abstract void sendFromCache();

    private void sendAll(RingMessage message) {
        sendAllOutgoing(message);

        sendAllDuplex(message);
    }

    private void sendAllDuplex(RingMessage message) {
        for (var connection : getDuplexLinks()) {
            connection.send(message);
        }
    }

    private void sendAllOutgoing(RingMessage message) {
        for (var connection : getOutgoingConnections()) {
            connection.send(message);
        }
    }

    private void runTermination() {
        sendAll(new RingMessage(true));
        terminate(amLeader);
    }

    protected final void checkForLeader(RingMessage message) {
        if (message.isLeaderDeclared()) {
            amLeader = false;
            shouldTerminate = true;
        }
    }

    protected final void readMessage(RingMessage message) {
        if (message == null)
            return;

        readNonNullMessage(message);
    }

    private void readNonNullMessage(RingMessage message) {
        if (message.isLeaderDeclared())
            checkForLeader(message);
        else
            processMessage(message);
    }

    protected abstract void processMessage(RingMessage message);

    protected void declareLeader() {
        amLeader = true;
        shouldTerminate = true;
    }
}
