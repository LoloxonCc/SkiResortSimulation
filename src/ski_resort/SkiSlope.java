package ski_resort;

import athletes.Athlete;
import events.RunStart;
import simulation.Simulation;
import simulation.Time;

import java.util.ArrayList;

// Represents a ski run connection between two stations in the ski resort.

public class SkiSlope extends Connection {
    private final int difficultyLevel;
    private final double basicAttractiveness;
    private final double bumpsResistance;

    public SkiSlope(int station1Id, int station2Id, int difficultyLevel, int runTime, double basicAttractiveness,
                    double bumpsResistance, SkiResort skiResort, int number) {
        super(station1Id, station2Id, skiResort, number, runTime);
        assert difficultyLevel >= 0 && difficultyLevel <= 10 : "Wrong difficulty level!";

        this.difficultyLevel = difficultyLevel;
        this.basicAttractiveness = basicAttractiveness;
        this.bumpsResistance = bumpsResistance;
    }

    public String toString() {
        String out = "Ski slope ";
        out += super.toString();
        out += "Surface levelling is now " + String.format("%.2f", calculateSurfaceLevelling()) + ".";
        return out;
    }

    // Calculates difficulty adjustment using formulae given in the project description.
    private double calculateDifficultyAdjustment(int skillLevel) {
        if (difficultyLevel >= skillLevel + 5)
            return 0.0;
        if (skillLevel + 5 > difficultyLevel && difficultyLevel >= skillLevel)
            return 1.0 - (double) (difficultyLevel - skillLevel) / 5.0;
        return Math.max(0.2, 1.0 - (double) (skillLevel - difficultyLevel) / 7.0);
    }

    // Calculates surface levelling using formulae given in the project description.
    private double calculateSurfaceLevelling() {
        return basicAttractiveness + (1.0 - basicAttractiveness) * Math.pow(bumpsResistance, athleteCounter);
    }

    // Calculates cumulative attractiveness for the athlete using formulae given in the project description.
    public double calculateCumulativeAttractiveness(Athlete athlete) {
        return athlete.getSkillAdjustmentWeight() * calculateDifficultyAdjustment(athlete.getSkillLevel()) +
                athlete.getSurfaceLevellingWeight() * calculateSurfaceLevelling() +
                athlete.getBoredomWeight() * (1 - athlete.getCurrentBoredom(this.getNumber()));
    }

    @Override
    public boolean isLift() {
        return false;
    }

    @Override
    public void scheduleEvent(Simulation simulation, Time time, Athlete athlete) {
        simulation.addEvent(new RunStart(time, athlete, this));
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public double getBasicAttractiveness() {
        return basicAttractiveness;
    }

    public double getBumpsResistance() {
        return bumpsResistance;
    }

    public ArrayList<String> parametersMapString() {
        ArrayList<String> out = new ArrayList<>();
        out.add("t" + this.number + ": poziom: " + this.difficultyLevel + ", czas: " + this.travelTime + "s");
        out.add("odporność: " + String.format("%.2f", this.basicAttractiveness) + ", " + String.format("%.5f", this.bumpsResistance));
        return out;
    }

    public ArrayList<String> statisticsMapString() {
        ArrayList<String> out = new ArrayList<>();
        out.add("t" + number +": śnieg: " + String.format("%.2f", this.calculateSurfaceLevelling()));
        out.add("zjazdy: " + this.athleteCounter);
        return out;
    }
}