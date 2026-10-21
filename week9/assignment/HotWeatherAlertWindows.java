package week9.assignment;

import java.util.Arrays;

/**
 * HotWeatherAlertWindows counts the number of k-hour contiguous blocks whose average
 * temperature equals or exceeds a given threshold, using a sliding window algorithm.
 *
 * Complexity Comparison:
 * - Brute-Force Recomputation:
 *   - Sums all k elements from scratch for every window starting at index 0 to n - k.
 *   - Time Complexity: O((n - k + 1) * k) = O(n * k), which becomes quadratic O(n^2) when k ~ n/2.
 *   - Space Complexity: O(1).
 * - Sliding Window Technique:
 *   - Initializes window sum for the first k elements in O(k).
 *   - For each subsequent step, slides the window by adding the incoming element and
 *     subtracting the outgoing element in O(1) time.
 *   - Time Complexity: O(n) linear scan regardless of window size k.
 *   - Auxiliary Space Complexity: O(1).
 */
public class HotWeatherAlertWindows {

    /**
     * Counts how many k-length contiguous windows have average >= threshold.
     *
     * @param readings array of hourly temperature readings
     * @param k size of consecutive window in hours
     * @param threshold minimum average temperature triggering an alert
     * @return count of alert-triggering windows
     */
    public static int countAlerts(int[] readings, int k, int threshold) {
        if (readings == null || k <= 0 || readings.length < k) {
            return 0;
        }

        long requiredSum = (long) k * threshold;
        long currentWindowSum = 0;
        int alertCount = 0;

        // Compute sum of the first window
        for (int i = 0; i < k; i++) {
            currentWindowSum += readings[i];
        }

        if (currentWindowSum >= requiredSum) {
            alertCount++;
        }

        // Slide the window across the remaining readings
        for (int i = k; i < readings.length; i++) {
            currentWindowSum += readings[i] - readings[i - k];
            if (currentWindowSum >= requiredSum) {
                alertCount++;
            }
        }

        return alertCount;
    }

    /**
     * Brute force recomputation implementation for comparative validation.
     */
    public static int countAlertsBruteForce(int[] readings, int k, int threshold) {
        if (readings == null || k <= 0 || readings.length < k) {
            return 0;
        }

        long requiredSum = (long) k * threshold;
        int alertCount = 0;

        for (int i = 0; i <= readings.length - k; i++) {
            long windowSum = 0;
            for (int j = i; j < i + k; j++) {
                windowSum += readings[j];
            }
            if (windowSum >= requiredSum) {
                alertCount++;
            }
        }

        return alertCount;
    }

    public static void main(String[] args) {
        int[] readings = {2, 2, 2, 2, 5, 5, 5, 8};
        int k = 3;
        int threshold = 4;

        System.out.println("Readings: " + Arrays.toString(readings));
        System.out.printf("Window size k: %d, Threshold: %d%n", k, threshold);

        int alerts = countAlerts(readings, k, threshold);
        int alertsBruteForce = countAlertsBruteForce(readings, k, threshold);

        System.out.println("Expected Output:   3");
        System.out.println("Sliding Window:    " + alerts);
        System.out.println("Brute Force:       " + alertsBruteForce);
    }
}
