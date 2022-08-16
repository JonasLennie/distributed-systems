package uk.jlennie.distributed.sync.infra;

public class ConnectionRead<M> {
    private final Connection<M> c;

    public int getSenderID() {
        return c.getSenderID();
    }

    public ConnectionRead(Connection<M> c) {
        this.c = c;
    }

    public M read() {
        return c.readMessage();
    }
}
