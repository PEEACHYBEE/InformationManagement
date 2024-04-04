package Midterm;

import java.sql.*;
import java.time.LocalDate;

/**
 * @author: Encarnacion, Ma. Earl Freskkie
 *          Escano, Nichole Jhoy
 *          Razo, Ma. Lourdes Shaine
 */
public class Customer {

    public static void viewAndSelectSchedule(int artistID) {
        DatabaseConnection.setCon();
        try {
            String scheduleQuery = "SELECT s.ScheduleID, s.Date, s.Time, a.ArtistName " +
                    "FROM schedule s " +
                    "JOIN ticket t ON s.ScheduleID = t.ScheduleID " +
                    "JOIN artist a ON s.ArtistID = a.ArtistID " +
                    "WHERE s.ArtistID = ?";

            PreparedStatement preparedStatement = DatabaseConnection.con.prepareStatement(scheduleQuery);
            preparedStatement.setInt(1, artistID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int scheduleID = resultSet.getInt("ScheduleID");
                String artistName = resultSet.getString("ArtistName");
                String date = resultSet.getString("Date");
                String time = resultSet.getString("Time");


                System.out.printf("%-13s %-19s %-13s %-10s%n",
                        scheduleID, artistName, date, time);
            }
        } catch (Exception e) {
            System.out.println("An error occurred while fetching the schedule.");
            e.printStackTrace();
        }
    } // end of viewAndSelectSchedule method

