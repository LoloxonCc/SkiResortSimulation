package ski_resort;

import queues.LiftQueue;
import queues.LiftQueueList;
import athletes.Athlete;

public class Lift extends Connection {
    private final int timeInterval;
    private final int maxGroupSize;
    private final LiftQueue queue;

    public Lift(int station1Id, int station2Id, int timeInterval, int maxGroupSize,
                int liftTime, SkiResort skiResort, int number) {
        super(station1Id, station2Id, skiResort, number, liftTime);
        this.timeInterval = timeInterval;
        this.maxGroupSize = maxGroupSize;
        this.queue = new LiftQueueList();
    }

    public int getTimeInterval() {
        return timeInterval;
    }

    public int getMaxGroupSize() {
        return maxGroupSize;
    }

    public void addToQueue(Athlete athlete) {
        queue.addLast(athlete);
    }

    public LiftQueue getQueue() {
        return queue;
    }

    public String toString() {
        String out = "Lift ";
        out += super.toString();
        return out;
    }

    public Athlete firstAthleteInQueue() {
        return queue.first();
    }
}