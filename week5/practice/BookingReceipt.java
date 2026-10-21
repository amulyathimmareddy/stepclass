package week5.practice;

import java.util.Arrays;

/**
 * Subclass representing multi-seat group bookings.
 */
class GroupBookingReceipt extends BookingReceipt {
    private final int groupSize;

    public GroupBookingReceipt(String bookingId, String[] seatNumbers, int groupSize) {
        super(bookingId, seatNumbers);
        this.groupSize = Math.max(1, groupSize);
    }

    public int getGroupSize() {
        return groupSize;
    }
}

/**
 * Problem 5: Immutable Booking Receipt & Nightly Settlement
 * Achieves strict immutability through final fields, defensive copying in constructors and getters,
 * and with-style mutation methods returning fresh instances.
 */
public class BookingReceipt {

    private final String bookingId;
    private final String[] seatNumbers;

    /**
     * Initializes booking receipt with defensive copying of mutable array input.
     *
     * @param bookingId   unique booking identifier
     * @param seatNumbers array of assigned seat numbers
     */
    public BookingReceipt(String bookingId, String[] seatNumbers) {
        this.bookingId = (bookingId != null) ? bookingId.trim() : "UNKNOWN";
        if (seatNumbers == null) {
            this.seatNumbers = new String[0];
        } else {
            this.seatNumbers = Arrays.copyOf(seatNumbers, seatNumbers.length);
        }
    }

    public String getBookingId() {
        return bookingId;
    }

    /**
     * Returns a defensive copy of internal seat array to prevent caller mutation of internal state.
     *
     * @return cloned seat array
     */
    public String[] getSeatNumbers() {
        return Arrays.copyOf(this.seatNumbers, this.seatNumbers.length);
    }

    /**
     * Simulates mutation by returning a brand-new instance with the updated seat.
     *
     * @param index   position in seat array to update
     * @param newSeat replacement seat designation
     * @return new BookingReceipt instance
     */
    public BookingReceipt withUpdatedSeat(int index, String newSeat) {
        if (index < 0 || index >= this.seatNumbers.length) {
            return this; // Out of bounds returns self
        }
        String[] updatedSeats = Arrays.copyOf(this.seatNumbers, this.seatNumbers.length);
        updatedSeats[index] = newSeat;
        return new BookingReceipt(this.bookingId, updatedSeats);
    }

    /**
     * Reconciles a batch of nightly receipts safely handling nulls and polymorphic types.
     *
     * @param receipts array of BookingReceipt objects
     * @return summary string matching: "X processed | Y null skipped | G group | I individual"
     */
    public static String processNightlySettlement(BookingReceipt[] receipts) {
        if (receipts == null || receipts.length == 0) {
            return "0 processed | 0 null skipped | 0 group | 0 individual";
        }

        int groupCount = 0;
        int individualCount = 0;
        int nullCount = 0;

        for (BookingReceipt receipt : receipts) {
            if (receipt == null) {
                nullCount++;
            } else if (receipt instanceof GroupBookingReceipt) {
                groupCount++;
            } else {
                individualCount++;
            }
        }

        int totalProcessed = groupCount + individualCount;
        return String.format("%d processed | %d null skipped | %d group | %d individual",
                totalProcessed, nullCount, groupCount, individualCount);
    }

    public static void main(String[] args) {
        try {
            // Test 1: Defensive copying on getter
            BookingReceipt receipt = new BookingReceipt("CH-1001", new String[] {"A1", "A2"});
            String[] externalSeats = receipt.getSeatNumbers();
            externalSeats[0] = "X"; // Attempted external mutation
            System.out.println("Defensive copy check (should be A1): " + receipt.getSeatNumbers()[0]);

            // Test 2: with-style immutable replacement
            BookingReceipt updated = receipt.withUpdatedSeat(1, "A3");
            System.out.println("Original seats: " + Arrays.toString(receipt.getSeatNumbers()));
            System.out.println("Updated seats:  " + Arrays.toString(updated.getSeatNumbers()));

            // Test 3: Nightly settlement batch with polymorphic dispatch and null safety
            BookingReceipt[] nightlyBatch = new BookingReceipt[] {
                new GroupBookingReceipt("CH-2002", new String[] {"B1", "B2"}, 2),
                null,
                new BookingReceipt("CH-3003", new String[] {"C1"})
            };

            String settlementReport = processNightlySettlement(nightlyBatch);
            System.out.println(settlementReport);
        } catch (Exception e) {
            System.err.println("Error processing nightly settlement: " + e.getMessage());
        }
    }
}
