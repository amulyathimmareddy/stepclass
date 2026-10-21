package week2.assignment;


public class ProductInventoryCsvParser {

    /**
     * Splits an inventory CSV line by comma, validates for exactly 3 fields,
     * and prints a formatted record line.
     */
    public static void parseInventoryRecord(String csvLine) {
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

        // Extract and trim the individual components
        String productName = fields[0].trim();
        String sku = fields[1].trim();
        String quantity = fields[2].trim();

        // Print formatted record matching the sample output
        System.out.printf("Product: %s | SKU: %s | Qty: %s%n", productName, sku, quantity);
    }

    public static void main(String[] args) {
        // Test Case 1: Valid CSV line
        String record1 = "Wireless Mouse,WM-2201,150";
        parseInventoryRecord(record1);

        // Test Case 2: Invalid CSV line (missing SKU)
        String record2 = "Wireless Mouse,150";
        parseInventoryRecord(record2);
    }
}
