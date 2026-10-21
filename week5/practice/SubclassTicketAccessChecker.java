package week5.practice;

/**
 * Problem 2: Subclass Ticket Access
 * Models the nuanced Java access rules for 'protected' members when accessed across packages:
 * - In a subclass in a different package, access to protected members of the superclass is allowed
 *   ONLY through references of the subclass type (or its subtypes) — NOT through a reference of the superclass parent type!
 */
public class SubclassTicketAccessChecker {

    /**
     * Classifies access permissions across 5 contexts including cross-package inheritance scenarios.
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

    public static void main(String[] args) {
        try {
            // Test cases from problem statement
            String ownTypeResult = classifyAccess("protected", "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE");
            String parentTypeResult = classifyAccess("protected", "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE");

            System.out.println("protected via SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE: " + ownTypeResult);
            System.out.println("protected via SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE: " + parentTypeResult);
        } catch (Exception e) {
            System.err.println("Error evaluating subclass access rules: " + e.getMessage());
        }
    }
}
