package Prelim.Activity0.Classes;

import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * The {@code ChannelTitle} class is responsible for processing a CSV file
 * containing YouTube channel data, searching for channel titles based on a query,
 * and writing the results to an output file.
 *
 * @author: ENCARNACION, Ma. Earl Freskkie A.
 * @since: January 18, 2024
 *
 * Algorithm:
 *
 * 1. Create a HashMap<String, Integer> named channelVideoCount to store the count of videos per channel.
 * 2. Open the CSV file located at filePath using a Scanner.
 * 3. Read the file line by line.
 * 4. Skip the header line.
 * 5. Split the line into a string array data using regex to correctly handle CSV format.
 * 6. Extract the channel title from the array (assuming it's in the 4th column).
 * 7. Convert the channel title to lowercase.
 * 8. Update the channelVideoCount map with the count of videos for each channel.
 * 9. Convert searchQuery to lowercase.
 * 10. Check if searchQuery exists in channelVideoCount.
 * 11. Prepare a result string indicating the number of videos for the channel or a message if no videos are found.
 * 12. Write the searchQuery and resultString to the writer.
 * 13. Flush the writer to ensure data is written to the file.
 * 14. Close the writer to release system resources.
 */


/**
 * Constructs a new {@code ChannelTitle} object and initializes a {@code FileWriter}
 * to write the search results to a specified file.
 *
 * @throws IOException if an I/O error occurs during writer initialization
 */
public class ChannelTitle {
    private FileWriter writer;

    public ChannelTitle() throws IOException {
        // Initialize FileWriter in append mode
        String outputFilePath = "ChannelTitleResults.csv";
        writer = new FileWriter(outputFilePath, true); // Open in append mode
    }

    /**
     * Searches the given CSV file for channel titles that match the specified query.
     * The results are written to the output file initialized in the constructor.
     *
     * @param filePath    the path of the CSV file to be searched
     * @param searchQuery the query string to search for in channel titles
     * @throws IOException if an I/O error occurs during file processing
     */

    public List<String[]> searchChannelTitles(String filePath, String searchQuery) throws IOException {
        HashMap<String, Integer> channelVideoCount = new HashMap<>();
        File file = new File(filePath);
        List<String[]> results = new ArrayList<>();

        try (Scanner scanner = new Scanner(file)) {
            // Skip the header line
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            // Read the CSV file and populate the HashMap
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (data.length > 3) {
                    String channelTitle = data[3].trim().replaceAll("\"", "").toLowerCase();
                    channelVideoCount.put(channelTitle, channelVideoCount.getOrDefault(channelTitle, 0) + 1);
                }
            }
        } catch (IOException e) {
            throw new IOException("The CSV file was not found: " + e.getMessage(), e);
        }

        // Convert search query to lowercase and search
        searchQuery = searchQuery.toLowerCase();
        if (channelVideoCount.containsKey(searchQuery)) {
            int videoCount = channelVideoCount.get(searchQuery);
            results.add(new String[]{searchQuery, String.valueOf(videoCount)});
            writer.write(searchQuery + "," + "Number of videos by '" + searchQuery + "': " + videoCount + "\n");
        } else {
            writer.write(searchQuery + "," + "No videos found for channel: " + searchQuery + "\n");
            results.add(new String[]{searchQuery, "No videos found"});
        }

        writer.flush(); // Flush data to file
        return results;
    }

    /**
     * Closes the {@code FileWriter}. Should be called after all writing operations
     * are completed to release system resources.
     *
     * @throws IOException if an I/O error occurs during file closing
     */
    public void closeWriter() throws IOException {
        if (writer != null) {
            writer.close();
        }
    }
}
