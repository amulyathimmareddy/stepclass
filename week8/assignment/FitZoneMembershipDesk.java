package week8.assignment;

/**
 * FitZoneMembershipDesk models gym memberships with tiered discounted duration plans,
 * lifecycle state management (Active, Frozen, Expired), and validation rules.
 */
public class FitZoneMembershipDesk {

    public static final double BASE_RATE_PER_MONTH = 1000.0;

    public enum MembershipStatus {
        ACTIVE,
        FROZEN,
        EXPIRED
    }

    public static abstract class MembershipPlan {
        private final String name;
        private final int months;

        public MembershipPlan(String name, int months) {
            this.name = name;
            this.months = months;
        }

        public String getName() {
            return name;
        }

        public int getMonths() {
            return months;
        }

        public abstract double calculateFee(double baseRatePerMonth);
    }

    public static class MonthlyPlan extends MembershipPlan {
        public MonthlyPlan() {
            super("Monthly", 1);
        }

        @Override
        public double calculateFee(double baseRatePerMonth) {
            return getMonths() * baseRatePerMonth;
        }
    }

    public static class QuarterlyPlan extends MembershipPlan {
        public QuarterlyPlan() {
            super("Quarterly", 3);
        }

        @Override
        public double calculateFee(double baseRatePerMonth) {
            // 3 months, 10% discount
            return getMonths() * baseRatePerMonth * 0.90;
        }
    }

    public static class AnnualPlan extends MembershipPlan {
        public AnnualPlan() {
            super("Annual", 12);
        }

        @Override
        public double calculateFee(double baseRatePerMonth) {
            // 12 months, 25% discount
            return getMonths() * baseRatePerMonth * 0.75;
        }
    }

    public static class Member {
        private final String name;

        public Member(String name) {
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Member name cannot be blank");
            }
            this.name = name.trim();
        }

        public String getName() {
            return name;
        }
    }

    public static class Membership {
        private final Member member;
        private final MembershipPlan plan;
        private final double fee;
        private MembershipStatus status;

        public Membership(Member member, MembershipPlan plan) {
            this.member = member;
            this.plan = plan;
            this.fee = plan.calculateFee(BASE_RATE_PER_MONTH);
            this.status = MembershipStatus.ACTIVE;
            System.out.printf("%s membership created for %s. Fee: \u20B9%,.2f. Status: %s.%n",
                    plan.getName(), member.getName(), fee, formatStatus(status));
        }

        private static String formatStatus(MembershipStatus s) {
            switch (s) {
                case ACTIVE: return "Active";
                case FROZEN: return "Frozen";
                case EXPIRED: return "Expired";
                default: return s.toString();
            }
        }

        public Member getMember() {
            return member;
        }

        public MembershipPlan getPlan() {
            return plan;
        }

        public double getFee() {
            return fee;
        }

        public MembershipStatus getStatus() {
            return status;
        }

        public void checkIn() {
            if (status == MembershipStatus.ACTIVE) {
                System.out.printf("%s checked in successfully.%n", member.getName());
            } else {
                System.out.printf("Check-in denied: %s's membership is %s.%n",
                        member.getName(), formatStatus(status));
            }
        }

        public void freeze() {
            if (status == MembershipStatus.EXPIRED) {
                System.out.println("Cannot freeze an Expired membership.");
                return;
            }
            if (status == MembershipStatus.FROZEN) {
                System.out.println("Membership is already frozen.");
                return;
            }
            status = MembershipStatus.FROZEN;
            System.out.printf("%s's membership frozen. Status: %s.%n",
                    member.getName(), formatStatus(status));
        }

        public void unfreeze() {
            if (status == MembershipStatus.EXPIRED) {
                System.out.println("Cannot unfreeze an Expired membership.");
                return;
            }
            if (status == MembershipStatus.ACTIVE) {
                System.out.println("Membership is already active.");
                return;
            }
            status = MembershipStatus.ACTIVE;
            System.out.printf("%s's membership unfrozen. Status: %s.%n",
                    member.getName(), formatStatus(status));
        }

        public void expire() {
            status = MembershipStatus.EXPIRED;
            System.out.printf("%s's membership expired. Status: %s.%n",
                    member.getName(), formatStatus(status));
        }
    }

    public static void main(String[] args) {
        Member asha = new Member("Asha");
        Member ravi = new Member("Ravi");

        // Asha buys Quarterly membership
        Membership ashaMem = new Membership(asha, new QuarterlyPlan());

        // Ravi buys Monthly membership
        Membership raviMem = new Membership(ravi, new MonthlyPlan());

        // Asha checks in
        ashaMem.checkIn();

        // Asha freezes her membership
        ashaMem.freeze();

        // Asha attempts to check in
        ashaMem.checkIn();

        // Ravi's membership expires
        raviMem.expire();

        // Ravi attempts to freeze his membership
        raviMem.freeze();
    }
}
