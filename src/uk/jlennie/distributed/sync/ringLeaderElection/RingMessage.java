package uk.jlennie.distributed.sync.ringLeaderElection;

public class RingMessage {
    private final boolean leaderDeclared;
    private final int pid;

    public RingMessage(boolean isLeaderDeclared) {
        if (!isLeaderDeclared)
            throw new RuntimeException("Bad construct of ring message");

        leaderDeclared = true;
        pid = 0;
    }

    public RingMessage(int pid) {
        leaderDeclared = false;
        this.pid = pid;
    }

    public boolean isLeaderDeclared() {
        return leaderDeclared;
    }

    public int getPid() {
        if (leaderDeclared)
            throw new RuntimeException("Looked at PID when failed");

        return pid;
    }
}
