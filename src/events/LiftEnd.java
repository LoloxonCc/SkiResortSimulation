package events;

import ski_resort.Node;
import athletes.Athlete;
import simulation.Time;

public class LiftEnd extends ConnectionEnd {
    public LiftEnd(Time time, Athlete athlete, Node station) {
        super(time, athlete, station);
    }

     public String getActionName() {
        return " finished lift";
     }
}