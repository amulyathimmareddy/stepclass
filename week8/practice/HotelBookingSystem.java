package week8.practice;

import java.time.LocalDate;
import java.util.*;

/**
 * HotelBookingSystem models room booking with dynamic pricing by room type,
 * reservation conflict detection across date intervals, and cancellation rules.
 */
public class HotelBookingSystem {

    public static class Customer {
        private final String id;
        private final String name;

        public Customer(String id, String name) {
            if (id == null || id.trim().isEmpty() || name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Customer id and name cannot be blank");
            }
            this.id = id.trim();
            this.name = name.trim();
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public static abstract class Room {
        private final String roomNumber;

        public Room(String roomNumber) {
            if (roomNumber == null || roomNumber.trim().isEmpty()) {
                throw new IllegalArgumentException("Room number cannot be blank");
            }
            this.roomNumber = roomNumber.trim();
        }

        public String getRoomNumber() {
            return roomNumber;
        }

        public abstract String getCategoryName();

        public abstract double calculatePrice(int nights);

        @Override
        public String toString() {
            return getCategoryName() + " " + roomNumber;
        }
    }

    public static class StandardRoom extends Room {
        private static final double RATE_PER_NIGHT = 100.0;

        public StandardRoom(String roomNumber) {
            super(roomNumber);
        }

        @Override
        public String getCategoryName() {
            return "Standard Room";
        }

        @Override
        public double calculatePrice(int nights) {
            return nights * RATE_PER_NIGHT;
        }
    }

    public static class DeluxeRoom extends Room {
        private static final double RATE_PER_NIGHT = 180.0;

        public DeluxeRoom(String roomNumber) {
            super(roomNumber);
        }

        @Override
        public String getCategoryName() {
            return "Deluxe Room";
        }

        @Override
        public double calculatePrice(int nights) {
            return nights * RATE_PER_NIGHT;
        }
    }

    public static class Suite extends Room {
        private static final double RATE_PER_NIGHT = 300.0;

        public Suite(String roomNumber) {
            super(roomNumber);
        }

        @Override
        public String getCategoryName() {
            return "Suite";
        }

        @Override
        public double calculatePrice(int nights) {
            return nights * RATE_PER_NIGHT;
        }
    }

    public static class Reservation {
        private final Customer customer;
        private final Room room;
        private final LocalDate startDate;
        private final LocalDate endDate;
        private final double price;
        private boolean active;

        public Reservation(Customer customer, Room room, LocalDate startDate, LocalDate endDate, double price) {
            this.customer = customer;
            this.room = room;
            this.startDate = startDate;
            this.endDate = endDate;
            this.price = price;
            this.active = true;
        }

        public Customer getCustomer() {
            return customer;
        }

        public Room getRoom() {
            return room;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public double getPrice() {
            return price;
        }

        public boolean isActive() {
            return active;
        }

        public void cancel() {
            this.active = false;
        }

        public boolean overlaps(LocalDate otherStart, LocalDate otherEnd) {
            if (!active) return false;
            return !startDate.isAfter(otherEnd) && !endDate.isBefore(otherStart);
        }
    }

    public static class Hotel {
        private final List<Reservation> reservations = new ArrayList<>();

        public boolean isAvailable(Room room, LocalDate start, LocalDate end) {
            for (Reservation r : reservations) {
                if (r.getRoom().getRoomNumber().equals(room.getRoomNumber()) && r.overlaps(start, end)) {
                    return false;
                }
            }
            return true;
        }

        public Reservation reserve(Customer customer, Room room, LocalDate start, LocalDate end, String label) {
            if (!isAvailable(room, start, end)) {
                System.out.printf("%s is not available from %s to %s.%n", room, start, end);
                return null;
            }
            int nights = (int) java.time.temporal.ChronoUnit.DAYS.between(start, end);
            if (nights <= 0) nights = 1;
            double price = room.calculatePrice(nights);
            Reservation reservation = new Reservation(customer, room, start, end, price);
            reservations.add(reservation);
            System.out.printf("Reservation confirmed for %s, %s (%s). Price: $%.2f.%n",
                    customer.getName(), room, label, price);
            return reservation;
        }

        public void cancelReservation(Reservation reservation, LocalDate currentDate, LocalDate deadline, String label) {
            if (reservation == null || !reservation.isActive()) {
                System.out.println("Reservation is not active.");
                return;
            }
            if (currentDate.isAfter(deadline)) {
                System.out.println("Cannot cancel reservation: cancellation deadline has passed.");
                return;
            }
            reservation.cancel();
            System.out.printf("Reservation for %s, %s (%s) cancelled successfully.%n",
                    reservation.getCustomer().getName(), reservation.getRoom(), label);
        }
    }

    public static void main(String[] args) {
        Hotel hotel = new Hotel();

        StandardRoom room101 = new StandardRoom("101");
        DeluxeRoom room201 = new DeluxeRoom("201");

        Customer customerA = new Customer("C-A", "Customer A");
        Customer customerB = new Customer("C-B", "Customer B");
        Customer customerC = new Customer("C-C", "Customer C");

        LocalDate jan1 = LocalDate.of(2026, 1, 1);
        LocalDate jan5 = LocalDate.of(2026, 1, 5);
        LocalDate jan3 = LocalDate.of(2026, 1, 3);
        LocalDate jan7 = LocalDate.of(2026, 1, 7);
        LocalDate feb10 = LocalDate.of(2026, 2, 10);
        LocalDate feb12 = LocalDate.of(2026, 2, 12);

        // Customer A checks availability
        if (hotel.isAvailable(room101, jan1, jan5)) {
            System.out.printf("%s is available from Jan 1 to Jan 5.%n", room101);
        }

        // Customer A reserves
        Reservation resA = hotel.reserve(customerA, room101, jan1, jan5, "Jan 1-5");

        // Customer B attempts to reserve overlapping dates
        hotel.reserve(customerB, room101, jan3, jan7, "Jan 3-7");

        // Customer A cancels before deadline
        hotel.cancelReservation(resA, LocalDate.of(2025, 12, 30), LocalDate.of(2025, 12, 31), "Jan 1-5");

        // Customer C reserves Deluxe Room
        hotel.reserve(customerC, room201, feb10, feb12, "Feb 10-12");
    }
}
