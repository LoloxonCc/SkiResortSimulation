package queues;

import athletes.Athlete;

public class LiftQueueElement {
    private final Athlete athlete;
    private LiftQueueElement next;
    private LiftQueueElement previous;

    public LiftQueueElement(Athlete athlete, LiftQueueElement previous, LiftQueueElement next) {
        this.athlete = athlete;
        this.previous = previous;
        this.next = next;
    }

    public Athlete getAthlete() {
        return athlete;
    }

    public LiftQueueElement getNext() {
        return next;
    }

    public void setPrevious(LiftQueueElement previous) {
        this.previous = previous;
    }

    public void setNext(LiftQueueElement next) {
        this.next = next;
    }
}