package uk.jlennie.distributed.sync.ringLeaderElection;

import uk.jlennie.distributed.sync.infra.Controller;
import uk.jlennie.distributed.util.GraphEdge;

import java.util.List;

public abstract class genericRingLEController extends Controller<LEMessage, Boolean> {


    public genericRingLEController(int numNodes) {
        super(getPIDs(numNodes), getConnections(numNodes));
    }

    protected static List<GraphEdge> getConnections(int numNodes) {
        return new CreateRing(numNodes).getConnection();
    }

    protected static List<Integer> getPIDs(int numNodes) {
        return new CreateRing(numNodes).getPIDs();
    }
}
