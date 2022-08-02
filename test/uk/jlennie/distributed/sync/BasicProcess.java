package uk.jlennie.distributed.sync;

class BasicProcess extends Process<Integer, Boolean> {
    // Basic process stub
    // Will terminate with true if reads 1 from any other process
    // Otherwise terminates false on second go
    // Sends 1 to all connected processes

    int round;

    public BasicProcess(int pid) {
        super(pid);
    }

    @Override
    public void sendMessages() {
        for (var p : outgoingConnections)
            p.send(1);
    }

    @Override
    public void readMessages() {
        if (round > 0)
            terminate(false);
        else
            for (var p : incomingConnections)
                if (p.read() == 1)
                    terminate(true);

        round ++;

    }
}
