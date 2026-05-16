package events;

import ski_resort.Node;
import athletes.Athlete;
import simulation.Simulation;
import simulation.Time;

public class RunEnd extends AthleteEvent {
    private final Node station;

    public RunEnd(Time time, Athlete athlete, Node station) {
        super(time, athlete);
        this.station = station;
    }

    public void perform(Simulation simulation) {
        athlete.decision(time, station, simulation);

        if(athlete.isTracked())
            System.out.println(message());
    }

    public String message() {
        return super.message() + " skied to the station " + station.getNumber() + ".";
    }
}
