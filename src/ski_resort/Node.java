package ski_resort;

// Represents a single station in the ski resort graph.

public class Node {
    private final int height; //not used in this part of the project
    private final boolean isConnected; //not used in this part of the project
    private final int x; //not used in this part of the project
    private final int y; //not used in this part of the project
    private Lift[] lifts;
    private SkiRun[] skiRuns;
    private final int number;
    
    public Node(int height, int x, int y, int numer, String s) {
        this.height = height;
        this.x = x;
        this.y = y;
        this.isConnected = s.equals("s");
        this.lifts = new Lift[0];
        this.skiRuns = new SkiRun[0];
        this.number = numer;
    }

    public int getNumber() {
        return number;
    }

    public SkiRun[] getSkiRuns() {
        return skiRuns.clone();
    }

    public Lift[] getLifts() {
        return lifts.clone();
    }
    
    public void addLift(Lift lift) {
        Lift[] newLifts = new Lift[lifts.length + 1];
        System.arraycopy(lifts, 0, newLifts, 0, lifts.length);
        newLifts[newLifts.length - 1] = lift;

        lifts = newLifts;
    }

    public void addSkiRun(SkiRun skiRun) {
        SkiRun[] newSkiRuns = new SkiRun[skiRuns.length + 1];
        System.arraycopy(skiRuns, 0, newSkiRuns, 0, skiRuns.length);
        newSkiRuns[newSkiRuns.length - 1] = skiRun;

        skiRuns = newSkiRuns;
    }
}