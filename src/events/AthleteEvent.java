package events;

import athletes.Athlete;
import simulation.Simulation;
import simulation.Time;

public abstract class AthleteEvent extends Event {
    protected final Athlete athlete;

    public AthleteEvent(Time time, Athlete athlete) {
        super(time);
        this.athlete = athlete;
    }

    public abstract void perform(Simulation simulation);

    public String message() {
        return time.toString() + ": Athlete " + athlete.getNumber();
    }
}