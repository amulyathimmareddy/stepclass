package week6.practice;

/**
 * Base member with polymorphic display info.
 */
class BaseMember {
    protected String memberId;
    protected int borrowLimit;
    protected int booksBorrowed;

    public BaseMember(String memberId, int borrowLimit) {
        this.memberId = (memberId != null) ? memberId.trim() : "UNKNOWN";
        this.borrowLimit = Math.max(1, borrowLimit);
        this.booksBorrowed = 0;
    }

    public void borrowBook() {
        if (booksBorrowed < borrowLimit) {
            booksBorrowed++;
        }
    }

    public int getBooksBorrowed() {
        return booksBorrowed;
    }

    public String displayInfo() {
        return String.format("General Member | Books Borrowed: %d", booksBorrowed);
    }
}

/**
 * Student member subclass.
 */
class StudentTreeMember extends BaseMember {
    protected String course;

    public StudentTreeMember(String memberId, int borrowLimit, String course) {
        super(memberId, borrowLimit);
        this.course = (course != null) ? course.trim() : "General";
    }

    @Override
    public String displayInfo() {
        return String.format("Student Member | Course: %s | Books Borrowed: %d", course, booksBorrowed);
    }
}

/**
 * Multilevel descendant: 3 generations deep.
 */
class HonorsStudentMember extends StudentTreeMember {
    private int bonusLimit;

    public HonorsStudentMember(String memberId, int borrowLimit, String course, int bonusLimit) {
        super(memberId, borrowLimit, course);
        this.bonusLimit = Math.max(0, bonusLimit);
    }

    @Override
    public String displayInfo() {
        return String.format("Honors Student Member | Course: %s | Bonus Limit: %d | Books Borrowed: %d",
                course, bonusLimit, booksBorrowed);
    }
}

/**
 * Hierarchical sibling: extends BaseMember directly.
 */
class FacultyMember extends BaseMember {
    private String department;

    public FacultyMember(String memberId, int borrowLimit, String department) {
        super(memberId, borrowLimit);
        this.department = (department != null) ? department.trim() : "General";
    }

    @Override
    public String displayInfo() {
        return String.format("Faculty Member | Department: %s | Books Borrowed: %d", department, booksBorrowed);
    }
}

/**
 * Problem 2: Three Branches of the Membership Tree
 * Demonstrates multilevel inheritance, hierarchical branching, and polymorphism.
 */
public class MembershipTreeHierarchy {

    /**
     * Inspects generation depth via instanceof checks.
     *
     * @param member target member reference
     * @return lineage description
     */
    public static String classifyGeneration(BaseMember member) {
        if (member == null) {
            return "Null Reference";
        }
        if (member instanceof HonorsStudentMember) {
            return "Multilevel descendant (3 generations deep)";
        } else if (member instanceof FacultyMember) {
            return "Hierarchical sibling (independent branch)";
        } else if (member instanceof StudentTreeMember) {
            return "Direct subclass (2 generations deep)";
        } else {
            return "General Member (root)";
        }
    }

    /**
     * Sums borrowed books polymorphically without type inspection inside loop.
     *
     * @param members array of BaseMember references
     * @return aggregate books borrowed
     */
    public static int getTotalBooksBorrowed(BaseMember[] members) {
        if (members == null) {
            return 0;
        }
        int total = 0;
        for (BaseMember m : members) {
            if (m != null) {
                total += m.getBooksBorrowed();
            }
        }
        return total;
    }

    public static void main(String[] args) {
        try {
            BaseMember gen = new BaseMember("STU1", 3);
            StudentTreeMember stu = new StudentTreeMember("STU2", 3, "CSE");
            HonorsStudentMember honors = new HonorsStudentMember("STU3", 3, "ECE", 2);
            FacultyMember faculty = new FacultyMember("STU4", 5, "Physics");

            // Verify polymorphic displayInfo
            System.out.println(gen.displayInfo());
            System.out.println(stu.displayInfo());
            System.out.println(honors.displayInfo());
            System.out.println(faculty.displayInfo());

            // Verify classification
            System.out.println(classifyGeneration(honors));
            System.out.println(classifyGeneration(faculty));

            // Setup borrowing counts
            stu.borrowBook();
            stu.borrowBook(); // 2
            honors.borrowBook(); // 1
            faculty.borrowBook();
            faculty.borrowBook();
            faculty.borrowBook(); // 3

            BaseMember[] mixed = {stu, honors, faculty};
            System.out.println("Total borrowed: " + getTotalBooksBorrowed(mixed));
        } catch (Exception e) {
            System.err.println("Error in membership tree: " + e.getMessage());
        }
    }
}
