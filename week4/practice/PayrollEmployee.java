package week4.practice;

/**
 * Problem M2: Payroll Batch Bonus Round
 * Resolves explicit field-parameter variable shadowing using the 'this' keyword.
 */
public class PayrollEmployee {

    // Instance fields
    private String empId;
    private double salary;

    /**
     * Constructor resolving field/parameter clash on both empId and salary using 'this'.
     *
     * @param empId  the employee identifier
     * @param salary the initial salary
     */
    public PayrollEmployee(String empId, double salary) {
        this.empId = empId;
        this.salary = salary;
    }

    /**
     * Applies a raise/bonus where parameter name 'salary' clashes with instance field 'salary'.
     * Uses 'this.salary' to explicitly differentiate the instance variable from local parameter.
     *
     * @param salary the raise increment to add
     */
    public void raiseSalary(double salary) {
        this.salary = this.salary + salary;
    }

    // Getters
    public String getEmpId() {
        return this.empId;
    }

    public double getSalary() {
        return this.salary;
    }

    /**
     * Prints the final employee compensation line.
     * Example: "E-101 | Final Salary: Rs 45000.0"
     */
    public void printFinalSalary() {
        System.out.printf("%s | Final Salary: Rs %.1f%n", this.empId, this.salary);
    }

    /**
     * Applies the identical festival bonus to a batch of employees in a single pass.
     *
     * @param team  array of PayrollEmployee objects
     * @param bonus fixed bonus raise to apply
     */
    public static void applyBatchBonus(PayrollEmployee[] team, double bonus) {
        if (team == null) {
            return;
        }
        for (PayrollEmployee emp : team) {
            if (emp != null) {
                emp.raiseSalary(bonus);
                emp.printFinalSalary();
            }
        }
    }

    public static void main(String[] args) {
        try {
            PayrollEmployee[] employees = new PayrollEmployee[] {
                new PayrollEmployee("E-101", 40000.0),
                new PayrollEmployee("E-102", 55000.0),
                new PayrollEmployee("E-103", 62000.0),
                new PayrollEmployee("E-104", 48000.0)
            };

            final double festivalBonus = 5000.0;
            applyBatchBonus(employees, festivalBonus);
        } catch (Exception e) {
            System.err.println("Error processing payroll batch raise: " + e.getMessage());
        }
    }
}
