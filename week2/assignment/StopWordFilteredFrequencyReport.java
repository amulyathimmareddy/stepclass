package week2.assignment;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StopWordFilteredFrequencyReport {

    /**
     * Normalizes the feedback string, excludes designated stop words,
     * computes frequency for remaining words, and prints them sorted by count in descending order.
     */
    public static void printFilteredWordFrequency(String feedback) {
        if (feedback == null || feedback.trim().isEmpty()) {
            return;
        }

        // Fixed list of stop words to filter out
        Set<String> stopWords = new HashSet<>(Arrays.asList("the", "was", "and", "a", "is", "of", "in"));

        // Step 1: Normalize: convert to lowercase and strip punctuation (e.g., periods and commas) using replace()
        String cleaned = feedback.toLowerCase();
        cleaned = cleaned.replace(".", "").replace(",", "");

        // Step 2: Split cleaned text into words using whitespace pattern
        String[] words = cleaned.trim().split("\\s+");

        // Step 3: Count frequency of each valid non-stop word
        Map<String, Integer> frequencyMap = new HashMap<>();
        for (String word : words) {
            if (!word.isEmpty() && !stopWords.contains(word)) {
                frequencyMap.put(word, frequencyMap.getOrDefault(word, 0) + 1);
            }
        }

        // Step 4: Sort entries by frequency count in descending order
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(frequencyMap.entrySet());
        entryList.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        // Step 5: Print each word with its count
        for (Map.Entry<String, Integer> entry : entryList) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public static void main(String[] args) {
        // Sample Test Case
        String feedback = "The mentor was great, the session was great and clear.";
        printFilteredWordFrequency(feedback);
    }
}