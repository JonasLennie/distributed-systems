package uk.jlennie.distributed.sync.ringLeaderElection;

import uk.jlennie.distributed.sync.infra.Controller;
import uk.jlennie.distributed.util.GraphEdge;

import java.util.List;

public abstract class genericRingLEController extends Controller<LEMessage, Boolean> {

    public genericRingLEController(List<Integer> processIDs, List<GraphEdge> connections) {
        super(processIDs, connections);
    }
}
