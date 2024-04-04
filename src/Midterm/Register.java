package Midterm;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Register {
    public static void signUp(String userType, String firstName, String lastName, String username, String password, String email) {
        DatabaseConnection.setCon();
        String hashedPassword = hashPassword(password);
        String insertUserQuery = "INSERT INTO user (userType, FirstName, LastName, Username, Password, Email) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = DatabaseConnection.con.prepareStatement(insertUserQuery)) {
            preparedStatement.setString(1, userType);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, lastName);
            preparedStatement.setString(4, username);
            preparedStatement.setString(5, hashedPassword);
            preparedStatement.setString(6, email);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User registered successfully!");
            } else {
                System.out.println("Failed to register user.");
            }
        } catch (SQLException e) {
            System.out.println("Registration failed.");
            e.printStackTrace();
        }
    } // end of signUp method

    public static boolean checkUsernameExists(String username) {
        DatabaseConnection.setCon();
        String query = "SELECT COUNT(*) FROM user WHERE Username = ?";
        try (PreparedStatement preparedStatement = DatabaseConnection.con.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    } // end of checkUsernameExists method

    private static String hashPassword(String password) {
        return password;
    } // end of hashPassword method

    public static int login(String username, String password) {
        DatabaseConnection.setCon();
        String query = "SELECT * FROM user WHERE username = ? AND password = ?";
        int userID = -1;
        boolean isAdmin = false;
        String uName = "";
        try {
            PreparedStatement preparedStatement = DatabaseConnection.con.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println("\nLogin successful!");
                isAdmin = resultSet.getString("UserType").equals("Admin");
                uName = resultSet.getString("username");
                userID = resultSet.getInt("userID");
            } else {
                System.out.println("Invalid username or password.");
                Main.main(null);
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while logging in.");
            e.printStackTrace();
        }
        if (isAdmin) {
            Main.runSubMenuAdmin(uName);
        } else {
            Main.runSubMenuCustomer(uName, userID);
        }
        return userID;
    } // end of login method
}

