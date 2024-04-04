package Midterm;

import java.sql.*;

/**
 * @author: San Miguel, Chloe' Lee
 */
public class DatabaseConnection {
    public static Connection con;

    /**
     * Set Connection
     */
    public static void setCon() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/kalapache", "root", "");
        } catch (Exception e) {
            System.out.println("Database connection failed.");
            e.printStackTrace();
        }
    }

} // end of DatabaseConnection class

