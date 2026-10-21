package week2.assignment;


public class AtmPinLengthValidator {

    /**
     * Checks if the entered PIN string is exactly 4 characters long.
     * Uses only length() and a single if / else condition.
     */
    public static void checkPinLength(String pin) {
        if (pin != null && pin.length() == 4) {
            System.out.println("PIN length OK.");
        } else {
            System.out.println("Invalid PIN — must be exactly 4 digits.");
        }
    }

    public static void main(String[] args) {
        // Test Case 1: From Sample Output
        String pin1 = "482";
        checkPinLength(pin1);

        // Test Case 2: From Sample Output
        String pin2 = "4820";
        checkPinLength(pin2);
    }
}
