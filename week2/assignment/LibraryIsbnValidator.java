package week2.assignment;



public class LibraryIsbnValidator {

    /**
     * Normalizes the raw input string:
     * Trims leading/trailing whitespace and uppercases only the first 3 characters,
     * leaving the rest untouched[cite: 23].
     */
    public static String normalizeCode(String raw) {
        if (raw == null) {
            return "";
        }

        String trimmed = raw.trim();

        if (trimmed.length() <= 3) {
            return trimmed.toUpperCase();
        }

        // Uppercase first 3 characters using substring() + concatenation[cite: 23]
        return trimmed.substring(0, 3).toUpperCase() + trimmed.substring(3);
    }

    /**
     * Validates and formats the normalized code:
     * - Exactly 13 characters[cite: 23]
     * - First 3 characters are letters[cite: 23]
     * - Remaining 10 characters are digits[cite: 23]
     * If valid, returns formatted string using StringBuilder: "[PUBCODE] YEAR: 20XX | CATALOG: 123456"[cite: 23]
     * If invalid, returns the specific failure reason[cite: 23].
     */
    public static String validateAndFormat(String code) {
        if (code == null || code.length() != 13) {
            return "Invalid: wrong length (must be exactly 13 characters)";
        }

        // Validate first 3 characters are letters using Character.isLetter() in a loop (no regex)[cite: 23]
        for (int i = 0; i < 3; i++) {
            if (!Character.isLetter(code.charAt(i))) {
                return "Invalid: publisher code must be 3 letters";
            }
        }

        // Validate remaining 10 characters are digits using Character.isDigit() in a loop (no regex)[cite: 23]
        for (int i = 3; i < 13; i++) {
            if (!Character.isDigit(code.charAt(i))) {
                return "Invalid: non-digit body";
            }
        }

        // Extract components:
        // Index 0-2: Publisher Code (3 letters)[cite: 23]
        // Index 3-6: Year (4 digits)[cite: 23]
        // Index 7-12: Catalog Number (6 digits)[cite: 23]
        String publisherCode = code.substring(0, 3);
        String year = code.substring(3, 7);
        String catalogNo = code.substring(7, 13);

        // Build the formatted display string using StringBuilder[cite: 23]
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(publisherCode).append("] YEAR: ")
          .append(year).append(" | CATALOG: ").append(catalogNo);

        return sb.toString();
    }

    public static void main(String[] args) {
        // Test Case 1: From Sample Input[cite: 23]
        String raw1 = " pen2026004251 ";
        String normalized1 = normalizeCode(raw1);
        System.out.println(validateAndFormat(normalized1));

        // Test Case 2: From Sample Input[cite: 23]
        String raw2 = "12N2026004251";
        String normalized2 = normalizeCode(raw2);
        System.out.println(validateAndFormat(normalized2));
    }
}
