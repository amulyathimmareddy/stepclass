package week6.practice;

/**
 * Base library membership entity representing foundational registration.
 */
class LibraryMember {
    private String memberId;
    private int borrowLimit;
    private int booksBorrowed;

    public LibraryMember(String memberId, int borrowLimit) {
        if (memberId == null || memberId.trim().length() < 4) {
            throw new IllegalArgumentException("memberId must be at least 4 characters.");
        }
        this.memberId = memberId.trim();
        this.borrowLimit = Math.max(1, borrowLimit);
        this.booksBorrowed = 0;
    }

    public void borrowBook() {
        if (booksBorrowed < borrowLimit) {
            booksBorrowed++;
        }
    }

    public String getMemberId() {
        return memberId;
    }

    public int getBorrowLimit() {
        return borrowLimit;
    }

    public int getBooksBorrowed() {
        return booksBorrowed;
    }
}

/**
 * Direct subclass representing enrolled student members.
 */
class StudentMember extends LibraryMember {
    private String course;

    public StudentMember(String memberId, int borrowLimit, String course) {
        super(memberId, borrowLimit);
        this.course = (course != null) ? course.trim() : "General";
    }

    public String getCourse() {
        return course;
    }
}

/**
 * Problem 1: Library Membership Foundation & Batch Enrollment Validator
 * Implements constructor validation throwing IllegalArgumentException and resilient batch enrollment via try/catch.
 */
public class LibraryEnrollmentValidator {

    /**
     * Attempts to enroll an array of member IDs, accumulating successful registrations and counting rejections.
     *
     * @param memberIds   array of proposed member ID strings
     * @param borrowLimit allowed checkout quota
     * @return formatted summary: "Enrolled: X | Rejected: Y"
     */
    public static String enrollBatch(String[] memberIds, int borrowLimit) {
        if (memberIds == null || memberIds.length == 0) {
            return "Enrolled: 0 | Rejected: 0";
        }

        int enrolled = 0;
        int rejected = 0;

        for (String id : memberIds) {
            try {
                new LibraryMember(id, borrowLimit);
                enrolled++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }

        return String.format("Enrolled: %d | Rejected: %d", enrolled, rejected);
    }

    public static void main(String[] args) {
        try {
            // Test 1: Invalid construction rejection
            try {
                new LibraryMember("LB1", 3);
            } catch (IllegalArgumentException e) {
                System.out.println("construction rejected");
            }

            // Test 2: Student member operations
            StudentMember student = new StudentMember("STU10", 3, "CSE");
            student.borrowBook();
            student.borrowBook();
            System.out.println("Books borrowed: " + student.getBooksBorrowed());

            // Test 3: Batch enrollment validator
            String[] batch = {"STU1", "LB1", "STU2", " ", "STU3"};
            System.out.println(enrollBatch(batch, 3));
        } catch (Exception e) {
            System.err.println("Error in enrollment validator: " + e.getMessage());
        }
    }
}
