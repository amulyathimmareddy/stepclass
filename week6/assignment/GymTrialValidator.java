package week6.assignment;

/**
 * Base gym membership entity.
 */
class GymMember {
    private String memberId;
    private int monthlyFee;
    private int sessionsAttended;

    public GymMember(String memberId, int monthlyFee) {
        if (memberId == null || memberId.trim().length() < 4) {
            throw new IllegalArgumentException("memberId must be at least 4 characters.");
        }
        this.memberId = memberId.trim();
        this.monthlyFee = Math.max(0, monthlyFee);
        this.sessionsAttended = 0;
    }

    public void attendSession() {
        sessionsAttended++;
    }

    public String getMemberId() {
        return memberId;
    }

    public int getMonthlyFee() {
        return monthlyFee;
    }

    public int getSessionsAttended() {
        return sessionsAttended;
    }
}

/**
 * Direct subclass for premium gym members.
 */
class PremiumMember extends GymMember {
    private String trainerName;

    public PremiumMember(String memberId, int monthlyFee, String trainerName) {
        super(memberId, monthlyFee);
        this.trainerName = (trainerName != null) ? trainerName.trim() : "Unassigned";
    }

    public String getTrainerName() {
        return trainerName;
    }
}

/**
 * Problem 1: Gym Membership Foundation & Batch Trial Sign-up Validator
 * Implements constructor-level validation and batch sign-up with try/catch error handling.
 */
public class GymTrialValidator {

    /**
     * Attempts batch registration of gym member applications.
     *
     * @param memberIds  array of application member ID codes
     * @param monthlyFee plan base fee
     * @return summary string: "Signed Up: X | Rejected: Y"
     */
    public static String signUpBatch(String[] memberIds, int monthlyFee) {
        if (memberIds == null || memberIds.length == 0) {
            return "Signed Up: 0 | Rejected: 0";
        }

        int signedUp = 0;
        int rejected = 0;

        for (String id : memberIds) {
            try {
                new GymMember(id, monthlyFee);
                signedUp++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }

        return String.format("Signed Up: %d | Rejected: %d", signedUp, rejected);
    }

    public static void main(String[] args) {
        try {
            // Test 1: Invalid ID rejected
            try {
                new GymMember("GM1", 1000);
            } catch (IllegalArgumentException e) {
                System.out.println("construction rejected");
            }

            // Test 2: Premium member session tracking
            PremiumMember member = new PremiumMember("MEM01", 2000, "Coach Riya");
            member.attendSession();
            member.attendSession();
            System.out.println("Sessions attended: " + member.getSessionsAttended());

            // Test 3: Batch sign-up validation
            String[] batch = {"MEM1", "GM1", "MEM2", " ", "MEM3"};
            System.out.println(signUpBatch(batch, 1000));
        } catch (Exception e) {
            System.err.println("Error in gym trial validator: " + e.getMessage());
        }
    }
}
