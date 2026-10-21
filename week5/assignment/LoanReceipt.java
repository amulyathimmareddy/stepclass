package week5.assignment;

import java.util.Arrays;

/**
 * Subclass representing reference-room-only loans that cannot leave the library building.
 */
class ReferenceOnlyLoanReceipt extends LoanReceipt {
    private final String roomNumber;

    public ReferenceOnlyLoanReceipt(String memberId, String[] bookIds, String roomNumber) {
        super(memberId, bookIds);
        this.roomNumber = (roomNumber != null) ? roomNumber.trim() : "Reading Room";
    }

    public String getRoomNumber() {
        return roomNumber;
    }
}

/**
 * Problem 5: Immutable Loan Receipt & Nightly Circulation Ledger
 * Enforces true immutability via final fields, defensive array copying, wither methods,
 * and a nightly circulation reconciliation engine handling polymorphic subtypes and null values.
 */
public class LoanReceipt {

    private final String memberId;
    private final String[] bookIds;

    // Static ledger state loaded once
    private static String branchOfficeCode;

    static {
        branchOfficeCode = "PAGETURNER-MAIN-01";
    }

    /**
     * Parameterized constructor performing defensive array cloning on receipt creation.
     *
     * @param memberId unique borrower identifier
     * @param bookIds  array of borrowed book catalog codes
     */
    public LoanReceipt(String memberId, String[] bookIds) {
        this.memberId = (memberId != null) ? memberId.trim() : "UNKNOWN";
        if (bookIds == null) {
            this.bookIds = new String[0];
        } else {
            this.bookIds = Arrays.copyOf(bookIds, bookIds.length);
        }
    }

    public String getMemberId() {
        return memberId;
    }

    /**
     * Returns a defensive copy of internal book catalog IDs to safeguard internal state.
     *
     * @return cloned copy of bookIds array
     */
    public String[] getBookIds() {
        return Arrays.copyOf(this.bookIds, this.bookIds.length);
    }

    public static String getBranchOfficeCode() {
        return branchOfficeCode;
    }

    /**
     * Simulates updating a book ID by producing a new immutable LoanReceipt instance.
     *
     * @param index position to replace
     * @param newId corrected book identifier
     * @return new LoanReceipt with the replacement made
     */
    public LoanReceipt withCorrectedBookId(int index, String newId) {
        if (index < 0 || index >= this.bookIds.length) {
            return this;
        }
        String[] updatedIds = Arrays.copyOf(this.bookIds, this.bookIds.length);
        updatedIds[index] = newId;
        return new LoanReceipt(this.memberId, updatedIds);
    }

    /**
     * Reconciles daily circulation receipts tolerating null items and distinguishing reference-only loans.
     *
     * @param receipts array of loan receipts (may contain null elements)
     * @return summary string: "X processed | Y null skipped | R reference-only | G regular"
     */
    public static String processNightlyCirculation(LoanReceipt[] receipts) {
        if (receipts == null || receipts.length == 0) {
            return "0 processed | 0 null skipped | 0 reference-only | 0 regular";
        }

        int nullCount = 0;
        int referenceOnlyCount = 0;
        int regularCount = 0;

        for (LoanReceipt receipt : receipts) {
            if (receipt == null) {
                nullCount++;
            } else if (receipt instanceof ReferenceOnlyLoanReceipt) {
                referenceOnlyCount++;
            } else {
                regularCount++;
            }
        }

        int totalProcessed = referenceOnlyCount + regularCount;
        return String.format("%d processed | %d null skipped | %d reference-only | %d regular",
                totalProcessed, nullCount, referenceOnlyCount, regularCount);
    }

    public static void main(String[] args) {
        try {
            // Test 1: Defensive copying prevents internal tampering
            LoanReceipt receipt = new LoanReceipt("LIB-8841", new String[] {"BK-100", "BK-101"});
            String[] externalIds = receipt.getBookIds();
            externalIds[0] = "HACKED";
            System.out.println("Defensive copy test (should be BK-100): " + receipt.getBookIds()[0]);

            // Test 2: with-style immutable correction
            LoanReceipt corrected = receipt.withCorrectedBookId(1, "BK-102");
            System.out.println("Original book IDs:  " + Arrays.toString(receipt.getBookIds()));
            System.out.println("Corrected book IDs: " + Arrays.toString(corrected.getBookIds()));

            // Test 3: Nightly circulation reconciliation with null-safety and instanceof dispatch
            LoanReceipt[] nightlyBatch = new LoanReceipt[] {
                new ReferenceOnlyLoanReceipt("LIB-001", new String[] {"BK-200"}, "Reading Room 3"),
                null,
                new LoanReceipt("LIB-002", new String[] {"BK-201"})
            };

            String report = processNightlyCirculation(nightlyBatch);
            System.out.println(report);
        } catch (Exception e) {
            System.err.println("Error processing nightly loan circulation: " + e.getMessage());
        }
    }
}
