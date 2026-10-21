package week6.practice;

/**
 * Base member with final auto-incrementing memberNumber.
 */
class AuditMember {
    private static int counter = 101;
    public final String memberNumber;
    private int borrowLimit;
    private int booksBorrowed;

    public AuditMember(int borrowLimit) {
        this.memberNumber = "LIB-" + (counter++);
        this.borrowLimit = Math.max(1, borrowLimit);
        this.booksBorrowed = 0;
    }

    public static int getMembersEnrolled() {
        return counter - 101;
    }

    public void borrowBook() {
        if (booksBorrowed < borrowLimit) {
            booksBorrowed++;
        }
    }

    public void borrowBook(String genre) {
        // Records genre and reuses core borrow logic
        borrowBook();
    }

    public int getBooksBorrowed() {
        return booksBorrowed;
    }
}

/**
 * Subclass representing faculty member for audit.
 */
class AuditFacultyMember extends AuditMember {
    private String department;

    public AuditFacultyMember(int borrowLimit, String department) {
        super(borrowLimit);
        this.department = (department != null) ? department.trim() : "General";
    }

    public String getDepartment() {
        return department;
    }
}

/**
 * Problem 5: Membership Numbers, Renewal Codes & the Nightly Circulation Audit
 * Implements final auto-generated identifiers, manual char validation for renewal codes, and null-safe nightly auditing.
 */
public class CirculationAuditManager {

    /**
     * Validates format: 'R' followed by two digits and one uppercase letter (e.g., "R12A").
     *
     * @param code proposed renewal code
     * @return true if valid format, false otherwise
     */
    public static boolean isValidRenewalCode(String code) {
        if (code == null || code.length() != 4) {
            return false;
        }

        if (code.charAt(0) != 'R') {
            return false;
        }

        if (!Character.isDigit(code.charAt(1)) || !Character.isDigit(code.charAt(2))) {
            return false;
        }

        return Character.isUpperCase(code.charAt(3));
    }

    /**
     * Reconciles a nightly batch of audit members safely skipping null values.
     *
     * @param members array of AuditMember objects
     * @return formatted report: "X processed | Y null skipped | F faculty | R regular"
     */
    public static String processNightlyAudit(AuditMember[] members) {
        if (members == null || members.length == 0) {
            return "0 processed | 0 null skipped | 0 faculty | 0 regular";
        }

        int nullCount = 0;
        int facultyCount = 0;
        int regularCount = 0;

        for (AuditMember m : members) {
            if (m == null) {
                nullCount++;
            } else if (m instanceof AuditFacultyMember) {
                facultyCount++;
            } else {
                regularCount++;
            }
        }

        int totalProcessed = facultyCount + regularCount;
        return String.format("%d processed | %d null skipped | %d faculty | %d regular",
                totalProcessed, nullCount, facultyCount, regularCount);
    }

    public static void main(String[] args) {
        try {
            AuditMember m1 = new AuditMember(3);
            System.out.println("Member Number: " + m1.memberNumber);
            System.out.println("Members Enrolled: " + AuditMember.getMembersEnrolled());

            // Validate renewal code formats
            System.out.println("isValidRenewalCode('R12A'): " + isValidRenewalCode("R12A"));
            System.out.println("isValidRenewalCode('R1A'):  " + isValidRenewalCode("R1A"));
            System.out.println("isValidRenewalCode('X12A'): " + isValidRenewalCode("X12A"));

            // Overloaded borrowing test
            m1.borrowBook();
            m1.borrowBook("Fiction");
            System.out.println("Books borrowed: " + m1.getBooksBorrowed());

            // Nightly audit reconciliation test
            AuditMember[] batch = new AuditMember[] {
                new AuditFacultyMember(5, "Physics"),
                null,
                new AuditMember(3)
            };

            System.out.println(processNightlyAudit(batch));
        } catch (Exception e) {
            System.err.println("Error running circulation audit: " + e.getMessage());
        }
    }
}
