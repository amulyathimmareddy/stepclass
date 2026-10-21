package week5.assignment;

/**
 * Problem 2: Reference Desk Subclass Reach
 * Implements an early-exit access-checking linter scanning for the very first denied access attempt.
 */
public class ReferenceDeskAccessChecker {

    /**
     * Classifies access permissions across 5 contexts including subclass inheritance in external packages.
     *
     * @param fieldModifier   one of {"private", "default", "protected", "public"}
     * @param accessorContext one of {"SAME_CLASS", "SAME_PACKAGE", "DIFFERENT_PACKAGE",
     *                        "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE", "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"}
     * @return "ALLOWED" or "DENIED"
     */
    public static String classifyAccess(String fieldModifier, String accessorContext) {
        if (fieldModifier == null || accessorContext == null) {
            return "DENIED";
        }

        switch (fieldModifier.toLowerCase()) {
            case "public":
                return "ALLOWED";

            case "protected":
                if ("SAME_CLASS".equalsIgnoreCase(accessorContext)
                        || "SAME_PACKAGE".equalsIgnoreCase(accessorContext)
                        || "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE".equalsIgnoreCase(accessorContext)) {
                    return "ALLOWED";
                }
                return "DENIED";

            case "default":
                if ("SAME_CLASS".equalsIgnoreCase(accessorContext)
                        || "SAME_PACKAGE".equalsIgnoreCase(accessorContext)) {
                    return "ALLOWED";
                }
                return "DENIED";

            case "private":
                if ("SAME_CLASS".equalsIgnoreCase(accessorContext)) {
                    return "ALLOWED";
                }
                return "DENIED";

            default:
                return "DENIED";
        }
    }

    /**
     * Scans an ordered batch of attempts sequentially, stopping and reporting at the very first denial.
     *
     * @param attempts ordered 2D array of {fieldModifier, accessorContext}
     * @return description of the first denial, or "None Denied" if all attempts were allowed
     */
    public static String firstDeniedAttempt(String[][] attempts) {
        if (attempts == null || attempts.length == 0) {
            return "None Denied";
        }

        for (int i = 0; i < attempts.length; i++) {
            String[] attempt = attempts[i];
            if (attempt != null && attempt.length >= 2) {
                String modifier = attempt[0];
                String context = attempt[1];
                String decision = classifyAccess(modifier, context);

                if ("DENIED".equals(decision)) {
                    // Early return at first violation
                    return String.format("%s via %s (attempt #%d)", modifier, context, (i + 1));
                }
            }
        }

        return "None Denied";
    }

    public static void main(String[] args) {
        try {
            // Test Case 1: Early-exit on attempt #2
            String[][] batch1 = new String[][] {
                {"public", "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"},
                {"protected", "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"},
                {"protected", "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"}
            };
            System.out.println("Batch 1 Result: " + firstDeniedAttempt(batch1));

            // Test Case 2: All allowed -> "None Denied"
            String[][] batch2 = new String[][] {
                {"public", "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"},
                {"protected", "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"}
            };
            System.out.println("Batch 2 Result: " + firstDeniedAttempt(batch2));
        } catch (Exception e) {
            System.err.println("Error evaluating reference desk access attempts: " + e.getMessage());
        }
    }
}
