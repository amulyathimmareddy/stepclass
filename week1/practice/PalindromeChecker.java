package week1.practice;
import java.util.Scanner;

public class PalindromeChecker {

    /**
     * Approach 1: Iterative Check
     * Compares characters from both ends moving inward toward the middle.
     */
    public static boolean isPalindromeIterative(String text) {
        if (text == null) {
            return false;
        }

        int start = 0;
        int end = text.length() - 1;

        while (start < end) {
            if (text.charAt(start) != text.charAt(end)) {
                return false;
            }
            start++;
            end--;
        }
        return true;
    }

    /**
     * Approach 2: Recursive Check
     * Compares the first and last characters, recursively shrinking the substring.
     */
    public static boolean isPalindromeRecursive(String text) {
        if (text == null) {
            return false;
        }
        return recursiveHelper(text, 0, text.length() - 1);
    }

    // Helper method to avoid creating new substring objects on every recursion step
    private static boolean recursiveHelper(String text, int start, int end) {
        if (start >= end) {
            return true;
        }
        if (text.charAt(start) != text.charAt(end)) {
            return false;
        }
        return recursiveHelper(text, start + 1, end - 1);
    }

    /**
     * Approach 3: Array Reversal Check
     * Converts the string to a char array, reverses it, and compares it to the original.
     */
    public static boolean isPalindromeArrayReversal(String text) {
        if (text == null) {
            return false;
        }

        char[] originalArray = text.toCharArray();
        char[] reversedArray = new char[originalArray.length];

        for (int i = 0; i < originalArray.length; i++) {
            reversedArray[i] = originalArray[originalArray.length - 1 - i];
        }

        // Compare each character
        for (int i = 0; i < originalArray.length; i++) {
            if (originalArray[i] != reversedArray[i]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter text to check: ");
        String input = scanner.nextLine();

        // Evaluate all three approaches
        boolean iterativeResult = isPalindromeIterative(input);
        boolean recursiveResult = isPalindromeRecursive(input);
        boolean arrayReversalResult = isPalindromeArrayReversal(input);

        // Format labels
        String iterLabel = iterativeResult ? "Palindrome" : "Not Palindrome";
        String recurLabel = recursiveResult ? "Palindrome" : "Not Palindrome";
        String arrayLabel = arrayReversalResult ? "Palindrome" : "Not Palindrome";

        // Print final formatted result
        System.out.printf("Iterative: %s | Recursive: %s | Array Reversal: %s%n",
                iterLabel, recurLabel, arrayLabel);

        scanner.close();
    }
}