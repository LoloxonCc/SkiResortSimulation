package athletes;

import simulation.Simulation;
import simulation.Time;
import ski_resort.*;

import java.util.*;

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
        BfsAlgorithm bfs = new BfsAlgorithm();
        SkiSlope targetSkiSlope = null;

        for(Node node : simulation.getSkiResort().getStations()) {
            for(SkiSlope skiSlope : node.getSkiSlopes()) {
                if(targetSkiSlope == null) {
                    targetSkiSlope = skiSlope;
                } else if(skiSlope.calculateCumulativeAttractiveness(this) > targetSkiSlope.calculateCumulativeAttractiveness(this)) {
                    targetSkiSlope = skiSlope;
                }
            }
        }

        if (targetSkiSlope != null) {
            List<Connection> path = bfs.findShortestPath(station, targetSkiSlope.getStartingStation());
            if (path != null) {
                tripPlan.addAll(path);
            }
            tripPlan.add(targetSkiSlope);
        }
    }
}
