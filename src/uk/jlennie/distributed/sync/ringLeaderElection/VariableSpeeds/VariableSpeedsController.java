package uk.jlennie.distributed.sync.ringLeaderElection.VariableSpeeds;

import uk.jlennie.distributed.sync.infra.Process;
import uk.jlennie.distributed.sync.ringLeaderElection.LEMessage;
import uk.jlennie.distributed.sync.ringLeaderElection.genericRingLEController;

public class VariableSpeedsController extends genericRingLEController {

    public VariableSpeedsController(int numConnections) {
        super(numConnections);
    }

    @Override
    protected Process<LEMessage, Boolean> constructDefaultProcess(int pid) {
        return null;
    }
}
