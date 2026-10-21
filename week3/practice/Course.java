package week3.practice;

/**
 * Problem M3: Overloaded Constructors for a Course
 * Demonstrates constructor overloading and constructor chaining via this(...) to avoid redundant logic.
 */
public class Course {

    private String code;
    private String title;
    private int credits;
    private int labCredits;

    /**
     * Master constructor: initializes all four fields directly with input validation.
     *
     * @param code       the course code (e.g., "21CSC201J")
     * @param title      the course title
     * @param credits    theory lecture credits
     * @param labCredits practical lab credits
     */
    public Course(String code, String title, int credits, int labCredits) {
        this.code = (code != null) ? code.trim() : "UNKNOWN";
        this.title = (title != null) ? title.trim() : "Untitled";
        this.credits = Math.max(0, credits);
        this.labCredits = Math.max(0, labCredits);
    }

    /**
     * Overloaded convenience constructor for theory-only courses (labCredits defaults to 0).
     * Reuses setup logic via this(...) chaining.
     *
     * @param code    the course code
     * @param title   the course title
     * @param credits theory lecture credits
     */
    public Course(String code, String title, int credits) {
        this(code, title, credits, 0);
    }

    /**
     * Calculates the cumulative course credits (theory + lab).
     *
     * @return sum of theory and lab credits
     */
    public int totalCredits() {
        return this.credits + this.labCredits;
    }

    // Getters
    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public int getCredits() {
        return credits;
    }

    public int getLabCredits() {
        return labCredits;
    }

    /**
     * Prints formatted course credit summary line.
     */
    public void printCreditSummary() {
        System.out.printf("%s total credits: %d%n", this.code, totalCredits());
    }

    public static void main(String[] args) {
        try {
            // Theory-only course using 3-parameter chained constructor
            Course theoryCourse = new Course("21CSC201J", "Data Structures", 4);

            // Integrated theory + lab course using 4-parameter master constructor
            Course labCourse = new Course("21CSC205L", "DSA Lab", 3, 1);

            theoryCourse.printCreditSummary();
            labCourse.printCreditSummary();
        } catch (Exception e) {
            System.err.println("Error initializing course objects: " + e.getMessage());
        }
    }
}
