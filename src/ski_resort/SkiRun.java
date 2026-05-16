package ski_resort;

import athletes.Athlete;

public class SkiRun extends Connection {
    private final int difficultyLevel;
    private final double basicAttractiveness;
    private final double bumpsResistance;

    public SkiRun(int station1Id, int station2Id, int difficultyLevel, int runTime, double basicAttractiveness,
                  double bumpsResistance, SkiResort skiResort, int number) {
        assert difficultyLevel >= 0 && difficultyLevel <= 10 : "Wrong difficulty level!";

        super(station1Id, station2Id, skiResort, number, runTime);
        this.difficultyLevel = difficultyLevel;
        this.basicAttractiveness = basicAttractiveness;
        this.bumpsResistance = bumpsResistance;
    }

    public String toString() {
        String out = "Ski run " + number;
        out += super.toString();
        return out;
    }
    private double calculateDifficultyAdjustment(int skillLevel) {
        if (difficultyLevel >= skillLevel + 5)
            return 0.0;
        if (skillLevel + 5 > difficultyLevel && difficultyLevel >= skillLevel)
            return 1.0 - (double) (difficultyLevel - skillLevel) / 5.0;
        return Math.max(0.2, 1.0 - (double) (skillLevel - difficultyLevel) / 7.0);
    }

    private double calculateSurfaceLevelling() {
        return basicAttractiveness + (1.0 - basicAttractiveness) * Math.pow(bumpsResistance, runCounter);
    }

    public double calculateCumulativeAttractiveness(Athlete athlete) {
        return athlete.getSkillAdjustmentWeight() * calculateDifficultyAdjustment(athlete.getSkillLevel()) + athlete.getSurfaceLevellingWeight() * calculateSurfaceLevelling();
    }
}