package week9.assignment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * PopularCanteenOrder identifies the most frequently ordered canteen item.
 * Breaks ties by selecting the item that appears earliest in the orders list.
 *
 * Complexity Comparison:
 * - Brute-Force Scanning:
 *   - For each element in the list, rescan the entire list to count its occurrences.
 *   - Time Complexity: O(n^2) where n is orders length.
 *   - Space Complexity: O(1).
 * - Hash-Based Counting:
 *   - In pass 1, record counts in a HashMap: O(n) time, O(u) space where u is distinct items.
 *   - In pass 2, iterate through the original array in order and choose the first item whose
 *     frequency matches the recorded maximum.
 *   - Time Complexity: O(n).
 *   - Auxiliary Space Complexity: O(u) where u <= n.
 */
public class PopularCanteenOrder {

    /**
     * Immutable result holding the most popular item name and order count.
     */
    public static class PopularItemResult {
        private final String item;
        private final int count;

        public PopularItemResult(String item, int count) {
            this.item = item;
            this.count = count;
        }

        public String getItem() {
            return item;
        }

        public int getCount() {
            return count;
        }

        @Override
        public String toString() {
            return "(\"" + item + "\", " + count + ")";
        }
    }

    /**
     * Finds the most frequent item, resolving ties by earliest first appearance.
     *
     * @param orders array of ordered item names
     * @return PopularItemResult containing (item, count)
     */
    public static PopularItemResult mostPopular(String[] orders) {
        if (orders == null || orders.length == 0) {
            throw new IllegalArgumentException("Orders list cannot be null or empty");
        }

        Map<String, Integer> counts = new HashMap<>();
        int maxCount = 0;

        // Pass 1: Build frequency map and compute highest frequency
        for (String item : orders) {
            int updated = counts.getOrDefault(item, 0) + 1;
            counts.put(item, updated);
            if (updated > maxCount) {
                maxCount = updated;
            }
        }

        // Pass 2: Traverse original sequence to select first occurrence of maxCount
        for (String item : orders) {
            if (counts.get(item) == maxCount) {
                return new PopularItemResult(item, maxCount);
            }
        }

        return new PopularItemResult(orders[0], 1);
    }

    public static void main(String[] args) {
        String[] orders1 = {"dosa", "idli", "vada", "dosa", "idli", "dosa", "tea"};
        System.out.println("Orders 1: " + Arrays.toString(orders1));
        System.out.println("Result 1: " + mostPopular(orders1));

        String[] orders2 = {"tea", "coffee", "coffee", "tea"};
        System.out.println("Orders 2: " + Arrays.toString(orders2));
        System.out.println("Result 2: " + mostPopular(orders2));
    }
}
