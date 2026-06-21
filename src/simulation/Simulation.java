package simulation;

import events.Event;
import events.LiftWorkStart;
import events.AthleteArrival;
import queues.EventPair;
import ski_resort.SkiResort;
import ski_resort.SkiSlope;
import ski_resort.Lift;
import ski_resort.Node;
import athletes.Athlete;

import java.util.PriorityQueue;
import java.util.Random;

// The core class of the ski resort simulation. Manages its global state and connect all parts of the system.

public class Simulation {
    private final SkiResort skiResort;
    private final Athlete[] athletes;
    private final Time simulationStartTime;
    private final Time comebackTime;
    private final Time simulationEndTime;
    private final Random generator;
    private final PriorityQueue<EventPair> eventQueue;
    private long eventCounter;

    public Simulation(Time simulationStartTime, Time comebackTime, Time simulationEndTime, SkiResort skiResort, Athlete[] athletes) {
        this.simulationStartTime = simulationStartTime;
        this.simulationEndTime = simulationEndTime;
        this.comebackTime = comebackTime;
        this.generator = new Random();
        this.eventQueue = new PriorityQueue<>();
        this.skiResort = skiResort;
        this.athletes = athletes;
        this.eventCounter = 0;
    }

    public SkiResort getSkiResort() {
        return skiResort;
    }

    public Time getSimulationStartTime() {
        return simulationStartTime;
    }

    public Time getSimulationEndTime() {
        return simulationEndTime;
    }

    public Time getComebackTime() {
        return comebackTime;
    }

    public Random getGenerator() {
        return generator;
    }

    public void addEvent(Event event) {
        eventQueue.add(new EventPair(event, eventCounter++));
    }

    // Method responsible for printing statistics for all lifts and ski runs.
    public void endMessage() {
        System.out.println("End message:");
        for(Node station : skiResort.getStations())
            for(SkiSlope skiSlope : station.getSkiSlopes())
                System.out.println(skiSlope);

        for(Node station : skiResort.getStations())
            for (Lift lift : station.getLifts())
                System.out.println(lift.toString(this));
    }

    public void simulate() {
        System.out.println(simulationStartTime + ": Simulation started!");

        // Initialization of athletes arrivals and first lift departures.
        for(Athlete athlete : athletes)
            addEvent(new AthleteArrival(athlete.getStartTime(), athlete));
        for(Node station : skiResort.getStations())
            addEvent(new LiftWorkStart(simulationStartTime, station.getLifts()));

        while(!eventQueue.isEmpty())
            getNextEvent().perform(this);

        System.out.println(simulationEndTime + ": Simulation ended!");
        endMessage();
    }

    public Event getNextEvent() {
        EventPair nextPair = eventQueue.poll();

        if (nextPair != null)
            return nextPair.getEvent();

        return null;
    }
}