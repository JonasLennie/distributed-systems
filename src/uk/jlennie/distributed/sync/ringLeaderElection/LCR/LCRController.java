package uk.jlennie.distributed.sync.ringLeaderElection.LCR;

import uk.jlennie.distributed.util.GraphEdge;
import uk.jlennie.distributed.sync.infra.Process;
import uk.jlennie.distributed.sync.ringLeaderElection.LEMessage;
import uk.jlennie.distributed.sync.ringLeaderElection.genericRingLEController;

import java.util.List;

public class LCRController extends genericRingLEController {
    
    public LCRController(List<Integer> processIDs, List<GraphEdge> connections) {
        super(processIDs, connections);
    }

    @Override
    protected Process<LEMessage, Boolean> constructDefaultProcess(int pid) {
        return null;
    }
}
