package week1.assignment;



public class WarehouseInventoryBalancer {

    /**
     * Computes the total quantity in both sections, checks balance status,
     * and identifies the highest quantity along with its section and item position.
     */
    public static void analyzeInventory(int[] sectionA, int[] sectionB) {
        if (sectionA == null || sectionB == null || sectionA.length == 0) {
            System.out.println("Invalid inventory data.");
            return;
        }

        int totalA = 0;
        int totalB = 0;

        int maxQuantity = Integer.MIN_VALUE;
        String maxSection = "";
        int maxItemIndex = -1; // 1-based index for item representation

        // Compute total and track maximum in Section A
        for (int i = 0; i < sectionA.length; i++) {
            totalA += sectionA[i];
            if (sectionA[i] > maxQuantity) {
                maxQuantity = sectionA[i];
                maxSection = "Section A";
                maxItemIndex = i + 1;
            }
        }

        // Compute total and track maximum in Section B
        for (int i = 0; i < sectionB.length; i++) {
            totalB += sectionB[i];
            if (sectionB[i] > maxQuantity) {
                maxQuantity = sectionB[i];
                maxSection = "Section B";
                maxItemIndex = i + 1;
            }
        }

        // Determine balance status
        String status = (totalA == totalB) ? "Balanced" : "Not Balanced";

        // Print final output matching sample format
        System.out.printf("Section A Total: %d | Section B Total: %d | Status: %s | Highest Quantity: %d (%s, Item %d)%n",
                totalA, totalB, status, maxQuantity, maxSection, maxItemIndex);
    }

    public static void main(String[] args) {
        // Sample Test Case
        int[] sectionA = {20, 15, 30};
        int[] sectionB = {25, 10, 30};

        analyzeInventory(sectionA, sectionB);
    }
}
