package events;

import ski_resort.Connection;
import athletes.Athlete;
import simulation.Time;

// Represents the event of an athlete finishing a ski run and arriving at the bottom station.

public class RunEnd extends ConnectionEnd {
    public RunEnd(Time time, Athlete athlete, Connection connection) {
        super(time, athlete, connection);
    }

    public String getActionDescription() {
        return " skied the run ";
    }
}