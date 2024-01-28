package Prelim.Activity0.Classes;

/**
 * @author: JAN DOLBY F. AQUINO
 * @since: January 20, 2024
 *
 * */

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


public class CountTags {
    public static void run() {

        Scanner kbd = new Scanner(System.in);

        Map<String, Integer> tagCounts = new HashMap<>();

        String[] data = readColumn(6, "Data.csv", ",");
        if (data == null) {
            System.out.println("File Cannot Be Located.");
            kbd.close();
            return;
        }

        tagCounts = countTags(data);


        Map<String, Integer> sortedTagCountsAscending = sortTagsByCountAscending(tagCounts);

        //sortedTagCountsAscending.forEach((key, value) -> System.out.println(key + " " + value));

        writeSortedTagCountsToCsv(sortedTagCountsAscending, "TagCountSortedAscending.csv");

    }

    public static String [] readColumn (int column, String filepath, String delimiter){
        ArrayList<String> columnData = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath))) {
            String currentLine;

            while ((currentLine = bufferedReader.readLine()) != null) {
                String[] data = currentLine.split(delimiter, -1); // -1 to include trailing empty strings

                // Check if the column index exists in the current row
                if (column < data.length) {
                    columnData.add(data[column]);
                } else {
                    // Handle the case where the column index doesn't exist in the current row
                    columnData.add(""); // or handle this case as needed
                }
            }

            return columnData.toArray(new String[0]);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return null;
        }
    }


    public static Map<String, Integer> countTags(String[] data) {
        Map<String, Integer> tagCounts = new HashMap<>();

        // Iterate over each index in the array data
        // variable "row" represents a single row from the data

        for (String row : data) {

            // The delimiter of each tag is "|"
            // Each row is split into individual tags based on the delimiter.
            // The split method returns an array of strings in a variable named "parts", where each tag is contained.

            String[] parts = row.split("\\|");

            // Each tag may contain double quotes (" ") which are removed using the method replace("\"", "").
            // The trim() method is used to remove any leading or trailing whitespace.

            for (String part : parts) {
                String cleanedPart = part.replace("\"", "").trim();

                // Check if the tag already exists in the tagCounts map.
                // If true, increment by 1.
                // If false, add to the map with a count of 1 using the method getOrDefault

                tagCounts.put(cleanedPart, tagCounts.getOrDefault(cleanedPart, 0) + 1);
            }
        }

        return tagCounts;
    }


    public static HashMap<String, Integer> sortTagsByCountAscending(Map<String, Integer> tagCounts){
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer> > list =
                new LinkedList<Map.Entry<String, Integer> >(tagCounts.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;

    }


    public static void writeSortedTagCountsToCsv(Map<String, Integer> sortedTagCounts, String filePath) {
        try (FileWriter csvWriter = new FileWriter(filePath)) {
            // Write header row
            csvWriter.append("Tag,Count\n");

            // Write data rows
            for (Map.Entry<String, Integer> entry : sortedTagCounts.entrySet()) {
                csvWriter.append(entry.getKey()).append(",");
                csvWriter.append(String.valueOf(entry.getValue())).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

