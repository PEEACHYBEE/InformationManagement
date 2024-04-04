package Midterm;

import Midterm.DataObjectClasses.Artist;
import Midterm.DataObjectClasses.Schedule;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author: San Miguel, Chloe' Lee
 */
public class TableDisplay {
    private static ArrayList<Artist> getArtist() throws Exception {
        DatabaseConnection.setCon();
        ArrayList<Artist> artists = new ArrayList<>();
        String query = "SELECT * FROM artist";
        Statement statement = DatabaseConnection.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet resultSet = statement.executeQuery(query);

        while(resultSet.next()) {
            Artist a = new Artist(resultSet.getInt(1), resultSet.getString(2),
                    resultSet.getString(3), resultSet.getString(4), resultSet.getString(5),
                    resultSet.getString(6));
            artists.add(a);
        }

        resultSet.close();
        return artists;
    } // end of getArtist method

    private static ArrayList<Schedule> getSchedule() throws Exception {
        DatabaseConnection.setCon();
        ArrayList<Schedule> schedules = new ArrayList<>();
        String query = "SELECT * FROM schedule";
        Statement statement = DatabaseConnection.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet resultSet = statement.executeQuery(query);

        while(resultSet.next()) {
            Schedule s = new Schedule(resultSet.getInt(1), resultSet.getString(2),
                    resultSet.getString(3), resultSet.getInt(4));
            schedules.add(s);
        }

        resultSet.close();
        return schedules;
    } // end of getSchedule method

    public static Schedule getScheduleByID(int scheduleID) {
        DatabaseConnection.setCon();
        try {
            String query = "SELECT * FROM schedule WHERE ScheduleID = ?";
            PreparedStatement preparedStatement = DatabaseConnection.con.prepareStatement(query);
            preparedStatement.setInt(1, scheduleID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Schedule(
                        resultSet.getInt("ScheduleID"),
                        resultSet.getString("Date"),
                        resultSet.getString("Time"),
                        resultSet.getInt("ArtistID")
                );
            }
            resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    } // end of getScheduleByID method

    public static void runDisplaySchedule() {
        DatabaseConnection.setCon();
        try {
            ArrayList<Schedule> schedules = getSchedule();
            System.out.println("Schedules: ");
            System.out.printf("%-15s %-14s %-12s %-20s%n", "Schedule ID", "Date", "Time", "Artist ID");
            System.out.printf("%-15s %-14s %-12s %-20s%n", "===========", "==========", "========", "=========");
            for (Schedule schedule : schedules) {
                System.out.println(schedule);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    } // end of runDisplaySchedule method

    public static void runDisplayArtist() {
        DatabaseConnection.setCon();
        try {
            ArrayList<Artist> artists = getArtist();
            System.out.println("Artists: ");
            System.out.printf("%-13s %-19s %-15s %-11s %-17s %-20s%n", "Artist ID", "Artist Name", "Artist Type",
                    "Genre", "Record Label", "Tour Name");
            System.out.printf("%-13s %-19s %-15s %-11s %-17s %-20s%n", "=========", "===============", "===========",
                    "=======", "============", "================================");
            for (Artist artist : artists) {
                System.out.println(artist);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    } // end of runDisplayArtist method
}

