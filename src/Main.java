import simulation.Parser;
import simulation.Time;
import simulation.Simulation;

public class Main {
    public static void main(String[] args) {
        Time simulationStart = new Time("09:00:00");
        Time comebackTime = new Time("15:00:00");
        Time simulationEnd = new Time("16:00:00");
        Parser parser = new Parser();
        System.out.println("Provide input data for a ski resort simulation:");
        Simulation simulation = parser.readData(simulationStart, comebackTime, simulationEnd);
        simulation.simulate();
    }
}