    public static void buyTickets(int userID, int artistID, int scheduleID, String phoneNumber, String eWalletType) {
        DatabaseConnection.setCon();
        try {
            String insertTransactionQuery = "INSERT INTO transaction " +
                    "(UserID, EWalletType, PhoneNumber, TransactionDate) " +
                    "VALUES (?,?,?,?)";
            PreparedStatement preparedStatement = DatabaseConnection.con.prepareStatement(insertTransactionQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, userID);
            preparedStatement.setString(2, eWalletType);
            preparedStatement.setString(3, phoneNumber);
            preparedStatement.setDate(4, Date.valueOf(LocalDate.now()));
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int transactionID = generatedKeys.getInt(1);
                System.out.println("Transaction successful. Your Transaction ID is: " + transactionID + "\nUserID: " + userID);
            } else {
                System.out.println("Failed to retrieve Transaction ID.");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    } // end of buyTickets method

    public static void displaySchedule(int artistID) {
        DatabaseConnection.setCon();
        try {
            String scheduleQuery = "SELECT s.ScheduleID, s.Date, s.Time, t.Price, a.ArtistName " +
                    "FROM schedule s " +
                    "JOIN ticket t ON s.ScheduleID = t.ScheduleID " +
                    "JOIN artist a ON s.ArtistID = a.ArtistID " +
                    "WHERE s.ArtistID = ?";
            PreparedStatement preparedStatement = DatabaseConnection.con.prepareStatement(scheduleQuery);
            preparedStatement.setInt(1, artistID);
            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("Schedule for Artist ID " + artistID + ":");
            System.out.printf("%-13s %-19s %-13s %-10s %-10s%n", "Schedule ID", "Artist Name", "Date", "Time", "Price");
            System.out.printf("%-13s %-19s %-13s %-10s %-10s%n", "============", "===============", "==========", "====", "=====");

            while (resultSet.next()) {
                int fetchedScheduleID = resultSet.getInt("ScheduleID");
                String artistName = resultSet.getString("ArtistName");
                String date = resultSet.getString("Date");
                String time = resultSet.getString("Time");
                double price = resultSet.getDouble("Price");

                System.out.printf("%-13s %-19s %-13s %-10s %-10s%n",
                        fetchedScheduleID, artistName, date, time, price);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } // end of displaySchedule method

    public static String getEWalletType(int walletChoice) {
        switch (walletChoice) {
            case 1:
                return "ApplePay";
            case 2:
                return "DragonPay";
            case 3:
                return "Gcash";
            case 4:
                return "PayMaya";
            default:
                return "";
        }
    } // end of getEWalletType method

    public static void enterTicketNoToWatch(int ticketCode, int userID) {
        DatabaseConnection.setCon();
        try {
            String ticketQuery = "INSERT INTO redeemed_tickets(RedeemDate, TicketCode, UserID) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = DatabaseConnection.con.prepareStatement(ticketQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setDate(1, Date.valueOf(LocalDate.now()));
            preparedStatement.setInt(2, ticketCode);
            preparedStatement.setInt(3, userID);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = preparedStatement.getGeneratedKeys();
                int redeemID = 0;
                if (rs.next()) {
                    redeemID = rs.getInt(1);
                }
                String artistName = null;
                String artist =
                        "SELECT a.ArtistName " +
                                "FROM TICKET_ORDERS t " +
                                "INNER JOIN TICKET ti ON t.TicketID = ti.TicketID " +
                                "INNER JOIN SCHEDULE s ON ti.ScheduleID = s.ScheduleID " +
                                "INNER JOIN ARTIST a ON s.ArtistID = a.ArtistID " +
                                "WHERE t.TicketCode = ?";
                PreparedStatement artistStatement = DatabaseConnection.con.prepareStatement(artist);
                artistStatement.setInt(1, ticketCode);
                ResultSet artistResultSet = artistStatement.executeQuery();
                if (artistResultSet.next()) {
                    artistName = artistResultSet.getString("ArtistName");
                }

                if (artistName != null) {
                    System.out.println();
                    System.out.println("Ticket redemption successful, User ID " + userID + "! You are now watching " + artistName +
                            "'s live stream. \nYour RedeemID is: " + redeemID);
                }
            } else {
                System.out.println("Ticket redemption failed. Please try again.");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while redeeming ticket: " + e.getMessage());
        }
    } // end of enterTicketNoToWatch method

    public static void runDisplayRedeemedTickets(int userID) {
        DatabaseConnection.setCon();
        try {
            String redeemedTicketsQuery =
                    "SELECT r.RedeemID, r.RedeemDate, r.TicketCode, a.ArtistName " +
                            "FROM redeemed_tickets r " +
                            "JOIN ticket_orders o ON r.TicketCode = o.TicketCode " +
                            "JOIN ticket t ON o.TicketID = t.TicketID " +
                            "JOIN schedule s ON t.ScheduleID = s.ScheduleID " +
                            "JOIN artist a ON s.ArtistID = a.ArtistID " +
                            "WHERE r.UserID = ?";
            PreparedStatement preparedStatement = DatabaseConnection.con.prepareStatement(redeemedTicketsQuery);
            preparedStatement.setInt(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("Redeemed Tickets for User ID " + userID + ":");
            System.out.printf("%-10s %-20s %-10s %-15s%n", "RedeemID", "Redeem Date", "Ticket Code", "Artist Name");
            System.out.printf("%-10s %-20s %-10s %-15s%n", "========", "==================", "===========", "============");

            while (resultSet.next()) {
                int redeemID = resultSet.getInt("RedeemID");
                String redeemDate = resultSet.getString("RedeemDate");
                int ticketCode = resultSet.getInt("TicketCode");
                String artistName = resultSet.getString("ArtistName");

                System.out.printf("%-10s %-20s %-10s %-15s%n", redeemID, redeemDate, ticketCode, artistName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } // end of runDisplayRedeemedTickets method

    public static void deleteAccount(String username, String password){
        DatabaseConnection.setCon();
        try {
            // Prepare a SQL statement to check if the password is correct
            PreparedStatement checkPasswordStatement = DatabaseConnection.con.prepareStatement(
                    "SELECT * FROM user WHERE Username = ? AND Password = ?");
            checkPasswordStatement.setString(1, username);
            checkPasswordStatement.setString(2, password);

            // Execute the query
            ResultSet resultSet = checkPasswordStatement.executeQuery();

            // If a matching record is found, delete the account
            if (resultSet.next()) {
                int userId = resultSet.getInt("UserID");

                PreparedStatement deleteRedeemedTicketsStatement = DatabaseConnection.con.prepareStatement(
                        "DELETE FROM redeemed_tickets WHERE UserID = ?");
                deleteRedeemedTicketsStatement.setInt(1, userId);
                deleteRedeemedTicketsStatement.executeUpdate();

                // Delete records from the ticket_orders table
                PreparedStatement deleteTicketOrdersStatement = DatabaseConnection.con.prepareStatement(
                        "DELETE FROM ticket_orders WHERE TransactionID IN (SELECT TransactionID FROM transaction WHERE UserID = ?)");
                deleteTicketOrdersStatement.setInt(1, userId);
                deleteTicketOrdersStatement.executeUpdate();

                // Delete records from the transaction table
                PreparedStatement deleteTransactionsStatement = DatabaseConnection.con.prepareStatement(
                        "DELETE FROM transaction WHERE UserID = ?");
                deleteTransactionsStatement.setInt(1, userId);
                deleteTransactionsStatement.executeUpdate();

                // Delete the user account
                PreparedStatement deleteUserStatement = DatabaseConnection.con.prepareStatement(
                        "DELETE FROM user WHERE Username = ?");
                deleteUserStatement.setString(1, username);
                int rowsAffected = deleteUserStatement.executeUpdate();
                // Check if deletion was successful
                if (rowsAffected > 0) {
                    System.out.println("Your account has been deleted.");
                } else {
                    System.out.println("Failed to delete your account.");
                }
            } else {
                System.out.println("Incorrect username or password.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


