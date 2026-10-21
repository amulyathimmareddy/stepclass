package week3.practice;

/**
 * Problem M4: Reference Copies and a Shared ID Card
 * Demonstrates reference copying, memory pointer aliasing, and reference identity (==).
 */
public class IdCard {

    // Instance fields
    String name;
    int booksIssued;

    /**
     * Constructs an IdCard object with the cardholder's name and issued book count.
     *
     * @param name        the student's name
     * @param booksIssued initial count of books issued
     */
    public IdCard(String name, int booksIssued) {
        this.name = (name != null) ? name.trim() : "Unknown";
        this.booksIssued = Math.max(0, booksIssued);
    }

    /**
     * Demonstrates reference assignment vs new instance creation.
     */
    public static void demonstrateReferenceCopy() {
        // Step 1: Create initial IdCard object
        IdCard ravi = new IdCard("Ravi", 0);

        // Step 2: Assign reference copy pointing to the same memory location
        IdCard duplicate = ravi;

        // Step 3: Modify state through the aliased reference variable
        duplicate.booksIssued = 3;

        // Step 4: Create a distinct, separate object with identical field values
        IdCard separate = new IdCard("Ravi", 3);

        // Verification and Output
        System.out.printf("Ravi's booksIssued (via first variable): %d%n", ravi.booksIssued);
        System.out.println("duplicate == ravi: " + (duplicate == ravi));
        System.out.println("separate == ravi: " + (separate == ravi));
    }

    public static void main(String[] args) {
        try {
            demonstrateReferenceCopy();
        } catch (Exception e) {
            System.err.println("Error demonstrating reference equality: " + e.getMessage());
        }
    }
}
