package athletes;

import ski_resort.*;
import simulation.Time;
import simulation.Simulation;

/*
    Represents an athlete (skier or snowboarder) participating in the ski resort simulation.
    It is responsible for decision-making process for selecting next connection used by the athlet.
 */
public abstract class Athlete {
    protected final int skillLevel;
    protected final double spontaneityCoefficient;
    protected final boolean tracked;
    protected final double skillAdjustmentWeight;
    protected final double surfaceLevellingWeight;
    protected final double boredomCoefficient;
    protected final double boredomWeight;
    protected final Node startingStation;
    protected final Time startTime;
    protected final int number;
    protected int totalDescentCount;
    protected final SlopeMemory[] slopeMemories;
    protected int connectionsCount;
    protected final LiftMemory[] liftMemories;

    public Athlete(int skillLevel, double spontaneityCoefficient, String tracked, double skillAdjustmentWeight,
                   double surfaceLevellingWeight, int stationId, SkiResort skiResort, Time startTime, int number,
                   double boredomCoefficient, double boredomWeight) {
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
        this.boredomCoefficient = boredomCoefficient;
        this.boredomWeight = boredomWeight;
        this.totalDescentCount = 0;

        int totalNumberOfSlopes = skiResort.getTotalSkiSlopesCount();
        this.slopeMemories = new SlopeMemory[totalNumberOfSlopes];
        for (int i = 0; i < totalNumberOfSlopes; i++)
            this.slopeMemories[i] = new SlopeMemory();

        this.connectionsCount = 0;
        int totalNumberOfLift = skiResort.getTotalLiftsCount();
        this.liftMemories = new LiftMemory[totalNumberOfLift];
        for (int i = 0; i < totalNumberOfLift; i++)
            this.liftMemories[i] = new LiftMemory();
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

    public double getCurrentBoredom(int slopeId) {
        SlopeMemory memory = slopeMemories[slopeId];

        int i = this.totalDescentCount - memory.getLastDescentNumber();

        return memory.getLastBoredomLevel() * Math.pow(1 - this.boredomCoefficient, i);
    }

    public double getBoredomCoefficient() {
        return boredomCoefficient;
    }

    public void increaseTotalDescentCount() {
        this.totalDescentCount++;
    }

    public SlopeMemory[] getSlopeMemories() {
        return slopeMemories;
    }

    public int getTotalDescentCount() {
        return totalDescentCount;
    }

    public double getBoredomWeight() {
        return boredomWeight;
    }

    public void increaseDescentCount(int slopeId) {
        slopeMemories[slopeId].increaseDescentCount();
    }

    public int getDescentCount(int slopeId) {
        return slopeMemories[slopeId].getDescentCount();
    }

    public abstract void decision(Time time, Node station, Simulation simulation);

    public void increaseConnectionsCount() {
        connectionsCount++;
    }

    public void updateSlopeReport(int slopeId) {
        this.slopeMemories[slopeId].updateReport(connectionsCount);
    }

    public void updateLiftReport(int liftId) {
        this.liftMemories[liftId].updateReport(connectionsCount);
    }

    public void increaseLiftCount(int liftId) {
        this.liftMemories[liftId].increaseLiftCount();
    }

    public int getLiftCount(int liftId) {
        return liftMemories[liftId].getLiftCount();
    }

    public String getLiftReport(int liftId) {
        return liftMemories[liftId].getReport();
    }

    public int getSlopeCount(int slopeId) {
        return slopeMemories[slopeId].getDescentCount();
    }

    public String getSlopeReport(int slopeId) {
        return slopeMemories[slopeId].getReport();
    }
}