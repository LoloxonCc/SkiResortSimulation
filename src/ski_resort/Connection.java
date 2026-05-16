package ski_resort;

public abstract class Connection {
    protected final Node startingStation;
    protected final Node endingStation;
    protected int runCounter;
    protected final int number;
    protected final int travelTime;

    public Connection(int stacja1Id, int stacja2Id, SkiResort skiResort, int number, int travelTime) {
        this.startingStation = skiResort.getStation(stacja1Id);
        this.endingStation = skiResort.getStation(stacja2Id);
        this.runCounter = 0;
        this.number = number;
        this.travelTime = travelTime;
    }

    public Node getEndingStation() {
        return endingStation;
    }

    public int getNumber() {
        return number;
    }

    public int getTravelTime() {
        return travelTime;
    }

    public void incrementRunCounter() {
        runCounter++;
    }

    public String toString() {
        String out = "";
        out += number + " completed " + runCounter + " runs.";
        return out;
    }
}