package week4.practice;

/**
 * Base fee account representing day-scholar student billing.
 */
class FeeAccount {
    public void pay(double amount) {
        System.out.println("Paid in one go (day-scholar account)");
    }
}

/**
 * Subclass representing hostel resident fee billing with installment plan.
 */
class HostelFeeAccount extends FeeAccount {
    @Override
    public void pay(double amount) {
        System.out.println("Paid in two installments (hostel account)");
    }
}

/**
 * Problem M5: Account Batch Payments
 * Demonstrates runtime type inspection using 'instanceof' to dispatch polymorphic operations and maintain tallies.
 */
public class AccountBatchPayments {

    private int hostelAccountsCount = 0;
    private int dayScholarAccountsCount = 0;

    /**
     * Dispatches payment processing based on dynamic type verification via 'instanceof'.
     *
     * @param account target fee account
     * @param amount  payment amount
     */
    public void processPayment(FeeAccount account, double amount) {
        if (account == null) {
            return;
        }

        if (account instanceof HostelFeeAccount) {
            hostelAccountsCount++;
            account.pay(amount);
        } else if (account instanceof FeeAccount) {
            dayScholarAccountsCount++;
            account.pay(amount);
        }
    }

    /**
     * Prints aggregate batch processing tallies.
     */
    public void printBatchSummary() {
        System.out.printf("Hostel accounts processed: %d | Day-scholar accounts processed: %d%n",
                hostelAccountsCount, dayScholarAccountsCount);
    }

    /**
     * Processes an entire batch array of accounts.
     *
     * @param accounts array of FeeAccount references
     * @param amount   amount to pay per account
     */
    public void processBatch(FeeAccount[] accounts, double amount) {
        if (accounts == null) {
            return;
        }
        for (FeeAccount account : accounts) {
            processPayment(account, amount);
        }
        printBatchSummary();
    }

    public static void main(String[] args) {
        try {
            AccountBatchPayments processor = new AccountBatchPayments();

            FeeAccount[] accounts = new FeeAccount[] {
                new HostelFeeAccount(),
                new HostelFeeAccount(),
                new FeeAccount(),
                new FeeAccount()
            };

            final double paymentAmount = 60000.0;
            processor.processBatch(accounts, paymentAmount);
        } catch (Exception e) {
            System.err.println("Error processing account batch payments: " + e.getMessage());
        }
    }
}
