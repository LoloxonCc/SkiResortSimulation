package athletes;

public class LiftMemory {
    private int liftCount;
    private String report;

    public LiftMemory() {
        this.liftCount = 0;
        this.report = "";
    }

    public int getLiftCount() {
        return liftCount;
    }

    public String getReport() {
        return report;
    }

    public void increaseLiftCount() {
        this.liftCount++;
    }

    public void updateReport(int descentNumber) {
        if(this.report.isEmpty())
            this.report = "" + descentNumber;
        else
            this.report += "," + descentNumber;
    }
}
