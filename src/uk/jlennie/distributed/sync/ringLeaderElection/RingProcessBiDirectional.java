package uk.jlennie.distributed.sync.ringLeaderElection;

import uk.jlennie.distributed.sync.infra.DuplexConnection;

 public abstract class RingProcessBiDirectional extends RingProcessGeneric {
    private DuplexConnection<RingMessage> leftConnection;
    private DuplexConnection<RingMessage> rightConnection;

    RingMessage rightCache;
    RingMessage leftCache;

    public RingProcessBiDirectional(int pid) {
        super(pid);

        rightCache = null;
        leftCache = null;
    }

    @Override
    public void setup() {
        super.setup();

        if (!rightConnectionProfileForRing())
            throw new RuntimeException("Bad set of connections for bidirectional ring.");

        leftConnection = getDuplexLinks().get(0);
        rightConnection = getDuplexLinks().get(1);
    }

     @Override
     protected final void sendFromCache() {
         leftConnection.send(leftCache);
         rightConnection.send(rightCache);
     }

     private boolean rightConnectionProfileForRing() {
        return getOutgoingConnections().isEmpty() && getIncomingConnections().isEmpty() & getDuplexLinks().size() == 2;
    }

    @Override
    public final void readMessages() {
        RingMessage leftMessage = leftConnection.read();
        RingMessage rightMessage = rightConnection.read();

        readMessage(rightMessage);
        readMessage(leftMessage);
    }

}
