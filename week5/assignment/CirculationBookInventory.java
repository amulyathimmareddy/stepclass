package week5.assignment;

/**
 * Problem 3: Book Copy Circulation Guard
 * Protects physical book copy availability against negative checkout overdrafts and excessive check-in returns.
 */
public class CirculationBookInventory {

    private int copiesTotal;
    private int copiesAvailable;

    /**
     * Initializes inventory with validated total copy count.
     *
     * @param copiesTotal total physical volume count (must be positive, up to 500)
     */
    public CirculationBookInventory(int copiesTotal) {
        if (copiesTotal <= 0) {
            this.copiesTotal = 0;
            this.copiesAvailable = 0;
        } else {
            this.copiesTotal = Math.min(500, copiesTotal);
            this.copiesAvailable = this.copiesTotal;
        }
    }

    /**
     * Checks out one copy. Silently rejected if no copies remain available.
     */
    public void checkOut() {
        if (this.copiesAvailable > 0) {
            this.copiesAvailable--;
        }
    }

    /**
     * Checks in one returned copy. Silently rejected if all copies are already present.
     */
    public void checkIn() {
        if (this.copiesAvailable < this.copiesTotal) {
            this.copiesAvailable++;
        }
    }

    /**
     * Read-only getter for remaining copies available on shelf.
     *
     * @return current available copy count
     */
    public int getCopiesAvailable() {
        return this.copiesAvailable;
    }

    public int getCopiesTotal() {
        return this.copiesTotal;
    }

    public static void main(String[] args) {
        try {
            CirculationBookInventory inventory = new CirculationBookInventory(3);

            // Perform 4 checkout calls (4th call should be silently rejected)
            inventory.checkOut();
            inventory.checkOut();
            inventory.checkOut();
            inventory.checkOut();
            System.out.println("Available copies after 4 checkouts (capacity 3): " + inventory.getCopiesAvailable());

            // Perform 4 check-in calls (4th call should be silently rejected)
            inventory.checkIn();
            inventory.checkIn();
            inventory.checkIn();
            inventory.checkIn();
            System.out.println("Available copies after 4 check-ins: " + inventory.getCopiesAvailable());
        } catch (Exception e) {
            System.err.println("Error verifying book circulation guard: " + e.getMessage());
        }
    }
}
