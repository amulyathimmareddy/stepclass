package week4.assignment;

/**
 * Problem A2: this Keyword for Canteen Inventory — Batch Restock
 * Disambiguates instance variables from parameter shadows using the 'this' keyword during restock operations.
 */
public class Item {

    private String itemName;
    private int stock;

    /**
     * Parameterized constructor resolving variable shadowing via 'this'.
     *
     * @param itemName item name
     * @param stock    initial stock quantity
     */
    public Item(String itemName, int stock) {
        this.itemName = itemName;
        this.stock = stock;
    }

    /**
     * Restocks item quantity, resolving parameter clash with instance variable via 'this.stock'.
     *
     * @param stock replenishment units added
     */
    public void restock(int stock) {
        this.stock = this.stock + stock;
    }

    public String getItemName() {
        return this.itemName;
    }

    public int getStock() {
        return this.stock;
    }

    /**
     * Formats and prints current item stock.
     * Example: "Samosa | Final Stock: 35"
     */
    public void printStock() {
        System.out.printf("%s | Final Stock: %d%n", this.itemName, this.stock);
    }

    /**
     * Performs a batch restock operation across an array of canteen inventory items.
     *
     * @param items          array of Item objects
     * @param restockQuantity units to add to each item
     */
    public static void batchRestock(Item[] items, int restockQuantity) {
        if (items == null) {
            return;
        }
        for (Item item : items) {
            if (item != null) {
                item.restock(restockQuantity);
                item.printStock();
            }
        }
    }

    public static void main(String[] args) {
        try {
            Item[] canteenItems = new Item[] {
                new Item("Samosa", 15),
                new Item("Tea Powder", 40),
                new Item("Bread", 8),
                new Item("Biscuit Packs", 25)
            };

            final int fridayRestockQuantity = 20;
            batchRestock(canteenItems, fridayRestockQuantity);
        } catch (Exception e) {
            System.err.println("Error processing canteen restock: " + e.getMessage());
        }
    }
}
