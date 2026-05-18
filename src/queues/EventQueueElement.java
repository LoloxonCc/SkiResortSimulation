package queues;

import events.Event;

// Represents a single node within a singly linked list used to implement the event queue.

public class EventQueueElement {
    private final Event event;
    private EventQueueElement next;

    public EventQueueElement(Event event) {
        this(event, null);
    }

    public EventQueueElement(Event event, EventQueueElement next) {
        this.event = event;
        this.next = next;
    }

    public Event getEvent() {
        return event;
    }

    public EventQueueElement getNext() {
        return next;
    }

    public void setNext(EventQueueElement next) {
        this.next = next;
    }
}