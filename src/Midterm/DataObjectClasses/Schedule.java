package Midterm.DataObjectClasses;

public class Schedule {
    private int scheduleID;
    private String date;
    private String time;
    private int artistID;

    public Schedule(String d, String t) {
        this.date = d;
        this.time = t;
    }

    public Schedule(int s, String d, String t, int a) {
        this.scheduleID = s;
        this.date = d;
        this.time = t;
        this.artistID = a;
    }

    public int getScheduleID() {
        return scheduleID;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getArtistID() {
        return artistID;
    }

    public String toString() {
        String formattedString = String.format("%-15s %-14s %-12s %-20s", scheduleID, date, time, artistID);
        return formattedString;
    }
}
