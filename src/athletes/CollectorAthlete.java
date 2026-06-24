package athletes;

import simulation.Simulation;
import simulation.Time;
import ski_resort.*;

import java.util.*;

public class CollectorAthlete extends Athlete {
    private final Queue<Connection> tripPlan;

    public CollectorAthlete(int skillLevel, double spontaneityCoefficient, String tracked, double skillAdjustmentWeight,
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
        int minDistance = Integer.MAX_VALUE;

        for(Node node : simulation.getSkiResort().getStations()) {
            for(SkiSlope skiSlope : node.getSkiSlopes()) {

                List<Connection> path = bfs.findShortestPath(station, skiSlope.getStartingStation());

                int currentDistance = path.size();

                if(targetSkiSlope == null) {
                    targetSkiSlope = skiSlope;
                    minDistance = currentDistance;
                }
                else if(this.getDescentCount(skiSlope.getNumber()) < this.getDescentCount(targetSkiSlope.getNumber())) {
                    targetSkiSlope = skiSlope;
                    minDistance = currentDistance;
                }
                else if(this.getDescentCount(skiSlope.getNumber()) == this.getDescentCount(targetSkiSlope.getNumber())) {
                    if(currentDistance < minDistance) {
                        targetSkiSlope = skiSlope;
                        minDistance = currentDistance;
                    }
                    else if(currentDistance == minDistance) {
                        if (skiSlope.calculateCumulativeAttractiveness(this) > targetSkiSlope.calculateCumulativeAttractiveness(this)) {
                            targetSkiSlope = skiSlope;
                        }
                    }
                }
            }
        }

        if (targetSkiSlope != null) {
            List<Connection> finalPath = bfs.findShortestPath(station, targetSkiSlope.getStartingStation());
            if (finalPath != null) {
                tripPlan.addAll(finalPath);
            }
            tripPlan.add(targetSkiSlope);
        }
    }
}