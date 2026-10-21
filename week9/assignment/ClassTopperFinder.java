package week9.assignment;

import java.util.Arrays;

/**
 * ClassTopperFinder determines the student with the highest total marks across subjects in a 2D grid.
 * Breaks ties by choosing the student with the lowest row index.
 *
 * Complexity Analysis:
 * - Time Complexity: O(m * n) where m is the number of students (rows) and n is the number of
 *   subjects (columns), because each cell's marks are visited once.
 * - Additional Space Complexity: O(1) beyond input storage, as only running sums and best index/total
 *   scalars are stored.
 */
public class ClassTopperFinder {

    /**
     * Immutable result holding the topper's row index and total marks.
     */
    public static class TopperResult {
        private final int rowIndex;
        private final int total;

        public TopperResult(int rowIndex, int total) {
            this.rowIndex = rowIndex;
            this.total = total;
        }

        public int getRowIndex() {
            return rowIndex;
        }

        public int getTotal() {
            return total;
        }

        @Override
        public String toString() {
            return "(" + rowIndex + ", " + total + ")";
        }
    }

    /**
     * Finds the student with the highest total marks.
     *
     * @param marks 2D array where marks[i][j] is the mark of student i in subject j
     * @return TopperResult containing (rowIndex, total)
     */
    public static TopperResult findTopper(int[][] marks) {
        if (marks == null || marks.length == 0) {
            throw new IllegalArgumentException("Marks grid cannot be null or empty");
        }

        int bestRow = -1;
        int bestTotal = Integer.MIN_VALUE;

        for (int r = 0; r < marks.length; r++) {
            int currentTotal = 0;
            if (marks[r] != null) {
                for (int c = 0; c < marks[r].length; c++) {
                    currentTotal += marks[r][c];
                }
            }

            // Strictly greater ensures tie goes to the smallest row index
            if (currentTotal > bestTotal) {
                bestTotal = currentTotal;
                bestRow = r;
            }
        }

        return new TopperResult(bestRow, bestTotal);
    }

    public static void main(String[] args) {
        int[][] marks = {
                {78, 85, 90},
                {88, 92, 79},
                {65, 70, 95}
        };

        System.out.println("Marks Grid:");
        for (int i = 0; i < marks.length; i++) {
            System.out.println("Student " + i + ": " + Arrays.toString(marks[i]));
        }

        TopperResult result = findTopper(marks);
        System.out.println("Expected Output: (1, 259)");
        System.out.println("Actual Output:   " + result);
    }
}
