package week2.practice;



public class FileExtensionValidator {

    /**
     * Validates whether an uploaded filename has an accepted extension (pdf, docx, zip)
     * regardless of case.
     */
    public static String validateFileExtension(String filename) {
        if (filename == null) {
            return "Rejected — invalid file type";
        }

        // Find the last index of '.'
        int lastDotIndex = filename.lastIndexOf('.');

        // Check if '.' exists and is not the last character
        if (lastDotIndex == -1 || lastDotIndex == filename.length() - 1) {
            return "Rejected — invalid file type";
        }

        // Extract the extension using substring()
        String extension = filename.substring(lastDotIndex + 1);

        // Compare case-insensitively against accepted types: pdf, docx, zip
        if (extension.equalsIgnoreCase("pdf") ||
            extension.equalsIgnoreCase("docx") ||
            extension.equalsIgnoreCase("zip")) {
            return "Accepted";
        } else {
            return "Rejected — invalid file type";
        }
    }

    public static void main(String[] args) {
        // Test Case 1: From Sample Output
        String file1 = "Assignment1.PDF";
        System.out.println(validateFileExtension(file1));

        // Test Case 2: From Sample Output
        String file2 = "notes.txt";
        System.out.println(validateFileExtension(file2));
    }
}
