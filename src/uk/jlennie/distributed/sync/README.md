# Synchronous Systems Model

This is a model of a synchronous network of devices. The fact that the network is synchronous means that the implementation is greatly simplified, where we may have one centralised controller, which asks each node to complete one execution cycle.

This model relies at its core on a Graph.
**G = (P, L)**
The vertices of the graph are *Processes*, and the edges are network *Links*. 

### Links
A link is defined by
**L = (L, R, M)**
Where
- L = A process which can write to M
- R = A process which can read to M
- M = A message which is sent over the link

We would say that L is connected to R.

### Processes
A process is  defined by
**P = (M, Q, S, O, I, *f*, *g*)**
Where
- M = Message Alphabet
- Q = Set of states
- S = Start State
- O = Outgoing neighbours (Processes which *P* can send a message to)
- I = Incoming neighbours ( Processes which *P* can read messages from)
- *f* = **Q x O => M** The message *sending* function; defines what message to send to each outgoing neighbour
- *g* = **Q x {(I, M)} => Q** The *transition* function; given the current state, and the set of messages received, choose a new state.

A process may also terminate, once it reaches some satisfied state.

### Rounds of execution

One round of execution involves the following steps.
1. A process runs *f* on all outgoing neighbours and adds the resulting message to the appropriate connection, M.
2. That process then runs *g*, reading what was written in each of its incoming links.
3. *g* transitions to a new state
4. This repeats for all processes.

That creates one round.

Such rounds are executed until, either some sufficiently large time has elapsed, or, all processes have terminated.

### Failure modes

There are a number of failure modes which can be introduced to such a system.
1. **Halting Failure** - Process stops execution at any point, while having written some arbitrary number of messages
2. **Byzantine Failure** - Process behaves arbitrarily, writing random messages, and progressing to random states. 
