package uk.jlennie.distributed.sync.infra;

class BasicProcess extends Process<Integer, Boolean> {
    // Basic process stub
    // Will terminate with true if reads 1 from any other process
    // Otherwise terminates false on second go
    // Sends 1 to all connected processes

    // With controller, will terminate false if it has no incoming connections
    // Otherwise will terminate true

    int round;

    public BasicProcess(int pid) {
        super(pid);
    }

    @Override
    public Process<Integer, Boolean> newInstance(int pid) {
        return new BasicProcess(pid);
    }

    @Override
    public void sendMessages() {
        for (var p : getOutgoingConnections())
            p.send(1);
    }

    @Override
    public void readMessages() {
        if (round > 0)
            terminate(false);
        else
            for (var p : getIncomingConnections())
                processValue(p);

        round ++;

    }

    private void processValue(ConnectionRead<Integer> p) {
        Integer readVal = p.read();
        if (readVal != null && readVal == 1)
            terminate(true);
    }
}
