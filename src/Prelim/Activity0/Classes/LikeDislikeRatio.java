package Prelim.Activity0.Classes;
/**
 *
 * The GeneralLikeDislike class reads video data from the data.csv file,
 * display the most liked and disliked videos, and create a new CSV files (LikesOutput.csv and DislikesOutput.csv)
 * with the TopLikes/Dislikes information.
 *  @author Razo, Ma. Lourdes Shaine C.
 * @since January 16, 2024
 * -----------------------------------------------------------------------------------------------
 * Algorithm:
 *
 * 1. Main Class (LikeDislikeRatio):
 *   - Initializes instances of the class and runs the program.
 * 2. run Method:
 *   - Processes video dataset by reading data from "data.csv" and populating likes and dislikes maps.
 *   - Displays the ratio of the most liked and disliked videos.
 *   - Saves the ratio results to a new CSV file ("OutputRatioLikeDislike.csv") sorted by ratio from most to least.
 *   - Handles IOException.
 * 3. processDataset Method:
 *   - Reads the CSV file, skips the header, and populates likes and dislikes maps with video data.
 *   - Uses try-with-resources to close the BufferedReader.
 *   - Handles NumberFormatException.
 * 4. displayMostLikedAndDislikedRatio Method:
 *   - Displays the most liked and disliked video titles and their respective ratios.
 * 5. saveRatioResults Method:
 *   - Saves the most liked and disliked video titles, counts, and ratios to a new CSV file.
 *   - Sorts titles by ratio from most to least.
 *   - Handles IOException.
 * 6. getRatio Method:
 *   - Calculates and returns the ratio for a given video title.
 *
 */


import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class LikeDislikeRatio {

    private static final String INPUT_FILE_PATH = "data.csv";
    private static final String OUTPUT_CSV_PATH = "OutputRatioLikeDislike.csv";

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        try {
            Map<String, Integer> likesMap = new HashMap<>();
            Map<String, Integer> dislikesMap = new HashMap<>();

            processDataset(likesMap, dislikesMap);

            // Display ratio of the most liked and disliked videos
            displayMostLikedAndDislikedRatio(likesMap, dislikesMap);

            // Save the ratio results to a new CSV file (sorted by ratio from most to least)
            saveRatioResults(likesMap, dislikesMap, OUTPUT_CSV_PATH);
        } catch (IOException e) {
            // Removed console print statements
        }
    }

    private static void processDataset(Map<String, Integer> likesMap, Map<String, Integer> dislikesMap) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE_PATH))) {
            // Skip the header row
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                if (data.length >= 16) {
                    try {
                        String title = data[2];
                        int likes = Integer.parseInt(data[8]);
                        int dislikes = Integer.parseInt(data[9]);

                        likesMap.put(title, likes);
                        dislikesMap.put(title, dislikes);
                    } catch (NumberFormatException e) {
                        continue;
                    }
                }
            }
        }
    }

    private static void displayMostLikedAndDislikedRatio(Map<String, Integer> likesMap, Map<String, Integer> dislikesMap) {

    }

    private static void saveRatioResults(Map<String, Integer> likesMap, Map<String, Integer> dislikesMap, String outputPath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputPath))) {
            // Write header to the CSV file
            writer.println("Title,MostLikedCount,MostDislikedCount,LikeDislikeRatio");

            // Sort titles by ratio from most to least
            List<String> sortedTitles = likesMap.entrySet().stream()
                    .sorted((entry1, entry2) -> {
                        double ratio1 = getRatio(entry1, dislikesMap);
                        double ratio2 = getRatio(entry2, dislikesMap);
                        return Double.compare(ratio2, ratio1);
                    })
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            for (String title : sortedTitles) {
                int mostLikedCount = likesMap.getOrDefault(title, 0);
                int mostDislikedCount = dislikesMap.getOrDefault(title, 0);
                double ratio = (mostLikedCount + mostDislikedCount == 0) ? 0 : (double) mostLikedCount / (mostLikedCount + mostDislikedCount);
                writer.println(title + "," + mostLikedCount + "," + mostDislikedCount + "," + ratio);
            }
        } catch (IOException e) {
        }
    }

    private static double getRatio(Map.Entry<String, Integer> entry, Map<String, Integer> dislikesMap) {
        int likes = entry.getValue();
        int dislikes = dislikesMap.getOrDefault(entry.getKey(), 0);
        return (likes + dislikes == 0) ? 0 : (double) likes / (likes + dislikes);
    }
} // end of Likes and Dislikes Ratio Class

