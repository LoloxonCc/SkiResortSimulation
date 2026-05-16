package events;

import athletes.Athlete;
import simulation.Simulation;
import simulation.Time;
import ski_resort.Node;

public abstract class ConnectionEnd extends AthleteEvent {
    protected final Node station;

    public ConnectionEnd(Time time, Athlete athlete, Node station) {
        super(time, athlete);
        this.station = station;
    }

    public void perform(Simulation simulation) {
        athlete.decision(time, station, simulation);

        if(athlete.isTracked())
            System.out.println(message());
    }

    protected String message() {
        return super.message() + getActionDescription() + " to the station " + station.getNumber() + ".";
    }

    protected abstract String getActionDescription();
}