package week5.practice;

/**
 * Representation of a cinema ticket with fields demonstrating Java's 4 distinct access levels:
 * - seatNumber: private (accessible only within MovieTicket)
 * - screenId: package-private / default (accessible within the same package)
 * - ticketPrice: protected (accessible within package and subclasses)
 * - movieTitle: public (accessible universally)
 */
class MovieTicket {
    private String seatNumber;
    /* default */ String screenId;
    protected double ticketPrice;
    public String movieTitle;

    public MovieTicket(String seatNumber, String screenId, double ticketPrice, String movieTitle) {
        this.seatNumber = seatNumber;
        this.screenId = screenId;
        this.ticketPrice = ticketPrice;
        this.movieTitle = movieTitle;
    }

    public String getSeatNumber() {
        return seatNumber;
    }
}

/**
 * Problem 1: Movie Ticket Field Visibility Checker
 * Evaluates whether field access attempts are ALLOWED or DENIED based on standard Java visibility rules.
 */
public class TicketAccessChecker {

    /**
     * Classifies access permissions based on field modifier and caller context.
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
     * Aggregates a batch of access attempts into a summary count.
     *
     * @param attempts 2D array of {fieldModifier, accessorContext}
     * @return formatted tally string: "Allowed: X | Denied: Y"
     */
    public static String summarizeBatch(String[][] attempts) {
        if (attempts == null || attempts.length == 0) {
            return "Allowed: 0 | Denied: 0";
        }

        int allowedCount = 0;
        int deniedCount = 0;

        for (String[] attempt : attempts) {
            if (attempt != null && attempt.length >= 2) {
                String result = classifyAccess(attempt[0], attempt[1]);
                if ("ALLOWED".equals(result)) {
                    allowedCount++;
                } else {
                    deniedCount++;
                }
            }
        }

        return String.format("Allowed: %d | Denied: %d", allowedCount, deniedCount);
    }

    public static void main(String[] args) {
        try {
            // Test individual classifications
            System.out.println("private in SAME_CLASS: " + classifyAccess("private", "SAME_CLASS"));
            System.out.println("protected in DIFFERENT_PACKAGE: " + classifyAccess("protected", "DIFFERENT_PACKAGE"));

            // Test batch summarization
            String[][] batch = new String[][] {
                {"default", "SAME_PACKAGE"},
                {"default", "DIFFERENT_PACKAGE"},
                {"public", "DIFFERENT_PACKAGE"}
            };

            System.out.println(summarizeBatch(batch));
        } catch (Exception e) {
            System.err.println("Error verifying ticket access visibility: " + e.getMessage());
        }
    }
}
