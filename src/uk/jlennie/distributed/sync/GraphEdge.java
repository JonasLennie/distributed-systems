package uk.jlennie.distributed.sync;

class GraphEdge {
    private int from;
    private int to;

    public GraphEdge(int from, int to) {
        this.from = from;
        this.to = to;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }
}
