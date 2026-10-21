package week4.practice;

/**
 * Problem M3: Late Fees — Skip the On-Time Accounts
 * Demonstrates 'final' methods preventing override alterations, combined with batch branching logic.
 */
public class LateFeeAccount {

    private String regNo;
    private double totalFee;

    // Rate constant: 1% of total fee charged per late day
    private static final double DAILY_LATE_FEE_RATE = 0.01;

    /**
     * Initializes student fee account.
     *
     * @param regNo    student registration number
     * @param totalFee total course tuition fee
     */
    public LateFeeAccount(String regNo, double totalFee) {
        this.regNo = (regNo != null) ? regNo.trim() : "UNKNOWN";
        this.totalFee = Math.max(0.0, totalFee);
    }

    public String getRegNo() {
        return regNo;
    }

    public double getTotalFee() {
        return totalFee;
    }

    /**
     * Final method locking the late fee formula against unauthorized subclass modification.
     *
     * @param daysLate number of overdue days
     * @return calculated late surcharge
     */
    public final double calculateLateFee(int daysLate) {
        if (daysLate <= 0) {
            return 0.0;
        }
        return this.totalFee * DAILY_LATE_FEE_RATE * daysLate;
    }

    /**
     * Final method locking the summary printing contract.
     * Evaluates whether the account is overdue or on time.
     *
     * @param daysLate number of days late
     */
    public final void printSummary(int daysLate) {
        if (daysLate <= 0) {
            System.out.printf("%s - On time, no late fee%n", this.regNo);
        } else {
            double lateFee = calculateLateFee(daysLate);
            System.out.printf("%s | Total Fee: Rs %.1f | Late Fee: Rs %.1f%n",
                    this.regNo, this.totalFee, lateFee);
        }
    }

    /**
     * Processes a batch of accounts across parallel data arrays in a single pass.
     *
     * @param regNos    array of registration numbers
     * @param totalFees array of tuition amounts
     * @param daysLate  array of overdue day counts
     */
    public static void processBatch(String[] regNos, double[] totalFees, int[] daysLate) {
        if (regNos == null || totalFees == null || daysLate == null) {
            System.out.println("Invalid batch input arrays.");
            return;
        }

        int count = Math.min(regNos.length, Math.min(totalFees.length, daysLate.length));
        for (int i = 0; i < count; i++) {
            LateFeeAccount account = new LateFeeAccount(regNos[i], totalFees[i]);
            account.printSummary(daysLate[i]);
        }
    }

    public static void main(String[] args) {
        try {
            String[] regNos = {"RA001", "RA002", "RA003", "RA004"};
            double[] totalFees = {200000.0, 150000.0, 180000.0, 220000.0};
            int[] daysLate = {10, 0, -2, 5};

            processBatch(regNos, totalFees, daysLate);
        } catch (Exception e) {
            System.err.println("Error processing late fee batch: " + e.getMessage());
        }
    }
}
