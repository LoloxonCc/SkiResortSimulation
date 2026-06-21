package events;

import ski_resort.SkiSlope;
import athletes.Athlete;
import simulation.Simulation;
import simulation.Time;

// Represents the event of an athlete beginning a ski run down the slope.

public class RunStart extends ConnectionStart {
    public RunStart(Time time, Athlete athlete, SkiSlope skiSlope) {
        super(time, athlete, skiSlope);
    }

    public void perform(Simulation simulation) {
        super.perform(simulation);
        athlete.increaseDescentCount(connection.getNumber());
        // Schedules arrival at the bottom.
        simulation.addEvent(new RunEnd(time.addSeconds(connection.getTravelTime()), athlete, connection));
    }

    protected String getActionDescription() {
        return "slope ";
    }
}