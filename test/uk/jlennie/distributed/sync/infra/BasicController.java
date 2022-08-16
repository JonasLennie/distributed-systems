package uk.jlennie.distributed.sync.infra;

import uk.jlennie.distributed.util.GraphEdge;

import java.util.List;

public class BasicController extends Controller<Integer, Boolean> {
    public BasicController(List<Integer> processIDs, List<GraphEdge> connections) {
        super(processIDs, connections);
    }

    @Override
    protected Process<Integer, Boolean> constructDefaultProcess(int pid) {
        return new BasicProcess(pid);
    }
}
