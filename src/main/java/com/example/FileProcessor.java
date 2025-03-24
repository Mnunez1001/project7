package com.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileProcessor {
    public static void main(String[] args) {

        String filePath = "target/classes/text2.txt"; // Specifies the file to read from.
        String outputFilePath = "target/classes/2sorted_words.txt"; // Output file. Stores sorted words.
        String outputFilePath2 = "target/classes/2word_frequencies.txt"; // Output file. Stores word frequencies.

        List<String> words = new ArrayList<>(); // List to hold extracted words
        Map<String, Integer> wordFrequencies = new HashMap<>(); // Map to count word occurrences

        // Opens the file using BufferedReader for efficient reading.
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Read each line of the file
            while ((line = reader.readLine()) != null) {
                // System.out.println(line); // Print to verify reading works
                // Uses regular expressions (Pattern) to extract words (letters and numbers).
                Pattern pattern = Pattern.compile("[a-zA-Z0-9]+");
                // Converts words to lowercase.
                Matcher matcher = pattern.matcher(line.toLowerCase());

                while (matcher.find()) {
                    String word = matcher.group();
                    // Exclude words that are only numbers
                    if (!word.matches("\\d+")) {
                        words.add(word);
                        //
                        wordFrequencies.put(word, wordFrequencies.getOrDefault(word, 0) + 1);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }

        // Print first 20 words to check correctness
        System.out.println("Extracted Words: " + words.subList(0, Math.min(20, words.size())));

        // Sort words alphabetically
        Collections.sort(words);

        // Write sorted words to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            // Write each word to a new line
            for (String word : words) {
                writer.write(word);
                writer.newLine();
            }
            System.out.println("Sorted words written to: " + outputFilePath);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }

        // Creates a list of WordCount objects, where each object holds:
        // A word and its frequency count.
        List<WordCount> wordCountList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : wordFrequencies.entrySet()) {
            wordCountList.add(new WordCount(entry.getKey(), entry.getValue()));
        }

        // Sort List by count in descending order
        Collections.sort(wordCountList);

        // Writes word-frequency pairs to outputFilePath2.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath2))) {
            // Write each word count to a new line
            for (WordCount wordCount : wordCountList) {
                writer.write(wordCount.toString());
                writer.newLine();
            }
            System.out.println("Word frequencies written to: " + outputFilePath2);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }

        // Start interactive word lookup
        Scanner scanner = new Scanner(System.in);
        // Loops infinitely, prompting the user for a word.
        while (true) {
            System.out.print("\nEnter a word to search for its frequency (or type 'exit' to quit): ");
            String input = scanner.nextLine().trim().toLowerCase();

            //// Exits if the user types "exit".
            if (input.equals("exit")) {
                System.out.println("Goodbye!");
                break;
            }

            // Ensure input is a valid word (not a number)
            if (input.matches("\\d+")) {
                System.out.println(
                        "The word \"" + input + "\" is a number and has no frequency. Please try another word.");
                continue;
            }

            // Check if word exists in map
            // Displays word frequency if found.
            if (wordFrequencies.containsKey(input)) {
                System.out.println("The word \"" + input + "\" appears " + wordFrequencies.get(input) + " times.");
            } else {
                System.out.println("The word \"" + input
                        + "\" is not present or doesn't have any frequencies. Please try another word.");
            }
        }

        scanner.close();

    }
}