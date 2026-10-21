package week2.practice;


public class BankTransactionReferenceValidator {

    /**
     * Normalizes the raw reference string:
     * Trims whitespace and uppercases only the first 3 characters,
     * leaving the rest untouched.
     */
    public static String normalizeReference(String raw) {
        if (raw == null) {
            return "";
        }

        String trimmed = raw.trim();

        // If the length is less than 3, uppercase whatever is present
        if (trimmed.length() <= 3) {
            return trimmed.toUpperCase();
        }

        // Uppercase first 3 characters using substring and concatenation
        return trimmed.substring(0, 3).toUpperCase() + trimmed.substring(3);
    }

    /**
     * Validates the normalized reference:
     * - Exactly 14 characters
     * - First 3 characters are letters
     * - Remaining 11 characters are digits
     * If valid, builds and returns formatted line: "[BANKCODE] DATE: dd/MM/yy | SEQ: 12345"
     * If invalid, returns the specific failure reason.
     */
    public static String validateAndFormat(String reference) {
        if (reference == null || reference.length() != 14) {
            return "Invalid: wrong length (must be exactly 14 characters)";
        }

        // Validate first 3 characters are letters (no regex)
        for (int i = 0; i < 3; i++) {
            if (!Character.isLetter(reference.charAt(i))) {
                return "Invalid: bank code must be 3 letters";
            }
        }

        // Validate remaining 11 characters are digits (no regex)
        for (int i = 3; i < 14; i++) {
            if (!Character.isDigit(reference.charAt(i))) {
                return "Invalid: remaining characters must be digits";
            }
        }

        // Extract components:
        // Index 0-2: Bank Code
        // Index 3-8: Date (ddMMyy -> 6 digits)
        // Index 9-13: Sequence Number (5 digits)
        String bankCode = reference.substring(0, 3);
        String day = reference.substring(3, 5);
        String month = reference.substring(5, 7);
        String year = reference.substring(7, 9);
        String seq = reference.substring(9, 14);

        // Build the formatted display string using StringBuilder
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(bankCode).append("] DATE: ")
          .append(day).append("/").append(month).append("/").append(year)
          .append(" | SEQ: ").append(seq);

        return sb.toString();
    }

    public static void main(String[] args) {
        // Test Case 1: From Sample Input
        String raw1 = " hdf03022600042 ";
        String normalized1 = normalizeReference(raw1);
        System.out.println(validateAndFormat(normalized1));

        // Test Case 2: From Sample Input
        String raw2 = "12F03022600042";
        String normalized2 = normalizeReference(raw2);
        System.out.println(validateAndFormat(normalized2));
    }
}
