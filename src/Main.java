import simulation.Time;
import simulation.Simulation;

public class Main {
    public static void main(String[] args) {
        Simulation simulation = new Simulation(new Time("09:00:00"), new Time("15:00:00"), new Time("16:00:00") );
        simulation.simulate();
    }
}