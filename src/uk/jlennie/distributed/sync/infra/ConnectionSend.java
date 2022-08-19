package uk.jlennie.distributed.sync.infra;

final public class ConnectionSend<M> {
    private final Connection<M> c;

    public int getReaderID() {
        return c.getReaderID();
    }

    public ConnectionSend(Connection<M> c) {
        this.c = c;
    }

    public void send(M m) {
        c.sendMessage(m);
    }
}
