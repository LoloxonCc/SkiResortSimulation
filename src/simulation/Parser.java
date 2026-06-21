package simulation;

import athletes.CollectorAthlete;
import athletes.GreedyAthlete;
import athletes.LocalAthlete;
import ski_resort.Node;
import ski_resort.SkiResort;
import ski_resort.SkiSlope;
import ski_resort.Lift;
import athletes.Athlete;

import java.util.Locale;
import java.util.Scanner;

// A utility class responsible for reading and parsing the simulation input data, assuming the specified input format.

public class Parser {
    private static Node[] readNodes(Scanner inputScanner, int n) {
        Node[] stations = new Node[n];
        for(int i = 0; i < n; i++) {
            String line = inputScanner.nextLine();
            Scanner lineScanner = new Scanner(line);

            int height = lineScanner.nextInt();
            int x = lineScanner.nextInt();
            int y = lineScanner.nextInt();

            if(lineScanner.hasNext())
                stations[i] = new Node(height, x, y, i, "s");
            else
                stations[i] = new Node(height, x, y, i, "");
            lineScanner.close();
        }
        return stations;
    }

    private static void readLifts(Scanner inputScanner, SkiResort skiResort, int n, Time simulationStartTime) {
        for(int i = 0; i < n; i++) {
            String line = inputScanner.nextLine();
            Scanner lineScanner = new Scanner(line);

            int station1Id = lineScanner.nextInt();
            int station2Id = lineScanner.nextInt();
            int timeInterval = lineScanner.nextInt();
            int maxGroupSize = lineScanner.nextInt();
            int liftTime = lineScanner.nextInt();

            skiResort.getStation(station1Id).addLift(new Lift(station1Id, station2Id, timeInterval, maxGroupSize, liftTime, skiResort, i, simulationStartTime));
            lineScanner.close();
        }
    }

    private static void readSkiSlopes(Scanner inputScanner, SkiResort skiResort, int n) {
        for(int i = 0; i < n; i++) {
            String line = inputScanner.nextLine();
            Scanner lineScanner = new Scanner(line);
            lineScanner.useLocale(Locale.ENGLISH);

            int station1Id = lineScanner.nextInt();
            int station2Id = lineScanner.nextInt();
            int difficultyLevel = lineScanner.nextInt();
            int runTime = lineScanner.nextInt();
            double basicAttractiveness = lineScanner.nextDouble();
            double bumpsResistance = lineScanner.nextDouble();

            skiResort.getStation(station1Id).addSkiSlope(new SkiSlope(station1Id, station2Id, difficultyLevel, runTime, basicAttractiveness, bumpsResistance, skiResort, i));
            lineScanner.close();
        }
    }

    private static Athlete[] readAthletes(Scanner inputScanner, SkiResort skiResort, int n) {
        int capacity = 16;
        int currentAthleteCount = 0;
        Athlete[] athletes = new Athlete[capacity];

        for(int i = 0; i < n; i++) {
            String line = inputScanner.nextLine();
            Scanner lineScanner = new Scanner(line);
            lineScanner.useLocale(Locale.ENGLISH);

            int m = lineScanner.nextInt();
            int skillLevel = lineScanner.nextInt();
            double spontaneityCoefficient = lineScanner.nextDouble();
            double boredomCoefficient = lineScanner.nextDouble();
            char type = lineScanner.next().charAt(0);
            String s;
            if(lineScanner.hasNext())
                s = "s";
            else
                s = "";

            line = inputScanner.nextLine();
            lineScanner.close();
            lineScanner = new Scanner(line);
            lineScanner.useLocale(Locale.ENGLISH);

            double skillAdjustmentWeight = lineScanner.nextDouble();
            double surfaceLevellingWeight = lineScanner.nextDouble();
            double boredomWeight = lineScanner.nextDouble();

            line = inputScanner.nextLine();
            lineScanner.close();
            lineScanner = new Scanner(line);

            int stationId = lineScanner.nextInt();
            Time time = new Time(lineScanner.next());
            int interval = 0;
            if(lineScanner.hasNextInt()) {
                interval = lineScanner.nextInt();
            }
            lineScanner.close();

            if (currentAthleteCount + m > capacity) {
                capacity = Math.max(capacity * 2, currentAthleteCount + m);
                Athlete[] newAthletes = new Athlete[capacity];
                System.arraycopy(athletes, 0, newAthletes, 0, currentAthleteCount);
                athletes = newAthletes;
            }

            for(int j = 0; j < m; j++) {
                if(type == 'L')
                    athletes[currentAthleteCount] = new LocalAthlete(
                        skillLevel, spontaneityCoefficient, s,
                        skillAdjustmentWeight, surfaceLevellingWeight, stationId, skiResort,
                        time.addSeconds(j * interval), currentAthleteCount, boredomCoefficient,
                        boredomWeight);
                else if(type == 'K')
                    athletes[currentAthleteCount] = new CollectorAthlete(
                            skillLevel, spontaneityCoefficient, s,
                            skillAdjustmentWeight, surfaceLevellingWeight, stationId, skiResort,
                            time.addSeconds(j * interval), currentAthleteCount, boredomCoefficient,
                            boredomWeight);
                else
                    athletes[currentAthleteCount] = new GreedyAthlete(
                            skillLevel, spontaneityCoefficient, s,
                            skillAdjustmentWeight, surfaceLevellingWeight, stationId, skiResort,
                            time.addSeconds(j * interval), currentAthleteCount, boredomCoefficient,
                            boredomWeight);
                currentAthleteCount++;
            }
        }

        Athlete[] finalAthletes = new Athlete[currentAthleteCount];
        System.arraycopy(athletes, 0, finalAthletes, 0, currentAthleteCount);

        return finalAthletes;
    }

    public Simulation readData(Time simulationStartTime, Time comebackTime, Time simulationEndTime) {
        Scanner inputScanner = new Scanner(System.in);

        int n = inputScanner.nextInt();
        inputScanner.nextLine();
        Node[] stations = readNodes(inputScanner, n);
        SkiResort skiResort = new SkiResort(stations);
        if (inputScanner.hasNextLine()) inputScanner.nextLine();

        n = inputScanner.nextInt();
        inputScanner.nextLine();
        readLifts(inputScanner, skiResort, n, simulationStartTime);
        if (inputScanner.hasNextLine()) inputScanner.nextLine();

        n = inputScanner.nextInt();
        inputScanner.nextLine();
        readSkiSlopes(inputScanner, skiResort, n);
        if (inputScanner.hasNextLine()) inputScanner.nextLine();

        n = inputScanner.nextInt();
        inputScanner.nextLine();
        Athlete[] athletes = readAthletes(inputScanner, skiResort, n);

        inputScanner.close();
        return new Simulation(simulationStartTime, comebackTime, simulationEndTime, skiResort, athletes);
    }
}