import kadra.mapki.pliki.WyjatekSystemuPlikow;
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
        try {
            System.out.println("Provide input data for a ski resort simulation:");
            Simulation simulation = parser.readData(simulationStart, comebackTime, simulationEnd, args[0]);

            simulation.simulate();

        } catch (WyjatekSystemuPlikow e) {
            System.err.println("File System Error: Catalog or file could not be created.");
            System.err.println("Check validity of path and whether you have permission to use it.");
            e.printStackTrace();

        } catch (Exception e) {
            System.err.println("Critical Error: There was unexpected error in the program logic.");
            System.err.println("Report it to the developer team with the following stack trace:");
            e.printStackTrace();
        }
    }
}