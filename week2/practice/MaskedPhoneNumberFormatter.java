package week2.practice;



public class MaskedPhoneNumberFormatter {

    /**
     * Validates that the phone number is exactly 10 digits.
     * Masks the first 6 digits with "XXXXXX" and separates the last 4 digits with a "-".
     */
    public static String maskPhoneNumber(String phone) {
        // Validation: must not be null and must have length exactly 10
        if (phone == null || phone.length() != 10) {
            return "Invalid phone number";
        }

        // Validation: ensure all characters are digits
        for (int i = 0; i < phone.length(); i++) {
            if (!Character.isDigit(phone.charAt(i))) {
                return "Invalid phone number";
            }
        }

        // Build the masked string using StringBuilder
        StringBuilder sb = new StringBuilder();
        sb.append("XXXXXX");
        sb.append(phone.substring(6)); // extract last 4 digits

        // Insert "-" between mask and last 4 digits
        sb.insert(6, "-");

        return sb.toString();
    }

    public static void main(String[] args) {
        // Test Case 1: From Sample Output
        String phone1 = "9876543210";
        System.out.println(maskPhoneNumber(phone1));

        // Test Case 2: From Sample Output
        String phone2 = "98765";
        System.out.println(maskPhoneNumber(phone2));
    }
}
