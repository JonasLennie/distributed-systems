package uk.jlennie.distributed.sync.ringLeaderElection;

public class LEMessage {
    public boolean leaderIsDeclared;
    public int pid;

    public LEMessage(Boolean isLeaderDeclared, Integer pid) {
        leaderIsDeclared = isLeaderDeclared;
        this.pid = pid;
    }
}
