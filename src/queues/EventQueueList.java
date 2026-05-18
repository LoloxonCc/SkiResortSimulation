package queues;

import events.Event;

// Implementation of the event queue based on a singly linked list.
// Events are inserted and maintained in strict chronological order.

public class EventQueueList implements EventQueue {
    private EventQueueElement head;

    public EventQueueList() {
        head = null;
    }

    // This method adds event in a sorted timeline. If the queue contains some events with the same time then new event
    // is added after them.
    public void add(Event event) {
        EventQueueElement newElement = new EventQueueElement(event);

        // If the new event is the earliest or firts in whole simulation then it is added as head.
        if(head == null || !head.getEvent().getTime().isEarlierThan(event.getTime())) {
            newElement.setNext(head);
            head = newElement;
            return;
        }

        EventQueueElement current = head;

        // Event is inserted to maintain a chronological order.
        // If it has a same time of happening as another in queue then it is added after the one in the queue.
        while (current.getNext() != null && current.getNext().getEvent().getTime().isEarlierThan(event.getTime()))
            current = current.getNext();

        newElement.setNext(current.getNext());
        current.setNext(newElement);
    }

    public boolean empty() {
        return head == null;
    }

    // Retrieves and removes the earliest scheduled event from the queue.
    public Event first() {
        assert !this.empty() : "Cannot take out of an empty queue!";
        Event z = head.getEvent();
        head = head.getNext();
        return z;
    }
}
