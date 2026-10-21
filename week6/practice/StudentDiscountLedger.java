package week6.practice;

import java.util.Arrays;

/**
 * Base library member with private fine tracking ledger.
 */
class AccountMember {
    protected String memberId;
    protected int borrowLimit;
    private int[] fineHistory;
    private int fineCount;
    private int totalFine;

    public AccountMember(String memberId, int borrowLimit) {
        this.memberId = (memberId != null) ? memberId.trim() : "UNKNOWN";
        this.borrowLimit = Math.max(1, borrowLimit);
        this.fineHistory = new int[10];
        this.fineCount = 0;
        this.totalFine = 0;
    }

    protected void chargeFine(int amount) {
        if (amount <= 0) {
            return;
        }
        if (fineCount < fineHistory.length) {
            fineHistory[fineCount++] = amount;
        }
        totalFine += amount;
    }

    public int[] getFineHistory() {
        return Arrays.copyOf(fineHistory, fineCount);
    }

    public int getTotalFine() {
        return totalFine;
    }
}

/**
 * Subclass applying 50% discount to all fines before forwarding to super.
 */
class DiscountedStudentMember extends AccountMember {
    private String course;

    public DiscountedStudentMember(String memberId, int borrowLimit, String course) {
        super(memberId, borrowLimit);
        this.course = (course != null) ? course.trim() : "General";
    }

    @Override
    protected void chargeFine(int amount) {
        // Halves the fine and delegates ledger recording to superclass
        super.chargeFine(amount / 2);
    }

    public String getCourse() {
        return course;
    }
}

/**
 * Problem 3: The Student Discount & Fine Ledger
 * Demonstrates super-delegation with method overriding and defensive copying of audit history.
 */
public class StudentDiscountLedger {

    public static void main(String[] args) {
        try {
            DiscountedStudentMember student = new DiscountedStudentMember("STU5", 3, "CSE");
            student.chargeFine(100);

            // Total fine should be 50 (halved)
            System.out.println("Total Fine: " + student.getTotalFine());

            // Defensive copy verification
            int[] history = student.getFineHistory();
            history[0] = 999; // Attempted tampering
            System.out.println("History after external tamper attempt: " + Arrays.toString(student.getFineHistory()));
        } catch (Exception e) {
            System.err.println("Error in discount ledger: " + e.getMessage());
        }
    }
}
