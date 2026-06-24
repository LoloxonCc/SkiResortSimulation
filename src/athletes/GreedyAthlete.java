package athletes;

import simulation.Simulation;
import simulation.Time;
import ski_resort.*;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
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

        if(tripPlan.isEmpty()) {
            double chance = simulation.getGenerator().nextDouble();

            if(chance < spontaneityCoefficient) {
                Connection choice = this.spontaneousChoice(simulation.getGenerator(), station);
                choice.scheduleEvent(simulation, time, this);
                return;
            }
            else {
                prepareTripPlan(station, simulation);
            }
        }

        if(!tripPlan.isEmpty())
            tripPlan.poll().scheduleEvent(simulation, time, this);
    }

    public void prepareTripPlan(Node station, Simulation simulation) {
        Map<Connection, Boolean> visited = new HashMap<>();
        Map<Connection, Connection> parent = new HashMap<>();

        Queue<Connection> queueBFS = new ArrayDeque<>();

        for(SkiSlope skiSlope : station.getSkiSlopes()) {
            queueBFS.add(skiSlope);
            visited.put(skiSlope, true);
            parent.put(skiSlope, null);
        }
        for(Lift lift : station.getLifts()) {
            queueBFS.add(lift);
            visited.put(lift, true);
            parent.put(lift, null);
        }

        while(!queueBFS.isEmpty()) {
            Connection connection = queueBFS.poll();
            Node nextStation = connection.getEndingStation();

            for(SkiSlope skiSlope : nextStation.getSkiSlopes()) {
                if(!visited.getOrDefault(skiSlope, false)) {
                    queueBFS.add(skiSlope);
                    visited.put(skiSlope, true);
                    parent.put(skiSlope, connection);
                }
            }
            for(Lift lift : nextStation.getLifts()) {
                if(!visited.getOrDefault(lift, false)) {
                    queueBFS.add(lift);
                    visited.put(lift, true);
                    parent.put(lift, connection);
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
            current = parent.get(current);
        }

        tripPlan.addAll(path);
    }
}
