package athletes;

import simulation.Simulation;
import simulation.Time;
import ski_resort.*;

import java.util.Random;

public class LocalAthlete extends Athlete {
    public LocalAthlete(int skillLevel, double spontaneityCoefficient, String tracked, double skillAdjustmentWeight,
                   double surfaceLevellingWeight, int stationId, SkiResort skiResort, Time startTime, int number,
                   double boredomCoefficient, double boredomWeight) {
        super(skillLevel, spontaneityCoefficient, tracked, skillAdjustmentWeight, surfaceLevellingWeight, stationId,
                skiResort, startTime, number, boredomCoefficient, boredomWeight);
    }

    public void decision(Time time, Node station, Simulation simulation) {
        // Athlete can make decision only before comebackTime (in this case 15:00:00).
        // Otherwise, he returns to his startingStation (which is not simulated).
        if(!time.isEarlierThan(simulation.getComebackTime()))
            return;

        double chance = simulation.getGenerator().nextDouble();
        Connection choice;

        if(chance < spontaneityCoefficient)
            choice = this.spontaneousChoice(simulation.getGenerator(), station);
        else
            choice = this.findBestConnection(station, simulation.getGenerator());

        choice.scheduleEvent(simulation, time, this);
    }

    private Lift chooseRandomLift(Random generator, Node station) {
        int liftID = generator.nextInt(0, station.getLifts().length);
        return station.getLifts()[liftID];
    }

    // Method for finding best route for the local athlete by conditions given in project description.
    // It checks ski runs that start from the station and those reachable by one lift ride and chooses the one
    // that suits the athlete best.
    // Method returns connection that the athlete should use, it is either ski run (if he chose one starting from this
    // station) or a lift (if he chose one starting from upper station).
    private Connection findBestReachableRoute(Node station) {
        Connection bestConnection = null;
        double maxAttractiveness = -1.0;

        // Checks ski runs starting from the station
        for(SkiSlope skiSlope : station.getSkiSlopes()) {
            double atr = skiSlope.calculateCumulativeAttractiveness(this);
            if (atr > maxAttractiveness) {
                maxAttractiveness = atr;
                bestConnection = skiSlope;
            }
        }

        // Checks ski runs reachable by one lift ride.
        for(Lift lift : station.getLifts()) {
            Node endingStation = lift.getEndingStation();
            for(SkiSlope t : endingStation.getSkiSlopes()) {
                double atr = t.calculateCumulativeAttractiveness(this);
                if (atr > maxAttractiveness) {
                    maxAttractiveness = atr;
                    bestConnection = lift;
                }
            }
        }

        return bestConnection;
    }

    public Connection findBestConnection(Node station, Random generator) {
        Connection bestConnection = findBestReachableRoute(station);

        if(bestConnection == null)
            // If there are no ski runs from this station and those that can be reached by lifts
            // then athlete chooses a random lift
            return this.chooseRandomLift(generator, station);

        return bestConnection;
    }

    // Sometimes athlete makes a spontaneous choice of a connection that he will use.
    public Connection spontaneousChoice(Random generator, Node station) {
        int chosenConnectionId = generator.nextInt(0, station.getLifts().length + station.getSkiSlopes().length);
        if(chosenConnectionId < station.getLifts().length)
            return station.getLifts()[chosenConnectionId];
        else
            return station.getSkiSlopes()[chosenConnectionId - station.getLifts().length];
    }
}
