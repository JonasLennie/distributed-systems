package uk.jlennie.distributed.sync.infra;

import java.util.*;

public abstract class ProcessDuplex<M, R> extends Process<M, R> {
    List<DuplexConnection<M>> duplexLinks;

    public ProcessDuplex(int pid) {
        super(pid);

        duplexLinks = new ArrayList<>();
    }

    class GetDuplexLinks {
        Iterator<ConnectionRead<M>> incomingIter;
        Iterator<ConnectionSend<M>> outgoingIter;
        ConnectionRead<M> lastIncoming;
        ConnectionSend<M> lastOutgoing;

        public GetDuplexLinks() {
            incomingConnections.sort(Comparator.comparing(ConnectionRead::getSenderID));
            outgoingConnections.sort(Comparator.comparing(ConnectionSend::getReaderID));

            incomingIter = incomingConnections.iterator();
            outgoingIter = outgoingConnections.iterator();
        }

        public void get() {
            if (cantConsume())
                return;

            consumeIncoming();
            consumeOutgoing();

            consumeIterator();

            if (lastPairWorks()) {
                addLastPair();
            }
        }

        private boolean cantConsume() {
            return incomingConnections.size() == 0 || outgoingConnections.size() == 0;
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

        private void consumeBoth() {
            incomingIter.remove();
            outgoingIter.remove();

            consumeIncoming();
            consumeOutgoing();
        }
    }

    @Override
    public void setup() {
        new GetDuplexLinks().get();
    }
}
