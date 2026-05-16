package ski_resort;

public class SkiResort {
    private Node[] stations;

    public SkiResort(int n) {
        stations = new Node[n];
    }

    public Node getStation(int i) {
        assert i >= 0 && i < stations.length : "Wrong station id!";
        return stations[i];
    }

    public Node[] getStations() {
        return stations.clone();
    }

    public int getStationCount() {
        return stations.length;
    }

    public void addStation(int height, int x, int y, String s, int i) {
        Node station;
        if(s.equals("s"))
            station = new Node(height, x, y, i, "s");
        else
            station = new Node(height, x, y, i, "");

        stations[i] = station;
    }
}