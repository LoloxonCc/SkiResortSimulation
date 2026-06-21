package queues;

import events.Event;

public class EventPair implements Comparable<EventPair> {
    private final Event event;
    private final long sequenceNumber;

    public EventPair(Event event, long sequenceNumber) {
        this.event = event;
        this.sequenceNumber = sequenceNumber;
    }

    public Event getEvent() {
        return event;
    }

    @Override
    public int compareTo(EventPair o) {
        long thisTime = this.event.getTime().toSeconds();
        long otherTime = o.getEvent().getTime().toSeconds();

        if(thisTime != otherTime) {
            return Long.compare(thisTime, otherTime);
        }

        return Long.compare(sequenceNumber, o.sequenceNumber);
    }
}
