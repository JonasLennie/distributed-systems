package uk.jlennie.distributed.sync.infra;

public interface Sendable<M> {
    void send(M m);
}
