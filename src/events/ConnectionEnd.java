package events;

import athletes.Athlete;
import simulation.Simulation;
import simulation.Time;
import ski_resort.Connection;

// Serves as an abstract base for events where athlete ends his journey on a lift or by a ski run.
// It triggers his next decision in the reached station.

public abstract class ConnectionEnd extends ConnectionAthleteEvent {
    public ConnectionEnd(Time time, Athlete athlete, Connection connection) {
        super(time, athlete, connection);
    }

    public void perform(Simulation simulation) {
        athlete.decision(time, connection.getEndingStation(), simulation);

        if(athlete.isTracked()) {
            athlete.increaseConnectionsCount();
            System.out.println(message());
        }
    }

    protected String message() {
        return super.message() + getActionDescription() + connection.getNumber() + ".\n" +
                super.message() + " arrived at the station " + connection.getEndingStation().getNumber() + ".";
    }

    protected abstract String getActionDescription();
}