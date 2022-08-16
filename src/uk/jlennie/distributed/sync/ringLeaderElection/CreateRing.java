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
            addBidirectional(0, 1);
        if (numNodes > 2)
            populateConnections(numNodes);
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
            addBidirectional(i, (i + 1) % numNodes);
    }

    private void addBidirectional(int first, int second) {
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
