package week3.practice;

/**
 * Problem M2: Encapsulated Mess-Card Wallet
 * Demonstrates data encapsulation, validation on mutations, and controlled read-only access.
 */
public class MessWallet {

    // Encapsulated state: private balance cannot be modified directly from outside
    private double balance;

    /**
     * Initializes wallet with an opening balance.
     * Guard against negative initial values by defaulting to 0.0 with a warning.
     *
     * @param openingBalance initial amount loaded onto the card
     */
    public MessWallet(double openingBalance) {
        if (openingBalance < 0.0) {
            System.out.println("Warning: Opening balance cannot be negative. Initialized to 0.0");
            this.balance = 0.0;
        } else {
            this.balance = openingBalance;
        }
    }

    /**
     * Tops up wallet balance. Rejects non-positive amounts with a message.
     *
     * @param amount top-up amount
     */
    public void topUp(double amount) {
        if (amount <= 0.0) {
            System.out.println("Top-up rejected: Amount must be greater than zero.");
            return;
        }
        this.balance += amount;
        System.out.printf("Balance after top-up: %.1f%n", this.balance);
    }

    /**
     * Deducts amount from wallet balance.
     * Rejects transactions that exceed the current balance or are non-positive.
     *
     * @param amount deduction amount
     * @return true if deduction was successful, false otherwise
     */
    public boolean deduct(double amount) {
        if (amount <= 0.0) {
            System.out.println("Deduct rejected: deduction amount must be greater than zero.");
            return false;
        }
        if (amount > this.balance) {
            System.out.println("Deduct rejected: insufficient balance");
            return false;
        }
        this.balance -= amount;
        return true;
    }

    /**
     * Read-only getter for wallet balance.
     *
     * @return current balance
     */
    public double getBalance() {
        return this.balance;
    }

    public static void main(String[] args) {
        try {
            // Execution matching sample test scenario
            final double initialOpening = 500.0;
            final double topUpAmount = 200.0;
            final double deductAmount = 1000.0;

            MessWallet wallet = new MessWallet(initialOpening);
            wallet.topUp(topUpAmount);
            wallet.deduct(deductAmount);
            System.out.printf("Final balance: %.1f%n", wallet.getBalance());
        } catch (Exception e) {
            System.err.println("Error processing wallet operations: " + e.getMessage());
        }
    }
}
