package uk.jlennie.distributed.sync.infra;

import java.util.*;

public abstract class ProcessDuplex<M, R> extends Process<M, R> {
    private final List<DuplexConnection<M>> duplexLinks;

    protected final List<DuplexConnection<M>> getDuplexLinks() {
        return new ArrayList<>(duplexLinks);
    }

    public ProcessDuplex(int pid) {
        super(pid);

        duplexLinks = new ArrayList<>();
    }

    private class GetDuplexLinks {
        private final Iterator<ConnectionRead<M>> incomingIter;
        private final Iterator<ConnectionSend<M>> outgoingIter;
        private ConnectionRead<M> lastIncoming;
        private ConnectionSend<M> lastOutgoing;

        public GetDuplexLinks() {
            final List<ConnectionRead<M>> incomingConnections = _internal_getIncomingConnections();
            final List<ConnectionSend<M>> outgoingConnections = _internal_getOutgoingConnections();

            incomingConnections.sort(Comparator.comparing(ConnectionRead::getSenderID));
            outgoingConnections.sort(Comparator.comparing(ConnectionSend::getReaderID));

            incomingIter = incomingConnections.iterator();
            outgoingIter = outgoingConnections.iterator();
        }

        public void createLinks() {
            if (cantConsume())
                return;

            consumeIncoming();
            consumeOutgoing();

            consumeIterator();

            if (lastPairWorks()) {
                addLastPair();
                removeBoth();
            }
        }

        private boolean cantConsume() {
            return getIncomingConnections().size() == 0 || getOutgoingConnections().size() == 0;
        }

        private void addLastPair() {
            duplexLinks.add(new DuplexConnection<>(lastOutgoing, lastIncoming));
        }

        private boolean lastPairWorks() {
            return lastIncoming.getSenderID() == lastOutgoing.getReaderID();
        }

        private void consumeIterator() {
            while(incomingIter.hasNext() && outgoingIter.hasNext()) {
                processIterator();
            }
        }

        private void processIterator() {
            if (lastPairWorks()) {
                addLastPair();
                consumeBoth();
                removeBoth();
            } else {
                consumeOne();
            }
        }

        private void consumeOne() {
            if (shouldConsumeIncomingNext())
                consumeIncoming();
            else
                consumeOutgoing();
        }

        private void consumeOutgoing() {
            lastOutgoing = outgoingIter.next();
        }

        private void consumeIncoming() {
            lastIncoming = incomingIter.next();
        }

        private boolean shouldConsumeIncomingNext() {
            return lastIncoming.getSenderID() < lastOutgoing.getReaderID();
        }

        private void removeBoth() {
            incomingIter.remove();
            outgoingIter.remove();
        }

        private void consumeBoth() {
            consumeIncoming();
            consumeOutgoing();
        }
    }

    @Override
    public void setup() {
        new GetDuplexLinks().createLinks();
    }
}
