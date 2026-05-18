package events;

import ski_resort.Connection;
import athletes.Athlete;
import simulation.Time;

// Represents the event of an athlete finishing a lift ride and arriving at the top station.

public class LiftEnd extends ConnectionEnd {
    public LiftEnd(Time time, Athlete athlete, Connection connection) {
        super(time, athlete, connection);
    }

     public String getActionDescription() {
        return " left the lift ";
     }
}