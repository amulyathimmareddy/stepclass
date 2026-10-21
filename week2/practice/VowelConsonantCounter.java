package week2.practice;



import java.util.Scanner;

public class VowelConsonantCounter {

    /**
     * Counts vowels (a, e, i, o, u) and consonants separately, ignoring spaces.
     */
    public static void countVowelsAndConsonants(String text) {
        if (text == null) {
            System.out.println("Vowels: 0 | Consonants: 0");
            return;
        }

        int vowels = 0;
        int consonants = 0;

        // Traverse each character using charAt()
        for (int i = 0; i < text.length(); i++) {
            char ch = Character.toLowerCase(text.charAt(i));

            // Check if the character is an English letter
            if (ch >= 'a' && ch <= 'z') {
                if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
                    vowels++;
                } else {
                    consonants++;
                }
            }
            // Spaces and non-alphabetic characters are automatically ignored
        }

        // Print formatted output matching the sample format
        System.out.printf("Vowels: %d | Consonants: %d%n", vowels, consonants);
    }

    public static void main(String[] args) {
        // Test Case from Sample Output
        String title = "Java Programming";
        countVowelsAndConsonants(title);

        // Optional: Interactive user input via Scanner
        /*
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter book title: ");
        String userInput = scanner.nextLine();
        countVowelsAndConsonants(userInput);
        scanner.close();
        */
    }
}
