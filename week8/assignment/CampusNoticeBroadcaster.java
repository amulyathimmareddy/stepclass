package week8.assignment;

import java.util.*;

/**
 * CampusNoticeBroadcaster models college notice publishing using the Observer / Strategy patterns,
 * with channel dispatching, department targeting, and validation rules.
 */
public class CampusNoticeBroadcaster {

    public interface NotificationChannel {
        String getChannelName();
        void send(Student student, Notice notice);
    }

    public static class EmailChannel implements NotificationChannel {
        @Override
        public String getChannelName() {
            return "Email";
        }

        @Override
        public void send(Student student, Notice notice) {
            System.out.printf("[Email \u2192 %s] %s.%n", student.getName(), notice.getTitle());
        }
    }

    public static class SmsChannel implements NotificationChannel {
        @Override
        public String getChannelName() {
            return "SMS";
        }

        @Override
        public void send(Student student, Notice notice) {
            System.out.printf("[SMS \u2192 %s] %s.%n", student.getName(), notice.getTitle());
        }
    }

    public static class AppChannel implements NotificationChannel {
        @Override
        public String getChannelName() {
            return "App";
        }

        @Override
        public void send(Student student, Notice notice) {
            System.out.printf("[App \u2192 %s] %s.%n", student.getName(), notice.getTitle());
        }
    }

    public static class Notice {
        private final String title;
        private final Set<String> targetDepartments;

        public Notice(String title, Set<String> targetDepartments) {
            if (title == null || title.trim().isEmpty()) {
                throw new IllegalArgumentException("Cannot post notice: Notice title is required.");
            }
            if (targetDepartments == null || targetDepartments.isEmpty()) {
                throw new IllegalArgumentException("Cannot post notice: At least one target department is required.");
            }
            this.title = title.trim();
            this.targetDepartments = new LinkedHashSet<>(targetDepartments);
        }

        public String getTitle() {
            return title;
        }

        public Set<String> getTargetDepartments() {
            return Collections.unmodifiableSet(targetDepartments);
        }
    }

    public static class Student {
        private final String name;
        private final String department;
        private final List<NotificationChannel> preferredChannels;

        public Student(String name, String department, List<NotificationChannel> preferredChannels) {
            if (name == null || name.trim().isEmpty() || department == null || department.trim().isEmpty()) {
                throw new IllegalArgumentException("Student name and department cannot be blank");
            }
            this.name = name.trim();
            this.department = department.trim().toUpperCase();
            this.preferredChannels = new ArrayList<>(preferredChannels);
        }

        public String getName() {
            return name;
        }

        public String getDepartment() {
            return department;
        }

        public List<NotificationChannel> getPreferredChannels() {
            return Collections.unmodifiableList(preferredChannels);
        }
    }

    public static class NoticeBoard {
        private final List<Student> students = new ArrayList<>();

        public void registerStudent(Student student) {
            students.add(student);
        }

        public void postNotice(String title, Set<String> targetDepartments) {
            Notice notice;
            try {
                notice = new Notice(title, targetDepartments);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                return;
            }

            System.out.printf("Notice '%s' posted to %s.%n",
                    notice.getTitle(), String.join(", ", notice.getTargetDepartments()));

            for (Student student : students) {
                if (notice.getTargetDepartments().contains(student.getDepartment())) {
                    for (NotificationChannel channel : student.getPreferredChannels()) {
                        channel.send(student, notice);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        NotificationChannel email = new EmailChannel();
        NotificationChannel sms = new SmsChannel();
        NotificationChannel app = new AppChannel();

        Student asha = new Student("Asha", "CSE", Arrays.asList(email, app));
        Student ravi = new Student("Ravi", "ECE", Collections.singletonList(sms));

        NoticeBoard noticeBoard = new NoticeBoard();
        noticeBoard.registerStudent(asha);
        noticeBoard.registerStudent(ravi);

        // Admin posts notice 'Lab Closed Tomorrow' for CSE
        noticeBoard.postNotice("Lab Closed Tomorrow", Collections.singleton("CSE"));

        // Admin posts notice 'Fee Deadline Extended' for CSE and ECE
        noticeBoard.postNotice("Fee Deadline Extended", new LinkedHashSet<>(Arrays.asList("CSE", "ECE")));

        // Admin attempts to post notice 'Sports Day' with no target department
        noticeBoard.postNotice("Sports Day", Collections.emptySet());
    }
}
