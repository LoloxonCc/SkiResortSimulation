package events;

import ski_resort.Lift;
import athletes.Athlete;
import simulation.Simulation;
import simulation.Time;

public class EnterQueue extends AthleteEvent {
    private final Lift lift;

    public EnterQueue(Time time, Athlete athlete, Lift lift) {
        super(time, athlete);
        this.lift = lift;
    }

    public void perform(Simulation simulation) {
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
