package queues;

import athletes.Athlete;

public interface LiftQueue {
    boolean empty();
    void addLast(Athlete athlete);
    Athlete first();
}