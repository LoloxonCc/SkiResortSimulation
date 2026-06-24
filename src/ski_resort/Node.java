package ski_resort;

// Represents a single station in the ski resort graph.

public class Node {
    private final int height; //not used in this part of the project
    private final boolean isConnected; //not used in this part of the project
    private final int x; //not used in this part of the project
    private final int y; //not used in this part of the project
    private Lift[] lifts;
    private int liftsCount;
    private SkiSlope[] skiSlopes;
    private int skiSlopesCount;
    private final int number;
    
    public Node(int height, int x, int y, int numer, String s) {
        this.height = height;
        this.x = x;
        this.y = y;
        this.isConnected = s.equals("s");
        this.lifts = new Lift[4];
        this.liftsCount = 0;
        this.skiSlopes = new SkiSlope[4];
        this.skiSlopesCount = 0;
        this.number = numer;
    }

    public int getNumber() {
        return number;
    }

    public SkiSlope[] getSkiSlopes() {
        SkiSlope[] exactSkiSlopes = new SkiSlope[skiSlopesCount];
        System.arraycopy(skiSlopes, 0, exactSkiSlopes, 0, skiSlopesCount);
        return exactSkiSlopes;
    }

    public Lift[] getLifts() {
        Lift[] exactLifts = new Lift[liftsCount];
        System.arraycopy(lifts, 0, exactLifts, 0, liftsCount);
        return exactLifts;
    }
    
    public void addLift(Lift lift) {
        if (liftsCount == lifts.length) {
            int newCapacity = lifts.length * 2;
            Lift[] newLifts = new Lift[newCapacity];
            System.arraycopy(lifts, 0, newLifts, 0, liftsCount);
            lifts = newLifts;
        }
        lifts[liftsCount] = lift;
        liftsCount++;
    }

    public void addSkiSlope(SkiSlope skiSlope) {
        if (skiSlopesCount == skiSlopes.length) {
            int newCapacity = skiSlopes.length * 2;
            SkiSlope[] newSkiSlopes = new SkiSlope[newCapacity];
            System.arraycopy(skiSlopes, 0, newSkiSlopes, 0, skiSlopesCount);
            skiSlopes = newSkiSlopes;
        }
        skiSlopes[skiSlopesCount] = skiSlope;
        skiSlopesCount++;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHeight() {
        return height;
    }

    public boolean isConnected() {
        return isConnected;
    }
}