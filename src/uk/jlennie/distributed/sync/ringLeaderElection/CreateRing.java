package uk.jlennie.distributed.sync.ringLeaderElection;

import uk.jlennie.distributed.util.GraphEdge;

import java.util.*;

public class CreateRing {
    List<Integer> PIDs;
    List<GraphEdge> connections;

    public CreateRing(int numNodes) {
        if (numNodes <= 0)
            throw new RuntimeException("Bad number of nodes");

        setup();

        populatePIDs(numNodes);

        if (numNodes == 2)
            addTwoLink();
        if (numNodes > 2)
            populateConnections(numNodes);
    }

    protected void addTwoLink() {
        addLink(0, 1);
    }

    private void setup() {
        PIDs = new ArrayList<>();
        connections = new ArrayList<>();
    }

    private void populatePIDs(int numNodes) {
        for (int i = 0; i < numNodes; i ++)
            PIDs.add(i);
    }

    private void populateConnections(int numNodes) {
        for (int i = 0; i < numNodes; i ++)
            addLink(i, (i + 1) % numNodes);
    }

    protected void addLink(int first, int second) {
        connections.add(new GraphEdge(first, second));
        connections.add(new GraphEdge(second, first));
    }


    public List<Integer> getPIDs() {
        return PIDs;
    }

    public List<GraphEdge> getConnection() {
        return connections;
    }
}
