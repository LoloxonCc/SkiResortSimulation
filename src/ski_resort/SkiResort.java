package ski_resort;

// Represents the main infrastructure container of the ski resort, functioning as a graph.

public class SkiResort {
    private final Node[] stations;

    public SkiResort(Node[] stations) {
        this.stations = stations;
    }

    public Node getStation(int i) {
        assert i >= 0 && i < stations.length : "Wrong station id!";
        return stations[i];
    }

    public Node[] getStations() {
        return stations.clone();
    }

    public int getTotalSkiSlopesCount() {
        int i = 0;
        for (Node station : stations)
            for(SkiSlope slope : station.getSkiSlopes())
                i++;

        return i;
    }
}