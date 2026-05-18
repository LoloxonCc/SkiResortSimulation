import simulation.Time;
import simulation.Simulation;

public class Main {
    public static void main(String[] args) {
        Time simulationStart = new Time("09:00:00");
        Time comebackTime = new Time("15:00:00");
        Time simulationEnd = new Time("16:00:00");
        Simulation simulation = new Simulation(simulationStart, comebackTime, simulationEnd);
        simulation.simulate();
    }
}