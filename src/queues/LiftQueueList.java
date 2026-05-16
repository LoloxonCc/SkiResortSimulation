package queues;

import athletes.Athlete;

public class LiftQueueList implements LiftQueue {
    private LiftQueueElement head;
    private LiftQueueElement tail;

    public LiftQueueList() {
        this.head  = null;
        this.tail = null;
    }

    public boolean empty() {
        return head == null;
    }

    public Athlete first() {
        assert !this.empty() : "Cannot take out of an empty queue!";

        Athlete athlete = head.getAthlete();
        head = head.getNext();

        if(head == null)
            tail = null;
        else
            head.setPrevious(null);

        return athlete;
    }

    public void addLast(Athlete athlete) {
        LiftQueueElement newElement = new LiftQueueElement(athlete, tail, null);

        if(empty())
            head = newElement;
        else
            tail.setNext(newElement);

        tail = newElement;
    }
}