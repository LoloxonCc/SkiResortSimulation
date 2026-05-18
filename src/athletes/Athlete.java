package athletes;

import ski_resort.*;
import simulation.Time;
import events.RunStart;
import simulation.Simulation;
import events.EnterQueue;

/*
    Represents an athlete (skier or snowboarder) participating in the ski resort simulation.
    It is responsible for decision-making process for selecting next connection used by the athlet.
 */
public class Athlete {
    private final int skillLevel;
    private final double spontaneityCoefficient;
    private final boolean tracked;
    private final double skillAdjustmentWeight;
    private final double surfaceLevellingWeight;
    private final Node startingStation;
    private final Time startTime;
    private final int number;

    public Athlete(int skillLevel, double spontaneityCoefficient, String tracked, double skillAdjustmentWeight,
                   double surfaceLevellingWeight, int stationId, SkiResort skiResort, Time startTime, int number) {
        assert skillLevel >= 0 && skillLevel <= 10 : "Wrong skill level!";
        assert spontaneityCoefficient >= 0.0 && spontaneityCoefficient <= 1.0 : "Wrong spontaneity coefficient!";

        this.skillLevel = skillLevel;
        this.spontaneityCoefficient = spontaneityCoefficient;
        this.tracked = tracked.equals("s");
        this.skillAdjustmentWeight = skillAdjustmentWeight;
        this.surfaceLevellingWeight = surfaceLevellingWeight;
        this.startingStation = skiResort.getStation(stationId);
        this.startTime = startTime;
        this.number = number;
    }

    public double getSkillAdjustmentWeight() {
        return skillAdjustmentWeight;
    }

    public double getSurfaceLevellingWeight() {
        return surfaceLevellingWeight;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public boolean isTracked() {
        return tracked;
    }

    public int getNumber() {
        return number;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Node getStartingStation() {
        return startingStation;
    }

    public void decision(Time time, Node station, Simulation simulation) {
        // Athlete can make decision only before comebackTime (in this case 15:00:00).
        // Otherwise, he returns to his startingStation (which is not simulated).
        if(!time.isEarlierThan(simulation.getComebackTime()))
            return;

        double chance = simulation.getGenerator().nextDouble();
        Connection choice;

        if(chance < spontaneityCoefficient)
            choice = station.spontaneousChoice(simulation.getGenerator());
        else
            choice = station.findBestConnection(this, simulation.getGenerator());

        if (choice instanceof SkiRun skiRun) {
            simulation.addEvent(new RunStart(time, this, skiRun));
        } else if (choice instanceof Lift lift) {
            simulation.addEvent(new EnterQueue(time, this, lift));
        }
    }
}