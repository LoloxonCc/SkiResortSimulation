package athletes;

import simulation.Simulation;
import simulation.Time;
import ski_resort.*;

import java.util.ArrayDeque;
import java.util.Queue;

public class GreedyAthlete extends Athlete {
    private final Queue<Connection> tripPlan;

    public GreedyAthlete(int skillLevel, double spontaneityCoefficient, String tracked, double skillAdjustmentWeight,
                        double surfaceLevellingWeight, int stationId, SkiResort skiResort, Time startTime, int number,
                        double boredomCoefficient, double boredomWeight) {
        super(skillLevel, spontaneityCoefficient, tracked, skillAdjustmentWeight, surfaceLevellingWeight, stationId,
                skiResort, startTime, number, boredomCoefficient, boredomWeight);
        tripPlan = new ArrayDeque<>();
    }

    public void decision(Time time, Node station, Simulation simulation) {
        if(!time.isEarlierThan(simulation.getComebackTime()))
            return;
        if(tripPlan.isEmpty())
            prepareTripPlan(station, simulation);
        if(!tripPlan.isEmpty())
            tripPlan.poll().scheduleEvent(simulation, time, this);
    }

    public void prepareTripPlan(Node station, Simulation simulation) {
        boolean[] visited = new boolean[simulation.getSkiResort().getTotalConnectionsCount()];
        Connection[] parent = new Connection[simulation.getSkiResort().getTotalConnectionsCount()];
        Queue<Connection> queueBFS = new ArrayDeque<>();
        for(SkiSlope skiSlope : station.getSkiSlopes()) {
            queueBFS.add(skiSlope);
            visited[skiSlope.getNumber()] = true;
            parent[skiSlope.getNumber()] = null;
        }
        for(Lift lift : station.getLifts()) {
            queueBFS.add(lift);
            visited[lift.getNumber()] = true;
            parent[lift.getNumber()] = null;
        }

        while(!queueBFS.isEmpty()) {
            Connection connection = queueBFS.poll();
            Node nextStation = connection.getEndingStation();

            for(SkiSlope skiSlope : nextStation.getSkiSlopes()) {
                if(!visited[skiSlope.getNumber()]) {
                    queueBFS.add(skiSlope);
                    visited[skiSlope.getNumber()] = true;
                    parent[skiSlope.getNumber()] = connection;
                }
            }
            for(Lift lift : nextStation.getLifts()) {
                if(!visited[lift.getNumber()]) {
                    queueBFS.add(lift);
                    visited[lift.getNumber()] = true;
                    parent[lift.getNumber()] = connection;
                }
            }
        }

        SkiSlope targetSkiSlope = null;
        for(Node node : simulation.getSkiResort().getStations()) {
            for(SkiSlope skiSlope : node.getSkiSlopes()) {
                if(targetSkiSlope == null)
                    targetSkiSlope = skiSlope;
                else if(skiSlope.calculateCumulativeAttractiveness(this) > targetSkiSlope.calculateCumulativeAttractiveness(this))
                    targetSkiSlope = skiSlope;
            }
        }

        ArrayDeque<Connection> path = new ArrayDeque<>();
        Connection current = targetSkiSlope;

        while (current != null) {
            path.addFirst(current);
            current = parent[current.getNumber()];
        }

        tripPlan.addAll(path);
    }
}
