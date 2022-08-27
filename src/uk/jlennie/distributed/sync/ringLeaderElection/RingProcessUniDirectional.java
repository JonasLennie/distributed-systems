package uk.jlennie.distributed.sync.ringLeaderElection;

import uk.jlennie.distributed.sync.infra.ConnectionRead;
import uk.jlennie.distributed.sync.infra.ConnectionSend;

abstract public class RingProcessUniDirectional extends RingProcessGeneric {
    private ConnectionRead<RingMessage> messageIn;
    private ConnectionSend<RingMessage> messageOut;

    RingMessage messageOutCache;

    public RingProcessUniDirectional(int pid) {
        super(pid);

        messageOutCache = null;
    }

    @Override
    public final void setup() {
        super.setup();

        if (!correctProfileForUnidirectionalRing())
            throw new RuntimeException("Bad connection profile for unidirectional ring");

        messageIn = getIncomingConnections().get(0);
        messageOut = getOutgoingConnections().get(0);
    }

    private boolean correctProfileForUnidirectionalRing() {
        return getIncomingConnections().size() == 1 && getOutgoingConnections().size() == 1 && getDuplexLinks().isEmpty();
    }

    @Override
    public final void readMessages() {
        RingMessage message = messageIn.read();

        processIncomingMessage(message);

        checkForLeader(message);
    }

    @Override
    protected final void sendFromCache() {
        messageOut.send(messageOutCache);
    }

    abstract void processIncomingMessage(RingMessage message);

}
