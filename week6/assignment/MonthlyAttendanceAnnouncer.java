package week6.assignment;

/**
 * Standard member for attendance announcement.
 */
class AnnounceMember {
    protected String memberId;
    protected int monthlyFee;
    protected int sessionsAttended;

    public AnnounceMember(String memberId, int monthlyFee) {
        this.memberId = (memberId != null) ? memberId.trim() : "UNKNOWN";
        this.monthlyFee = Math.max(0, monthlyFee);
        this.sessionsAttended = 0;
    }

    public String displayInfo() {
        return String.format("Standard | Sessions: %d", sessionsAttended);
    }
}

/**
 * Premium member for attendance announcement.
 */
class AnnouncePremiumMember extends AnnounceMember {
    private String trainerName;

    public AnnouncePremiumMember(String memberId, int monthlyFee, String trainerName) {
        super(memberId, monthlyFee);
        this.trainerName = (trainerName != null) ? trainerName.trim() : "Unassigned";
    }

    public String getTrainerName() {
        return trainerName;
    }

    @Override
    public String displayInfo() {
        return String.format("Premium | Trainer: %s | Sessions: %d", trainerName, sessionsAttended);
    }
}

/**
 * Problem 4: The Monthly Attendance Announcer
 * Assembles announcement reports with StringBuilder, polymorphic dispatch, and safe downcasting guards.
 */
public class MonthlyAttendanceAnnouncer {

    /**
     * Builds consolidated announcement across mixed gym memberships.
     *
     * @param members array of AnnounceMember references
     * @return consolidated announcement string
     */
    public static String batchPrint(AnnounceMember[] members) {
        if (members == null || members.length == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        for (AnnounceMember member : members) {
            if (member != null) {
                sb.append(member.displayInfo());
                if (member instanceof AnnouncePremiumMember) {
                    AnnouncePremiumMember prem = (AnnouncePremiumMember) member;
                    sb.append(" [Trainer via downcast: ").append(prem.getTrainerName()).append("]");
                }
                sb.append(" | ");
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        try {
            AnnounceMember[] batch = new AnnounceMember[] {
                new AnnounceMember("MEM6", 1000),
                new AnnouncePremiumMember("MEM7", 2000, "Coach Riya")
            };

            System.out.println(batchPrint(batch));
        } catch (Exception e) {
            System.err.println("Error generating monthly attendance report: " + e.getMessage());
        }
    }
}
