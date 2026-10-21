package week3.practice;

/**
 * Problem M5: Instance vs Static: Splitting a Class Correctly
 * Demonstrates shared class-level static attributes/methods vs object-level instance state.
 */
public class Student {

    // Instance fields unique to each student
    private String name;
    private double attendance;

    // Static fields shared across all student instances
    private static String collegeName = "SRM Institute of Science and Technology";
    private static int studentCount = 0;

    /**
     * Constructs a Student instance and increments the static counter tracking total students.
     *
     * @param name       student's name
     * @param attendance percentage attendance
     */
    public Student(String name, double attendance) {
        this.name = (name != null) ? name.trim() : "Unknown";
        this.attendance = attendance;
        studentCount++; // Thread-safe / static increment per object creation
    }

    // Instance getters
    public String getName() {
        return name;
    }

    public double getAttendance() {
        return attendance;
    }

    /**
     * Static method accessing only static class-level fields.
     * Does not reference any instance state (no 'this').
     */
    public static void printCollegeInfo() {
        System.out.println(collegeName);
        System.out.println("Students created: " + studentCount);
    }

    /**
     * Getter for static student count.
     */
    public static int getStudentCount() {
        return studentCount;
    }

    public static void main(String[] args) {
        try {
            // Instantiate two student records
            Student student1 = new Student("Ravi", 85.5);
            Student student2 = new Student("Meera", 92.0);

            // Access static method via class identifier, not instance reference
            Student.printCollegeInfo();
        } catch (Exception e) {
            System.err.println("Error displaying student college info: " + e.getMessage());
        }
    }
}
