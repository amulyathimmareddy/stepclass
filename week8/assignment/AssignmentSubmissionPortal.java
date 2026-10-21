package week8.assignment;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * AssignmentSubmissionPortal models an academic assignment submission system with
 * polymorphic late penalty calculations, grading workflows, and resubmission guards.
 */
public class AssignmentSubmissionPortal {

    public enum SubmissionStatus {
        SUBMITTED,
        GRADED
    }

    public static class Student {
        private final String name;

        public Student(String name) {
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Student name cannot be blank");
            }
            this.name = name.trim();
        }

        public String getName() {
            return name;
        }
    }

    /**
     * Abstract assignment defining due dates, maximum marks, and penalty algorithms.
     */
    public static abstract class Assignment {
        private final String title;
        private final int maxMarks;
        private final LocalDate dueDate;

        public Assignment(String title, int maxMarks, LocalDate dueDate) {
            if (title == null || title.trim().isEmpty()) {
                throw new IllegalArgumentException("Title cannot be blank");
            }
            if (maxMarks <= 0) {
                throw new IllegalArgumentException("Max marks must be positive");
            }
            this.title = title.trim();
            this.maxMarks = maxMarks;
            this.dueDate = dueDate;
        }

        public String getTitle() {
            return title;
        }

        public int getMaxMarks() {
            return maxMarks;
        }

        public LocalDate getDueDate() {
            return dueDate;
        }

        public int calculateLateDays(LocalDate submissionDate) {
            if (submissionDate.isAfter(dueDate)) {
                return (int) ChronoUnit.DAYS.between(dueDate, submissionDate);
            }
            return 0;
        }

        public abstract double calculateFinalMarks(double awardedMarks, int lateDays);
        public abstract int getPenaltyPercentage(int lateDays);
    }

    public static class CodingAssignment extends Assignment {
        private static final double PENALTY_PER_DAY = 0.10; // 10% per day

        public CodingAssignment(String title, int maxMarks, LocalDate dueDate) {
            super(title, maxMarks, dueDate);
        }

        @Override
        public double calculateFinalMarks(double awardedMarks, int lateDays) {
            double penaltyFraction = Math.min(1.0, lateDays * PENALTY_PER_DAY);
            return Math.max(0.0, awardedMarks * (1.0 - penaltyFraction));
        }

        @Override
        public int getPenaltyPercentage(int lateDays) {
            return (int) Math.min(100, lateDays * 10);
        }
    }

    public static class WrittenAssignment extends Assignment {
        private static final double PENALTY_PER_DAY = 0.20; // 20% per day

        public WrittenAssignment(String title, int maxMarks, LocalDate dueDate) {
            super(title, maxMarks, dueDate);
        }

        @Override
        public double calculateFinalMarks(double awardedMarks, int lateDays) {
            double penaltyFraction = Math.min(1.0, lateDays * PENALTY_PER_DAY);
            return Math.max(0.0, awardedMarks * (1.0 - penaltyFraction));
        }

        @Override
        public int getPenaltyPercentage(int lateDays) {
            return (int) Math.min(100, lateDays * 20);
        }
    }

    public static class Submission {
        private final Student student;
        private final Assignment assignment;
        private LocalDate submissionDate;
        private SubmissionStatus status;
        private double finalMarks;

        public Submission(Student student, Assignment assignment, LocalDate submissionDate) {
            this.student = student;
            this.assignment = assignment;
            this.submissionDate = submissionDate;
            this.status = SubmissionStatus.SUBMITTED;

            int lateDays = assignment.calculateLateDays(submissionDate);
            String timingStr = lateDays == 0 ? "on time" : lateDays + " days late";
            System.out.printf("%s's submission for '%s' received (%s). Status: %s.%n",
                    student.getName(), assignment.getTitle(), timingStr, status);
        }

        public void resubmit(LocalDate newDate) {
            if (status == SubmissionStatus.GRADED) {
                System.out.printf("Cannot resubmit: '%s' has already been graded.%n", assignment.getTitle());
                return;
            }
            this.submissionDate = newDate;
            System.out.printf("Resubmission received for %s on %s.%n", assignment.getTitle(), newDate);
        }

        public void grade(double awardedMarks) {
            if (status == SubmissionStatus.GRADED) {
                throw new IllegalStateException("Submission has already been graded");
            }
            int lateDays = assignment.calculateLateDays(submissionDate);
            this.finalMarks = assignment.calculateFinalMarks(awardedMarks, lateDays);
            this.status = SubmissionStatus.GRADED;

            if (lateDays > 0) {
                int penaltyPercent = assignment.getPenaltyPercentage(lateDays);
                System.out.printf("%s graded: %d/%d after %d%% late penalty. Status: %s.%n",
                        student.getName(), (int) Math.round(finalMarks), assignment.getMaxMarks(), penaltyPercent, status);
            } else {
                System.out.printf("%s graded: %d/%d. Status: %s.%n",
                        student.getName(), (int) Math.round(finalMarks), assignment.getMaxMarks(), status);
            }
        }
    }

    public static void main(String[] args) {
        LocalDate mar10 = LocalDate.of(2026, 3, 10);
        LocalDate mar12 = LocalDate.of(2026, 3, 12);
        LocalDate mar14 = LocalDate.of(2026, 3, 14);

        Assignment linkedListLab = new CodingAssignment("Linked List Lab", 50, mar10);
        Assignment designEssay = new WrittenAssignment("Design Essay", 50, mar12);

        Student asha = new Student("Asha");
        Student ravi = new Student("Ravi");

        // Asha submits on time
        Submission ashaSub = new Submission(asha, linkedListLab, mar10);

        // Ravi submits 2 days late
        Submission raviSub = new Submission(ravi, designEssay, mar14);

        // Faculty awards Asha 45 marks
        ashaSub.grade(45);

        // Faculty awards Ravi 40 marks
        raviSub.grade(40);

        // Asha attempts to resubmit
        ashaSub.resubmit(mar12);
    }
}
