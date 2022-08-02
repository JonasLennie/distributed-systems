package uk.jlennie.distributed.sync;

class Connection<M> {
    private M message;

    public Connection() {
        message = null;
    }

    public M readMessage() {
        return message;
    }

    public void sendMessage(M message) {
        this.message = message;
    }
}
