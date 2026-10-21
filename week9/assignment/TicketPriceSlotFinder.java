package week9.assignment;

import java.util.Arrays;

/**
 * TicketPriceSlotFinder determines the index of an existing ticket price or the exact insertion
 * index required to maintain sorted order, using Binary Search.
 *
 * Complexity Comparison:
 * - Linear Scan:
 *   - Iterates through the array sequentially from index 0 until finding prices[i] >= newPrice.
 *   - Time Complexity: O(n) where n is the length of prices.
 *   - Additional Space Complexity: O(1).
 * - Binary Search:
 *   - Halves the search space at each comparison. When target is absent, the search terminates
 *     with low pointing precisely to the insertion index.
 *   - Time Complexity: O(log n).
 *   - Additional Space Complexity: O(1).
 */
public class TicketPriceSlotFinder {

    /**
     * Finds the index of newPrice or its correct insertion slot in sorted distinct array.
     *
     * @param prices sorted list of distinct prices in ascending order
     * @param newPrice candidate price
     * @return index between 0 and prices.length (inclusive)
     */
    public static int findSlot(int[] prices, int newPrice) {
        if (prices == null || prices.length == 0) {
            return 0;
        }

        int low = 0;
        int high = prices.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (prices[mid] == newPrice) {
                return mid;
            } else if (prices[mid] < newPrice) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return low;
    }

    /**
     * Linear scan implementation for comparison.
     */
    public static int findSlotLinear(int[] prices, int newPrice) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] >= newPrice) {
                return i;
            }
        }
        return prices.length;
    }

    public static void main(String[] args) {
        int[] prices = {120, 150, 200, 260};
        System.out.println("Prices: " + Arrays.toString(prices));

        int p1 = 150;
        System.out.printf("findSlot(%d): %d (Expected: 1)%n", p1, findSlot(prices, p1));

        int p2 = 210;
        System.out.printf("findSlot(%d): %d (Expected: 3)%n", p2, findSlot(prices, p2));

        int p3 = 300;
        System.out.printf("findSlot(%d): %d (Expected: 4)%n", p3, findSlot(prices, p3));
    }
}
