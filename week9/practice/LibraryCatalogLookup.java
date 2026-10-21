package week9.practice;

import java.util.Arrays;
import java.util.List;

/**
 * LibraryCatalogLookup searches a sorted book catalog by ISBN using Binary Search.
 *
 * Complexity Analysis:
 * - Time Complexity: O(log n) where n is the number of catalog entries, because the
 *   search space is halved at each step.
 * - Additional Space Complexity: O(1) iterative binary search requires only pointer variables.
 */
public class LibraryCatalogLookup {

    /**
     * Immutable book entry record.
     */
    public static class BookEntry {
        private final String isbn;
        private final String title;

        public BookEntry(String isbn, String title) {
            if (isbn == null || title == null) {
                throw new IllegalArgumentException("ISBN and title cannot be null");
            }
            this.isbn = isbn;
            this.title = title;
        }

        public String getIsbn() {
            return isbn;
        }

        public String getTitle() {
            return title;
        }

        @Override
        public String toString() {
            return "(" + isbn + ", " + title + ")";
        }
    }

    /**
     * Finds a book's title by target ISBN using binary search on a sorted list.
     *
     * @param catalog sorted list of BookEntry objects ordered by ISBN ascending
     * @param targetIsbn target ISBN string to locate
     * @return book title if found, or "Not Found" otherwise
     */
    public static String findBook(List<BookEntry> catalog, String targetIsbn) {
        if (catalog == null || targetIsbn == null || catalog.isEmpty()) {
            return "Not Found";
        }

        int low = 0;
        int high = catalog.size() - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            BookEntry midBook = catalog.get(mid);
            int cmp = midBook.getIsbn().compareTo(targetIsbn);

            if (cmp == 0) {
                return midBook.getTitle();
            } else if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return "Not Found";
    }

    public static void main(String[] args) {
        List<BookEntry> catalog = Arrays.asList(
                new BookEntry("0001112223", "Introduction to Algebra"),
                new BookEntry("0002223334", "Beginning Python"),
                new BookEntry("0003334445", "Classic Mythology"),
                new BookEntry("0004445556", "Data and Society"),
                new BookEntry("0005556667", "European History")
        );

        String target1 = "0003334445";
        System.out.printf("Lookup ISBN %s: %s%n", target1, findBook(catalog, target1));

        String target2 = "0009998887";
        System.out.printf("Lookup ISBN %s: %s%n", target2, findBook(catalog, target2));
    }
}
