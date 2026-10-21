package week4.assignment;

/**
 * Problem A4: Static Block — Library Membership Card Setup
 * Demonstrates one-time class initialization using static initialization blocks.
 */
public class MembershipCard {

    // Shared library metadata across all cards
    private static String libraryName;
    private static String validUntil;

    // Instance property unique to student cardholder
    private String studentName;

    // Static initialization block runs strictly once when MembershipCard class is loaded
    static {
        libraryName = "SRM Central Library";
        validUntil = "May 2027";
        System.out.println("Library info loaded");
    }

    /**
     * Constructs a membership card for a student.
     *
     * @param studentName the student's name
     */
    public MembershipCard(String studentName) {
        this.studentName = (studentName != null) ? studentName.trim() : "Unknown";
    }

    public String getStudentName() {
        return studentName;
    }

    public static String getLibraryName() {
        return libraryName;
    }

    public static String getValidUntil() {
        return validUntil;
    }

    /**
     * Prints card issuance confirmation line.
     * Example: "Membership card issued: Ananya"
     */
    public void printConfirmation() {
        System.out.println("Membership card issued: " + this.studentName);
    }

    /**
     * Batch issues cards for an array of student names.
     *
     * @param names array of student names
     */
    public static void issueCardBatch(String[] names) {
        if (names == null) {
            return;
        }
        for (String name : names) {
            MembershipCard card = new MembershipCard(name);
            card.printConfirmation();
        }
    }

    public static void main(String[] args) {
        try {
            String[] names = {"Ananya", "Rohan", "Priya", "Arjun", "Sneha"};
            issueCardBatch(names);
        } catch (Exception e) {
            System.err.println("Error issuing membership cards: " + e.getMessage());
        }
    }
}
