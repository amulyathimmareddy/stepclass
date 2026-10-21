package week9.practice;

import java.util.Arrays;

/**
 * MaximizeAreaBetweenBoundaries computes the maximum water a container can hold between
 * vertical boundary lines (Container With Most Water problem).
 *
 * Complexity Comparison:
 * 1. Brute-Force Approach:
 *    - Examines every pair (i, j) with i < j.
 *    - Time Complexity: O(n^2) where n is array length.
 *    - Space Complexity: O(1).
 * 2. Two-Pointer Optimal Approach:
 *    - Places left pointer at 0 and right pointer at n - 1.
 *    - The width is maximal initially; moving the pointer pointing to the shorter line
 *      inward is the only way to potentially find a larger area, since the width decreases
 *      and the bottleneck is always min(height[left], height[right]).
 *    - Time Complexity: O(n), as each step moves one pointer closer until they meet.
 *    - Additional Space Complexity: O(1), using only index and area tracking variables.
 */
public class MaximizeAreaBetweenBoundaries {

    /**
     * Optimal O(n) solution using two converging pointers.
     *
     * @param heights array of non-negative vertical line heights
     * @return maximum container area achievable
     */
    public static int maxContainerArea(int[] heights) {
        if (heights == null || heights.length < 2) {
            return 0;
        }

        int left = 0;
        int right = heights.length - 1;
        int maxArea = 0;

        while (left < right) {
            int width = right - left;
            int hLeft = heights[left];
            int hRight = heights[right];
            int minHeight = Math.min(hLeft, hRight);
            int currentArea = minHeight * width;

            if (currentArea > maxArea) {
                maxArea = currentArea;
            }

            // Move the pointer corresponding to the shorter boundary
            if (hLeft < hRight) {
                left++;
            } else {
                right--;
            }
        }

        return maxArea;
    }

    /**
     * Brute-force O(n^2) solution for verification.
     *
     * @param heights array of non-negative vertical line heights
     * @return maximum container area achievable
     */
    public static int maxContainerAreaBruteForce(int[] heights) {
        if (heights == null || heights.length < 2) {
            return 0;
        }

        int maxArea = 0;
        for (int i = 0; i < heights.length; i++) {
            for (int j = i + 1; j < heights.length; j++) {
                int area = Math.min(heights[i], heights[j]) * (j - i);
                if (area > maxArea) {
                    maxArea = area;
                }
            }
        }
        return maxArea;
    }

    public static void main(String[] args) {
        int[] heights = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        System.out.println("Heights: " + Arrays.toString(heights));

        int optimalResult = maxContainerArea(heights);
        int bruteForceResult = maxContainerAreaBruteForce(heights);

        System.out.println("Expected Output:   49");
        System.out.println("Optimal Output:     " + optimalResult);
        System.out.println("BruteForce Output:  " + bruteForceResult);
    }
}
