package week2.assignment;



public class WordReversalEncoder {

    /**
     * Splits the sentence into individual words, reverses each word 
     * individually using StringBuilder, and joins them back together with spaces.
     */
    public static String reverseEachWord(String sentence) {
        if (sentence == null || sentence.isEmpty()) {
            return "";
        }

        // Split sentence by single space
        String[] words = sentence.split(" ");
        StringBuilder result = new StringBuilder();

        // Loop through each word
        for (int i = 0; i < words.length; i++) {
            StringBuilder reversedWord = new StringBuilder();

            // Reverse the individual word using a loop
            for (int j = words[i].length() - 1; j >= 0; j--) {
                reversedWord.append(words[i].charAt(j));
            }

            // Append reversed word to the final result
            result.append(reversedWord);

            // Add space between words (avoiding trailing space at the end)
            if (i < words.length - 1) {
                result.append(" ");
            }
        }

        return result.toString();
    }

    public static void main(String[] args) {
        // Test Case: From Sample Input
        String input = "hello club";
        String output = reverseEachWord(input);

        System.out.println(output);
    }
}
