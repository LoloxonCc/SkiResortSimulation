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

    private SkiResort skiResort;
    private BfsAlgorithm bfs;
    private Node n0, n1, n2, n3, n4, n5;

    @BeforeEach
    void setUp() {
        n0 = new Node(0, 0, 0, 0, "");
        n1 = new Node(1, 1, 1, 1, "");
        n2 = new Node(2, 2, 2, 2, "");
        n3 = new Node(3, 3, 3, 3, "");
        n4 = new Node(4, 4, 4, 4, "");
        n5 = new Node(5, 5, 5, 5, "");

        Node[] nodes = {n0, n1, n2, n3, n4, n5};
        skiResort = new SkiResort(nodes);
        bfs = new BfsAlgorithm();
        Time t = new Time("09:00:00");

        n0.addLift(new Lift(0, 1, 10, 2, 100, skiResort, 0, t));

        n1.addSkiSlope(new SkiSlope(1, 0, 5, 100, 0.5, 0.5, skiResort, 1));
        n1.addSkiSlope(new SkiSlope(1, 2, 5, 100, 0.5, 0.5, skiResort, 2));

        n2.addSkiSlope(new SkiSlope(2, 0, 5, 100, 0.5, 0.5, skiResort, 3));
        n2.addLift(new Lift(2, 3, 10, 2, 100, skiResort, 4, t));
        n2.addLift(new Lift(2, 4, 10, 2, 100, skiResort, 5, t));

        n3.addSkiSlope(new SkiSlope(3, 1, 5, 100, 0.5, 0.5, skiResort, 6));
        n3.addSkiSlope(new SkiSlope(3, 4, 5, 100, 0.5, 0.5, skiResort, 7));

        n4.addLift(new Lift(4, 5, 10, 2, 100, skiResort, 9, t));

        n5.addSkiSlope(new SkiSlope(5, 3, 5, 100, 0.5, 0.5, skiResort, 11));
        n5.addSkiSlope(new SkiSlope(5, 4, 5, 100, 0.5, 0.5, skiResort, 12));
    }

    @Test
    void testPathFrom0To4() {
        List<Connection> path = bfs.findShortestPath(n0, n4);
        assertNotNull(path);
        assertEquals(3, path.size(), "Distance from 0 to 4 should be 3.");
        assertEquals(0, path.get(0).getStartingStation().getNumber());
        assertEquals(1, path.get(1).getStartingStation().getNumber());
        assertEquals(2, path.get(2).getStartingStation().getNumber());
        assertEquals(4, path.get(2).getEndingStation().getNumber());
    }

    @Test
    void testDirectPathFrom3To1() {
        List<Connection> path = bfs.findShortestPath(n3, n1);
        assertNotNull(path);
        assertEquals(1, path.size(), "Distance from 3 to 1 should be 1.");
        assertEquals(3, path.getFirst().getStartingStation().getNumber());
        assertEquals(1, path.getFirst().getEndingStation().getNumber());
    }

    @Test
    void testEmptyPathFrom2To2() {
        List<Connection> path = bfs.findShortestPath(n2, n2);
        assertNotNull(path);
        assertTrue(path.isEmpty(), "Path should be empty.");
    }

    @Test
    void testPathFrom4To3() {
        List<Connection> path = bfs.findShortestPath(n4, n3);
        assertNotNull(path);
        assertEquals(2, path.size(), "Distance from 4 to 3 should be 2.");
        assertEquals(3, path.get(1).getEndingStation().getNumber(), "Last node should be 3.");
    }
}