package week3.assignment;

/**
 * Problem M3: Overloaded Constructors for an Employee
 * Implements constructor chaining with this(...) to support permanent staff and interns without duplicate code.
 */
public class Employee {

    private String empId;
    private String empName;
    private double salary;
    private boolean isIntern;

    /**
     * Primary constructor for permanent salaried employees.
     *
     * @param empId   unique employee identifier
     * @param empName full name of the employee
     * @param salary  monthly/annual base compensation
     */
    public Employee(String empId, String empName, double salary) {
        this.empId = (empId != null) ? empId.trim() : "UNKNOWN";
        this.empName = (empName != null) ? empName.trim() : "Unnamed";
        this.salary = Math.max(0.0, salary);
        this.isIntern = false;
    }

    /**
     * Overloaded constructor for interns with no initial fixed salary.
     * Chains to 3-arg constructor via this(...) setting salary to 0.0, then flags isIntern as true.
     *
     * @param empId   unique employee identifier
     * @param empName full name of the intern
     */
    public Employee(String empId, String empName) {
        this(empId, empName, 0.0);
        this.isIntern = true;
    }

    // Getters
    public String getEmpId() {
        return empId;
    }

    public String getEmpName() {
        return empName;
    }

    public double getSalary() {
        return salary;
    }

    public boolean isIntern() {
        return isIntern;
    }

    /**
     * Prints profile summary matching expected format:
     * Example: "E-101 | Divya | Rs 65000.0 | Intern: false"
     */
    public void printProfile() {
        System.out.printf("%s | %s | Rs %.1f | Intern: %b%n", empId, empName, salary, isIntern);
    }

    public static void main(String[] args) {
        try {
            // Instantiate permanent employee
            Employee permanentStaff = new Employee("E-101", "Divya", 65000.0);

            // Instantiate intern using chained constructor
            Employee intern = new Employee("E-102", "Arjun");

            // Print profiles
            permanentStaff.printProfile();
            intern.printProfile();
        } catch (Exception e) {
            System.err.println("Error creating employee profile: " + e.getMessage());
        }
    }
}
