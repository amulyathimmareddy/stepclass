package week5.practice;

/**
 * Problem 3: Seat Booking Encapsulation Guard
 * Protects cinema screen capacity and seat counts against negative or out-of-bounds transitions.
 */
public class CineScreen {

    private int seatsTotal;
    private int seatsAvailable;

    /**
     * Constructs CineScreen with verified capacity.
     * Rejects capacity <= 0.
     *
     * @param seatsTotal total seating capacity (must be positive, up to 300)
     */
    public CineScreen(int seatsTotal) {
        if (seatsTotal <= 0) {
            System.out.println("construction rejected: Total seats must be greater than zero.");
            this.seatsTotal = 0;
            this.seatsAvailable = 0;
            return;
        }
        this.seatsTotal = Math.min(300, seatsTotal);
        this.seatsAvailable = this.seatsTotal;
    }

    /**
     * Books a single seat if available.
     * Silently rejects the booking if no seats are remaining (prevents negative counts).
     */
    public void bookSeat() {
        if (this.seatsAvailable > 0) {
            this.seatsAvailable--;
        }
    }

    /**
     * Cancels a booking, restoring an available seat.
     * Silently rejects the cancellation if already at maximum capacity.
     */
    public void cancelBooking() {
        if (this.seatsAvailable < this.seatsTotal) {
            this.seatsAvailable++;
        }
    }

    /**
     * Returns the count of remaining bookable seats.
     *
     * @return available seats
     */
    public int getSeatsAvailable() {
        return this.seatsAvailable;
    }

    public int getSeatsTotal() {
        return this.seatsTotal;
    }

    public static void main(String[] args) {
        try {
            // Test 1: Invalid construction rejection
            System.out.print("Testing new CineScreen(0): ");
            new CineScreen(0);

            // Test 2: Consecutive bookings reaching boundary 0
            CineScreen screen = new CineScreen(2);
            screen.bookSeat();
            screen.bookSeat();
            screen.bookSeat(); // 3rd booking rejected silently
            System.out.println("Seats available after 3 bookings on capacity 2: " + screen.getSeatsAvailable());

            // Test 3: Consecutive cancellations reaching upper boundary 2
            screen.cancelBooking();
            screen.cancelBooking();
            screen.cancelBooking(); // 3rd cancellation rejected silently
            System.out.println("Seats available after 3 cancellations: " + screen.getSeatsAvailable());
        } catch (Exception e) {
            System.err.println("Error simulating cinema screen seat booking: " + e.getMessage());
        }
    }
}
