package events;

import ski_resort.SkiRun;
import athletes.Athlete;
import simulation.Simulation;
import simulation.Time;

public class RunStart extends ConnectionStart {
    public RunStart(Time time, Athlete athlete, SkiRun skiRun) {
        super(time, athlete, skiRun);
    }

    public void perform(Simulation simulation) {
        super.perform(simulation);
        simulation.addEvent(new RunEnd(time.addSeconds(connection.getTravelTime()), athlete, connection));
    }

    protected String getActionDescription() {
        return "ski run ";
    }
}