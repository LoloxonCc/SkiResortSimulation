package events;

import athletes.Athlete;
import simulation.Simulation;
import simulation.Time;
import ski_resort.Connection;

// Serves as an abtract base for events involving an athlete and a connection, either ski run or lift.

public abstract class ConnectionAthleteEvent extends AthleteEvent {
    protected final Connection connection;

    public ConnectionAthleteEvent(Time time, Athlete athlete, Connection connection) {
        super(time, athlete);
        this.connection = connection;
    }

    public abstract void perform(Simulation simulation);
}