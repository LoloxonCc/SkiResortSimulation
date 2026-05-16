package events;

import ski_resort.Lift;
import simulation.Simulation;
import simulation.Time;

public class LiftDeparture extends Event {
    private final Lift lift;

    public LiftDeparture(Time time, Lift lift) {
        super(time);
        this.lift = lift;
    }

    public void perform(Simulation simulation) {
        int i = 0;

        while(!lift.getQueue().empty() && i < lift.getMaxGroupSize()) {
            simulation.addEvent(new LiftStart(time, lift.firstAthleteInQueue(), lift));
            i++;
        }

        if(time.addSeconds(lift.getTimeInterval()).isEarlierThan(simulation.getSimulationEndTime()))
            simulation.addEvent(new LiftDeparture(time.addSeconds(lift.getTimeInterval()), lift));
    }
}