package ski_resort;

// An abstract base class representing a directed edge being either ski run or lift.

import athletes.Athlete;
import simulation.Simulation;
import simulation.Time;

public abstract class Connection {
    protected final Node startingStation;
    protected final Node endingStation;
    protected int athleteCounter;
    protected final int number;
    protected final int travelTime;

    public Connection(int stacja1Id, int stacja2Id, SkiResort skiResort, int number, int travelTime) {
        this.startingStation = skiResort.getStation(stacja1Id);
        this.endingStation = skiResort.getStation(stacja2Id);
        this.athleteCounter = 0;
        this.number = number;
        this.travelTime = travelTime;
    }

    public Node getEndingStation() {
        return endingStation;
    }

    public int getNumber() {
        return number;
    }

    public int getTravelTime() {
        return travelTime;
    }

    public int getAthleteCounter() {
        return athleteCounter;
    }

    public Node getStartingStation() {
        return startingStation;
    }

    public void incrementAthleteCounter() {
        athleteCounter++;
    }

    public String toString() {
        return number + ":\nCompleted " + athleteCounter + " runs.\n";
    }

    public abstract void scheduleEvent(Simulation simulation, Time time, Athlete athlete);
}