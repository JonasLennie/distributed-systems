package uk.jlennie.distributed.sync.ringLeaderElection.LCR;

import uk.jlennie.distributed.sync.ringLeaderElection.LEMessage;
import uk.jlennie.distributed.sync.ringLeaderElection.genericRingLEProcess;

public class LCRProcess extends genericRingLEProcess {

    Integer sendNext;
    boolean leaderFound;
    boolean amLeader;
    public LCRProcess(int pid) {
        super(pid);

        sendNext = pid;
    }

    @Override
    public void sendMessages() {
        if (outgoingConnections.size() == 0) {
            terminateAsLeader();
            return;
        }

        sendWithOutgoingConnections();

    }

    private void sendWithOutgoingConnections() {
        if (!leaderFound)
            sendNextPID();
        else
            circulateNewLeader();
    }

    private void circulateNewLeader() {
        sendNewLeader();

        if (amLeader)
            terminateAsLeader();
        else
            terminateNotAsLeader();
    }

    private void terminateNotAsLeader() {
        terminate(false);
    }

    private void terminateAsLeader() {
        terminate(true);
    }

    private void sendNewLeader() {
        send(true, 0);
    }

    private void send(boolean isLeaderDeclared, int pid) {
        sendMessage(new LEMessage(isLeaderDeclared, pid));
    }

    private void sendMessage(LEMessage m) {
        (outgoingConnections.get(0)).send(m);
    }

    private void sendNextPID() {
        if (sendNext == null)
            sendMessage(null);
        else
            send(false, sendNext);
    }

    @Override
    public void readMessages() {
        if (incomingConnections.size() == 0)
            return;

        readMessagesHasConnection();
    }

    private void readMessagesHasConnection() {
        LEMessage message = getMessage();

        if (message == null) {
            return;
        }
        processNonNullMessage(message);
    }

    private void processNonNullMessage(LEMessage message) {
        if (message.leaderIsDeclared) {
            recievedLeaderDeclaredMessage();
        } else processNormalMessage(message);
    }

    private void processNormalMessage(LEMessage message) {
        if (message.pid == this.pid) {
            becomeLeader();
        } else if (message.pid > this.pid) {
            passOnPID(message);
        } else {
            discardPID();
        }
    }

    private void passOnPID(LEMessage message) {
        sendNext = message.pid;
    }

    private void discardPID() {
        sendNext = null;
    }

    private void becomeLeader() {
        leaderFound = true;
        amLeader = true;
    }

    private void recievedLeaderDeclaredMessage() {
        leaderFound = true;
        amLeader = false;
    }

    private LEMessage getMessage() {
        return incomingConnections.get(0).read();
    }
}
