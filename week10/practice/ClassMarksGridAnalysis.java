package week10.practice;

import java.util.ArrayList;
import java.util.List;

/**
 * Record representing student marks in a subject sequence.
 */
class StudentRecord {
    String name;
    int[] marks;

    public StudentRecord(String name, int[] marks) {
        this.name = name;
        this.marks = marks;
    }

    public int getTotal() {
        int sum = 0;
        for (int m : marks) {
            sum += m;
        }
        return sum;
    }
}

/**
 * Problem 4: Class Marks Grid Analysis
 * Analyzes student marks in 2D array, computing individual totals, subject averages, and top earner.
 */
public class ClassMarksGridAnalysis {

    public static void analyzeMarks(List<StudentRecord> students) {
        if (students == null || students.isEmpty()) {
            return;
        }

        int numStudents = students.size();
        int numSubjects = students.get(0).marks.length;

        // 1. Calculate and print student totals
        StringBuilder totalsSb = new StringBuilder("Totals ");
        String topperName = "";
        int topperTotal = -1;

        for (int i = 0; i < numStudents; i++) {
            StudentRecord s = students.get(i);
            int total = s.getTotal();
            if (i > 0) {
                totalsSb.append(", ");
            }
            totalsSb.append(s.name).append(" ").append(total);

            if (total > topperTotal) {
                topperTotal = total;
                topperName = s.name;
            }
        }
        System.out.println(totalsSb.toString());

        // 2. Calculate subject averages
        double[] subjectSums = new double[numSubjects];
        for (StudentRecord s : students) {
            for (int j = 0; j < numSubjects; j++) {
                subjectSums[j] += s.marks[j];
            }
        }

        StringBuilder avgSb = new StringBuilder("averages ");
        for (int j = 0; j < numSubjects; j++) {
            if (j > 0) {
                avgSb.append(", ");
            }
            avgSb.append(String.format("%.2f", subjectSums[j] / numStudents));
        }
        System.out.println(avgSb.toString());

        // 3. Print topper
        System.out.printf("topper %s (%d)%n", topperName, topperTotal);
    }

    public static void main(String[] args) {
        try {
            List<StudentRecord> list = new ArrayList<>();
            list.add(new StudentRecord("Asha", new int[] {78, 85, 90}));
            list.add(new StudentRecord("Ravi", new int[] {88, 92, 79}));
            list.add(new StudentRecord("Neha", new int[] {65, 70, 95}));

            analyzeMarks(list);
        } catch (Exception e) {
            System.err.println("Error analyzing class marks: " + e.getMessage());
        }
    }
}
