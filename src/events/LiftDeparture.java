package events;

import ski_resort.Lift;
import simulation.Simulation;
import simulation.Time;

// Represents an event of lift departure with or without a group of athletes.

public class LiftDeparture extends Event {
    private final Lift lift;

    public LiftDeparture(Time time, Lift lift) {
        super(time);
        this.lift = lift;
    }

    public void perform(Simulation simulation) {
        int i = 0;

        // In one run a lift can take up a group of a max size given in its specification.
        // If the max size of a group is bigger than the number of athletes in a queue it takes all of them.
        while(!lift.getQueue().empty() && i < lift.getMaxGroupSize()) {
            simulation.addEvent(new LiftStart(time, lift.firstAthleteInQueue(), lift));
            i++;
        }
        // Next departure can be scheduled only before simulation comeback time specified in the project (15:00:00).
        if(time.addSeconds(lift.getTimeInterval()).isEarlierThan(simulation.getComebackTime()))
            simulation.addEvent(new LiftDeparture(time.addSeconds(lift.getTimeInterval()), lift));
    }
}