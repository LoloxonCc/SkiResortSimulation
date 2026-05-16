package events;

import athletes.Athlete;
import simulation.Simulation;
import simulation.Time;
import ski_resort.Connection;

public abstract class ConnectionEvent extends AthleteEvent {
    protected final Connection connection;

    public ConnectionEvent(Time time, Athlete athlete, Connection connection) {
        super(time, athlete);
        this.connection = connection;
    }

    public abstract void perform(Simulation simulation);
}