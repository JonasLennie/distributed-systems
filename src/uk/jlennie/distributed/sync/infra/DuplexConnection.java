package uk.jlennie.distributed.sync.infra;

public class DuplexConnection<M> {
    ConnectionSend<M> outgoingConnection;
    ConnectionRead<M> incomingConnection;
    int id;

    public DuplexConnection(ConnectionSend<M> outgoingConnection, ConnectionRead<M> incomingConnection) {
        if (outgoingConnection.getReaderID() != incomingConnection.getSenderID())
            throw new RuntimeException("Not a valid bidirectional connection constructor");

        this.outgoingConnection = outgoingConnection;
        this.incomingConnection = incomingConnection;
        this.id = outgoingConnection.getReaderID();
    }

    public void send(M messageOut) {
        outgoingConnection.send(messageOut);
    }

    public M read() {
        return incomingConnection.read();
    }

    public int getID() {
        return id;
    }
}
