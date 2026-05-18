package simulation;

import ski_resort.SkiResort;
import ski_resort.SkiRun;
import ski_resort.Lift;
import athletes.Athlete;

import java.util.Locale;
import java.util.Scanner;

// A utility class responsible for reading and parsing the simulation input data, assuming the specified input format.

public class Parser {
    private static void readNodes(Scanner inputScanner, SkiResort skiResort) {
        for(int i = 0; i < skiResort.getStationCount(); i++) {
            String line = inputScanner.nextLine();
            Scanner lineScanner = new Scanner(line);

            int height = lineScanner.nextInt();
            int x = lineScanner.nextInt();
            int y = lineScanner.nextInt();

            if(lineScanner.hasNext())
                skiResort.addStation(height, x, y, "s", i);
            else
                skiResort.addStation(height, x, y, "", i);
        }
    }

    private static void readLifts(Scanner inputScanner, SkiResort skiResort, int n) {
        for(int i = 0; i < n; i++) {
            String line = inputScanner.nextLine();
            Scanner lineScanner = new Scanner(line);

            int station1Id = lineScanner.nextInt();
            int station2Id = lineScanner.nextInt();
            int timeInterval = lineScanner.nextInt();
            int maxGroupSize = lineScanner.nextInt();
            int liftTime = lineScanner.nextInt();

            skiResort.getStation(station1Id).addLift(new Lift(station1Id, station2Id, timeInterval, maxGroupSize, liftTime, skiResort, i));
        }
    }

    private static void readSkiRuns(Scanner inputScanner, SkiResort skiResort, int n) {
        for(int i = 0; i < n; i++) {
            String line = inputScanner.nextLine();
            Scanner lineScanner = new Scanner(line);
            lineScanner.useLocale(Locale.ENGLISH);

            int station1Id = lineScanner.nextInt();
            int station2Id = lineScanner.nextInt();
            int difficultyLevel = lineScanner.nextInt();
            int runTime = lineScanner.nextInt();
            double bassicAtractiveness = lineScanner.nextDouble();
            double bumpsResistance = lineScanner.nextDouble();

            skiResort.getStation(station1Id).addSkiRun(new SkiRun(station1Id, station2Id, difficultyLevel, runTime, bassicAtractiveness, bumpsResistance, skiResort, i));
        }
    }

    private static void readAthletes(Scanner inputScanner, Simulation simulation, int n) {
        Athlete[] athletes = new Athlete[0];

        for(int i = 0; i < n; i++) {
            String line = inputScanner.nextLine();
            Scanner lineScanner = new Scanner(line);
            lineScanner.useLocale(Locale.ENGLISH);

            int m = lineScanner.nextInt();
            int skillLevel = lineScanner.nextInt();
            double spontaneityCoefficient = lineScanner.nextDouble();
            String s;
            if(lineScanner.hasNext())
                s = "s";
            else
                s = "";

            line = inputScanner.nextLine();
            lineScanner = new Scanner(line);
            lineScanner.useLocale(Locale.ENGLISH);

            double skillAdjustmentWeight = lineScanner.nextDouble();
            double surfaceLevellingWeight = lineScanner.nextDouble();

            line = inputScanner.nextLine();
            lineScanner = new Scanner(line);

            int stationId = lineScanner.nextInt();
            Time time = new Time(lineScanner.next());
            int interval = 0;
            if(lineScanner.hasNextInt()) {
                interval = lineScanner.nextInt();
            }

            Athlete[] newAthletes = new Athlete[athletes.length + m];
            System.arraycopy(athletes, 0, newAthletes, 0, athletes.length);

            for(int j = 0; j < m; j++)
                newAthletes[athletes.length + j] = new Athlete(skillLevel, spontaneityCoefficient, s,
                        skillAdjustmentWeight, surfaceLevellingWeight, stationId, simulation.getSkiResort(),
                        time.addSeconds(j * interval), athletes.length + j);

            athletes = newAthletes;
        }

        simulation.setAthletes(athletes);
    }

    public void readData(Simulation simulation) {
        Scanner scannerWejscia = new Scanner(System.in);

        int n = scannerWejscia.nextInt();
        scannerWejscia.nextLine();
        simulation.setSkiResort(new SkiResort(n));
        readNodes(scannerWejscia, simulation.getSkiResort());
        if (scannerWejscia.hasNextLine()) scannerWejscia.nextLine();

        n = scannerWejscia.nextInt();
        scannerWejscia.nextLine();
        readLifts(scannerWejscia, simulation.getSkiResort(), n);
        if (scannerWejscia.hasNextLine()) scannerWejscia.nextLine();

        n = scannerWejscia.nextInt();
        scannerWejscia.nextLine();
        readSkiRuns(scannerWejscia, simulation.getSkiResort(), n);
        if (scannerWejscia.hasNextLine()) scannerWejscia.nextLine();

        n = scannerWejscia.nextInt();
        scannerWejscia.nextLine();
        readAthletes(scannerWejscia, simulation, n);
    }
}