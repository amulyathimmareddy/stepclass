package week9.practice;

import java.util.Arrays;

/**
 * WarehouseGridSummary processes a 2D layout grid of storage bins to compute total items
 * and locate the coordinates of the first occurrence of the maximum bin.
 *
 * Complexity Analysis:
 * - Time Complexity: O(m * n) where m is the number of rows and n is the number of columns,
 *   as every cell must be visited exactly once.
 * - Additional Space Complexity: O(1) beyond input storage, as only constant scalar variables
 *   are maintained for total count, max value, and coordinates.
 */
public class WarehouseGridSummary {

    /**
     * 2D Coordinate position.
     */
    public static class Coordinate {
        private final int row;
        private final int col;

        public Coordinate(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }

        @Override
        public String toString() {
            return "(" + row + ", " + col + ")";
        }
    }

    /**
     * Summary result containing total items and maximum bin coordinate.
     */
    public static class SummaryResult {
        private final long totalItems;
        private final Coordinate maxCoordinate;

        public SummaryResult(long totalItems, Coordinate maxCoordinate) {
            this.totalItems = totalItems;
            this.maxCoordinate = maxCoordinate;
        }

        public long getTotalItems() {
            return totalItems;
        }

        public Coordinate getMaxCoordinate() {
            return maxCoordinate;
        }

        @Override
        public String toString() {
            return "(" + totalItems + ", " + maxCoordinate + ")";
        }
    }

    /**
     * Computes the warehouse summary across all storage cells.
     *
     * @param grid 2D array representing bin item counts
     * @return SummaryResult with total items and first max coordinate
     */
    public static SummaryResult warehouseSummary(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return new SummaryResult(0, new Coordinate(-1, -1));
        }

        long totalItems = 0;
        int maxItems = Integer.MIN_VALUE;
        int maxRow = 0;
        int maxCol = 0;

        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                int current = grid[r][c];
                totalItems += current;
                if (current > maxItems) {
                    maxItems = current;
                    maxRow = r;
                    maxCol = c;
                }
            }
        }

        return new SummaryResult(totalItems, new Coordinate(maxRow, maxCol));
    }

    public static void main(String[] args) {
        int[][] grid = {
                {4, 9, 2},
                {7, 1, 6},
                {3, 12, 5}
        };

        System.out.println("Warehouse Grid:");
        for (int[] row : grid) {
            System.out.println(Arrays.toString(row));
        }

        SummaryResult summary = warehouseSummary(grid);
        System.out.println("Expected Output: (49, (2, 1))");
        System.out.println("Actual Output:   " + summary);
    }
}
