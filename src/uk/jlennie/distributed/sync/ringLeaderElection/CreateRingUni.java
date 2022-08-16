package uk.jlennie.distributed.sync.ringLeaderElection;

import uk.jlennie.distributed.util.GraphEdge;

public class CreateRingUni extends CreateRing{
    public CreateRingUni(int n) {
        super(n);
    }

    @Override
    protected void addLink(int first, int second) {
        connections.add(new GraphEdge(first, second));
    }

    @Override
    protected void addTwoLink() {
        addLink(0, 1);
        addLink(1, 0);
    }
}
