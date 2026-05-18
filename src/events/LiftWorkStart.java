package events;

import ski_resort.Lift;
import simulation.Simulation;
import simulation.Time;

// Represents the initialization event that starts the operation of ski lifts.

public class LiftWorkStart extends Event {
    private final Lift[] lifts;

    public LiftWorkStart(Time time, Lift[] lifts) {
        super(time);
        this.lifts = lifts;
    }

    public void perform(Simulation simulation) {
        for(Lift lift : lifts)
            simulation.addEvent(new LiftDeparture(this.time, lift));
    }
}