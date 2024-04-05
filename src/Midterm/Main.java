package Midterm;

import Midterm.DataObjectClasses.Artist;
import Midterm.DataObjectClasses.Schedule;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);

    // <editor-fold defaultstate="collapsed" desc="REGISTRATION">//
    public static void registration() {
        String userType;
        while (true) {
            System.out.print("Enter user type (Admin/Customer): ");
            userType = scanner.nextLine();
            if (!userType.equalsIgnoreCase("Admin") && !userType.equalsIgnoreCase("Customer")) {
                System.out.println("Invalid user type. Please enter 'Admin' or 'Customer'.");
            } else {
                break;
            }
        }
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();
        String username;
        while (true) {
            System.out.print("Enter username: ");
            username = scanner.nextLine();
            if (Register.checkUsernameExists(username)) {
                System.out.println("This username already exists. Please try another.");
            } else {
                break;
            }
        }

        String password;
        while (true) {
            System.out.print("Enter password: ");
            password = scanner.nextLine();
            if (!password.matches(".*[0-9].*") || !password.matches(".*[A-Z].*")) {
                System.out.println("Password must contain at least one number and one uppercase letter.");
            } else {
                break;
            }
        }

        String email;
        while (true) {
            System.out.print("Enter email: ");
            email = scanner.nextLine();
            if (!email.contains("@")) {
                System.out.println("Invalid email. Please include an '@' in the email address.");
            } else {
                break;
            }
        }

        Register.signUp(userType, firstName, lastName, username, password, email);
    }

    public static void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();
        int userID = Register.login(username, password);
        if (userID != -1) {
            enterTicketNoToWatch(userID);
            buyTickets(userID);
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="CUSTOMER">//
    public static void viewAndSelectScheduleOfMusicians() {
        TableDisplay.runDisplayArtist();
        System.out.println("=====================================================");
        System.out.print("Enter Artist ID to view the schedule: ");
        int artistID = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Schedule for Artist ID " + artistID + ":");
        System.out.printf("%-13s %-19s %-13s %-10s%n", "Schedule ID", "Artist Name", "Date", "Time");
        System.out.printf("%-13s %-19s %-13s %-10s%n", "============", "===============", "==========", "====");

        Customer.viewAndSelectSchedule(artistID);

        System.out.println("=====================================================");
    }

    public static void buyTickets(int userID) {
        do {
            TableDisplay.runDisplayArtist();
            try {
                int artistID;
                while (true) {
                    System.out.println("=====================================================");
                    System.out.print("Enter Artist ID you want to watch: ");
                    artistID = scanner.nextInt();
                    scanner.nextLine();

                    if (artistID <= 0 || artistID > 20) {
                        System.out.println("Invalid Artist ID. Please enter a valid ID from the choices.");
                    } else {
                        break;
                    }
                }

                int scheduleID;
                while (true) {
                    Customer.displaySchedule(artistID);
                    System.out.print("Enter the Schedule ID you want to order: ");
                    scheduleID = scanner.nextInt();
                    scanner.nextLine();
                    break;
                }

                System.out.print("Enter your phone number:");
                String phoneNumber = scanner.nextLine();

                System.out.println("Choose an e-wallet:");
                System.out.println("1. Apple Pay");
                System.out.println("2. Dragon Pay");
                System.out.println("3. Gcash");
                System.out.println("4. PayMaya");
                System.out.print("Enter the number corresponding to your e-wallet choice: ");
                int walletChoice = scanner.nextInt();
                scanner.nextLine();
                String eWalletType = Customer.getEWalletType(walletChoice);

                Customer.buyTickets(userID, artistID, scheduleID, phoneNumber, eWalletType);

                String choice;
                boolean validChoice;
                do {
                    System.out.print("\nDo you want to buy another ticket? (yes/no): ");
                    choice = scanner.next().toLowerCase();
                    validChoice = choice.equals("yes") || choice.equals("no");
                    if (!validChoice) {
                        System.out.println("Invalid input. Please enter 'yes' or 'no'.");
                    }
                } while (!validChoice);

                if (!choice.equals("yes")) {
                    return;
                }
                scanner.nextLine();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (true);
    }

    public static void enterTicketNoToWatch(int userID) {
        do {
            System.out.println();
            Customer.runDisplayRedeemedTickets(userID);
            try {
                System.out.println("==================================================================");
                System.out.println();
                System.out.print("Enter Ticket Code: ");
                int ticketCode = scanner.nextInt();
                scanner.nextLine();

                Customer.enterTicketNoToWatch(ticketCode, userID);

                String choice;
                boolean validChoice;
                do {
                    System.out.print("\nDo you want to redeem another ticket? (yes/no): ");
                    choice = scanner.next().toLowerCase();
                    validChoice = choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("no");
                    if (!validChoice) {
                        System.out.println("Invalid input. Please enter 'yes' or 'no'.");
                    }
                } while (!validChoice);

                if (!choice.equalsIgnoreCase("yes")) {
                    return;
                }
                scanner.nextLine();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }  while (true);
    } // end of enterTicketNoToWatch method

    public static void deleteAccount(){
        System.out.print("Are you sure you want to delete this account? (yes/no): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();

        if (confirmation.equals("yes")) {
            System.out.print("Enter your username:");
            String username = scanner.nextLine();

            System.out.print("Enter your password:");
            String password = scanner.nextLine();

            Customer.deleteAccount(username, password);
        } else {
            System.out.println("Account deletion canceled.");
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="ADMIN">//
    public static void addMusiciansSchedule() {
        do {
            TableDisplay.runDisplayArtist();
            try {
                System.out.println();
                System.out.println("==================================================================");
                LocalDate date = null;
                LocalTime time = null;

                while (date == null || time == null) {
                    System.out.print("Enter Date (YYYY-MM-DD): ");
                    String dateString = scanner.nextLine();
                    try {
                        date = LocalDate.parse(dateString);
                        if (date.isBefore(LocalDate.now())) {
                            System.out.println("Invalid date. Please enter a valid date.");
                            date = null;
                        } else {
                            System.out.print("Enter Time (HH:MM:SS): ");
                            String timeString = scanner.nextLine();
                            try {
                                time = LocalTime.parse(timeString);
                                LocalDateTime dateTime = LocalDateTime.of(date, time);
                                if (dateTime.isBefore(LocalDateTime.now())) {
                                    System.out.println("Invalid time. Please enter a valid time.");
                                    time = null;
                                }
                            } catch (DateTimeParseException e) {
                                System.out.println("Invalid time format. Please enter time in HH:MM:SS format.");
                            }
                        }
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date format. Please enter date in YYYY-MM-DD format.");
                    }
                }

                System.out.print("Enter Artist ID: ");
                int artistID = scanner.nextInt();

                Schedule schedule = new Schedule(date.toString(), time.toString());
                Artist artist = new Artist(artistID);
                Admin.addMusiciansSchedule(schedule, artist);

                String choice;
                boolean validChoice;
                do {
                    System.out.print("Do you want to add another musician's schedule? (yes/no): ");
                    choice = scanner.next().toLowerCase();
                    validChoice = choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("no");
                    if (!validChoice) {
                        System.out.println("Invalid input. Please enter 'yes' or 'no'.");
                    }
                } while (!validChoice);

                if (!choice.equalsIgnoreCase("yes")) {
                    return;
                }
                scanner.nextLine();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (true);
    }

    public static void modifyMusiciansSchedule() {
        do {
            TableDisplay.runDisplaySchedule();
            System.out.println();
            System.out.println("=====================================================");
            System.out.print("Enter Schedule ID to modify: ");
            int scheduleID = scanner.nextInt();
            scanner.nextLine();
            Schedule schedule = TableDisplay.getScheduleByID(scheduleID);
            if (schedule == null) {
                System.out.println("Schedule ID not found. Please enter a valid Schedule ID.");
                continue;
            }

            LocalDate scheduleDate = LocalDate.parse(schedule.getDate());
            if (scheduleDate.isBefore(LocalDate.now())) {
                System.out.println("This schedule has already passed and cannot be modified.");
                String choice;
                do {
                    System.out.print("Do you want to enter another schedule ID? (yes/no): ");
                    choice = scanner.nextLine().toLowerCase();
                } while (!choice.equals("yes") && !choice.equals("no"));

                if (choice.equals("yes")) {
                    continue;
                } else {
                    return;
                }
            }

            System.out.print("Enter New Date (YYYY-MM-DD): ");
            String newDate = scanner.nextLine();
            System.out.print("Enter New Time (HH:MM:SS): ");
            String newTime = scanner.nextLine();
            System.out.print("Enter New Artist ID: ");
            int newArtistID = scanner.nextInt();
            scanner.nextLine();
            Admin.modifyMusiciansSchedule(scheduleID, newDate, newTime, newArtistID);

            String choice;
            boolean validChoice;
            do {
                System.out.print("Do you want to modify another musician's schedule? (yes/no): ");
                choice = scanner.next().toLowerCase();
                validChoice = choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("no");
                if (!validChoice) {
                    System.out.println("Invalid input. Please enter 'yes' or 'no'.");
                }
            } while (!validChoice);

            if (!choice.equalsIgnoreCase("yes")) {
                return;
            }
        } while (true);
    }

    public static void viewListOfViewers() {
        try {
            ResultSet resultSet = Admin.fetchScheduleAndArtist();

            System.out.println("Schedule ID\t\tSchedule Date\t\tArtist Name");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("ScheduleID") + "\t\t\t\t" + resultSet.getString("Date") + "\t\t\t" + resultSet.getString("ArtistName"));
            }

            System.out.print("\nChoose a date (YYYY-MM-DD) to display list of viewers: ");
            String chosenDate = scanner.nextLine().trim();
            LocalDate.parse(chosenDate);

            ResultSet viewersResultSet = Admin.fetchViewersForDate(chosenDate);
            System.out.println("\nList of Viewers for " + chosenDate + ":");
            System.out.println("User ID\t\t\tUser Name");
            while (viewersResultSet.next()) {
                System.out.println(viewersResultSet.getInt("UserID") + "\t\t\t\t" + viewersResultSet.getString("UserName"));
            }

        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please enter a valid date (YYYY-MM-DD).");
            viewListOfViewers();
        } catch (SQLException e) {
            System.out.println("Failed to retrieve schedule and artist information.");
            e.printStackTrace();
        }
    }

    public static void deleteScheduleMenu() throws SQLException {
        TableDisplay.runDisplayArtist();
        System.out.print("Select an Artist ID to view their schedule/s: ");
        int artistID = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Schedule for Artist ID " + artistID + ":");
        System.out.printf("%-13s %-19s %-13s %-10s%n", "Schedule ID", "Artist Name", "Date", "Time");
        System.out.printf("%-13s %-19s %-13s %-10s%n", "============", "===============", "==========", "====");

        Customer.viewAndSelectSchedule(artistID);
        System.out.print("Select a Schedule ID to delete the Artist's schedule: ");
        Scanner kbd = new Scanner(System.in);
        int scheduleID = kbd.nextInt();
        Admin.deleteSchedule(scheduleID);
    }
    // </editor-fold>

    public static int subMenuAdmin(String uName) throws InputMismatchException {
        System.out.println("\nWelcome, Admin " + uName + "!");
        System.out.println("1. Add the musician's schedule");
        System.out.println("2. Modify the musician's schedule");
        System.out.println("3. View the list of viewers");
        System.out.println("4. Delete a schedule");
        System.out.println("5. Logout");
        System.out.print("Enter the number of your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    public static int subMenuCustomer(String uName) throws InputMismatchException {
        System.out.println("\nWelcome, Customer " + uName + "!");
        System.out.println("1. View and select the schedule of musicians");
        System.out.println("2. Buy tickets");
        System.out.println("3. Enter the ticket number to watch");
        System.out.println("4. Delete your account");
        System.out.println("5. Logout");
        System.out.print("Enter the number of your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    public static int menu() throws InputMismatchException {
        System.out.println("\n===========>>> GIG LIVE STREAM <<<==========");
        System.out.println("Greetings! Welcome to GIG live stream!\n");
        System.out.println("1. Registration");
        System.out.println("2. Login");
        System.out.println("3. Exit program");
        System.out.print("Enter the number of your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    public static void runSubMenuAdmin(String uName) {
        while(true) {
            try {
                int choice = subMenuAdmin(uName);
                switch(choice) {
                    case 1 -> addMusiciansSchedule();
                    case 2 -> modifyMusiciansSchedule();
                    case 3 -> viewListOfViewers();
                    case 4 -> deleteScheduleMenu();
                    case 5 -> {
                        System.out.println("Thank you for using GIG live stream!\nLogging out...");
                        run();
                    }
                    default -> System.out.println("Invalid Input");
                }
            } catch (InputMismatchException | SQLException exception) {
                scanner.nextLine();
                System.out.println("Invalid Input");
            }
        }
    }

    public static void runSubMenuCustomer(String uName, int userID) {
        while(true) {
            try {
                int choice = subMenuCustomer(uName);
                switch(choice) {
                    case 1 -> viewAndSelectScheduleOfMusicians();
                    case 2 -> buyTickets(userID);
                    case 3 -> enterTicketNoToWatch(userID);
                    case 4 -> deleteAccount();
                    case 5 -> {
                        System.out.println("Thank you for using GIG live stream!\nLogging out...");
                        run();
                    }
                    default -> System.out.println("Invalid Input");
                }
            } catch (InputMismatchException exception) {
                scanner.nextLine();
                System.out.println("Invalid Input");
            }
        }
    }

    public static void run() {
        while(true) {
            try {
                int choice = menu();
                switch(choice) {
                    case 1 -> registration();
                    case 2 -> login();
                    case 3 -> System.exit(0);
                    default -> System.out.println("Invalid Input");
                }
            } catch (InputMismatchException exception) {
                scanner.nextLine();
                System.out.println("Invalid Input");
            }
        }
    }

    public static void main(String[] args) {
        DatabaseConnection.setCon();
        run();
    }
} // end of Main class
