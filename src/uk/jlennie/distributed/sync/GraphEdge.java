package uk.jlennie.distributed.sync;

class GraphEdge {
    private final int sender;
    private final int reader;

    public GraphEdge(int sender, int reader) {
        this.sender = sender;
        this.reader = reader;
    }

    public int getSender() {
        return sender;
    }

    public int getReader() {
        return reader;
    }
}
