package events;

import simulation.Simulation;
import simulation.Time;

public abstract class Event {
    protected final Time time;

    protected Event(Time time) {
        this.time = time;
    }

    public Time getTime() {
        return time;
    }

    public abstract void perform(Simulation simulation);
}
