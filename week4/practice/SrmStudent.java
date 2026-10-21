package week4.practice;

/**
 * Problem M4: One-Time College Setup, Many Students
 * Demonstrates static initialization blocks executing once at class-load time across batch instantiations.
 */
public class SrmStudent {

    // Class-level shared fields initialized in static block
    private static String collegeName;
    private static String academicYear;

    // Instance field
    private String name;

    // Static initialization block runs strictly once when class is loaded into the JVM
    static {
        collegeName = "SRM Institute of Science and Technology";
        academicYear = "2026-2027";
        System.out.println("College info loaded");
    }

    /**
     * Constructs a student record and outputs individual creation confirmation.
     *
     * @param name student's full name
     */
    public SrmStudent(String name) {
        this.name = (name != null) ? name.trim() : "Unknown";
        System.out.println("Student record created: " + this.name);
    }

    public String getName() {
        return name;
    }

    public static String getCollegeName() {
        return collegeName;
    }

    public static String getAcademicYear() {
        return academicYear;
    }

    /**
     * Batch creates multiple student records sequentially from an array of names.
     *
     * @param names array of student names
     */
    public static void createStudentBatch(String[] names) {
        if (names == null) {
            return;
        }
        for (String studentName : names) {
            new SrmStudent(studentName);
        }
    }

    public static void main(String[] args) {
        try {
            String[] names = {"Ravi", "Meera", "Karthik", "Divya", "Anitha"};
            createStudentBatch(names);
        } catch (Exception e) {
            System.err.println("Error initializing SRM student batch: " + e.getMessage());
        }
    }
}
