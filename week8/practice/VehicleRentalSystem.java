package week8.practice;

/**
 * VehicleRentalSystem models a car rental service with polymorphic vehicle categories,
 * availability tracking, and rental workflows.
 */
public class VehicleRentalSystem {

    /**
     * Customer renting a vehicle.
     */
    public static class Customer {
        private final String id;
        private final String name;

        public Customer(String id, String name) {
            if (id == null || id.trim().isEmpty() || name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Customer id and name cannot be null or blank");
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

    /**
     * Abstract base class for vehicles in the fleet.
     */
    public static abstract class Vehicle {
        private final String registrationNumber;
        private final String model;
        private boolean rented;

        public Vehicle(String registrationNumber, String model) {
            if (registrationNumber == null || registrationNumber.trim().isEmpty() ||
                model == null || model.trim().isEmpty()) {
                throw new IllegalArgumentException("Registration and model cannot be null or blank");
            }
            this.registrationNumber = registrationNumber.trim();
            this.model = model.trim();
            this.rented = false;
        }

        public String getRegistrationNumber() {
            return registrationNumber;
        }

        public String getModel() {
            return model;
        }

        public boolean isRented() {
            return rented;
        }

        protected void setRented(boolean rented) {
            this.rented = rented;
        }

        public abstract double calculateRentalCharge(int days);

        @Override
        public String toString() {
            return model;
        }
    }

    /**
     * Sedan vehicle category with daily base rate.
     */
    public static class Sedan extends Vehicle {
        private static final double DAILY_RATE = 50.0;

        public Sedan(String registrationNumber, String model) {
            super(registrationNumber, model);
        }

        @Override
        public double calculateRentalCharge(int days) {
            if (days <= 0) {
                throw new IllegalArgumentException("Rental days must be greater than zero");
            }
            return days * DAILY_RATE;
        }
    }

    /**
     * SUV vehicle category with daily base rate.
     */
    public static class SUV extends Vehicle {
        private static final double DAILY_RATE = 80.0;

        public SUV(String registrationNumber, String model) {
            super(registrationNumber, model);
        }

        @Override
        public double calculateRentalCharge(int days) {
            if (days <= 0) {
                throw new IllegalArgumentException("Rental days must be greater than zero");
            }
            return days * DAILY_RATE;
        }
    }

    /**
     * Truck vehicle category with daily base rate.
     */
    public static class Truck extends Vehicle {
        private static final double DAILY_RATE = 110.0;

        public Truck(String registrationNumber, String model) {
            super(registrationNumber, model);
        }

        @Override
        public double calculateRentalCharge(int days) {
            if (days <= 0) {
                throw new IllegalArgumentException("Rental days must be greater than zero");
            }
            return days * DAILY_RATE;
        }
    }

    /**
     * Rental transaction record tracking customer, vehicle, duration, and status.
     */
    public static class Rental {
        private final Customer customer;
        private final Vehicle vehicle;
        private final int days;
        private final double totalCharge;
        private boolean active;

        public Rental(Customer customer, Vehicle vehicle, int days) {
            this.customer = customer;
            this.vehicle = vehicle;
            this.days = days;
            this.totalCharge = vehicle.calculateRentalCharge(days);
            this.active = true;
            vehicle.setRented(true);
        }

        public Customer getCustomer() {
            return customer;
        }

        public Vehicle getVehicle() {
            return vehicle;
        }

        public int getDays() {
            return days;
        }

        public double getTotalCharge() {
            return totalCharge;
        }

        public boolean isActive() {
            return active;
        }

        public void returnVehicle() {
            if (!active) {
                throw new IllegalStateException("Rental is already closed");
            }
            this.active = false;
            this.vehicle.setRented(false);
        }
    }

    /**
     * Central service managing rental transactions.
     */
    public static class RentalAgency {
        public Rental rentVehicle(Customer customer, Vehicle vehicle, int days) {
            if (vehicle.isRented()) {
                System.out.println(vehicle.getModel() + " is currently unavailable.");
                return null;
            }
            Rental rental = new Rental(customer, vehicle, days);
            System.out.printf("%s rented successfully by %s. Rental charge: $%.2f.%n",
                    vehicle.getModel(), customer.getName(), rental.getTotalCharge());
            return rental;
        }

        public void returnVehicle(Rental rental) {
            if (rental == null || !rental.isActive()) {
                System.out.println("No active rental to return.");
                return;
            }
            Vehicle vehicle = rental.getVehicle();
            Customer customer = rental.getCustomer();
            rental.returnVehicle();
            System.out.printf("%s returned by %s.%n", vehicle.getModel(), customer.getName());
        }
    }

    public static void main(String[] args) {
        RentalAgency agency = new RentalAgency();

        Customer customer1 = new Customer("C1", "Customer 1");
        Customer customer2 = new Customer("C2", "Customer 2");
        Customer customer3 = new Customer("C3", "Customer 3");

        Vehicle sedanA = new Sedan("SED-101", "Sedan A");
        Vehicle suvB = new SUV("SUV-202", "SUV B");

        // Customer 1 rents Sedan A for 3 days
        Rental rental1 = agency.rentVehicle(customer1, sedanA, 3);

        // Customer 2 attempts to rent Sedan A for 2 days
        agency.rentVehicle(customer2, sedanA, 2);

        // Customer 1 returns Sedan A
        agency.returnVehicle(rental1);

        // Customer 3 rents SUV B for 5 days
        agency.rentVehicle(customer3, suvB, 5);
    }
}
