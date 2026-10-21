package week8.practice;

/**
 * EmployeeLeaveManagementSystem models an enterprise leave management workflow,
 * managing leave request lifecycle states, employee categories, and review operations.
 */
public class EmployeeLeaveManagementSystem {

    public enum LeaveStatus {
        PENDING,
        APPROVED,
        REJECTED
    }

    /**
     * Abstract base representation of an employee.
     */
    public static abstract class Employee {
        private final String id;
        private final String name;

        public Employee(String id, String name) {
            if (id == null || id.trim().isEmpty() || name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Employee id and name cannot be null or blank");
            }
            this.id = id.trim();
            this.name = name.trim();
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public abstract String getRoleTitle();

        @Override
        public String toString() {
            return name;
        }
    }

    public static class FullTimeEmployee extends Employee {
        public FullTimeEmployee(String id, String name) {
            super(id, name);
        }

        @Override
        public String getRoleTitle() {
            return "Full-Time Employee";
        }
    }

    public static class PartTimeEmployee extends Employee {
        public PartTimeEmployee(String id, String name) {
            super(id, name);
        }

        @Override
        public String getRoleTitle() {
            return "Part-Time Employee";
        }
    }

    public static class Contractor extends Employee {
        public Contractor(String id, String name) {
            super(id, name);
        }

        @Override
        public String getRoleTitle() {
            return "Contractor";
        }
    }

    /**
     * Leave request tracking employee, date range, duration, and approval status.
     */
    public static class LeaveRequest {
        private final Employee employee;
        private final String dateRange;
        private final int days;
        private LeaveStatus status;

        public LeaveRequest(Employee employee, String dateRange, int days) {
            if (employee == null) {
                throw new IllegalArgumentException("Employee cannot be null");
            }
            if (dateRange == null || dateRange.trim().isEmpty()) {
                throw new IllegalArgumentException("Date range cannot be blank");
            }
            if (days <= 0) {
                throw new IllegalArgumentException("Days must be positive");
            }
            this.employee = employee;
            this.dateRange = dateRange.trim();
            this.days = days;
            this.status = LeaveStatus.PENDING;
        }

        public Employee getEmployee() {
            return employee;
        }

        public String getDateRange() {
            return dateRange;
        }

        public int getDays() {
            return days;
        }

        public LeaveStatus getStatus() {
            return status;
        }

        public void approve() {
            if (this.status != LeaveStatus.PENDING) {
                throw new IllegalStateException("Only pending requests can be approved");
            }
            this.status = LeaveStatus.APPROVED;
        }

        public void reject() {
            if (this.status != LeaveStatus.PENDING) {
                throw new IllegalStateException("Only pending requests can be rejected");
            }
            this.status = LeaveStatus.REJECTED;
        }

        public void revertToPending() {
            if (this.status == LeaveStatus.APPROVED || this.status == LeaveStatus.REJECTED) {
                throw new IllegalStateException("Cannot change leave request status from " +
                        this.status + " to " + LeaveStatus.PENDING + ".");
            }
            this.status = LeaveStatus.PENDING;
        }
    }

    /**
     * Manager reviewing employee leave requests.
     */
    public static class Manager {
        private final String name;

        public Manager(String name) {
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Manager name cannot be blank");
            }
            this.name = name.trim();
        }

        public String getName() {
            return name;
        }

        public void review(LeaveRequest request, boolean approve) {
            if (approve) {
                request.approve();
                System.out.printf("%s's leave request (%s) approved. Status: %s.%n",
                        request.getEmployee().getName(), request.getDateRange(), request.getStatus());
            } else {
                request.reject();
                System.out.printf("%s's leave request (%s) rejected. Status: %s.%n",
                        request.getEmployee().getName(), request.getDateRange(), request.getStatus());
            }
        }
    }

    public static LeaveRequest submitLeave(Employee employee, String dateRange, int days) {
        LeaveRequest request = new LeaveRequest(employee, dateRange, days);
        System.out.printf("Leave request submitted for %s (%s). Status: %s.%n",
                employee.getName(), dateRange, request.getStatus());
        return request;
    }

    public static void main(String[] args) {
        FullTimeEmployee john = new FullTimeEmployee("EMP-101", "John");
        PartTimeEmployee jane = new PartTimeEmployee("EMP-102", "Jane");

        Manager alice = new Manager("Alice");
        Manager bob = new Manager("Bob");

        // John submits leave request for 5 days (Jan 1-5)
        LeaveRequest johnRequest = submitLeave(john, "Jan 1-5", 5);

        // Manager Alice reviews John's request and approves it
        alice.review(johnRequest, true);

        // Jane submits leave request for 2 days (Feb 10-11)
        LeaveRequest janeRequest = submitLeave(jane, "Feb 10-11", 2);

        // Manager Bob reviews Jane's request and rejects it
        bob.review(janeRequest, false);

        // John attempts to change his approved leave request to Pending
        try {
            johnRequest.revertToPending();
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }
}
