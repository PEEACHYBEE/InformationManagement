package Midterm.DataObjectClasses;

public class Artist {
    private int artistID;
    private String artistName;
    private String artistType;
    private String genre;
    private String recordLabel;
    private String tourName;

    public Artist(int i) {
        this.artistID = i;
    }

    public Artist(int i, String n, String t, String g, String r, String tn) {
        this.artistID = i;
        this.artistName = n;
        this.artistType = t;
        this.genre = g;
        this.recordLabel = r;
        this.tourName = tn;
    }

    public int getArtistID() {
        return artistID;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getArtistType() {
        return artistType;
    }

    public String getGenre() {
        return genre;
    }

    public String getRecordLabel() {
        return recordLabel;
    }

    public String getTourName() {
        return tourName;
    }

    public String toString() {
        String formattedString = String.format("%-13s %-19s %-15s %-11s %-17s %-20s", artistID, artistName, artistType,
                genre, recordLabel, tourName);
        return formattedString;
    }
}
