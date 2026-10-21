package week7.assignment;

/**
 * SkylineDeliveryFleet models a mixed delivery fleet containing drones and ground robots,
 * demonstrating abstract class inheritance, interface implementation across unrelated classes,
 * and dynamic type inspection using instanceof.
 */
public class SkylineDeliveryFleet {

    /**
     * Abstract base class for all drone variants.
     */
    public static abstract class Drone {
        public abstract String fly();
    }

    /**
     * Interface defining real-time tracking behavior.
     */
    public interface Trackable {
        String getLocation();
    }

    /**
     * Delivery drone capable of both flight and real-time tracking.
     */
    public static class DeliveryDrone extends Drone implements Trackable {
        private final String id;

        public DeliveryDrone(String id) {
            if (id == null || id.trim().isEmpty()) {
                throw new IllegalArgumentException("Drone ID cannot be null or blank");
            }
            this.id = id.trim();
        }

        public String getId() {
            return id;
        }

        @Override
        public String fly() {
            return "DeliveryDrone " + id + " delivering package";
        }

        @Override
        public String getLocation() {
            return id + " at Sector 4";
        }
    }

    /**
     * Scout drone for reconnaissance; extends Drone but is not Trackable.
     */
    public static class ScoutDrone extends Drone {
        private final String id;

        public ScoutDrone(String id) {
            if (id == null || id.trim().isEmpty()) {
                throw new IllegalArgumentException("Drone ID cannot be null or blank");
            }
            this.id = id.trim();
        }

        public String getId() {
            return id;
        }

        @Override
        public String fly() {
            return "ScoutDrone " + id + " scouting perimeter";
        }
    }

    /**
     * Ground robot that implements Trackable but has no inheritance relationship with Drone.
     */
    public static class GroundRobot implements Trackable {
        private final String id;

        public GroundRobot(String id) {
            if (id == null || id.trim().isEmpty()) {
                throw new IllegalArgumentException("Robot ID cannot be null or blank");
            }
            this.id = id.trim();
        }

        public String getId() {
            return id;
        }

        @Override
        public String getLocation() {
            return id + " at Sector 4";
        }
    }

    /**
     * Checks if the supplied object implements Trackable and safely queries its location.
     *
     * @param o candidate object
     * @return current location if trackable, or "Tracking not available"
     */
    public static String getLocationIfTrackable(Object o) {
        if (o instanceof Trackable) {
            Trackable trackable = (Trackable) o;
            return trackable.getLocation();
        }
        return "Tracking not available";
    }

    public static void main(String[] args) {
        try {
            DeliveryDrone deliveryDrone = new DeliveryDrone("DR-1");
            ScoutDrone scoutDrone = new ScoutDrone("SC-1");
            GroundRobot groundRobot = new GroundRobot("GR-1");

            System.out.println("DeliveryDrone: " + getLocationIfTrackable(deliveryDrone));
            System.out.println("ScoutDrone: " + getLocationIfTrackable(scoutDrone));
            System.out.println("GroundRobot: " + getLocationIfTrackable(groundRobot));
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
