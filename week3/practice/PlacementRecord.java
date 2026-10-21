package week3.practice;

/**
 * Problem M1: From Parallel Arrays to a Class
 * Replaces parallel arrays (names, companies, packages) with an encapsulated PlacementRecord class.
 */
public class PlacementRecord {

    // Instance fields
    private String studentName;
    private String company;
    private double packageLpa;

    /**
     * Parameterized constructor to initialize a placement record.
     * Validates input parameters to avoid null or negative values.
     *
     * @param studentName the name of the placed student
     * @param company     the hiring company name
     * @param packageLpa  the annual compensation in LPA
     */
    public PlacementRecord(String studentName, String company, double packageLpa) {
        if (studentName == null || studentName.trim().isEmpty()) {
            this.studentName = "Unknown";
        } else {
            this.studentName = studentName.trim();
        }

        if (company == null || company.trim().isEmpty()) {
            this.company = "Unknown";
        } else {
            this.company = company.trim();
        }

        if (packageLpa < 0.0) {
            this.packageLpa = 0.0;
        } else {
            this.packageLpa = packageLpa;
        }
    }

    // Getters
    public String getStudentName() {
        return studentName;
    }

    public String getCompany() {
        return company;
    }

    public double getPackageLpa() {
        return packageLpa;
    }

    /**
     * Prints one formatted placement record line matching the required output format:
     * Example: "Ravi -> TCS @ 4.5 LPA"
     */
    public void printRecord() {
        System.out.printf("%s -> %s @ %.1f LPA%n", studentName, company, packageLpa);
    }

    /**
     * Helper method to print an array of placement records in sequence.
     *
     * @param records array of PlacementRecord objects
     */
    public static void displayAllRecords(PlacementRecord[] records) {
        if (records == null || records.length == 0) {
            System.out.println("No placement records available.");
            return;
        }

        for (PlacementRecord record : records) {
            if (record != null) {
                record.printRecord();
            }
        }
    }

    public static void main(String[] args) {
        try {
            // Sample test data as specified in problem statement
            PlacementRecord[] records = new PlacementRecord[] {
                new PlacementRecord("Ravi", "TCS", 4.5),
                new PlacementRecord("Anitha", "Zoho", 6.2),
                new PlacementRecord("Karthik", "Infosys", 4.0)
            };

            // Display all records
            displayAllRecords(records);
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }
}
