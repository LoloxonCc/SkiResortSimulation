package ski_resort;

import events.EnterQueue;
import queues.LiftQueue;
import queues.LiftQueueList;
import athletes.Athlete;
import simulation.Simulation;
import simulation.Time;

// Represents a ski lift connection between two stations in the ski resort.

public class Lift extends Connection {
    private final int timeInterval;
    private final int maxGroupSize;
    private final LiftQueue queue;

    public Lift(int station1Id, int station2Id, int timeInterval, int maxGroupSize,
                int liftTime, SkiResort skiResort, int number, Time simulationStartTime) {
        super(station1Id, station2Id, skiResort, number, liftTime);
        this.timeInterval = timeInterval;
        this.maxGroupSize = maxGroupSize;
        this.queue = new LiftQueueList(simulationStartTime);
    }

    public int getTimeInterval() {
        return timeInterval;
    }

    public int getMaxGroupSize() {
        return maxGroupSize;
    }

    public void addToQueue(Athlete athlete) {
        queue.addLast(athlete);
    }

    public LiftQueue getQueue() {
        return queue;
    }

    public String toString(Simulation simulation) {
        String out = "Lift ";
        out += super.toString();
        out += "Max queue length " + queue.getMaxSize() + ".\n";
        out += "Average queue length " + String.format("%.2f", queue.calculateAverageSize(simulation.getSimulationEndTime(), simulation.getSimulationStartTime())) + ".\n";
        out += "Percentage of occupied places " + String.format("%.2f", calculateOccupiedPlacesPercentage(simulation)) + ".";
        return out;
    }

    public Athlete firstAthleteInQueue() {
        return queue.first();
    }

    @Override
    public boolean isLift() {
        return true;
    }

    @Override
    public void scheduleEvent(Simulation simulation, Time time, Athlete athlete) {
        simulation.addEvent(new EnterQueue(time, athlete, this));
    }

    private double calculateOccupiedPlacesPercentage(Simulation simulation) {
        int durationInSeconds = simulation.getComebackTime().toSeconds() - simulation.getSimulationStartTime().toSeconds();

        int liftDeparturesCount = durationInSeconds / timeInterval;

        int maxPassengerCount = liftDeparturesCount * maxGroupSize;
        if (maxPassengerCount == 0) return 0.0;

        return ((double) athleteCounter / (double) maxPassengerCount) * 100.0;
    }

    public void updateQueueSum(Time time) {
        queue.updateSum(time);
    }
}