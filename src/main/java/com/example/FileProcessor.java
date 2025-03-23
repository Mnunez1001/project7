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
        

     String filePath = "target/classes/text2.txt"; // Path to the file to read
     String outputFilePath = "target/classes/2sorted_words.txt"; // Output file
     String outputFilePath2 = "target/classes/2word_frequencies.txt"; // Output file
     
     List<String> words = new ArrayList<>(); // Using List for now
     Map<String, Integer> wordFrequencies = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                //System.out.println(line); // Print to verify reading works
                 // Extract words (ignoring punctuation, keeping words with numbers)
                Pattern pattern = Pattern.compile("[a-zA-Z0-9]+"); 
                Matcher matcher = pattern.matcher(line.toLowerCase());

                while (matcher.find()) {
                    String word = matcher.group();
                    // Exclude words that are only numbers
                    if (!word.matches("\\d+")) {
                        words.add(word);
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
            for (String word : words) {
                writer.write(word);
                writer.newLine();
            }
            System.out.println("Sorted words written to: " + outputFilePath);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }

        // Convert HashMap to List of WordCount objects
        List<WordCount> wordCountList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : wordFrequencies.entrySet()) {
            wordCountList.add(new WordCount(entry.getKey(), entry.getValue()));
        }
        
        // Sort List by count in descending order
        Collections.sort(wordCountList);

        // Write word frequencies to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath2))) {
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
        while (true) {
            System.out.print("\nEnter a word to search for its frequency (or type 'exit' to quit): ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("exit")) {
                System.out.println("Goodbye!");
                break;
            }

            // Ensure input is a valid word (not a number)
            if (input.matches("\\d+")) {
                System.out.println("The word \"" + input + "\" is a number and has no frequency. Please try another word.");
                continue;
            }

            // Check if word exists in map
            if (wordFrequencies.containsKey(input)) {
                System.out.println("The word \"" + input + "\" appears " + wordFrequencies.get(input) + " times.");
            } else {
                System.out.println("The word \"" + input + "\" is not present or doesn't have any frequencies. Please try another word.");
            }
        }

        scanner.close();



















    }
}