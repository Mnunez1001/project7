package com.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileProcessor {
    public static void main(String[] args) {
        

     String filePath = "target/classes/text.txt"; // Path to the file to read
     String outputFilePath = "target/classes/sorted_words.txt"; // Output file
     List<String> words = new ArrayList<>(); // Using List for now

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



















    }
}