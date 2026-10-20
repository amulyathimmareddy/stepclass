package week1.practice;



import java.util.Scanner;

public class FirstNonRepeatingCharacter {

    /**
     * Finds and returns the first character with a frequency of 1.
     * Returns '\0' (null character) if no non-repeating character exists.
     */
    public static char findFirstNonRepeatingChar(String text) {
        if (text == null || text.isEmpty()) {
            return '\0';
        }

        // Standard ASCII/Extended ASCII frequency array (256 characters)
        int[] frequency = new int[256];

        // Step 1: Count frequency of each character
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            frequency[ch]++;
        }

        // Step 2: Scan left to right to find the first character with frequency 1
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (frequency[ch] == 1) {
                return ch; // Early exit on the first unique character
            }
        }

        return '\0';
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter text: ");
        String input = scanner.nextLine();

        char result = findFirstNonRepeatingChar(input);

        // Display formatted output matching the sample format
        if (result != '\0') {
            System.out.println("First Non-Repeating Character: '" + result + "'");
        } else {
            System.out.println("No Non-Repeating Character Found");
        }

        scanner.close();
    }
}
