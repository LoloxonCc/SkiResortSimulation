package athletes;

import ski_resort.Connection;
import ski_resort.Lift;
import ski_resort.Node;
import ski_resort.SkiSlope;

import java.util.*;

/*
    Class that implements BFS logic. Used by GreedyAthlete and CollectorAthlete to prepare trip plan.
 */

public class BfsAlgorithm {
    public List<Connection> findShortestPath(Node start, Node target) {
        if(start.getNumber() == target.getNumber()) {
            return new ArrayList<>();
        }

        Queue<Node> queue = new ArrayDeque<>();
        Map<Node, Boolean> visited = new HashMap<>();
        Map<Node, Connection> parentEdge = new HashMap<>();

        queue.add(start);
        visited.put(start, true);

        while(!queue.isEmpty()) {
            Node current = queue.poll();

            if(current.getNumber() == target.getNumber()) {
                break;
            }

            for(SkiSlope slope : current.getSkiSlopes()) {
                Node neighbor = slope.getEndingStation();
                if (!visited.getOrDefault(neighbor, false)) {
                    visited.put(neighbor, true);
                    parentEdge.put(neighbor, slope);
                    queue.add(neighbor);
                }
            }

            for(Lift lift : current.getLifts()) {
                Node neighbor = lift.getEndingStation();
                if (!visited.getOrDefault(neighbor, false)) {
                    visited.put(neighbor, true);
                    parentEdge.put(neighbor, lift);
                    queue.add(neighbor);
                }
            }
        }

        if(!visited.getOrDefault(target, false)) {
            return null;
        }

        LinkedList<Connection> path = new LinkedList<>();
        Node current = target;
        while(current.getNumber() != start.getNumber()) {
            Connection edge = parentEdge.get(current);
            path.addFirst(edge);
            current = edge.getStartingStation();
        }

        return path;
    }
}