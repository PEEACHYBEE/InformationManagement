package Prelim;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class YoutubeDataAnalyzer {
    public static void main(String[] args) {
        try {
            // Load the CSV file
            String filePath = "U.csv"; // Replace with actual file path
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            // Skip the header line
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            // HashMap to store the count of videos per channel, in lowercase
            HashMap<String, Integer> channelVideoCount = new HashMap<>();

            // Read the CSV file and populate the HashMap
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                // Improved parsing logic
                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (data.length > 3) {
                    String channelTitle = data[3].trim().replaceAll("\"", "").toLowerCase();
                    channelVideoCount.put(channelTitle, channelVideoCount.getOrDefault(channelTitle, 0) + 1);
                }
            }

            // Close the scanner
            scanner.close();

            // User input for channel title search
            Scanner kbd = new Scanner(System.in);
            String userChoice;

            do {
                System.out.print("Enter a channel title to search: ");
                String searchQuery = kbd.nextLine().toLowerCase(); // Convert search query to lowercase

                // Search and display the result
                if (channelVideoCount.containsKey(searchQuery)) {
                    System.out.println("Number of videos by '" + searchQuery + "': " + channelVideoCount.get(searchQuery));
                } else {
                    System.out.println("No videos found for channel: " + searchQuery);
                }

                // Ask if the user wants to search again
                System.out.print("\nDo you want to search again? (yes/no): ");
                userChoice = kbd.nextLine().trim().toLowerCase();

            } while (userChoice.equals("yes"));

            kbd.close();

        } catch (FileNotFoundException e) {
            System.out.println("The CSV file was not found: " + e.getMessage());
        }
    }
}
