package simulation;

public class Time {
    private int h;
    private int m;
    private int s;

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
        String out = "";
        if(h < 10)
            out += "0" + String.valueOf(h);
        else
            out += String.valueOf(h);

        out += ":";

        if(m < 10)
            out += "0" + String.valueOf(m);
        else
            out += String.valueOf(m);

        out += ":";

        if(s < 10)
            out += "0" + String.valueOf(s);
        else
            out += String.valueOf(s);

        return out;
    }

    public Time addSeconds(int t) {
        int totalTimeInSeconds = this.toSeconds() + t;

        int newH = totalTimeInSeconds / 3600;
        int remainder = totalTimeInSeconds % 3600;
        int newM = remainder / 60;
        int newS = remainder % 60;

        String newTime = String.format("%02d:%02d:%02d", newH, newM, newS);
        return new Time(newTime);
    }


    private int toSeconds() {
        return h * 3600 + m * 60 +s;
    }

    public boolean isEarlierThan(Time time) {
        return this.toSeconds() <= time.toSeconds();
    }
}