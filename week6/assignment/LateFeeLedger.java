package week6.assignment;

import java.util.Arrays;

/**
 * Base member with private late fee ledger.
 */
class AccountGymMember {
    protected String memberId;
    protected int monthlyFee;
    private int[] lateFeeHistory;
    private int feeCount;
    private int totalLateFees;

    public AccountGymMember(String memberId, int monthlyFee) {
        this.memberId = (memberId != null) ? memberId.trim() : "UNKNOWN";
        this.monthlyFee = Math.max(0, monthlyFee);
        this.lateFeeHistory = new int[10];
        this.feeCount = 0;
        this.totalLateFees = 0;
    }

    protected void chargeLateFee(int amount) {
        if (amount <= 0) {
            return;
        }
        if (feeCount < lateFeeHistory.length) {
            lateFeeHistory[feeCount++] = amount;
        }
        totalLateFees += amount;
    }

    public int[] getLateFeeHistory() {
        return Arrays.copyOf(lateFeeHistory, feeCount);
    }

    public int getTotalLateFees() {
        return totalLateFees;
    }
}

/**
 * Subclass granting 50% discount on cancellation late fees.
 */
class DiscountedPremiumMember extends AccountGymMember {
    private String trainerName;

    public DiscountedPremiumMember(String memberId, int monthlyFee, String trainerName) {
        super(memberId, monthlyFee);
        this.trainerName = (trainerName != null) ? trainerName.trim() : "Unassigned";
    }

    @Override
    protected void chargeLateFee(int amount) {
        // Halves the penalty and delegates recording to superclass
        super.chargeLateFee(amount / 2);
    }

    public String getTrainerName() {
        return trainerName;
    }
}

/**
 * Problem 3: The Premium Loyalty Discount & Late-Fee Ledger
 * Demonstrates superclass delegation with @Override and defensive copying of audit history.
 */
public class LateFeeLedger {

    public static void main(String[] args) {
        try {
            DiscountedPremiumMember member = new DiscountedPremiumMember("MEM5", 2000, "Coach Riya");
            member.chargeLateFee(200);

            // Total late fee should be 100 (halved)
            System.out.println("Total Late Fees: " + member.getTotalLateFees());

            // Defensive copy verification
            int[] history = member.getLateFeeHistory();
            history[0] = 999; // Attempted tampering
            System.out.println("History after external tamper attempt: " + Arrays.toString(member.getLateFeeHistory()));
        } catch (Exception e) {
            System.err.println("Error in late fee ledger: " + e.getMessage());
        }
    }
}
