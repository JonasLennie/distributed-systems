package uk.jlennie.distributed.sync.ringLeaderElection;

import uk.jlennie.distributed.sync.infra.Controller;
import uk.jlennie.distributed.util.GraphEdge;

import java.util.List;

public abstract class genericRingLEControllerUni extends Controller<LEMessage, Boolean> {
    public genericRingLEControllerUni(int numNodes) {
        super(getPIDs(numNodes), getConnections(numNodes));
    }

    protected static List<GraphEdge> getConnections(int numNodes) {
        return new CreateRingUni(numNodes).getConnection();
    }

    protected static List<Integer> getPIDs(int numNodes) {
        return new CreateRingUni(numNodes).getPIDs();
    }
}
