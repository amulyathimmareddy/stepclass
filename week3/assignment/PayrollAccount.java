package week3.assignment;

/**
 * Problem M2: Encapsulated Payroll Account
 * Encapsulates financial state, guards against invalid modifications, and provides controlled accessors.
 */
public class PayrollAccount {

    // Private fields preventing uncontrolled external modification
    private double basicSalary;
    private double bonus;

    /**
     * Initializes payroll account with opening basic salary.
     *
     * @param basicSalary the starting base pay
     */
    public PayrollAccount(double basicSalary) {
        if (basicSalary < 0.0) {
            System.out.println("Warning: Basic salary cannot be negative. Initialized to 0.0");
            this.basicSalary = 0.0;
        } else {
            this.basicSalary = basicSalary;
        }
        this.bonus = 0.0;
    }

    /**
     * Credits bonus amount to the employee's payroll. Rejects amount <= 0.
     *
     * @param amount bonus to add
     */
    public void creditBonus(double amount) {
        if (amount <= 0.0) {
            System.out.println("Credit rejected: Bonus amount must be greater than zero.");
            return;
        }
        this.bonus += amount;
        System.out.printf("Bonus credited: Rs %.1f%n", amount);
    }

    /**
     * Deducts tax from basic salary by a specified percentage (0 - 100%).
     *
     * @param percent tax deduction percentage
     */
    public void deductTax(double percent) {
        if (percent < 0.0 || percent > 100.0) {
            System.out.println("Tax deduction rejected: Percentage must be between 0 and 100.");
            return;
        }
        double taxAmount = (this.basicSalary * percent) / 100.0;
        this.basicSalary -= taxAmount;
        System.out.printf("Tax deducted: %d%%%n", (int) percent);
    }

    /**
     * Calculates and returns total net take-home salary (basic + bonus).
     *
     * @return net take-home earnings
     */
    public double getNetSalary() {
        return this.basicSalary + this.bonus;
    }

    public static void main(String[] args) {
        try {
            // Execution matching sample scenario
            final double initialBase = 50000.0;
            final double bonusCredit = 5000.0;
            final double taxPercent = 10.0;

            PayrollAccount account = new PayrollAccount(initialBase);
            account.creditBonus(bonusCredit);
            account.deductTax(taxPercent);
            System.out.printf("Net salary: Rs %.1f%n", account.getNetSalary());
        } catch (Exception e) {
            System.err.println("Error processing payroll account: " + e.getMessage());
        }
    }
}
