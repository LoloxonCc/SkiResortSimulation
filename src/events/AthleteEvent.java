package events;

import athletes.Athlete;
import simulation.Simulation;
import simulation.Time;


// Serves as an abstract base for any event that an athlete engages in.

public abstract class AthleteEvent extends Event {
    protected final Athlete athlete;

    public AthleteEvent(Time time, Athlete athlete) {
        super(time);
        this.athlete = athlete;
    }

    public abstract void perform(Simulation simulation);

    protected String message() {
        return time.toString() + ": Athlete " + athlete.getNumber();
    }
}