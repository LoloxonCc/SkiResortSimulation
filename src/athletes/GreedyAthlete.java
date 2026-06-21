package athletes;

import simulation.Simulation;
import simulation.Time;
import ski_resort.Connection;
import ski_resort.Node;
import ski_resort.SkiResort;

import java.util.LinkedList;
import java.util.Queue;

public class GreedyAthlete extends Athlete {
    private final Queue<Connection> tripPlan;

    public GreedyAthlete(int skillLevel, double spontaneityCoefficient, String tracked, double skillAdjustmentWeight,
                        double surfaceLevellingWeight, int stationId, SkiResort skiResort, Time startTime, int number,
                        double boredomCoefficient, double boredomWeight) {
        super(skillLevel, spontaneityCoefficient, tracked, skillAdjustmentWeight, surfaceLevellingWeight, stationId,
                skiResort, startTime, number, boredomCoefficient, boredomWeight);
        tripPlan = new LinkedList<>();
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

    }
}
