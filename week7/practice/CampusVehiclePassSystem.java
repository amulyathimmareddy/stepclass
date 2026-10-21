package week7.practice;

import java.util.HashMap;
import java.util.Map;

/**
 * Interface identifying electric chargeable vehicles.
 */
interface Chargeable {
    boolean canCharge();
}

/**
 * Abstract vehicle representing common pass attributes.
 */
abstract class Vehicle {
    protected String passNumber;
    protected String ownerName;

    public Vehicle(String passNumber, String ownerName) {
        this.passNumber = passNumber;
        this.ownerName = ownerName;
    }

    public String getPassNumber() {
        return passNumber;
    }

    public abstract String getType();
    public abstract int getPassFee();
}

class Bike extends Vehicle {
    public Bike(String passNumber, String ownerName) {
        super(passNumber, ownerName);
    }

    @Override
    public String getType() {
        return "Bike";
    }

    @Override
    public int getPassFee() {
        return 300;
    }
}

class Car extends Vehicle {
    public Car(String passNumber, String ownerName) {
        super(passNumber, ownerName);
    }

    @Override
    public String getType() {
        return "Car";
    }

    @Override
    public int getPassFee() {
        return 1000;
    }
}

class EBike extends Vehicle implements Chargeable {
    public EBike(String passNumber, String ownerName) {
        super(passNumber, ownerName);
    }

    @Override
    public String getType() {
        return "EBike";
    }

    @Override
    public int getPassFee() {
        return 300;
    }

    @Override
    public boolean canCharge() {
        return true;
    }
}

class ECar extends Vehicle implements Chargeable {
    public ECar(String passNumber, String ownerName) {
        super(passNumber, ownerName);
    }

    @Override
    public String getType() {
        return "ECar";
    }

    @Override
    public int getPassFee() {
        return 1000;
    }

    @Override
    public boolean canCharge() {
        return true;
    }
}

/**
 * Problem 2: Campus Vehicle Pass System
 * Incorporates an interface for chargeable vehicles with polymorphic fee computation.
 */
public class CampusVehiclePassSystem {

    private Map<String, Vehicle> registry = new HashMap<>();

    public void registerPass(String type, String passNumber, String ownerName) {
        Vehicle vehicle;
        switch (type) {
            case "Bike":
                vehicle = new Bike(passNumber, ownerName);
                break;
            case "Car":
                vehicle = new Car(passNumber, ownerName);
                break;
            case "EBike":
                vehicle = new EBike(passNumber, ownerName);
                break;
            case "ECar":
                vehicle = new ECar(passNumber, ownerName);
                break;
            default:
                System.out.println("Unknown vehicle type: " + type);
                return;
        }

        registry.put(passNumber, vehicle);
        System.out.printf("%s (%s) pass fee %d%n", passNumber, vehicle.getType(), vehicle.getPassFee());
    }

    public void bookChargingBay(String passNumber) {
        Vehicle vehicle = registry.get(passNumber);
        if (vehicle == null) {
            System.out.println("Vehicle not found: " + passNumber);
            return;
        }

        if (vehicle instanceof Chargeable) {
            System.out.printf("%s charging bay allotted%n", passNumber);
        } else {
            System.out.printf("%s rejected: charging unsupported%n", passNumber);
        }
    }

    public static void main(String[] args) {
        try {
            CampusVehiclePassSystem system = new CampusVehiclePassSystem();

            system.registerPass("Bike", "KA01", "Asha");
            system.registerPass("ECar", "KA02", "Ravi");

            system.bookChargingBay("KA02");
            system.bookChargingBay("KA01");
        } catch (Exception e) {
            System.err.println("Error running campus vehicle pass system: " + e.getMessage());
        }
    }
}
