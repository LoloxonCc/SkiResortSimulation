package queues;

import athletes.Athlete;
import simulation.Time;

// Implementation of the queue of athletes waiting for a lift.

public class LiftQueueList implements LiftQueue {
    private LiftQueueElement head;
    private LiftQueueElement tail;
    private int currentSize;
    private int maxSize;
    private long sizeSum;
    private Time lastOperationTime;

    public LiftQueueList(Time time) {
        this.head  = null;
        this.tail = null;
        this.currentSize = 0;
        this.maxSize = 0;
        this.sizeSum = 0;
        this.lastOperationTime = time;
    }

    public boolean empty() {
        return head == null;
    }

    public Athlete first() {
        assert !this.empty() : "Cannot take out of an empty queue!";

        Athlete athlete = head.getAthlete();
        head = head.getNext();
        currentSize--;

        if(head == null)
            tail = null;
        else
            head.setPrevious(null);

        return athlete;
    }

    public void addLast(Athlete athlete) {
        LiftQueueElement newElement = new LiftQueueElement(athlete, tail, null);
        currentSize++;
        if(currentSize > maxSize)
            maxSize = currentSize;

        if(empty())
            head = newElement;
        else
            tail.setNext(newElement);

        tail = newElement;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void updateSum(Time time) {
        long secondsElapsed = (long) time.toSeconds() - lastOperationTime.toSeconds();
        if (secondsElapsed > 0) {
            this.sizeSum += (long) currentSize * secondsElapsed;
        }
        this.lastOperationTime = time;
    }

    @Override
    public double calculateAverageSize(Time simulationEndTime, Time simulationStartTime) {
        updateSum(simulationEndTime);

        long totalTimeInSeconds = simulationEndTime.toSeconds() - simulationStartTime.toSeconds();


        return (double) sizeSum / (double) totalTimeInSeconds;
    }
}