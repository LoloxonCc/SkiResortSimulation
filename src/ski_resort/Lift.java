package ski_resort;

import queues.LiftQueue;
import queues.LiftQueueList;
import athletes.Athlete;

public class Lift extends Connection {
    private final int timeInterval;
    private final int maxGroupSize;
    private final int liftTime;
    private LiftQueue queue;
    private int number;

    public Lift(int station1Id, int station2Id, int timeInterval, int maxGroupSize,
                int liftTime, SkiResort skiResort, int number) {
        super(station1Id, station2Id, skiResort);
        this.timeInterval = timeInterval;
        this.maxGroupSize = maxGroupSize;
        this.liftTime = liftTime;
        this.number = number;
        this.queue = new LiftQueueList();
    }

    public int getTimeInterval() {
        return timeInterval;
    }

    public int getLiftTime() {
        return liftTime;
    }

    public int getMaxGroupSize() {
        return maxGroupSize;
    }

    public int getNumber() {
        return number;
    }

    public void addToQueue(Athlete athlete) {
        queue.addLast(athlete);
    }

    public LiftQueue getQueue() {
        return queue;
    }

    public String toString() {
        String out = "Lift " + number;
        out += super.toString();
        return out;
    }

    public Athlete firstAthleteInQueue() {
        return queue.first();
    }
}