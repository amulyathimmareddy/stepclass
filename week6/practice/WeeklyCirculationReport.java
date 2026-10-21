package week6.practice;

/**
 * Member for circulation report.
 */
class ReportMember {
    protected String memberId;
    protected int borrowLimit;
    protected int booksBorrowed;

    public ReportMember(String memberId, int borrowLimit) {
        this.memberId = (memberId != null) ? memberId.trim() : "UNKNOWN";
        this.borrowLimit = Math.max(1, borrowLimit);
        this.booksBorrowed = 0;
    }

    public String displayInfo() {
        return String.format("General | Books: %d", booksBorrowed);
    }
}

/**
 * Student member for circulation report.
 */
class ReportStudentMember extends ReportMember {
    private String course;

    public ReportStudentMember(String memberId, int borrowLimit, String course) {
        super(memberId, borrowLimit);
        this.course = (course != null) ? course.trim() : "General";
    }

    public String getCourse() {
        return course;
    }

    @Override
    public String displayInfo() {
        return String.format("Student | Course: %s | Books: %d", course, booksBorrowed);
    }
}

/**
 * Problem 4: The Weekly Circulation Report
 * Assembles batch reports with StringBuilder, polymorphic display calls, and safe downcasting guards.
 */
public class WeeklyCirculationReport {

    /**
     * Builds report text across mixed members.
     *
     * @param members array of ReportMember references
     * @return consolidated report string
     */
    public static String batchPrint(ReportMember[] members) {
        if (members == null || members.length == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        for (ReportMember member : members) {
            if (member != null) {
                sb.append(member.displayInfo());
                if (member instanceof ReportStudentMember) {
                    ReportStudentMember stu = (ReportStudentMember) member;
                    sb.append(" [Course via downcast: ").append(stu.getCourse()).append("]");
                }
                sb.append(" | ");
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        try {
            ReportMember[] batch = new ReportMember[] {
                new ReportMember("LB5", 3),
                new ReportStudentMember("STU6", 3, "ECE")
            };

            System.out.println(batchPrint(batch));
        } catch (Exception e) {
            System.err.println("Error generating circulation report: " + e.getMessage());
        }
    }
}
