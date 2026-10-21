package week5.assignment;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Representation of a library membership entity demonstrating Java's 4 access tiers:
 * - membershipPin: private (accessible strictly within LibraryMember)
 * - branchCode: package-private / default (accessible within package)
 * - finesOwed: protected (accessible within package and subclasses)
 * - displayName: public (accessible globally)
 */
class LibraryMember {
    private int membershipPin;
    /* default */ String branchCode;
    protected double finesOwed;
    public String displayName;

    public LibraryMember(int membershipPin, String branchCode, double finesOwed, String displayName) {
        this.membershipPin = membershipPin;
        this.branchCode = branchCode;
        this.finesOwed = finesOwed;
        this.displayName = displayName;
    }

    public int getMembershipPin() {
        return membershipPin;
    }
}

/**
 * Problem 1: Membership Field Reach Checker
 * Evaluates access attempts and groups results by access modifier.
 */
public class MemberReachChecker {

    /**
     * Determines whether field access is ALLOWED or DENIED based on standard Java visibility.
     *
     * @param fieldModifier   one of {"private", "default", "protected", "public"}
     * @param accessorContext one of {"SAME_CLASS", "SAME_PACKAGE", "DIFFERENT_PACKAGE"}
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
                        || "SAME_PACKAGE".equalsIgnoreCase(accessorContext)) {
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
     * Groups access results by modifier and formats a comprehensive breakdown report.
     * Format: "private: X allowed / Y denied | default: X allowed / Y denied | protected: X allowed / Y denied | public: X allowed / Y denied"
     *
     * @param attempts 2D array of {fieldModifier, accessorContext}
     * @return formatted grouped summary string
     */
    public static String summarizeByModifier(String[][] attempts) {
        // Initialize order-preserving map for all 4 canonical modifiers
        Map<String, int[]> tallyMap = new LinkedHashMap<>();
        tallyMap.put("private", new int[] {0, 0});   // [0] = allowed, [1] = denied
        tallyMap.put("default", new int[] {0, 0});
        tallyMap.put("protected", new int[] {0, 0});
        tallyMap.put("public", new int[] {0, 0});

        if (attempts != null) {
            for (String[] attempt : attempts) {
                if (attempt != null && attempt.length >= 2) {
                    String modifier = attempt[0].toLowerCase();
                    String context = attempt[1];
                    String decision = classifyAccess(modifier, context);

                    if (tallyMap.containsKey(modifier)) {
                        int[] counts = tallyMap.get(modifier);
                        if ("ALLOWED".equals(decision)) {
                            counts[0]++;
                        } else {
                            counts[1]++;
                        }
                    }
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, int[]> entry : tallyMap.entrySet()) {
            if (!first) {
                sb.append(" | ");
            }
            sb.append(String.format("%s: %d allowed / %d denied",
                    entry.getKey(), entry.getValue()[0], entry.getValue()[1]));
            first = false;
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        try {
            String[][] batch = new String[][] {
                {"private", "SAME_CLASS"},
                {"private", "SAME_PACKAGE"},
                {"default", "SAME_PACKAGE"},
                {"default", "DIFFERENT_PACKAGE"},
                {"protected", "SAME_PACKAGE"},
                {"protected", "SAME_CLASS"},
                {"public", "DIFFERENT_PACKAGE"}
            };

            String summary = summarizeByModifier(batch);
            System.out.println(summary);
        } catch (Exception e) {
            System.err.println("Error verifying membership field reach: " + e.getMessage());
        }
    }
}
