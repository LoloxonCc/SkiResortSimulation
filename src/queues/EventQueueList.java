package queues;

import events.Event;

public class EventQueueList implements EventQueue {
    private EventQueueElement head;

    public EventQueueList() {
        head = null;
    }

    public void add(Event event) {
        EventQueueElement newElement = new EventQueueElement(event);

        if(head == null || !head.getEvent().getTime().isEarlierThan(event.getTime())) {
            newElement.setNext(head);
            head = newElement;
            return;
        }

        EventQueueElement current = head;

        while (current.getNext() != null && current.getNext().getEvent().getTime().isEarlierThan(event.getTime()))
            current = current.getNext();

        newElement.setNext(current.getNext());
        current.setNext(newElement);
    }

    @Override
    public boolean empty() {
        return head == null;
    }

    @Override
    public Event first() {
        assert !this.empty() : "Cannot take out of an empty queue!";
        Event z = head.getEvent();
        head = head.getNext();
        return z;
    }
}
