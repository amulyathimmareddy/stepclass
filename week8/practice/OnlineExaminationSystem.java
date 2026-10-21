package week8.practice;

import java.util.*;

/**
 * OnlineExaminationSystem models an online test platform with polymorphic question evaluation,
 * attempt tracking, and immutability guarantees post-submission.
 */
public class OnlineExaminationSystem {

    public enum AttemptStatus {
        IN_PROGRESS,
        SUBMITTED
    }

    /**
     * Abstract question with self-evaluation capability.
     */
    public static abstract class Question {
        private final String id;
        private final String prompt;
        private final int points;

        public Question(String id, String prompt, int points) {
            if (id == null || id.trim().isEmpty() || prompt == null || prompt.trim().isEmpty()) {
                throw new IllegalArgumentException("Question ID and prompt cannot be blank");
            }
            if (points <= 0) {
                throw new IllegalArgumentException("Points must be positive");
            }
            this.id = id.trim();
            this.prompt = prompt.trim();
            this.points = points;
        }

        public String getId() {
            return id;
        }

        public String getPrompt() {
            return prompt;
        }

        public int getPoints() {
            return points;
        }

        public abstract boolean evaluate(String submittedAnswer);
    }

    public static class MultipleChoiceQuestion extends Question {
        private final String correctOption;

        public MultipleChoiceQuestion(String id, String prompt, int points, String correctOption) {
            super(id, prompt, points);
            this.correctOption = correctOption.trim().toUpperCase();
        }

        @Override
        public boolean evaluate(String submittedAnswer) {
            return submittedAnswer != null && correctOption.equalsIgnoreCase(submittedAnswer.trim());
        }
    }

    public static class TrueFalseQuestion extends Question {
        private final boolean correctAnswer;

        public TrueFalseQuestion(String id, String prompt, int points, boolean correctAnswer) {
            super(id, prompt, points);
            this.correctAnswer = correctAnswer;
        }

        @Override
        public boolean evaluate(String submittedAnswer) {
            if (submittedAnswer == null) {
                return false;
            }
            return Boolean.parseBoolean(submittedAnswer.trim()) == correctAnswer;
        }
    }

    public static class ShortAnswerQuestion extends Question {
        private final String expectedAnswer;

        public ShortAnswerQuestion(String id, String prompt, int points, String expectedAnswer) {
            super(id, prompt, points);
            this.expectedAnswer = expectedAnswer.trim().toLowerCase();
        }

        @Override
        public boolean evaluate(String submittedAnswer) {
            return submittedAnswer != null && expectedAnswer.equalsIgnoreCase(submittedAnswer.trim());
        }
    }

    public static class Student {
        private final String id;
        private final String name;

        public Student(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public static class Examination {
        private final String code;
        private final String title;
        private final List<Question> questions;

        public Examination(String code, String title) {
            this.code = code;
            this.title = title;
            this.questions = new ArrayList<>();
        }

        public void addQuestion(Question question) {
            questions.add(question);
        }

        public List<Question> getQuestions() {
            return Collections.unmodifiableList(questions);
        }

        public int getTotalPoints() {
            int sum = 0;
            for (Question q : questions) {
                sum += q.getPoints();
            }
            return sum;
        }

        public String getCode() {
            return code;
        }

        public String getTitle() {
            return title;
        }
    }

    public static class Attempt {
        private final Student student;
        private final Examination examination;
        private final Map<String, String> answers;
        private AttemptStatus status;

        public Attempt(Student student, Examination examination) {
            this.student = student;
            this.examination = examination;
            this.answers = new LinkedHashMap<>();
            this.status = AttemptStatus.IN_PROGRESS;
        }

        public void recordAnswer(String questionId, String answer) {
            if (status == AttemptStatus.SUBMITTED) {
                throw new IllegalStateException("Cannot change answers for a submitted examination.");
            }
            answers.put(questionId, answer);
            System.out.printf("Answer recorded for %s.%n", questionId);
        }

        public void submit() {
            if (status == AttemptStatus.SUBMITTED) {
                throw new IllegalStateException("Exam is already submitted.");
            }
            this.status = AttemptStatus.SUBMITTED;
            System.out.printf("%s submitted by %s.%n", examination.getTitle(), student.getName());

            int totalScore = 0;
            StringBuilder resultSummary = new StringBuilder("Result: ");
            List<Question> questions = examination.getQuestions();
            for (int i = 0; i < questions.size(); i++) {
                Question q = questions.get(i);
                String recorded = answers.get(q.getId());
                boolean correct = q.evaluate(recorded);
                int scoreEarned = correct ? q.getPoints() : 0;
                totalScore += scoreEarned;

                resultSummary.append(String.format("%s: %s (%d points)",
                        q.getId(), (correct ? "Correct" : "Incorrect"), scoreEarned));
                if (i < questions.size() - 1) {
                    resultSummary.append(", ");
                }
            }
            resultSummary.append(String.format(". Total score: %d/%d.", totalScore, examination.getTotalPoints()));
            System.out.println(resultSummary);
        }
    }

    public static void main(String[] args) {
        Examination examA = new Examination("EX-101", "Exam A");
        examA.addQuestion(new MultipleChoiceQuestion("Question 1", "Select prime: A) 4 B) 6 C) 7 D) 9", 5, "C"));
        examA.addQuestion(new TrueFalseQuestion("Question 2", "The Earth is flat", 5, false));

        Student student1 = new Student("S1", "Student 1");
        System.out.printf("%s started by %s.%n", examA.getTitle(), student1.getName());

        Attempt attempt = new Attempt(student1, examA);
        attempt.recordAnswer("Question 1", "C");
        attempt.recordAnswer("Question 2", "True");

        attempt.submit();

        // Attempting to change answer after submission
        try {
            attempt.recordAnswer("Question 1", "A");
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }
}
