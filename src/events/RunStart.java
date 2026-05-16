package events;

import ski_resort.SkiRun;
import athletes.Athlete;
import simulation.Simulation;
import simulation.Time;

public class RunStart extends AthleteEvent {
    private final SkiRun skiRun;

    public RunStart(Time time, Athlete athlete, SkiRun skiRun) {
        super(time, athlete);
        this.skiRun = skiRun;
    }

    public void perform(Simulation simulation) {
        skiRun.incrementRunCounter();

        simulation.addEvent(new RunEnd(new Time(time.addSeconds(skiRun.getRunTime())), athlete, skiRun.getEndingStation()));

        if(athlete.isTracked())
            System.out.println(message());
    }

    public String message() {
        return super.message() + " entered the ski run " + skiRun.getNumber() + ".";
    }
}