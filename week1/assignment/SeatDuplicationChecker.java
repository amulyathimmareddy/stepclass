package week1.assignment;



public class SeatDuplicationChecker {

    /**
     * Checks for duplicate seat numbers using nested loops on the array.
     * Avoids using the Collections framework as specified in the task.
     */
    public static void checkDuplicateSeats(int[] seatNumbers) {
        if (seatNumbers == null || seatNumbers.length == 0) {
            System.out.println("No Duplicate Seats Found");
            return;
        }

        boolean hasDuplicate = false;

        // Nested loops to compare every seat number with every other seat number
        for (int i = 0; i < seatNumbers.length; i++) {
            // Check if seatNumbers[i] has already been printed as a duplicate
            boolean alreadyReported = false;
            for (int k = 0; k < i; k++) {
                if (seatNumbers[k] == seatNumbers[i]) {
                    alreadyReported = true;
                    break;
                }
            }

            if (alreadyReported) {
                continue;
            }

            // Compare seatNumbers[i] with the rest of the array elements
            for (int j = i + 1; j < seatNumbers.length; j++) {
                if (seatNumbers[i] == seatNumbers[j]) {
                    System.out.println("Duplicate Seat Number Found: " + seatNumbers[i]);
                    hasDuplicate = true;
                    break;
                }
            }
        }

        if (!hasDuplicate) {
            System.out.println("No Duplicate Seats Found");
        }
    }

    public static void main(String[] args) {
        // Test Case 1: From Sample Input
        int[] hallA = {101, 102, 103, 102, 105};
        System.out.println("Testing Hall A:");
        checkDuplicateSeats(hallA);

        System.out.println();

        // Test Case 2: From Sample Input
        int[] hallB = {101, 102, 103, 104, 105};
        System.out.println("Testing Hall B:");
        checkDuplicateSeats(hallB);
    }
}
