package uk.jlennie.distributed.sync;

class ConnectionSend<M> {
    private final Connection<M> c;

    public ConnectionSend(Connection<M> c) {
        this.c = c;
    }

    public void send(M m) {
        c.sendMessage(m);
    }
}
