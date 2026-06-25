package queues;

import events.Event;

/*
    Represents a pair: event and event's chronological number. Used to replace EventQueue with PriorityQueue.
 */

public class EventPair implements Comparable<EventPair> {
    private final Event event;
    private final long chronologicalNumber;

    public EventPair(Event event, long chronologicalNumber) {
        this.event = event;
        this.chronologicalNumber = chronologicalNumber;
    }

    public Event getEvent() {
        return event;
    }

    public int compareTo(EventPair o) {
        long thisTime = this.event.getTime().toSeconds();
        long otherTime = o.getEvent().getTime().toSeconds();

        if(thisTime != otherTime) {
            return Long.compare(thisTime, otherTime);
        }

        return Long.compare(chronologicalNumber, o.chronologicalNumber);
    }
}
