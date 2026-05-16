package events;

import ski_resort.Node;
import athletes.Athlete;
import simulation.Time;

public class RunEnd extends ConnectionEnd {
    public RunEnd(Time time, Athlete athlete, Node station) {
        super(time, athlete, station);
    }

    public String getActionDescription() {
        return " skied";
    }
}