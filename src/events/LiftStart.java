package events;

import ski_resort.Lift;
import athletes.Athlete;
import simulation.Simulation;
import simulation.Time;

public class LiftStart extends ConnectionStart {
    public LiftStart(Time time, Athlete athlete, Lift lift) {
        super(time, athlete, lift);
    }

    public void perform(Simulation simulation) {
        super.perform(simulation);
        simulation.addEvent(new LiftEnd(new Time(time.addSeconds(connection.getTravelTime())), athlete, connection));
    }

    protected String getActionDescription() {
        return "lift ";
    }
}