package athletes;

public class SlopeMemory {
    private double lastBoredomLevel;
    private int lastDescentNumber;
    private int descentCount;
    private final StringBuilder report;

    public SlopeMemory() {
        lastBoredomLevel = 0.0;
        lastDescentNumber = 0;
        descentCount = 0;
        report = new StringBuilder();
    }

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

    public int getDescentCount() {
        return descentCount;
    }

    public void increaseDescentCount() {
        descentCount++;
    }

    public void updateReport(int descentNumber) {
        if(!report.isEmpty())
            report.append(",");
        report.append(descentNumber);
    }

    public String getReport() {
        return report.toString();
    }
}
