package week4.practice;

/**
 * Problem M1: Library Book Cataloguing
 * Demonstrates constructor overloading and this(...) chaining with default fallback logic.
 */
public class LibraryBook {

    private String title;
    private String isbn;
    private boolean catalogued;

    /**
     * Primary constructor: sets title, validated ISBN (or "PENDING"), and marks catalogued as true.
     *
     * @param title the book title
     * @param isbn  the International Standard Book Number
     */
    public LibraryBook(String title, String isbn) {
        this.title = (title != null && !title.trim().isEmpty()) ? title.trim() : "Untitled";
        if (isbn == null || isbn.trim().isEmpty()) {
            this.isbn = "PENDING";
        } else {
            this.isbn = isbn.trim();
        }
        this.catalogued = true;
    }

    /**
     * Overloaded constructor for books without a confirmed ISBN.
     * Chains to primary constructor via this(...) using "PENDING" as the fallback.
     *
     * @param title the book title
     */
    public LibraryBook(String title) {
        this(title, "PENDING");
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean isCatalogued() {
        return catalogued;
    }

    /**
     * Formats and prints catalog status line.
     * Example: "Clean Code | 978-0132350884 | Catalogued: true"
     */
    public void printCatalogStatus() {
        System.out.printf("%s | %s | Catalogued: %b%n", title, isbn, catalogued);
    }

    /**
     * Batch processes arrays of titles and ISBNs in a single pass.
     *
     * @param titles parallel array of book titles
     * @param isbns  parallel array of ISBN strings (some may be empty)
     */
    public static void processBatch(String[] titles, String[] isbns) {
        if (titles == null || isbns == null) {
            System.out.println("Invalid batch input arrays.");
            return;
        }

        int count = Math.min(titles.length, isbns.length);
        for (int i = 0; i < count; i++) {
            LibraryBook book;
            if (isbns[i] == null || isbns[i].trim().isEmpty()) {
                book = new LibraryBook(titles[i]);
            } else {
                book = new LibraryBook(titles[i], isbns[i]);
            }
            book.printCatalogStatus();
        }
    }

    public static void main(String[] args) {
        try {
            String[] titles = {"Clean Code", "Untitled Draft", "1984", "Notes"};
            String[] isbns = {"978-0132350884", "", "9780451524935", ""};

            processBatch(titles, isbns);
        } catch (Exception e) {
            System.err.println("Error processing library book batch: " + e.getMessage());
        }
    }
}
