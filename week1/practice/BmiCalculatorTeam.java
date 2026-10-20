package week1.practice;
import java.util.Random;

public class BmiCalculatorTeam {

    /**
     * Classifies the health status based on standard BMI thresholds:
     * BMI < 18.5 -> Underweight
     * 18.5 - 24.9 -> Normal
     * 25 - 29.9 -> Overweight
     * >= 30 -> Obese
     */
    public static String getBmiStatus(double bmi) {
        if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi <= 24.9) {
            return "Normal";
        } else if (bmi <= 29.9) {
            return "Overweight";
        } else {
            return "Obese";
        }
    }

    /**
     * Computes BMI for each person and prints the complete wellness report table.
     */
    public static void printWellnessReport(double[] heights, double[] weights) {
        System.out.println("----------------------------------------------------------------------");
        System.out.printf("%-10s | %-12s | %-12s | %-8s | %-12s%n",
                "Person", "Height (m)", "Weight (kg)", "BMI", "Status");
        System.out.println("----------------------------------------------------------------------");

        for (int i = 0; i < heights.length; i++) {
            double height = heights[i];
            double weight = weights[i];

            // BMI = weight / (height * height)
            double bmi = weight / (height * height);
            String status = getBmiStatus(bmi);

            System.out.printf("Person %-3d | %-12.2f | %-12.2f | %-8.2f | %-12s%n",
                    (i + 1), height, weight, bmi, status);
        }

        System.out.println("----------------------------------------------------------------------");
    }

    public static void main(String[] args) {
        int teamSize = 10;
        double[] heights = new double[teamSize];
        double[] weights = new double[teamSize];

        Random random = new Random();

        // Generating realistic sample data:
        // Height between 1.50 m and 2.00 m
        // Weight between 45.0 kg and 110.0 kg
        for (int i = 0; i < teamSize; i++) {
            heights[i] = 1.50 + (random.nextDouble() * 0.50);
            weights[i] = 45.0 + (random.nextDouble() * 65.0);
        }

        // Include the sample test cases explicitly for verification
        heights[0] = 1.75;
        weights[0] = 70.0;

        heights[1] = 1.60;
        weights[1] = 90.0;

        // Print final formatted wellness report
        printWellnessReport(heights, weights);
    }
}
