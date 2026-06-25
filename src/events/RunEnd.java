package events;

import athletes.SlopeMemory;
import simulation.Simulation;
import ski_resort.Connection;
import athletes.Athlete;
import simulation.Time;

// Represents the event of an athlete finishing a ski run and arriving at the bottom station.

public class RunEnd extends ConnectionEnd {
    public RunEnd(Time time, Athlete athlete, Connection connection) {
        super(time, athlete, connection);
    }

    @Override
    public void perform(Simulation simulation) {
        double currentBoredom = athlete.getCurrentBoredom(connection.getNumber());

        double newBoredom = athlete.getBoredomCoefficient() + (1 - athlete.getBoredomCoefficient()) * currentBoredom;

        athlete.increaseTotalDescentCount();

        SlopeMemory memory = athlete.getSlopeMemory(connection.getNumber());
        memory.setLastBoredomLevel(newBoredom);
        memory.setLastDescentNumber(athlete.getTotalDescentCount());

        if(athlete.isTracked())
            athlete.updateSlopeReport(connection.getNumber());

        super.perform(simulation);
    }

    public String getActionDescription() {
        return " skied the slope ";
    }
}