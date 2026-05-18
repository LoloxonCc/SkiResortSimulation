package events;

import ski_resort.Lift;
import athletes.Athlete;
import simulation.Simulation;
import simulation.Time;

// Represents the event of an athlete entering the queue for a lift.

public class EnterQueue extends AthleteEvent {
    private final Lift lift;

    public EnterQueue(Time time, Athlete athlete, Lift lift) {
        super(time, athlete);
        this.lift = lift;
    }

    public void perform(Simulation simulation) {
        // Athlete can join the queue only before his comeback time specified in the project description.
        if(time.isEarlierThan(simulation.getComebackTime())) {
            lift.addToQueue(athlete);

            if(athlete.isTracked())
                System.out.println(message());
        }
    }

    protected String message() {
        return super.message() + " entered the queue to the lift " + lift.getNumber() + ".";
    }
}
