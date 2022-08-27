package uk.jlennie.distributed.sync.infra;
final class Connection<M> implements Readable<M>, Sendable<M> {
    private M message;
    private final int senderID;
    private final int readerID;

    public Connection(int senderID, int readerID) {
        message = null;
        this.senderID = senderID;
        this.readerID = readerID;
    }

    public Connection() {
        this(0, 0);
    }

    public int getSenderID() {
        return senderID;
    }

    public int getReaderID() {
        return readerID;
    }

    public M read() {
        M messageToReturn = this.message;

        this.message = null;

        return messageToReturn;
    }

    public void send(M message) {
        this.message = message;
    }
}
