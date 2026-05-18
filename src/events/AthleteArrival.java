package events;

import athletes.Athlete;
import simulation.Simulation;
import simulation.Time;


// Represents the initial event of an athlete arriving at his beginning station, when he makes his first decision.

public class AthleteArrival extends AthleteEvent {
    public AthleteArrival(Time time, Athlete athlete) {
        super(time, athlete);
    }

    public void perform(Simulation simulation) {
        athlete.decision(athlete.getStartTime(), athlete.getStartingStation(), simulation);

        if(athlete.isTracked())
            System.out.println(message());
    }

    protected String message() {
        return super.message() + " arrived at the station " + athlete.getStartingStation().getNumber() + ".";
    }
}