package events;

import ski_resort.Node;
import athletes.Athlete;
import simulation.Simulation;
import simulation.Time;

public class LiftEnd extends AthleteEvent {
    private final Node station;

    public LiftEnd(Time time, Athlete athlete, Node station) {
        super(time, athlete);
        this.station = station;
    }

    public void perform(Simulation simulation) {
        athlete.decision(time, station, simulation);

        if(athlete.isTracked())
            System.out.println(message());
    }

    public String message() {
        return super.message() + " finished lift to the station " + station.getNumber() + ".";
    }
}