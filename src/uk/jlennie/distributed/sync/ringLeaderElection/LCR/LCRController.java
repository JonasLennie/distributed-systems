package uk.jlennie.distributed.sync.ringLeaderElection.LCR;

import uk.jlennie.distributed.sync.infra.Process;
import uk.jlennie.distributed.sync.ringLeaderElection.LEMessage;
import uk.jlennie.distributed.sync.ringLeaderElection.genericRingLEControllerUni;

public class LCRController extends genericRingLEControllerUni {
    
    public LCRController(int numConnections) {
        super(numConnections);
    }

    @Override
    protected Process<LEMessage, Boolean> constructDefaultProcess(int pid) {
        return new LCRProcess(pid);
    }
}
