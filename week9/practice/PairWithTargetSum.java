package week9.practice;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * PairWithTargetSum determines if any two distinct elements in an unsorted array sum up to a target value.
 *
 * Complexity Comparison:
 * 1. Brute-Force Approach:
 *    - Compares every unique pair (i, j) with i < j.
 *    - Time Complexity: O(n^2) where n is array length.
 *    - Space Complexity: O(1) auxiliary space.
 * 2. Optimal Hash Set Approach:
 *    - Checks if the complement (target - nums[i]) was previously seen in a HashSet.
 *    - Time Complexity: O(n) average time for linear scan with O(1) hash lookups.
 *    - Space Complexity: O(n) auxiliary space to store elements in the hash set.
 */
public class PairWithTargetSum {

    /**
     * Optimal O(n) solution using HashSet.
     *
     * @param nums array of integers (can contain negatives and duplicates)
     * @param target target sum
     * @return true if two distinct elements sum to target, false otherwise
     */
    public static boolean hasPairWithSum(int[] nums, int target) {
        if (nums == null || nums.length < 2) {
            return false;
        }

        Set<Integer> seen = new HashSet<>();
        for (int num : nums) {
            int complement = target - num;
            if (seen.contains(complement)) {
                return true;
            }
            seen.add(num);
        }
        return false;
    }

    /**
     * Brute-force O(n^2) solution for benchmarking and validation.
     *
     * @param nums array of integers
     * @param target target sum
     * @return true if two distinct elements sum to target, false otherwise
     */
    public static boolean hasPairWithSumBruteForce(int[] nums, int target) {
        if (nums == null || nums.length < 2) {
            return false;
        }

        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[] nums1 = {2, 7, 11, 15};
        int target1 = 9;
        System.out.printf("Array: %s, Target: %d -> Optimal: %b, BruteForce: %b%n",
                Arrays.toString(nums1), target1,
                hasPairWithSum(nums1, target1),
                hasPairWithSumBruteForce(nums1, target1));

        int[] nums2 = {3, 4, 6};
        int target2 = 20;
        System.out.printf("Array: %s, Target: %d -> Optimal: %b, BruteForce: %b%n",
                Arrays.toString(nums2), target2,
                hasPairWithSum(nums2, target2),
                hasPairWithSumBruteForce(nums2, target2));
    }
}
