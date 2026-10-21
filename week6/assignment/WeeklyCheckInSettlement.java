package week6.assignment;

/**
 * Base member with final membershipNumber and fee tracking.
 */
class CheckInMember {
    private static int counter = 2001;
    public final String membershipNumber;
    private int monthlyFee;
    private int feesPaid;

    public CheckInMember(int monthlyFee) {
        this.membershipNumber = "GYM-" + (counter++);
        this.monthlyFee = Math.max(0, monthlyFee);
        this.feesPaid = 0;
    }

    public static int getMembersEnrolled() {
        return counter - 2001;
    }

    public void payFee(int amount) {
        if (amount > 0) {
            feesPaid += amount;
        }
    }

    public void payFee(int amount, String mode) {
        // Records mode and reuses core payment logic
        payFee(amount);
    }

    public int getFeesPaid() {
        return feesPaid;
    }

    public int getMonthlyFee() {
        return monthlyFee;
    }
}

/**
 * Subclass for group class members.
 */
class CheckInGroupClassMember extends CheckInMember {
    private String className;

    public CheckInGroupClassMember(int monthlyFee, String className) {
        super(monthlyFee);
        this.className = (className != null) ? className.trim() : "General";
    }

    public String getClassName() {
        return className;
    }
}

/**
 * Problem 5: Membership Numbers, Referral Codes & the Weekly Check-in Settlement
 * Demonstrates final immutable identification numbers, manual character parsing, and null-safe settlement.
 */
public class WeeklyCheckInSettlement {

    /**
     * Validates referral code format: 'G' followed by two digits and one uppercase letter (e.g. "G45B").
     *
     * @param code proposed referral code
     * @return true if valid format, false otherwise
     */
    public static boolean isValidReferralCode(String code) {
        if (code == null || code.length() != 4) {
            return false;
        }

        if (code.charAt(0) != 'G') {
            return false;
        }

        if (!Character.isDigit(code.charAt(1)) || !Character.isDigit(code.charAt(2))) {
            return false;
        }

        return Character.isUpperCase(code.charAt(3));
    }

    /**
     * Reconciles weekly check-ins across mixed members safely handling null entries.
     *
     * @param members array of CheckInMember objects
     * @return summary string: "X processed | Y null skipped | G group | I individual"
     */
    public static String processWeeklyCheckIn(CheckInMember[] members) {
        if (members == null || members.length == 0) {
            return "0 processed | 0 null skipped | 0 group | 0 individual";
        }

        int nullCount = 0;
        int groupCount = 0;
        int individualCount = 0;

        for (CheckInMember m : members) {
            if (m == null) {
                nullCount++;
            } else if (m instanceof CheckInGroupClassMember) {
                groupCount++;
            } else {
                individualCount++;
            }
        }

        int totalProcessed = groupCount + individualCount;
        return String.format("%d processed | %d null skipped | %d group | %d individual",
                totalProcessed, nullCount, groupCount, individualCount);
    }

    public static void main(String[] args) {
        try {
            CheckInMember m1 = new CheckInMember(1000);
            System.out.println("Membership Number: " + m1.membershipNumber);
            System.out.println("Members Enrolled: " + CheckInMember.getMembersEnrolled());

            // Validate referral codes
            System.out.println("isValidReferralCode('G45B'): " + isValidReferralCode("G45B"));
            System.out.println("isValidReferralCode('G4B'):  " + isValidReferralCode("G4B"));
            System.out.println("isValidReferralCode('X45B'): " + isValidReferralCode("X45B"));

            // Overloaded fee payments
            m1.payFee(500);
            m1.payFee(500, "UPI");
            System.out.println("Fees paid: " + m1.getFeesPaid());

            // Weekly check-in reconciliation test
            CheckInMember[] batch = new CheckInMember[] {
                new CheckInGroupClassMember(1500, "Zumba"),
                null,
                new CheckInMember(1000)
            };

            System.out.println(processWeeklyCheckIn(batch));
        } catch (Exception e) {
            System.err.println("Error running weekly check-in settlement: " + e.getMessage());
        }
    }
}
