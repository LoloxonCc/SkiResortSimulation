package athletes;

public class SlopeMemory {
    private double lastBoredomLevel;
    private int lastDescentNumber;
    private int descentCount;
    private String report;

    public SlopeMemory() {
        lastBoredomLevel = 0.0;
        lastDescentNumber = 0;
        descentCount = 0;
        report = "";
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
        if(this.report.isEmpty())
            this.report = "" + descentNumber;
        else
            this.report += "," + descentNumber;
    }

    public String getReport() {
        return report;
    }
}
