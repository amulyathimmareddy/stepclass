package week3.assignment;

/**
 * Problem M5: Instance vs Static: Splitting an Employee Class Correctly
 * Separates instance state (empName, salary) from shared class-level static attributes (companyName, employeeCount).
 */
public class CompanyEmployee {

    // Instance fields unique to each employee
    private String empName;
    private double salary;

    // Static fields shared across all employee objects
    private static String companyName = "Bright Horizon Technologies";
    private static int employeeCount = 0;

    /**
     * Parameterized constructor for company employee.
     * Increments the static employee tally upon every instantiation.
     *
     * @param empName employee name
     * @param salary  employee salary
     */
    public CompanyEmployee(String empName, double salary) {
        this.empName = (empName != null) ? empName.trim() : "Unknown";
        this.salary = Math.max(0.0, salary);
        employeeCount++;
    }

    // Instance getters
    public String getEmpName() {
        return empName;
    }

    public double getSalary() {
        return salary;
    }

    /**
     * Static method accessing only static members (cannot access instance fields without an object).
     */
    public static void printCompanyInfo() {
        System.out.println(companyName);
        System.out.println("Employees on record: " + employeeCount);
    }

    public static int getEmployeeCount() {
        return employeeCount;
    }

    public static void main(String[] args) {
        try {
            // Create three CompanyEmployee objects
            CompanyEmployee emp1 = new CompanyEmployee("Alice", 75000.0);
            CompanyEmployee emp2 = new CompanyEmployee("Bob", 62000.0);
            CompanyEmployee emp3 = new CompanyEmployee("Charlie", 58000.0);

            // Call static method via class identifier
            CompanyEmployee.printCompanyInfo();
        } catch (Exception e) {
            System.err.println("Error displaying company employee info: " + e.getMessage());
        }
    }
}
