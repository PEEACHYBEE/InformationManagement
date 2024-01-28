package Prelim.Activity0.Classes;

/**
 ALGORITHM:
 1. User inputs a date in the 'yy.dd.mm' format, and the program proceeds to read trending dates from 'data.csv'. The
 output CSV file path is set to 'outputFilteringDatesAndSortingViews.csv'.
 2. The program checks the validity of the entered date within the trending dates. If the date is valid, the process
 continues; otherwise, an error message is printed, and the program exits.
 3. Dates, titles, count views, and average views are read from 'data.csv'. The program then displays titles and view
 counts for the selected date, organized in descending order based on views. The results are stored in a list.
 4. The obtained results are written to 'outputFilteringDatesAndSortingViews.csv'. A success message is printed on the
 console. The program handles any potential errors during the file writing process.
 5. If the entered date is deemed invalid or not found in the trending dates, the program prints an error message
 indicating an invalid date or the absence of content for the selected date.

 @author: SAN MIGUEL, CHLOE' LEE
 @since: JANUARY 18, 19, 21, 23, and 25, 2024 */

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * This class represents a program that allows users to choose a date and
 * view the most popular content on the selected date.
 */

public class FilteringDatesAndSortingViews {
    private String filepath = "data.csv"; // Path to the input CSV file
    private String outputCsvPath = "outputFilteringDatesAndSortingViews.csv"; // Path to the output CSV file

    /**
     * Processes the user-selected date, reads trending dates from a CSV file, validates the selected date, displays
     * views and titles for the selected date, calculates the average views, and writes the results to a CSV file.
     * Constructs a summary string containing average views, selected date, and instructions to open the generated CSV
     * file for full details.
     *
     * @param userSelectedDate The date selected by the user in the format specified.
     * @return A summary string containing average views, selected date, and file instructions.
     *         If no data is found for the selected date, returns a message indicating no data.
     * @param userSelectedDate The date selected by the user in the format specified.
     * @return A summary string containing average views, selected date, and file instructions.
     *         If no data is found for the selected date, returns a message indicating no data.
     */

    public String processDateAndWriteResults(String userSelectedDate) {
        String summaryData = null;
        // Read trending dates from the CSV file
        String[] trendingDates = readColumn(1, filepath, ",");
        // Check if the selected date is valid and exists in the trending dates
        if (trendingDates != null && isValidDate(userSelectedDate, trendingDates)) {
            // Display views and titles for the selected date and write results to CSV
            List<String> results = showViewsAndTitle(userSelectedDate);
            double averageViews = calculateAverageViews(results);
            writeResultsToCsv(results, averageViews, userSelectedDate);

            // Construct the summary string
            summaryData = "Average: " + String.format("%.2f", averageViews) + "\n"
                    + "Date: " + userSelectedDate + "\n"
                    + "\nTo see the full details, open the file \"outputFilteringDatesAndSortingViews_"+userSelectedDate+".csv\".";

        } else {
            // Return a message indicating no data found for the selected date
            summaryData = "No data available for the date: " + userSelectedDate;
        }

        return summaryData;
    } // end of processDateAndWriteResults method

