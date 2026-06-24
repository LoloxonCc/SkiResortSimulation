import simulation.Parser;
import simulation.Time;
import simulation.Simulation;

public class Main {
    public static void main(String[] args) {
        if(args.length != 1) {
            System.err.println("Usage: java -jar Main.jar <input file>");
            System.exit(1);
        }
        Time simulationStart = new Time("09:00:00");
        Time comebackTime = new Time("15:00:00");
        Time simulationEnd = new Time("16:00:00");
        Parser parser = new Parser();
        System.out.println("Provide input data for a ski resort simulation:");
        Simulation simulation = parser.readData(simulationStart, comebackTime, simulationEnd, args[0]);
        try {
            simulation.simulate();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}