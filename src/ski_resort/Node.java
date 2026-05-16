package ski_resort;

import athletes.Athlete;

import java.util.Random;

public class Node {
    private final int height; //not used in this part of the project
    private final boolean isConnected; //not used in this part of the project
    private final int x; //not used in this part of the project
    private final int y; //not used in this part of the project
    private Lift[] lifts;
    private SkiRun[] skiRuns;
    private int number;
    
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

    private Lift chooseRandomLift(Random generator) {
        int liftID = generator.nextInt(0, lifts.length);
        return lifts[liftID];
    }

    private Connection findBestReachableRoute(Athlete athlete) {
        Connection bestConnection = null;
        double maxAttractiveness = -1.0;

        for(SkiRun skiRun : skiRuns) {
            double atr = skiRun.calculateCumulativeAttractiveness(athlete);
            if (atr > maxAttractiveness) {
                maxAttractiveness = atr;
                bestConnection = skiRun;
            }
        }

        for(Lift lift : lifts) {
            Node endingStation = lift.getEndingStation();
            for(SkiRun t : endingStation.getSkiRuns()) {
                double atr = t.calculateCumulativeAttractiveness(athlete);
                if (atr > maxAttractiveness) {
                    maxAttractiveness = atr;
                    bestConnection = lift;
                }
            }
        }

        return bestConnection;
    }

    public Connection findBestConnection(Athlete athlete, Random generator) {
        Connection bestConnection = findBestReachableRoute(athlete);

        if(bestConnection == null)
            // If there are no ski runs from this station and those that can be reached by lifts
            // then athlete chooses a random lift
            return this.chooseRandomLift(generator);

        return bestConnection;
    }

    public Connection spontaneousChoice(Random generator) {
        int chosenConnectionId = generator.nextInt(0, lifts.length + skiRuns.length);
        if(chosenConnectionId < lifts.length)
            return lifts[chosenConnectionId];
        else
            return skiRuns[chosenConnectionId - lifts.length];
    }
}