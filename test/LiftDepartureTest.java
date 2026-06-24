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
    private SkiResort mockResort;
    private Time startTime;

    @BeforeEach
    void setUp() {
        startTime = new Time("09:00:00");

        Node[] stations = new Node[] {
                new Node(500, 0, 0, 0, "s"),
                new Node(800, 10, 10, 1, "")
        };
        mockResort = new SkiResort(stations);

        lift = new Lift(0, 1, 10, 3, 120, mockResort, 0, startTime);
    }

    @Test
    void testLiftDepartureWithFourAthletes() {
        assertTrue(lift.getQueue().empty());

        for (int i = 0; i < 4; i++) {
            Athlete athlete = new LocalAthlete(5, 0.1, "", 0.5, 0.5, 0, mockResort, startTime, i, 0.1, 0.1);
            lift.addToQueue(athlete);
        }

        int boardedCount = 0;
        lift.updateQueueSum(startTime);
        while (!lift.getQueue().empty() && boardedCount < lift.getMaxGroupSize()) {
            lift.firstAthleteInQueue();
            lift.incrementAthleteCounter();
            boardedCount++;
        }

        assertEquals(3, boardedCount, "Wagonik powinien zabrać 3 sportowców.");
        assertEquals(3, lift.getAthleteCounter(), "Licznik przejazdów wyciągu powinien wzrosnąć o 3.");
        assertFalse(lift.getQueue().empty(), "W kolejce powinien zostać jeszcze 1 sportowiec.");
    }

    @Test
    void testLiftDepartureWithFewerAthletesThanCapacity() {
        assertTrue(lift.getQueue().empty());

        for (int i = 0; i < 2; i++) {
            Athlete athlete = new LocalAthlete(5, 0.1, "", 0.5, 0.5, 0, mockResort, startTime, i, 0.1, 0.1);
            lift.addToQueue(athlete);
        }

        int boardedCount = 0;
        lift.updateQueueSum(startTime);
        while (!lift.getQueue().empty() && boardedCount < lift.getMaxGroupSize()) {
            lift.firstAthleteInQueue();
            lift.incrementAthleteCounter();
            boardedCount++;
        }

        assertEquals(2, boardedCount, "Wagonik powinien zabrać wszystkich (2) sportowców.");
        assertEquals(2, lift.getAthleteCounter(), "Licznik przejazdów powinien wynosić 2.");
        assertTrue(lift.getQueue().empty(), "Kolejka powinna być teraz całkowicie pusta.");
    }

    @Test
    void testMaxQueueSizeStatistic() {
        for (int i = 0; i < 4; i++) {
            Athlete athlete = new LocalAthlete(5, 0.1, "", 0.5, 0.5, 0, mockResort, startTime, i, 0.1, 0.1);
            lift.addToQueue(athlete);
        }

        int boardedCount = 0;
        lift.updateQueueSum(startTime);
        while (!lift.getQueue().empty() && boardedCount < lift.getMaxGroupSize()) {
            lift.firstAthleteInQueue();
            lift.incrementAthleteCounter();
            boardedCount++;
        }

        Athlete lastAthlete = new LocalAthlete(5, 0.1, "", 0.5, 0.5, 0, mockResort, startTime, 99, 0.1, 0.1);
        lift.addToQueue(lastAthlete);

        assertEquals(4, lift.getQueue().getMaxSize(), "Maksymalna historyczna długość kolejki powinna wynosić 4.");
    }
}