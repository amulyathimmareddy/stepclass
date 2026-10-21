package week3.assignment;

/**
 * Problem M1: From Parallel Arrays to a Class — Library Inventory
 * Replaces disconnected parallel arrays with a cohesive BookInventory object.
 */
public class BookInventory {

    private String title;
    private String author;
    private int copiesAvailable;

    /**
     * Parameterized constructor initializing all three book properties.
     *
     * @param title           the book title
     * @param author          the author's name
     * @param copiesAvailable number of physical copies in inventory
     */
    public BookInventory(String title, String author, int copiesAvailable) {
        this.title = (title != null) ? title.trim() : "Untitled";
        this.author = (author != null) ? author.trim() : "Unknown Author";
        this.copiesAvailable = Math.max(0, copiesAvailable);
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getCopiesAvailable() {
        return copiesAvailable;
    }

    /**
     * Prints a formatted inventory entry line.
     * Example: "Clean Code by Robert C. Martin - 3 copies available"
     */
    public void printEntry() {
        System.out.printf("%s by %s - %d copies available%n", title, author, copiesAvailable);
    }

    /**
     * Prints an entire catalogue of books in sequence.
     *
     * @param catalogue array of BookInventory entries
     */
    public static void displayCatalogue(BookInventory[] catalogue) {
        if (catalogue == null || catalogue.length == 0) {
            System.out.println("No inventory records found.");
            return;
        }

        for (BookInventory book : catalogue) {
            if (book != null) {
                book.printEntry();
            }
        }
    }

    public static void main(String[] args) {
        try {
            // Test data matching problem specification
            BookInventory[] books = new BookInventory[] {
                new BookInventory("Clean Code", "Robert C. Martin", 3),
                new BookInventory("Effective Java", "Joshua Bloch", 5),
                new BookInventory("Refactoring", "Martin Fowler", 0),
                new BookInventory("Design Patterns", "GoF", 2)
            };

            displayCatalogue(books);
        } catch (Exception e) {
            System.err.println("Error processing library inventory: " + e.getMessage());
        }
    }
}
