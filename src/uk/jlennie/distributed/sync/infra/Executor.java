package uk.jlennie.distributed.sync.infra;

import java.util.*;

final class Executor<M, R> {
    private final List<Process<M, R>> activeProcesses;
    private final Map<Integer, R> results;
    private Iterator<Process<M, R>> processesIter;


    public Executor(List<Process<M, R>> processes) {
        activeProcesses = new ArrayList<>(processes);
        results = new HashMap<>();
    }

    public Map<Integer, R> run() {
        executeProgram();

        return results;
    }

    private void executeProgram() {
        while (!allProcessesTerminated())
            executeOneRound();
    }

    private boolean allProcessesTerminated() {
        return activeProcesses.isEmpty();
    }

    private void executeOneRound() {
        sendMessages();
        readMessages();

        checkForTerminatedProcesses();
    }

    private void checkForTerminatedProcesses() {
        processesIter = activeProcesses.iterator();

        while (processesIter.hasNext()) {
            checkTerminated(processesIter.next());
        }
    }

    private void readMessages() {
        activeProcesses.forEach(Process::readMessages);
    }

    private void sendMessages() {
        activeProcesses.forEach(Process::sendMessages);
    }

    private void checkTerminated(Process<M, R> p) {
        if (p.terminated)
            addResultAndRemoveProcess(p);
    }

    private void addResultAndRemoveProcess(Process<M, R> p) {
        results.put(p.getPid(), p.getResult());
        processesIter.remove();
    }
}
