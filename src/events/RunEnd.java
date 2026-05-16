package events;

import ski_resort.Connection;
import athletes.Athlete;
import simulation.Time;

public class RunEnd extends ConnectionEnd {
    public RunEnd(Time time, Athlete athlete, Connection connection) {
        super(time, athlete, connection);
    }

    public String getActionDescription() {
        return " skied the run ";
    }
}