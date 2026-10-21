package week10.practice;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract staff member representing common payroll attributes.
 */
abstract class Staff {
    protected String name;

    public Staff(String name) {
        this.name = (name != null) ? name.trim() : "Unknown";
    }

    public String getName() {
        return name;
    }

    public abstract String getType();
    public abstract int calculatePay();

    @Override
    public String toString() {
        return String.format("Payslip[name=%s, type=%s, pay=%d]", name, getType(), calculatePay());
    }
}

class FullTimeStaff extends Staff {
    private int monthlySalary;

    public FullTimeStaff(String name, int monthlySalary) {
        super(name);
        this.monthlySalary = Math.max(0, monthlySalary);
    }

    @Override
    public String getType() {
        return "FullTime";
    }

    @Override
    public int calculatePay() {
        return monthlySalary;
    }
}

class PartTimeStaff extends Staff {
    private int hoursWorked;
    private int hourlyRate;

    public PartTimeStaff(String name, int hoursWorked, int hourlyRate) {
        super(name);
        this.hoursWorked = Math.max(0, hoursWorked);
        this.hourlyRate = Math.max(0, hourlyRate);
    }

    @Override
    public String getType() {
        return "PartTime";
    }

    @Override
    public int calculatePay() {
        return hoursWorked * hourlyRate;
    }
}

class InternStaff extends Staff {
    private int stipend;

    public InternStaff(String name, int stipend) {
        super(name);
        this.stipend = Math.max(0, stipend);
    }

    @Override
    public String getType() {
        return "Intern";
    }

    @Override
    public int calculatePay() {
        return stipend;
    }
}

/**
 * Problem 5: Payroll Register System
 * Computes compensation across staff roles, reports payslips, cumulative payroll, and top earner.
 */
public class PayrollRegisterSystem {

    private List<Staff> staffMembers = new ArrayList<>();

    public void addStaff(Staff staff) {
        if (staff != null) {
            staffMembers.add(staff);
        }
    }

    public void processPayroll() {
        int totalPay = 0;
        Staff topEarner = null;
        int maxPay = -1;

        for (Staff s : staffMembers) {
            System.out.println(s);
            int pay = s.calculatePay();
            totalPay += pay;
            if (pay > maxPay) {
                maxPay = pay;
                topEarner = s;
            }
        }

        System.out.println("Total " + totalPay);
        if (topEarner != null) {
            System.out.println("top earner " + topEarner.getName());
        }
    }

    public static void main(String[] args) {
        try {
            PayrollRegisterSystem payroll = new PayrollRegisterSystem();

            // Input: FullTime Asha 50000, PartTime Ravi 80 300, Intern Neha 15000
            payroll.addStaff(new FullTimeStaff("Asha", 50000));
            payroll.addStaff(new PartTimeStaff("Ravi", 80, 300));
            payroll.addStaff(new InternStaff("Neha", 15000));

            payroll.processPayroll();
        } catch (Exception e) {
            System.err.println("Error processing payroll register: " + e.getMessage());
        }
    }
}
