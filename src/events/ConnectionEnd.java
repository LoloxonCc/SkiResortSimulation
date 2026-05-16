package events;

import athletes.Athlete;
import simulation.Simulation;
import simulation.Time;
import ski_resort.Connection;

public abstract class ConnectionEnd extends ConnectionEvent {
    public ConnectionEnd(Time time, Athlete athlete, Connection connection) {
        super(time, athlete, connection);
    }

    public void perform(Simulation simulation) {
        athlete.decision(time, connection.getEndingStation(), simulation);

        if(athlete.isTracked())
            System.out.println(message());
    }

    protected String message() {
        return super.message() + getActionDescription() + connection.getNumber() + ".";
    }

    protected abstract String getActionDescription();
}