package events;

import athletes.Athlete;
import simulation.Simulation;
import simulation.Time;
import ski_resort.Connection;

// Serves as an abstract base for events where an athlete starts using a connection, either ski run or a lift.

public abstract class ConnectionStart extends ConnectionAthleteEvent {

    public ConnectionStart(Time time, Athlete athlete, Connection connection) {
        super(time, athlete, connection);
    }

    public void perform(Simulation simulation) {
        connection.incrementAthleteCounter();
        if(athlete.isTracked())
            System.out.println(message());
    }

    protected String message() {
        return super.message() + " entered the " + getActionDescription() + connection.getNumber() + ".";
    }

    protected abstract String getActionDescription();
}