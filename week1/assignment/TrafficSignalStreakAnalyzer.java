package week1.assignment;



public class TrafficSignalStreakAnalyzer {

    /**
     * Scans through the signal log string to track consecutive identical characters
     * and reports the longest streak's color and count.
     */
    public static void findLongestStreak(String signalLog) {
        if (signalLog == null || signalLog.isEmpty()) {
            System.out.println("No signals recorded.");
            return;
        }

        char maxChar = signalLog.charAt(0);
        int maxLength = 1;

        char currentChar = signalLog.charAt(0);
        int currentLength = 1;

        // Traverse through the log starting from the second character
        for (int i = 1; i < signalLog.length(); i++) {
            if (signalLog.charAt(i) == currentChar) {
                currentLength++;
            } else {
                // If streak broke, update running maximum if current is larger
                if (currentLength > maxLength) {
                    maxLength = currentLength;
                    maxChar = currentChar;
                }
                // Reset counter for the new character
                currentChar = signalLog.charAt(i);
                currentLength = 1;
            }
        }

        // Final check for the streak reaching the end of the string
        if (currentLength > maxLength) {
            maxLength = currentLength;
            maxChar = currentChar;
        }

        // Output matching the required format
        System.out.printf("Longest Streak: '%c' repeated %d times%n", maxChar, maxLength);
    }

    public static void main(String[] args) {
        // Test Case 1: From Sample Output
        String log1 = "RRGGGYRR";
        findLongestStreak(log1);

        // Test Case 2: From Sample Output
        String log2 = "RRRRYYGG";
        findLongestStreak(log2);
    }
}
