package week1.assignment;



import java.util.Scanner;

public class TypingAccuracyChecker {

    /**
     * Compares the original string and typed string character by character.
     * Computes accuracy percentage and identifies the position of the first mismatch.
     */
    public static void checkTypingAccuracy(String original, String typed) {
        if (original == null || typed == null) {
            System.out.println("Invalid input strings.");
            return;
        }

        int totalChars = original.length();
        int matchedCount = 0;
        int firstMismatchPos = -1;
        char originalMismatchChar = '\0';
        char typedMismatchChar = '\0';

        // Iterate through characters at corresponding positions
        for (int i = 0; i < totalChars; i++) {
            char origChar = original.charAt(i);
            char typedChar = typed.charAt(i);

            if (origChar == typedChar) {
                matchedCount++;
            } else if (firstMismatchPos == -1) {
                // 1-based indexing for display
                firstMismatchPos = i + 1;
                originalMismatchChar = origChar;
                typedMismatchChar = typedChar;
            }
        }

        // Calculate accuracy percentage
        double accuracy = totalChars > 0 ? ((double) matchedCount / totalChars) * 100.0 : 0.0;

        // Print output matching the sample format
        if (firstMismatchPos != -1) {
            System.out.printf("Matched: %d/%d | Accuracy: %.2f%% | First Mismatch at position %d ('%c' vs '%c')%n",
                    matchedCount, totalChars, accuracy, firstMismatchPos, originalMismatchChar, typedMismatchChar);
        } else {
            System.out.printf("Matched: %d/%d | Accuracy: %.2f%% | No Mismatches%n",
                    matchedCount, totalChars, accuracy);
        }
    }

    public static void main(String[] args) {
        // Test Case 1: From Sample Output
        String original1 = "hello world";
        String typed1 = "hello worlt";
        checkTypingAccuracy(original1, typed1);

        // Test Case 2: From Sample Output
        String original2 = "coding";
        String typed2 = "coding";
        checkTypingAccuracy(original2, typed2);
    }
}
