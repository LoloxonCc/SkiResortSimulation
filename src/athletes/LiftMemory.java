package athletes;

public class LiftMemory {
    private int liftCount;
    private final StringBuilder report;

    public LiftMemory() {
        this.liftCount = 0;
        this.report = new StringBuilder();
    }

    public int getLiftCount() {
        return liftCount;
    }

    public String getReport() {
        return report.toString();
    }

    public void increaseLiftCount() {
        this.liftCount++;
    }

    public void updateReport(int descentNumber) {
        if (!report.isEmpty()) {
            report.append(",");
        }
        report.append(descentNumber);
    }
}
