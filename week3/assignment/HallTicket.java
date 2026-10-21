package week3.assignment;

/**
 * Problem M4: Reference Copies and a Shared Exam Hall Ticket
 * Demonstrates pointer aliasing, reference mutability, and object identity comparison.
 */
public class HallTicket {

    String studentName;
    int seatNumber;

    /**
     * Parameterized constructor for HallTicket.
     *
     * @param studentName the examinee's name
     * @param seatNumber  the assigned seat number
     */
    public HallTicket(String studentName, int seatNumber) {
        this.studentName = (studentName != null) ? studentName.trim() : "Unknown";
        this.seatNumber = Math.max(0, seatNumber);
    }

    /**
     * Demonstrates how two reference variables point to the same object in heap memory.
     */
    public static void demonstrateReferenceCopy() {
        // Step 1: Instantiate original hall ticket
        HallTicket priya = new HallTicket("Priya", 0);

        // Step 2: Assign reference copy (no new object allocated)
        HallTicket copy = priya;

        // Step 3: Mutate object through copied reference
        copy.seatNumber = 45;

        // Step 4: Instantiate independent object with identical data
        HallTicket separate = new HallTicket("Priya", 45);

        // Output results matching specification
        System.out.printf("Priya's seatNumber (via first variable): %d%n", priya.seatNumber);
        System.out.println("copy == priya: " + (copy == priya));
        System.out.println("separate == priya: " + (separate == priya));
    }

    public static void main(String[] args) {
        try {
            demonstrateReferenceCopy();
        } catch (Exception e) {
            System.err.println("Error demonstrating hall ticket reference aliasing: " + e.getMessage());
        }
    }
}
