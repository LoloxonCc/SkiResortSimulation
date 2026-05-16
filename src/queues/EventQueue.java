package queues;

import events.Event;

public interface EventQueue {
    Event first();
    void add(Event event);
    boolean empty();
}