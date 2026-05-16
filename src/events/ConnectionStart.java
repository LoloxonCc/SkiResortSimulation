package events;

import athletes.Athlete;
import simulation.Simulation;
import simulation.Time;
import ski_resort.Connection;

public abstract class ConnectionStart extends ConnectionEvent {

    public ConnectionStart(Time time, Athlete athlete, Connection connection) {
        super(time, athlete, connection);
    }

    public void perform(Simulation simulation) {
        connection.incrementRunCounter();
        if(athlete.isTracked())
            System.out.println(message());
    }

    protected String message() {
        return super.message() + " entered the " + getActionDescription() + connection.getNumber() + ".";
    }

    protected abstract String getActionDescription();
}