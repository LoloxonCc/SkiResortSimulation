package events;

import ski_resort.Lift;
import athletes.Athlete;
import simulation.Simulation;
import simulation.Time;

public class LiftStart extends AthleteEvent {
    private final Lift lift;

    public LiftStart(Time time, Athlete athlete, Lift lift) {
        super(time, athlete);
        this.lift = lift;
    }

    public void perform(Simulation simulation) {
        lift.incrementRunCounter();

        simulation.addEvent(new LiftEnd(new Time(time.addSeconds(lift.getLiftTime())), athlete, lift.getEndingStation()));

        if(athlete.isTracked())
            System.out.println(message());
    }

    protected String message() {
        return super.message() + " entered the lift " + lift.getNumber();
    }
}