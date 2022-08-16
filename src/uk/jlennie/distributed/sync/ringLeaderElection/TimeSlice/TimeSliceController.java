package uk.jlennie.distributed.sync.ringLeaderElection.TimeSlice;

import uk.jlennie.distributed.sync.infra.Process;
import uk.jlennie.distributed.sync.ringLeaderElection.LEMessage;
import uk.jlennie.distributed.sync.ringLeaderElection.genericRingLEController;

public class TimeSliceController extends genericRingLEController {

    public TimeSliceController(int numConnections) {
        super(numConnections);
    }

    @Override
    protected Process<LEMessage, Boolean> constructDefaultProcess(int pid) {
        return null;
    }
}
