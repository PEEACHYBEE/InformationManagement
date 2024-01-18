package Prelim;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class ChannelTitle {
    private FileWriter writer;
    public ChannelTitle() throws IOException {
        // Initialize FileWriter
        String outputFilePath = "searchResults.csv";
        writer = new FileWriter(outputFilePath);
    }
    public void searchChannelTitles(String filePath) {
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            // Initialize FileWriter
            String outputFilePath = "searchResults.csv";
            FileWriter writer = new FileWriter(outputFilePath);

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
                String resultString;
                if (channelVideoCount.containsKey(searchQuery)) {
                    resultString = "Number of videos by '" + searchQuery + "': " + channelVideoCount.get(searchQuery);
                    System.out.println(resultString);
                } else {
                    resultString = "No videos found for channel: " + searchQuery;
                    System.out.println(resultString);
                }

                try {
                    writer.write(searchQuery + "," + resultString + "\n");
                    writer.flush(); // Flush data to file
                } catch (IOException e) {
                    System.out.println("An error occurred while writing to the file: " + e.getMessage());
                }

                // Ask if the user wants to search again
                System.out.print("\nDo you want to search again? (yes/no): ");
                userChoice = kbd.nextLine().trim().toLowerCase();

            } while (userChoice.equals("yes"));

            kbd.close();
            writer.close(); // Close FileWriter

        } catch (FileNotFoundException e) {
            System.out.println("The CSV file was not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
    public void closeWriter() throws IOException {
        if (writer != null) {
            writer.close();
        }
    }
}
