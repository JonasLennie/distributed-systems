package uk.jlennie.distributed.sync.infra;

final public class ConnectionRead<M> implements Readable<M> {
    private final Connection<M> c;

    public int getSenderID() {
        return c.getSenderID();
    }

    public ConnectionRead(Connection<M> c) {
        this.c = c;
    }

    public M read() {
        return c.read();
    }
}
