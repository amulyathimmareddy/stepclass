package week6.assignment;

/**
 * Base member with polymorphic display info.
 */
class BaseGymMember {
    protected String memberId;
    protected int monthlyFee;
    protected int sessionsAttended;

    public BaseGymMember(String memberId, int monthlyFee) {
        this.memberId = (memberId != null) ? memberId.trim() : "UNKNOWN";
        this.monthlyFee = Math.max(0, monthlyFee);
        this.sessionsAttended = 0;
    }

    public void attendSession() {
        sessionsAttended++;
    }

    public int getSessionsAttended() {
        return sessionsAttended;
    }

    public String displayInfo() {
        return String.format("Standard Member | Sessions: %d", sessionsAttended);
    }
}

/**
 * Premium member tier.
 */
class PremiumTierMember extends BaseGymMember {
    protected String trainerName;

    public PremiumTierMember(String memberId, int monthlyFee, String trainerName) {
        super(memberId, monthlyFee);
        this.trainerName = (trainerName != null) ? trainerName.trim() : "Unassigned";
    }

    @Override
    public String displayInfo() {
        return String.format("Premium Member | Trainer: %s | Sessions: %d", trainerName, sessionsAttended);
    }
}

/**
 * Multilevel descendant: 3 generations deep.
 */
class EliteMember extends PremiumTierMember {
    private String lockerNumber;

    public EliteMember(String memberId, int monthlyFee, String trainerName, String lockerNumber) {
        super(memberId, monthlyFee, trainerName);
        this.lockerNumber = (lockerNumber != null) ? lockerNumber.trim() : "None";
    }

    @Override
    public String displayInfo() {
        return String.format("Elite Member | Trainer: %s | Locker: %s | Sessions: %d",
                trainerName, lockerNumber, sessionsAttended);
    }
}

/**
 * Hierarchical sibling: extends BaseGymMember directly.
 */
class GroupClassMember extends BaseGymMember {
    private String className;

    public GroupClassMember(String memberId, int monthlyFee, String className) {
        super(memberId, monthlyFee);
        this.className = (className != null) ? className.trim() : "General";
    }

    @Override
    public String displayInfo() {
        return String.format("Group Class Member | Class: %s | Sessions: %d", className, sessionsAttended);
    }
}

/**
 * Problem 2: Three Tiers of Gym Membership
 * Demonstrates multilevel inheritance, hierarchical branching, and polymorphic aggregation.
 */
public class GymMembershipHierarchy {

    /**
     * Inspects generation depth via instanceof checks.
     *
     * @param member target member reference
     * @return lineage description
     */
    public static String classifyGeneration(BaseGymMember member) {
        if (member == null) {
            return "Null Reference";
        }
        if (member instanceof EliteMember) {
            return "Multilevel descendant (3 generations deep)";
        } else if (member instanceof GroupClassMember) {
            return "Hierarchical sibling (independent branch)";
        } else if (member instanceof PremiumTierMember) {
            return "Direct subclass (2 generations deep)";
        } else {
            return "Standard Member (root)";
        }
    }

    /**
     * Sums sessions attended polymorphically across all tiers without type-checking.
     *
     * @param members array of BaseGymMember references
     * @return aggregate session count
     */
    public static int getTotalSessionsAttended(BaseGymMember[] members) {
        if (members == null) {
            return 0;
        }
        int total = 0;
        for (BaseGymMember m : members) {
            if (m != null) {
                total += m.getSessionsAttended();
            }
        }
        return total;
    }

    public static void main(String[] args) {
        try {
            BaseGymMember std = new BaseGymMember("MEM1", 1000);
            PremiumTierMember prem = new PremiumTierMember("MEM2", 2000, "Coach Riya");
            EliteMember elite = new EliteMember("MEM3", 3000, "Coach Arjun", "L12");
            GroupClassMember group = new GroupClassMember("MEM4", 1500, "Zumba");

            System.out.println(std.displayInfo());
            System.out.println(prem.displayInfo());
            System.out.println(elite.displayInfo());
            System.out.println(group.displayInfo());

            System.out.println(classifyGeneration(elite));
            System.out.println(classifyGeneration(group));

            // Set up sessions
            prem.attendSession();
            prem.attendSession();
            prem.attendSession(); // 3
            elite.attendSession();
            elite.attendSession(); // 2
            group.attendSession();
            group.attendSession();
            group.attendSession();
            group.attendSession(); // 4

            BaseGymMember[] mix = {prem, elite, group};
            System.out.println("Total sessions: " + getTotalSessionsAttended(mix));
        } catch (Exception e) {
            System.err.println("Error in gym hierarchy: " + e.getMessage());
        }
    }
}
