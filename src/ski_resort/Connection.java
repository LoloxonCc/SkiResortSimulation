package ski_resort;

public abstract class Connection {
    protected final Node startingStation;
    protected final Node endingStation;
    protected int runCounter;

    public Connection(int stacja1Id, int stacja2Id, SkiResort skiResort) {
        this.startingStation = skiResort.getStation(stacja1Id);
        this.endingStation = skiResort.getStation(stacja2Id);
        this.runCounter = 0;
    }

    public Node getEndingStation() {
        return endingStation;
    }

    public void incrementRunCounter() {
        runCounter++;
    }

    public String toString() {
        String out = "";
        out += " completed " + runCounter + " runs.";
        return out;
    }
}