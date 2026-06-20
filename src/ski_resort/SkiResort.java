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
}