package com.example;

/**
 * The WordCount class represents a word and its frequency count.
 * It implements Comparable to allow sorting based on frequency in descending
 * order.
 * 
 * @author Miguel Alexander Nunez Palomares
 * @version 1.0
 */
public class WordCount implements Comparable<WordCount> {
    private String word;
    private int count;

    /**
     * Constructs a WordCount object with the specified word and its frequency
     * count.
     * 
     * @param word  the word to be stored
     * @param count the frequency of the word
     */
    public WordCount(String word, int count) {
        this.word = word;
        this.count = count;
    }

    /**
     * Returns the word associated with this object.
     * 
     * @return the word
     */
    public String getWord() {
        return word;
    }

    /**
     * Returns the frequency count of the word.
     * 
     * @return the count of occurrences
     */
    public int getCount() {
        return count;
    }

    /**
     * Compares this WordCount object to another based on the frequency count.
     * Sorting is done in descending order (higher count comes first).
     * 
     * @param other the other WordCount object to compare against
     * @return a negative integer if this count is greater than the other,
     *         zero if equal, or a positive integer if this count is less than the
     *         other
     */
    @Override
    public int compareTo(WordCount other) {
        return Integer.compare(other.count, this.count); // Sort in descending order
    }

    /**
     * Returns a string representation of the WordCount object.
     * The format is "word: count".
     * 
     * @return a string representing the word and its count
     */
    @Override
    public String toString() {
        return word + ": " + count;
    }

}
