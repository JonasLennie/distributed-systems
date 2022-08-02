package uk.jlennie.distributed.sync;

class ConnectionRead<M> {
    private final Connection<M> c;

    public ConnectionRead(Connection<M> c) {
        this.c = c;
    }

    public M read() {
        return c.readMessage();
    }
}
