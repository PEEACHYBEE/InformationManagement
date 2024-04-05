package Midterm;
import Midterm.DataObjectClasses.Artist;
import Midterm.DataObjectClasses.Schedule;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * @author: Lictag, Bryane Harry
 *          San Miguel, Chloe' Lee
 */
public class Admin {
    public static void addMusiciansSchedule(Schedule s, Artist a) {
        try {
            String query = "INSERT INTO schedule(Date, Time, ArtistID) VALUES(?,?,?)";
            PreparedStatement preparedStatement = DatabaseConnection.con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, s.getDate());
            preparedStatement.setString(2, s.getTime());
            preparedStatement.setInt(3, a.getArtistID());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int scheduleID = generatedKeys.getInt(1);
                System.out.println("New schedule added. The Schedule ID is: " + scheduleID);
            } else {
                System.out.println("...");
            }
        } catch (Exception e) {
            System.out.println("Failed to add schedule." + e.getMessage());
        }
    } // end of addMusiciansSchedule method

    public static void modifyMusiciansSchedule(int scheduleID, String newDate, String newTime, int newArtistID) {
        try {
            LocalDate currentDate = LocalDate.now();
            LocalDate scheduleDate = LocalDate.parse(newDate);
            if (scheduleDate.isBefore(currentDate)) {
                System.out.println("This schedule has already passed and cannot be modified.");
                return;
            }

            String query = "UPDATE schedule SET Date=?, Time=?, ArtistID=? WHERE ScheduleID=?";
            PreparedStatement preparedStatement = DatabaseConnection.con.prepareStatement(query);
            preparedStatement.setString(1, newDate);
            preparedStatement.setString(2, newTime);
            preparedStatement.setString(3, String.valueOf(newArtistID));
            preparedStatement.setInt(4, scheduleID);
            preparedStatement.executeUpdate();
            System.out.println("Schedule updated successfully.");
            System.out.println("=====================================================");
            TableDisplay.runDisplaySchedule();
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please enter date in YYYY-MM-DD format.");
        } catch (SQLException e) {
            System.out.println("Failed to update schedule.");
        }
    } // end of modifyMusiciansSchedule method

    public static ResultSet fetchScheduleAndArtist() throws SQLException {
        DatabaseConnection.setCon();
        String query = "SELECT s.ScheduleID, s.Date, a.ArtistName " +
                "FROM schedule s JOIN artist a ON s.ArtistID = a.ArtistID";
        PreparedStatement preparedStatement = DatabaseConnection.con.prepareStatement(query);
        return preparedStatement.executeQuery();
    } // end of fetchScheduleAndArtist method

    public static ResultSet fetchViewersForDate(String chosenDate) throws SQLException {
        DatabaseConnection.setCon();
        String viewersQuery = "SELECT DISTINCT rt.UserID, u.UserName " +
                "FROM redeemed_tickets rt " +
                "JOIN user u ON rt.UserID = u.UserID " +
                "WHERE rt.RedeemDate = ?";
        PreparedStatement viewersStatement = DatabaseConnection.con.prepareStatement(viewersQuery);
        viewersStatement.setString(1, chosenDate);
        return viewersStatement.executeQuery();
    } // end of fetchViewersForDate method

    public static void deleteSchedule(int scheduleID) throws SQLException{
        try{
            String deleteQuery = "DELETE FROM schedule WHERE ScheduleID = ?";
            PreparedStatement deleteStatement = DatabaseConnection.con.prepareStatement(deleteQuery);
            deleteStatement.setInt(1, scheduleID);
            deleteStatement.executeUpdate();
            System.out.println("Schedule ID " + scheduleID + " has been successfully deleted.");
        } catch (Exception E){
            E.printStackTrace();
        }

    }
}
