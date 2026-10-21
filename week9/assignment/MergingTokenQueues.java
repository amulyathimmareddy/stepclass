package week9.assignment;

import java.util.Arrays;

/**
 * MergingTokenQueues merges two pre-sorted token lists into a single sorted list
 * using a two-pointer technique in linear time.
 *
 * Complexity Comparison:
 * - Two-Pointer Merge:
 *   - Time Complexity: O(m + n) where m and n are the lengths of counterA and counterB,
 *     because each token is visited exactly once.
 *   - Space Complexity: O(m + n) to hold the merged resulting array.
 * - Naive Concatenation and Sort:
 *   - Appending both lists takes O(m + n) followed by standard comparison sort taking
 *     O((m + n) log(m + n)), which unnecessarily wastes time by ignoring existing sorted order.
 */
public class MergingTokenQueues {

    /**
     * Merges two sorted arrays into a single sorted array.
     *
     * @param counterA first sorted array
     * @param counterB second sorted array
     * @return merged sorted array preserving duplicates
     */
    public static int[] mergeTokens(int[] counterA, int[] counterB) {
        if (counterA == null && counterB == null) {
            return new int[0];
        }
        if (counterA == null || counterA.length == 0) {
            return counterB != null ? Arrays.copyOf(counterB, counterB.length) : new int[0];
        }
        if (counterB == null || counterB.length == 0) {
            return Arrays.copyOf(counterA, counterA.length);
        }

        int m = counterA.length;
        int n = counterB.length;
        int[] merged = new int[m + n];

        int i = 0;
        int j = 0;
        int k = 0;

        while (i < m && j < n) {
            if (counterA[i] <= counterB[j]) {
                merged[k++] = counterA[i++];
            } else {
                merged[k++] = counterB[j++];
            }
        }

        while (i < m) {
            merged[k++] = counterA[i++];
        }

        while (j < n) {
            merged[k++] = counterB[j++];
        }

        return merged;
    }

    public static void main(String[] args) {
        int[] counterA1 = {3, 8, 15, 20};
        int[] counterB1 = {5, 8, 12};
        int[] res1 = mergeTokens(counterA1, counterB1);
        System.out.println("Sample 1 Output: " + Arrays.toString(res1));

        int[] counterA2 = {};
        int[] counterB2 = {4, 9};
        int[] res2 = mergeTokens(counterA2, counterB2);
        System.out.println("Sample 2 Output: " + Arrays.toString(res2));
    }
}
