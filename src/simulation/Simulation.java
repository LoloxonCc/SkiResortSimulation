package simulation;

import events.Event;
import events.LiftWorkStart;
import events.AthleteArrival;
import kadra.mapki.GeneratorMapek;
import kadra.mapki.pliki.WyjatekSystemuPlikow;
import kadra.mapki.styl.GruboscKonturu;
import kadra.mapki.styl.StylKrawedzi;
import kadra.mapki.styl.StylLinii;
import kadra.mapki.styl.StylWezla;
import queues.EventPair;
import ski_resort.SkiResort;
import ski_resort.SkiSlope;
import ski_resort.Lift;
import ski_resort.Node;
import athletes.Athlete;

import java.util.*;
import java.util.stream.Collectors;

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
    private final String mapsPath;

    public Simulation(Time simulationStartTime, Time comebackTime, Time simulationEndTime, SkiResort skiResort, Athlete[] athletes, String mapsPath) {
        this.simulationStartTime = simulationStartTime;
        this.simulationEndTime = simulationEndTime;
        this.comebackTime = comebackTime;
        this.generator = new Random();
        this.eventQueue = new PriorityQueue<>();
        this.skiResort = skiResort;
        this.athletes = athletes;
        this.eventCounter = 0;
        this.mapsPath = mapsPath;
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
    private void endMessage() {
        System.out.println("End message:");

        System.out.println("Slopes statistics:");
        ArrayList<SkiSlope> skiSlopes = new ArrayList<>();
        for(Node station : skiResort.getStations())
            skiSlopes.addAll(Arrays.asList(station.getSkiSlopes()));
        skiSlopes.sort(Comparator.comparingInt(SkiSlope::getNumber));
        for(SkiSlope skiSlope : skiSlopes)
            System.out.println(skiSlope.toString());

        System.out.println("Lifts statistics:");
        ArrayList<Lift> lifts = new ArrayList<>();
        for(Node station : skiResort.getStations())
            lifts.addAll(Arrays.asList(station.getLifts()));
        lifts.sort(Comparator.comparingInt(Lift::getNumber));
        for(Lift lift : lifts)
            System.out.println(lift.toString(this));
    }

    public void simulate() throws WyjatekSystemuPlikow {
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
        visualise();
    }

    public Event getNextEvent() {
        EventPair nextPair = eventQueue.poll();

        if (nextPair != null)
            return nextPair.getEvent();

        return null;
    }

    private void visualise() throws WyjatekSystemuPlikow {
        GeneratorMapek generatorMapek = new GeneratorMapek(mapsPath);
        firstMap(generatorMapek);
        secondMap(generatorMapek);

        ArrayList<Athlete> trackedAthletes = Arrays.stream(athletes)
                .filter(Athlete::isTracked)
                .collect(Collectors.toCollection(ArrayList::new));

        for(Athlete athlete : trackedAthletes)
            thirdMap(generatorMapek, athlete);
    }

    private void firstMap(GeneratorMapek generatorMapek) throws WyjatekSystemuPlikow {
        generatorMapek.zeruj();

        for(Node station : skiResort.getStations()) {
            if(station.isConnected())
                generatorMapek.dodajWezel(station.getNumber(), station.getX(), station.getY(), new StylWezla(GruboscKonturu.POGRUBIONY));
            else
                generatorMapek.dodajWezel(station.getNumber(), station.getX(), station.getY(), new StylWezla(GruboscKonturu.ZWYKLY));
        }

        for(Node station : skiResort.getStations()) {
            for(SkiSlope skiSlope : station.getSkiSlopes()) {
                generatorMapek.dodajKrawedz(station.getNumber(), skiSlope.getEndingStation().getNumber(),
                        new StylKrawedzi(StylLinii.CIAGLA), skiSlope.parametersMapString());
            }

            for(Lift lift : station.getLifts()) {
                generatorMapek.dodajKrawedz(station.getNumber(), lift.getEndingStation().getNumber(),
                        new StylKrawedzi(StylLinii.PRZERYWANA), lift.parametersMapString());
            }
        }

        generatorMapek.tworzMapke("mapka1.tex");
        System.out.println("First map created.");
    }

    private void secondMap(GeneratorMapek generatorMapek) throws WyjatekSystemuPlikow {
        generatorMapek.zeruj();

        for(Node station : skiResort.getStations()) {
            if(station.isConnected())
                generatorMapek.dodajWezel(station.getNumber(), station.getX(), station.getY(), new StylWezla(GruboscKonturu.POGRUBIONY));
            else
                generatorMapek.dodajWezel(station.getNumber(), station.getX(), station.getY(), new StylWezla(GruboscKonturu.ZWYKLY));
        }

        for(Node station : skiResort.getStations()) {
            for(SkiSlope skiSlope : station.getSkiSlopes()) {
                generatorMapek.dodajKrawedz(station.getNumber(), skiSlope.getEndingStation().getNumber(),
                        new StylKrawedzi(StylLinii.CIAGLA), skiSlope.statisticsMapString());
            }

            for(Lift lift : station.getLifts()) {
                generatorMapek.dodajKrawedz(station.getNumber(), lift.getEndingStation().getNumber(),
                        new StylKrawedzi(StylLinii.PRZERYWANA), lift.statisticsMapString(this));
            }
        }

        generatorMapek.tworzMapke("mapka2.tex");
        System.out.println("Second map created.");
    }

    private void thirdMap(GeneratorMapek generatorMapek, Athlete athlete) throws WyjatekSystemuPlikow {
        generatorMapek.zeruj();

        for(Node station : skiResort.getStations()) {
            if(station.isConnected())
                generatorMapek.dodajWezel(station.getNumber(), station.getX(), station.getY(), new StylWezla(GruboscKonturu.POGRUBIONY));
            else
                generatorMapek.dodajWezel(station.getNumber(), station.getX(), station.getY(), new StylWezla(GruboscKonturu.ZWYKLY));
        }

        for(Node station : skiResort.getStations()) {
            for(Lift lift : station.getLifts()) {
                String report = "w" + lift.getNumber() + "(" + athlete.getLiftCount(lift.getNumber()) + "):"
                        + athlete.getLiftReport(lift.getNumber());
                generatorMapek.dodajKrawedz(station.getNumber(), lift.getEndingStation().getNumber(),
                        new StylKrawedzi(StylLinii.PRZERYWANA), report);
            }
            for(SkiSlope skiSlope : station.getSkiSlopes()) {
                String report = "t" + skiSlope.getNumber() + "(" + athlete.getSlopeCount(skiSlope.getNumber()) + "):"
                        + athlete.getSlopeReport(skiSlope.getNumber());
                generatorMapek.dodajKrawedz(station.getNumber(), skiSlope.getEndingStation().getNumber(),
                        new StylKrawedzi(StylLinii.CIAGLA), report);
            }
        }

        generatorMapek.tworzMapke("mapka3_" + athlete.getNumber() + ".tex");
        System.out.println("Map created for an athlete " + athlete.getNumber() + ".");
    }
}