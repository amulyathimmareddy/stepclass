package week1.assignment;



public class MovieReviewWordLengthProfiler {

    /**
     * Splits the review into individual words and classifies them by length:
     * Short: 1-4 letters
     * Medium: 5-8 letters
     * Long: 9+ letters
     */
    public static void classifyWordLengths(String review) {
        if (review == null || review.trim().isEmpty()) {
            System.out.println("Short: 0 | Medium: 0 | Long: 0");
            return;
        }

        // Split by one or more whitespace characters
        String[] words = review.trim().split("\\s+");

        int shortCount = 0;
        int mediumCount = 0;
        int longCount = 0;

        for (String word : words) {
            // Remove punctuation to count only letters if needed, or take word length directly
            int length = word.length();

            if (length >= 1 && length <= 4) {
                shortCount++;
            } else if (length >= 5 && length <= 8) {
                mediumCount++;
            } else if (length >= 9) {
                longCount++;
            }
        }

        // Display results matching the exact sample output format
        System.out.printf("Short: %d | Medium: %d | Long: %d%n", shortCount, mediumCount, longCount);
    }

    public static void main(String[] args) {
        // Sample Test Case:
        // Short (1-4): "This" (4), "was" (3), "and" (3) -> 3
        // Medium (5-8): "movie" (5)                     -> 1
        // Long (9+): "absolutely" (10), "fantastic" (9), "thrilling" (9) -> 3
        String review = "This movie was absolutely fantastic and thrilling";
        classifyWordLengths(review);
    }
}
