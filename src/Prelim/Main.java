package Prelim;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    private final Scanner kbd = new Scanner(System.in);
    private ChannelTitle channelTitle;

    public static void main (String[] args) throws IOException {
        Main program = new Main();
        program.run();
    }

    public void run() throws IOException {
        int choice = 0;
        while (choice != 2) {
            mainMenu();
            choice = Integer.parseInt(readString("Please select from the options above: "));
            switch (choice) {
                case 1 -> channelTitle();
            }
        }
        System.out.println("\nProgram terminated.\nThank you for using this program!\n");
        if (channelTitle != null) {
            try {
                channelTitle.closeWriter();
            } catch (IOException e) {
                System.out.println("Error closing the file writer: " + e.getMessage());
            }
        }
        System.exit(0);
    }

    public void channelTitle() throws IOException {
        System.out.println("\n------------ Channel Title -----------");
        channelTitle = new ChannelTitle();
        String filePath = "data.csv"; // Replace with the actual file path
        channelTitle.searchChannelTitles(filePath);
    }

    public void mainMenu() {
        System.out.println("=======================================");
        System.out.println("|           PRELIM ACTIVITY 0         |");
        System.out.println("| 1. Search for the channel title     |");
        System.out.println("| 2. Exit the Program                 |");
        System.out.println("=======================================");
    } // end of mainMenu method

    // Read a string input with a prompt message
    public String readString(String promptMessage) {
        System.out.print(promptMessage);
        return kbd.nextLine();
    } // end of readString method
}
