package uk.jlennie.distributed.sync.ringLeaderElection;

public class LEMessage {
    boolean leaderIsDeclared;
    int pid;

    public LEMessage(boolean isLeaderDeclared, int pid) {
        leaderIsDeclared = isLeaderDeclared;
        this.pid = pid;
    }
}
