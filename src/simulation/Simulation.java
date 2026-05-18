package simulation;

import events.Event;
import events.LiftWorkStart;
import events.AthleteArrival;
import queues.EventQueue;
import queues.EventQueueList;
import ski_resort.SkiResort;
import ski_resort.SkiRun;
import ski_resort.Lift;
import ski_resort.Node;
import athletes.Athlete;

import java.util.Random;

// The core class of the ski resort simulation. Manages its global state and connect all parts of the system.

public class Simulation {
    private SkiResort skiResort;
    private Athlete[] athletes;
    private final Time simulationStartTime;
    private final Time comebackTime;
    private final Time simulationEndTime;
    private final Random generator;
    private final EventQueue eventQueue;
    private final Parser parser;

    public Simulation(Time simulationStartTime, Time comebackTime, Time koniecSymulacji) {
        this.simulationStartTime = simulationStartTime;
        this.simulationEndTime = koniecSymulacji;
        this.comebackTime = comebackTime;
        this.generator = new Random();
        this.eventQueue = new EventQueueList();
        this.parser = new Parser();
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

    public void setSkiResort(SkiResort skiResort) {
        this.skiResort = skiResort;
    }

    public void setAthletes(Athlete[] athletes) {
        this.athletes = athletes;
    }

    public void addEvent(Event event) {
        eventQueue.add(event);
    }

    // Method responsible for printing statistics for all lifts and ski runs.
    public void endMessage() {
        System.out.println("End message:");
        for(Node station : skiResort.getStations())
            for(SkiRun skiRun : station.getSkiRuns())
                System.out.println(skiRun);

        for(Node station : skiResort.getStations())
            for (Lift lift : station.getLifts())
                System.out.println(lift);
    }

    public void simulate() {
        System.out.println("Provide input data for a ski resort simulation:");
        parser.readData(this);

        System.out.println(simulationStartTime + ": Simulation started!");

        // Initialization of athletes arrivals and first lift departures.
        for(Athlete athlete : athletes)
            addEvent(new AthleteArrival(athlete.getStartTime(), athlete));
        for(Node station : skiResort.getStations())
            addEvent(new LiftWorkStart(simulationStartTime, station.getLifts()));

        while(!eventQueue.empty())
            eventQueue.first().perform(this);

        System.out.println(simulationEndTime + ": Simulation ended!");
        endMessage();
    }
}