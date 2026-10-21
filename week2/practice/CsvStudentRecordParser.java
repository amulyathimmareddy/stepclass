package week2.practice;



public class CsvStudentRecordParser {

    /**
     * Splits a CSV line by comma, validates for exactly 3 fields,
     * and prints the formatted record.
     */
    public static void parseStudentRecord(String csvLine) {
        if (csvLine == null) {
            System.out.println("Invalid Record");
            return;
        }

        // Split by comma
        String[] fields = csvLine.split(",");

        // Validate that exactly 3 fields are present
        if (fields.length != 3) {
            System.out.println("Invalid Record");
            return;
        }

        // Extract and trim the fields
        String name = fields[0].trim();
        String rollNo = fields[1].trim();
        String dept = fields[2].trim();

        // Print the formatted output matching the sample
        System.out.printf("Name: %s | Roll No: %s | Dept: %s%n", name, rollNo, dept);
    }

    public static void main(String[] args) {
        // Test Case 1: From Sample Output
        String record1 = "Ananya Verma,RA2211003010123,CSE";
        parseStudentRecord(record1);

        // Test Case 2: Invalid Record Test
        String record2 = "Rahul Sharma,CSE";
        parseStudentRecord(record2);
    }
}
