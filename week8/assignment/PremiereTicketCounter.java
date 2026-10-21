package week8.assignment;

import java.util.*;

/**
 * PremiereTicketCounter models a college auditorium movie ticket booking system with
 * polymorphic seat pricing, availability management, and cancellation logic.
 */
public class PremiereTicketCounter {

    public static abstract class Seat {
        private final String seatNumber;

        public Seat(String seatNumber) {
            if (seatNumber == null || seatNumber.trim().isEmpty()) {
                throw new IllegalArgumentException("Seat number cannot be blank");
            }
            this.seatNumber = seatNumber.trim();
        }

        public String getSeatNumber() {
            return seatNumber;
        }

        public abstract double getPrice();

        @Override
        public String toString() {
            return seatNumber;
        }
    }

    public static class RegularSeat extends Seat {
        public RegularSeat(String seatNumber) {
            super(seatNumber);
        }

        @Override
        public double getPrice() {
            return 150.0;
        }
    }

    public static class PremiumSeat extends Seat {
        public PremiumSeat(String seatNumber) {
            super(seatNumber);
        }

        @Override
        public double getPrice() {
            return 250.0;
        }
    }

    public static class ReclinerSeat extends Seat {
        public ReclinerSeat(String seatNumber) {
            super(seatNumber);
        }

        @Override
        public double getPrice() {
            return 400.0;
        }
    }

    public static class Customer {
        private final String name;

        public Customer(String name) {
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Customer name cannot be blank");
            }
            this.name = name.trim();
        }

        public String getName() {
            return name;
        }
    }

    public static class Booking {
        private final Customer customer;
        private final Show show;
        private final List<Seat> seats;
        private final double total;
        private boolean cancelled;

        public Booking(Customer customer, Show show, List<Seat> seats, double total) {
            this.customer = customer;
            this.show = show;
            this.seats = new ArrayList<>(seats);
            this.total = total;
            this.cancelled = false;
        }

        public Customer getCustomer() {
            return customer;
        }

        public List<Seat> getSeats() {
            return Collections.unmodifiableList(seats);
        }

        public double getTotal() {
            return total;
        }

        public boolean isCancelled() {
            return cancelled;
        }

        public void cancel() {
            if (cancelled) {
                System.out.println("Booking is already cancelled.");
                return;
            }
            cancelled = true;
            show.releaseSeats(seats);

            List<String> labels = new ArrayList<>();
            for (Seat s : seats) {
                labels.add(s.getSeatNumber());
            }
            System.out.printf("%s's booking cancelled. Seats %s released.%n",
                    customer.getName(), String.join(", ", labels));
        }
    }

    public static class Show {
        private final String time;
        private final Set<String> bookedSeats = new HashSet<>();

        public Show(String time) {
            this.time = time;
        }

        public String getTime() {
            return time;
        }

        public synchronized Booking bookSeats(Customer customer, List<Seat> requestedSeats) {
            if (requestedSeats == null || requestedSeats.isEmpty()) {
                System.out.println("No seats selected.");
                return null;
            }
            if (requestedSeats.size() > 6) {
                System.out.println("Cannot book more than 6 seats in a single booking.");
                return null;
            }

            for (Seat seat : requestedSeats) {
                if (bookedSeats.contains(seat.getSeatNumber())) {
                    System.out.printf("Seat %s is already booked for this show.%n", seat.getSeatNumber());
                    return null;
                }
            }

            double total = 0.0;
            List<String> seatNumbers = new ArrayList<>();
            for (Seat seat : requestedSeats) {
                bookedSeats.add(seat.getSeatNumber());
                total += seat.getPrice();
                seatNumbers.add(seat.getSeatNumber());
            }

            Booking booking = new Booking(customer, this, requestedSeats, total);
            System.out.printf("Booking confirmed for %s: %s. Total: \u20B9%.2f.%n",
                    customer.getName(), String.join(", ", seatNumbers), total);
            return booking;
        }

        protected void releaseSeats(List<Seat> seats) {
            for (Seat seat : seats) {
                bookedSeats.remove(seat.getSeatNumber());
            }
        }
    }

    public static void main(String[] args) {
        Show show7PM = new Show("7 PM");

        Customer asha = new Customer("Asha");
        Customer ravi = new Customer("Ravi");
        Customer neha = new Customer("Neha");

        Seat a1 = new RegularSeat("A1");
        Seat a2 = new RegularSeat("A2");
        Seat f5 = new PremiumSeat("F5");
        Seat r1 = new ReclinerSeat("R1");

        // Asha books Regular seats A1 and A2 and Premium seat F5
        Booking ashaBooking = show7PM.bookSeats(asha, Arrays.asList(a1, a2, f5));

        // Ravi attempts to book seat A2
        show7PM.bookSeats(ravi, Collections.singletonList(a2));

        // Ravi books Recliner seat R1
        show7PM.bookSeats(ravi, Collections.singletonList(r1));

        // Asha cancels her booking before show starts
        if (ashaBooking != null) {
            ashaBooking.cancel();
        }

        // Neha books seat A2
        show7PM.bookSeats(neha, Collections.singletonList(a2));
    }
}
