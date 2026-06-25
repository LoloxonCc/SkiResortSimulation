import athletes.Athlete;
import athletes.LocalAthlete;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import simulation.Time;
import ski_resort.Lift;
import ski_resort.SkiResort;
import ski_resort.Node;

import static org.junit.jupiter.api.Assertions.*;

class LiftDepartureTest {

    private Lift lift;
    private SkiResort skiResort;
    private Time startTime;

    @BeforeEach
    void setUp() {
        startTime = new Time("09:00:00");

        Node[] stations = new Node[] {
                new Node(500, 0, 0, 0, "s"),
                new Node(800, 10, 10, 1, "")
        };
        skiResort = new SkiResort(stations);

        lift = new Lift(0, 1, 10, 3, 120, skiResort, 0, startTime);
    }

    @Test
    void testOne() {
        assertTrue(lift.isQueueEmpty());

        for (int i = 0; i < 4; i++) {
            Athlete athlete = new LocalAthlete(5, 0.1, "", 0.5,
                    0.5, 0, skiResort, startTime, i, 0.1, 0.1);
            lift.addToQueue(athlete);
        }

        int boardedCount = 0;
        lift.updateQueueSum(startTime);
        while (!lift.isQueueEmpty() && boardedCount < lift.getMaxGroupSize()) {
            lift.firstAthleteInQueue();
            lift.incrementAthleteCounter();
            boardedCount++;
        }

        assertEquals(3, boardedCount, "Lift should board 3 athletes.");
        assertEquals(3, lift.getAthleteCounter(), "Athlete counter should increase by 3.");
        assertEquals(1, lift.getCurrentQueueSize(), "There should be one athlete left in the queue.");
    }

    @Test
    void testTwo() {
        assertTrue(lift.isQueueEmpty());

        for (int i = 0; i < 2; i++) {
            Athlete athlete = new LocalAthlete(5, 0.1, "", 0.5, 0.5, 0, skiResort, startTime, i, 0.1, 0.1);
            lift.addToQueue(athlete);
        }

        int boardedCount = 0;
        lift.updateQueueSum(startTime);
        while (!lift.isQueueEmpty() && boardedCount < lift.getMaxGroupSize()) {
            lift.firstAthleteInQueue();
            lift.incrementAthleteCounter();
            boardedCount++;
        }

        assertEquals(2, boardedCount, "Lift should board  all (2) athletes.");
        assertEquals(2, lift.getAthleteCounter(), "Athlete counter should increase by 2.");
        assertTrue(lift.isQueueEmpty(), "Queue should be empty.");
    }

    @Test
    void testThree() {
        for (int i = 0; i < 4; i++) {
            Athlete athlete = new LocalAthlete(5, 0.1, "", 0.5, 0.5, 0, skiResort, startTime, i, 0.1, 0.1);
            lift.addToQueue(athlete);
        }

        int boardedCount = 0;
        lift.updateQueueSum(startTime);
        while (!lift.isQueueEmpty() && boardedCount < lift.getMaxGroupSize()) {
            lift.firstAthleteInQueue();
            lift.incrementAthleteCounter();
            boardedCount++;
        }

        Athlete lastAthlete = new LocalAthlete(5, 0.1, "", 0.5, 0.5, 0, skiResort, startTime, 99, 0.1, 0.1);
        lift.addToQueue(lastAthlete);

        assertEquals(4, lift.getMaxQueueSize(), "Maximum queue size should be 4.");
    }
}