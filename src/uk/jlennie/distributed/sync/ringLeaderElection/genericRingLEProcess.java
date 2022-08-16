package uk.jlennie.distributed.sync.ringLeaderElection;

import uk.jlennie.distributed.sync.infra.Process;

public abstract class genericRingLEProcess extends Process<LEMessage, Boolean> {
    public genericRingLEProcess(int pid) {
        super(pid);
    }
}
