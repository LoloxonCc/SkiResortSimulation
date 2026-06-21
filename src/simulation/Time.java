package simulation;

// Represent virtual time in the simulation. Provides utility methods for operations.

public class Time {
    private final int h;
    private final int m;
    private final int s;

    public Time(String time) {
        String[] parts = time.split(":");
        h = Integer.parseInt(parts[0]);
        m = Integer.parseInt(parts[1]);
        s = Integer.parseInt(parts[2]);
    }

    public Time(Time time) {
        this.h = time.getH();
        this.m = time.getM();
        this.s = time.getS();
    }

    public Time(int h, int m, int s) {
        this.h = h;
        this.m = m;
        this.s = s;
    }

    public int getH() {
        return h;
    }

    public int getM() {
        return m;
    }

    public int getS() {
        return s;
    }

    public String toString() {
        return String.format("%02d:%02d:%02d", h, m, s);
    }

    public Time addSeconds(int t) {
        int totalTimeInSeconds = this.toSeconds() + t;

        int newH = totalTimeInSeconds / 3600;
        int remainder = totalTimeInSeconds % 3600;
        int newM = remainder / 60;
        int newS = remainder % 60;

        return new Time(newH, newM, newS);
    }

    public int toSeconds() {
        return h * 3600 + m * 60 +s;
    }

    public boolean isEarlierThan(Time time) {
        return this.toSeconds() <= time.toSeconds();
    }
}