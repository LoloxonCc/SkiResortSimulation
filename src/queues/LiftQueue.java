package queues;

import athletes.Athlete;
import simulation.Time;

// Defines the contract for a queue managing athletes waiting for a ski lift.
// It is a standard FIFO approach.

public interface LiftQueue {
    boolean empty();
    // Adds an athlete to the end of queue.
    void addLast(Athlete athlete);
    // Retrieves and removes the first athlete from the fron.
    Athlete first();

    int getMaxSize();

    void updateSum(Time time);

    int calculateAverageSize(Time simulationEndTime, Time simulationStartTime);

    int getCurrentSize();
}