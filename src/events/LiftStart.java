package events;

import ski_resort.Lift;
import athletes.Athlete;
import simulation.Simulation;
import simulation.Time;

// Represents the event of an athlete boarding a ski lift and beginning the ascent.

public class LiftStart extends ConnectionStart {
    public LiftStart(Time time, Athlete athlete, Lift lift) {
        super(time, athlete, lift);
    }

    public void perform(Simulation simulation) {
        super.perform(simulation);

        athlete.increaseLiftCount(connection.getNumber());
        // Schedules a lift end
        simulation.addEvent(new LiftEnd(time.addSeconds(connection.getTravelTime()), athlete, connection));
    }

    protected String getActionDescription() {
        return "lift ";
    }
}