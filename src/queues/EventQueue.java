package queues;

import events.Event;

// Defines the contract for the event queue in the simulation.
// It manages and schedules events in a chronological order.

public interface EventQueue {
    // Retrieves and removes earliest scheduled event from the queue.
    Event first();
    // Inserts a new event into queue in a chronological order.
    void add(Event event);
    // Checks whether the event queue is empty.
    boolean empty();
}