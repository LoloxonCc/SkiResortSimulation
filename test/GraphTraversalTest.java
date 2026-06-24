import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import simulation.Time;
import ski_resort.Connection;
import ski_resort.Lift;
import ski_resort.Node;
import ski_resort.SkiResort;
import ski_resort.SkiSlope;
import athletes.BfsAlgorithm;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class GraphTraversalTest {

    private SkiResort resort;
    private BfsAlgorithm bfs;
    private Node n0, n1, n2, n3, n4, n5;

    @BeforeEach
    void setUp() {
        n0 = new Node(0, 0, 0, 0, "");
        n1 = new Node(100, 0, 0, 1, "");
        n2 = new Node(200, 0, 0, 2, "");
        n3 = new Node(300, 0, 0, 3, "");
        n4 = new Node(200, 0, 0, 4, "");
        n5 = new Node(400, 0, 0, 5, "");

        Node[] nodes = {n0, n1, n2, n3, n4, n5};
        resort = new SkiResort(nodes);
        bfs = new BfsAlgorithm();
        Time t = new Time("09:00:00");

        n0.addLift(new Lift(0, 1, 10, 2, 100, resort, 0, t)); // 0 -> 1 (w)

        n1.addSkiSlope(new SkiSlope(1, 0, 5, 100, 0.5, 0.5, resort, 1)); // 1 -> 0 (t)
        n1.addSkiSlope(new SkiSlope(1, 2, 5, 100, 0.5, 0.5, resort, 2)); // 1 -> 2 (t)

        n2.addSkiSlope(new SkiSlope(2, 0, 5, 100, 0.5, 0.5, resort, 3)); // 2 -> 0 (t)
        n2.addLift(new Lift(2, 3, 10, 2, 100, resort, 4, t)); // 2 -> 3 (w)
        n2.addLift(new Lift(2, 4, 10, 2, 100, resort, 5, t)); // 2 -> 4 (w)

        n3.addSkiSlope(new SkiSlope(3, 1, 5, 100, 0.5, 0.5, resort, 6)); // 3 -> 1 (t)
        n3.addSkiSlope(new SkiSlope(3, 4, 5, 100, 0.5, 0.5, resort, 7)); // 3 -> 4 (t)
        n3.addLift(new Lift(3, 5, 10, 2, 100, resort, 8, t)); // 3 -> 5 (w)

        n4.addLift(new Lift(4, 5, 10, 2, 100, resort, 9, t)); // 4 -> 5 (w)
        n4.addSkiSlope(new SkiSlope(4, 2, 5, 100, 0.5, 0.5, resort, 10)); // 4 -> 2 (t) - dodane dla drugiej ścieżki

        n5.addSkiSlope(new SkiSlope(5, 3, 5, 100, 0.5, 0.5, resort, 11)); // 5 -> 3 (t)
        n5.addSkiSlope(new SkiSlope(5, 4, 5, 100, 0.5, 0.5, resort, 12)); // 5 -> 4 (t)
    }

    @Test
    void testPathFrom0To4() {
        List<Connection> path = bfs.findShortestPath(n0, n4);
        assertNotNull(path);
        assertEquals(3, path.size(), "Odległość z 0 do 4 powinna wynosić 3");
        assertEquals(0, path.get(0).getStartingStation().getNumber());
        assertEquals(1, path.get(1).getStartingStation().getNumber());
        assertEquals(2, path.get(2).getStartingStation().getNumber());
        assertEquals(4, path.get(2).getEndingStation().getNumber());
    }

    @Test
    void testDirectPathFrom3To1() {
        List<Connection> path = bfs.findShortestPath(n3, n1);
        assertNotNull(path);
        assertEquals(1, path.size(), "Odległość z 3 do 1 powinna wynosić 1");
        assertEquals(3, path.getFirst().getStartingStation().getNumber());
        assertEquals(1, path.getFirst().getEndingStation().getNumber());
    }

    @Test
    void testEmptyPathFrom2To2() {
        List<Connection> path = bfs.findShortestPath(n2, n2);
        assertNotNull(path);
        assertTrue(path.isEmpty(), "Ścieżka z 2 do 2 powinna być pusta (odległość 0)");
    }

    @Test
    void testPathFrom4To3() {
        List<Connection> path = bfs.findShortestPath(n4, n3);
        assertNotNull(path);
        assertEquals(2, path.size(), "Odległość z 4 do 3 powinna wynosić 2");
        assertEquals(3, path.get(1).getEndingStation().getNumber(), "Ostatni węzeł to stacja 3");
    }
}