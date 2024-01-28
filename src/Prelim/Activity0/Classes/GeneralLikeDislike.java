package Prelim.Activity0.Classes;

/**
 * The GeneralLikeDislike class processes video data from a CSV file, displays
 * the most liked and disliked videos, and creates new CSV files with the Top Likes/Dislikes information. And also shows the Most Likes
 * and Least Likes as well as with the Dislike feature.
 *
 * @author Escaño, Nichole Jhoy C.
 * @since January 16, 2024
 *
 * Algorithm:
 * 1. Read video data from the data.csv file and create a list of CSVData objects.
 * 2. Provided a method viewMostLikes(String tag) that filters the data based on a given tag. Sort the filtered data based on likes in descending order.
 * 3. Provided a method viewMostDislikes(String tag) that filters the data based on a given tag. Sort the filtered data based on dislikes in descending order.
 * 4. Created methods to calculate the maximum and minimum values of likes and dislikes in a given list of CSVData.
 * 5. Implemented createMostLikesCSVFile and createMostDislikedCSVFile methods for the new CSV files containing the top likes and dislikes information
 *    for videos with a specified tag.
 * 7. Provided a method getLikesSummary and getDislikesSummary to generate summaries of the likes and dislikes based on a tag.
 * 8. Implemented a case-insensitive string checker to determine if a source string contains a specified target string.
 *
 */

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GeneralLikeDislike {
    private List<CSVData> data;

    public GeneralLikeDislike(String filePath) {
        this.data = readCSVFile(filePath);
    }

    /**
     * Reads video data from the specified CSV file.
     *
     * @param filePath The path of the CSV file.
     * @return A list of CSVData objects representing video data.
     */
    private List<CSVData> readCSVFile(String filePath) {
        List<CSVData> data = new ArrayList<>();

        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8))) {
            // Read the header line
            br.readLine();

            while ((line = br.readLine()) != null) {
                try {
                    String[] rowData = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                    if (rowData.length >= 16) {
                        int likes = parseInteger(rowData[8].trim());  // Likes are in column I
                        int dislikes = parseInteger(rowData[9].trim());  // Dislikes are in column J
                        String title = rowData[2]; // Title is in column C
                        String tags = rowData[6]; // Tags is in column G

                        CSVData videoData = new CSVData(title, likes, dislikes,tags);
                        data.add(videoData);
                    }
                } catch (Exception e) {
                    // Handle parsing exception
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    } // end of readCSVFile method

    /**
     * Parses an integer from the given value. If the value is empty or equals
     * "\"\"", returns 0.
     *
     * @param value The string to parse.
     * @return The parsed integer.
     */
    private int parseInteger(String value) {
        if (value.isEmpty() || value.equals("\"\"")) {
            return 0;
        }
        return Integer.parseInt(value);
    } // end of parseInteger method

    /**
     * Displays the titles and like counts of the most liked videos with the
     * specified tag.
     *
     * @param tag The tag to filter by.
     */
    public void viewMostLikes(String tag) {
        try {
            if (data != null) {
                List<CSVData> filteredData = data.stream()
                        .filter(videoData -> caseInsensitiveChecker(videoData.getTags(), tag))
                        .collect(Collectors.toList());

                if (!filteredData.isEmpty()) {
                    Collections.sort(filteredData, Comparator.comparingInt(CSVData::getLikes).reversed());

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    } // end of viewMostLikes method

    /**
     * Displays the titles and dislike counts of the most disliked videos with
     * the specified tag.
     *
     * @param tag The tag to filter by.
     */
    public void viewMostDislikes(String tag) {
        try {
            if (data != null) {
                List<CSVData> filteredData = data.stream()
                        .filter(videoData -> caseInsensitiveChecker(videoData.getTags(), tag))
                        .collect(Collectors.toList());

                if (!filteredData.isEmpty()) {
                    Collections.sort(filteredData, Comparator.comparingInt(CSVData::getDislikes).reversed());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }  // end of viewMostDislikes method

    /**
     * Checks whether a given source string contains a specified target string.
     *
     * @param source The source string.
     * @param target The target string to check for.
     * @return True if the source contains the target; otherwise, false.
     */
    private static boolean caseInsensitiveChecker(String source, String target) {
        return source.toLowerCase().contains(target.toLowerCase());
    } // end of caseInsensitiveChecker method

    /**
     * Gets the maximum number of likes from a list of video data.
     *
     * @param data The list of video data.
     * @return The maximum number of likes.
     */
    private int getMostLikes(List<CSVData> data) {
        return data.stream().mapToInt(CSVData::getLikes).max().orElse(0);
    } // end of getMostLikes method

    /**
     * Gets the minimum number of likes from a list of video data.
     *
     * @param data The list of video data.
     * @return The minimum number of likes.
     */
    private int getLeastLikes(List<CSVData> data) {
        return data.stream().mapToInt(CSVData::getLikes).min().orElse(0);
    } // end of getLeastLikes method

    /**
     * Gets the maximum number of dislikes from a list of video data.
     *
     * @param data The list of video data.
     * @return The maximum number of dislikes.
     */
    private int getMostDislikes(List<CSVData> data) {
        return data.stream().mapToInt(CSVData::getDislikes).max().orElse(0);
    } // end of getMostDislikes method

    /**
     * Gets the minimum number of dislikes from a list of video data.
     *
     * @param data The list of video data.
     * @return The minimum number of dislikes.
     */
    private int getLeastDislikes(List<CSVData> data) {
        return data.stream().mapToInt(CSVData::getDislikes).min().orElse(0);
    } // end of getLeastDislikes method

    /**
     * Creates new CSV files (LikesOutput.csv and DislikesOutput.csv) with the
     * Top Likes/Dislikes information for videos with the specified tag.
     *
     * @param baseFilePath The base file path for the output CSV files.
     * @param tag The tag to filter by.
     */
    public void createMostLikesCSVFile(String baseFilePath, String tag) {
        String sanitizedTag = tag.replaceAll("[^a-zA-Z0-9]", "_");

        String likesOutputFilePath = baseFilePath + "_" + sanitizedTag + ".csv";

        try (PrintWriter likesWriter = new PrintWriter(new FileWriter(likesOutputFilePath, false))) {
            if (data != null && !data.isEmpty()) {
                List<CSVData> likesData = data.stream()
                        .filter(videoData -> caseInsensitiveChecker(videoData.getTags(), tag))
                        .sorted(Comparator.comparingInt(CSVData::getLikes).reversed())
                        .collect(Collectors.toList());

                int mostLikes = getMostLikes(likesData);
                int leastLikes = getLeastLikes(likesData);

                likesWriter.println("Most Likes: " + mostLikes + ", Least Likes: " + leastLikes);
                likesWriter.println("Title, Tag, Like Count");

                for (CSVData videoData : likesData) {
                    likesWriter.println(videoData.getTitle() + "," + tag + "," + videoData.getLikes());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets a summary of likes based on the specified tag.
     *
     * @param tag The tag to filter by.
     * @return A string summarizing likes information.
     */
    public String getLikesSummary(String tag) {
        if (data != null && !data.isEmpty()) {
            int mostLikes = getMostLikes(data.stream()
                    .filter(videoData -> caseInsensitiveChecker(videoData.getTags(), tag))
                    .collect(Collectors.toList()));
            int leastLikes = getLeastLikes(data.stream()
                    .filter(videoData -> caseInsensitiveChecker(videoData.getTags(), tag))
                    .collect(Collectors.toList()));

            return "Tag: " + tag + "\nMost Likes: " + mostLikes + "\nLeast Likes: " + leastLikes;
        }
        return "No data available for the tag: " + tag;
    }

    /**
     * Creates new CSV files (LikesOutput.csv and DislikesOutput.csv) with the
     * Top Dislikes information for videos with the specified tag.
     *
     * @param baseFilePath The base file path for the output CSV files.
     * @param tag The tag to filter by.
     */
    public void createMostDislikesCSVFile(String baseFilePath, String tag) {

        String sanitizedTag = tag.replaceAll("[^a-zA-Z0-9]", "_");
        String dislikesOutputFilePath = baseFilePath + "_" + sanitizedTag + ".csv";

        try (PrintWriter dislikesWriter = new PrintWriter(new FileWriter(dislikesOutputFilePath, false))) {
            if (data != null && !data.isEmpty()) {
                List<CSVData> dislikesData = data.stream()
                        .filter(videoData -> caseInsensitiveChecker(videoData.getTags(), tag))
                        .sorted(Comparator.comparingInt(CSVData::getDislikes).reversed())
                        .collect(Collectors.toList());

                int mostDislikes = getMostDislikes(dislikesData);
                int leastDislikes = getLeastDislikes(dislikesData);

                dislikesWriter.println("Most Dislikes: " + mostDislikes + ", Least Dislikes: " + leastDislikes);
                dislikesWriter.println("Title, Tag, Dislike Count");

                for (CSVData videoData : dislikesData) {
                    dislikesWriter.println(videoData.getTitle() + "," + tag + "," + videoData.getDislikes());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets a summary of dislikes based on the specified tag.
     *
     * @param tag The tag to filter by.
     * @return A string summarizing dislikes information.
     */
    public String getDislikesSummary(String tag) {
        if (data != null && !data.isEmpty()) {
            int mostDislikes = getMostDislikes(data.stream()
                    .filter(videoData -> caseInsensitiveChecker(videoData.getTags(), tag))
                    .collect(Collectors.toList()));
            int leastDislikes = getLeastDislikes(data.stream()
                    .filter(videoData -> caseInsensitiveChecker(videoData.getTags(), tag))
                    .collect(Collectors.toList()));

            return "Tag: " + tag + "\nMost Dislikes: " + mostDislikes + "\nLeast Dislikes: " + leastDislikes;
        }
        return "No data available for the tag: " + tag;
    }


    /**
     * Represents video data extracted from the CSV file.
     */
    public class CSVData {
        private String title;
        private int likes;
        private int dislikes;
        private String tags;

        public CSVData(String title, int likes, int dislikes, String tags) {
            this.title = title;
            this.likes = likes;
            this.dislikes = dislikes;
            this.tags = tags;
        }

        public String getTitle() {
            return title;
        }

        public int getLikes() {
            return likes;
        }

        public int getDislikes() {
            return dislikes;
        }

        public String getTags(){
            return tags;
        }

    } // end of CSVData

} // end of GeneralLikeDislike class
