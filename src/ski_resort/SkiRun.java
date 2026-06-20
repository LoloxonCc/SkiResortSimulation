package ski_resort;

import athletes.Athlete;
import events.RunStart;
import simulation.Simulation;
import simulation.Time;

// Represents a ski run connection between two stations in the ski resort.

public class SkiRun extends Connection {
    private final int difficultyLevel;
    private final double basicAttractiveness;
    private final double bumpsResistance;

    public SkiRun(int station1Id, int station2Id, int difficultyLevel, int runTime, double basicAttractiveness,
                  double bumpsResistance, SkiResort skiResort, int number) {
        super(station1Id, station2Id, skiResort, number, runTime);
        assert difficultyLevel >= 0 && difficultyLevel <= 10 : "Wrong difficulty level!";

        this.difficultyLevel = difficultyLevel;
        this.basicAttractiveness = basicAttractiveness;
        this.bumpsResistance = bumpsResistance;
    }

    public String toString() {
        String out = "Ski run ";
        out += super.toString();
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
        return basicAttractiveness + (1.0 - basicAttractiveness) * Math.pow(bumpsResistance, runCounter);
    }

    // Calculates cumulative attractiveness for the athlete using formulae given in the project description.
    public double calculateCumulativeAttractiveness(Athlete athlete) {
        return athlete.getSkillAdjustmentWeight() * calculateDifficultyAdjustment(athlete.getSkillLevel()) + athlete.getSurfaceLevellingWeight() * calculateSurfaceLevelling();
    }

    @Override
    public boolean isLift() {
        return false;
    }

    @Override
    public void scheduleEvent(Simulation simulation, Time time, Athlete athlete) {
        simulation.addEvent(new RunStart(time, athlete, this));
    }
}