    /**
     * Retrieves and displays views and titles for the specified date from a CSV file. Sorts the data based on view
     * counts and returns a list of formatted results.
     *
     * @param selectedDate The date for which views and titles are to be displayed.
     * @return A list of formatted results containing views and titles for the specified date.
     *         If no data is found for the specified date, an empty list is returned.
     * @param selectedDate The date for which views and titles are to be displayed.
     * @return A list of formatted results containing views and titles for the specified date.
     *         If no data is found for the specified date, an empty list is returned.
     */
    private List<String> showViewsAndTitle(String selectedDate) {
        List<String> results = new ArrayList<>();
        int totalViews = 0; // Total views for the selected date
        int matchedCount = 0; // Count of data points that match the selected date

        // Read the relevant columns from the CSV file
        String[] dates = readColumn(1, filepath, ",");
        String[] titles = readColumn(2, filepath, ",");
        String[] views = readColumn(7, filepath, ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");


        // Create an array of indices and sort them based on view counts
        Integer[] indices = new Integer[views.length];
        for (int i = 0; i < indices.length; i++) {
            indices[i] = i;
        }

        Arrays.sort(indices, Comparator.comparingInt(i -> {
            try {
                return Integer.parseInt(views[i]);
            } catch (NumberFormatException e) {
                return 0;
            }
        }));

        // Iterate through the sorted indices and display results
        for (int i = indices.length - 1; i >= 0; i--) {
            int index = indices[i];
            if (selectedDate.equals(dates[index])) {
                String viewStr = views[index];
                if (!viewStr.isEmpty()) {
                    // Parse view count and update totalViews and matchedCount
                    int viewCount = Integer.parseInt(viewStr);
                    totalViews += viewCount;
                    matchedCount++;

                    // Display the result and add it to the results list
                    String result = "Views: " + viewStr + ", Title: " + titles[index];
                    results.add(result);
                }
            }
        }

        // Display the average views for the selected date if matched data is found
        if (matchedCount > 0) {
            double averageViews = (double) totalViews / matchedCount;
        }

        return results;
    } // end of showViewsAndTitle

    /**
     * Writes the provided results and average views to a CSV file.
     *
     * @param results           The list of formatted results containing views and titles.
     * @param averageViews      The average views calculated for the specified date.
     * @param userSelectedDate  The date for which results are generated and being written to the CSV file.
     * @param results           The list of formatted results containing views and titles.
     * @param averageViews      The average views calculated for the specified date.
     * @param userSelectedDate  The date for which results are generated and being written to the CSV file.
     *
     * @throws NullPointerException if the results or userSelectedDate is null.
     */
    private void writeResultsToCsv(List<String> results, double averageViews, String userSelectedDate) {
        String outputCsvPath = "outputFilteringDatesAndSortingViews_" + userSelectedDate.replace(".", "_") + ".csv"; // Dynamic file name

        try (PrintWriter writer = new PrintWriter(new FileWriter(outputCsvPath))) {
            for (String result : results) {
                writer.println(result);
            }
            // Add a line for average views
            writer.println("Average views for all data:," + String.format("%.2f", averageViews));
        } catch (IOException e) {
            e.printStackTrace();
        }
    } // end of writeResultsToCsv method

    /**
     * Checks if the selected date is valid based on the trending dates.
     *
     * @param selectedDate   The selected date to validate.
     * @param trendingDates  Array of trending dates.
     * @return True if the selected date is valid; false otherwise.
     */
    private boolean isValidDate(String selectedDate, String[] trendingDates) {
        // Check if the selected date exists in the trending dates
        for (String date : trendingDates) {
            if (selectedDate.equals(date)) {
                return true;
            }
        }
        return false;
    } // end of isValidDate method

    /**
     * Calculates the average views from the provided results.
     *
     * @param results The list of results containing view counts.
     * @return The average views.
     */
    private double calculateAverageViews(List<String> results) {
        int totalViews = 0;
        int count = 0;

        for (String result : results) {
            // Extract the view count from the result
            String[] parts = result.split(", ");
            String viewStr = parts[0].substring(parts[0].lastIndexOf(" ") + 1);
            int viewCount = Integer.parseInt(viewStr);
            // Update totalViews and count
            totalViews += viewCount;
            count++;
        }

        // Calculate and return the average views
        return (double) totalViews / count;
    }

    /**
     * Reads a specific column from a CSV file.
     *
     * @param column      The column number to read.
     * @param filepath    The path of the CSV file.
     * @param delimiter   The delimiter used in the CSV file.
     * @return An array containing the data from the specified column.
     */
    private static String[] readColumn(int column, String filepath, String delimiter) {
        ArrayList<String> columnData = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath))) {
            String currentLine;

            bufferedReader.readLine(); // Skip header

            // Read lines from the file and extract data from the specified column
            while ((currentLine = bufferedReader.readLine()) != null) {
                String[] data = currentLine.split(delimiter, -1);
                if (column < data.length) {
                    columnData.add(data[column]);
                } else {
                    columnData.add("");
                }
            }

            return columnData.toArray(new String[0]);
        } catch (IOException e) {
            return null;
        }
    } // end of readColumn method
} // end of Classes.FilteringDatesAndSortingViews class

