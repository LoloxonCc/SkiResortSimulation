package athletes;

public class SlopeMemory {
    private double lastBoredomLevel = 0.0;
    private int lastDescentNumber = 0;

    public double getLastBoredomLevel() {
        return lastBoredomLevel;
    }

    public int getLastDescentNumber() {
        return lastDescentNumber;
    }

    public void setLastBoredomLevel(double lastBoredomLevel) {
        this.lastBoredomLevel = lastBoredomLevel;
    }

    public void setLastDescentNumber(int lastDescentNumber) {
        this.lastDescentNumber = lastDescentNumber;
    }
}
