package week7.assignment;

/**
 * DigitalClassroomSetup models devices in a digital classroom where some devices
 * are chargeable, demonstrating abstract classes, interface implementation,
 * and method overloading (compile-time polymorphism).
 */
public class DigitalClassroomSetup {

    /**
     * Abstract base representation for any classroom device.
     */
    public static abstract class ClassroomDevice {
        public abstract String operate();
    }

    /**
     * Interface defining charging capabilities with method overloading.
     */
    public interface Chargeable {
        String charge();
        String charge(int minutes);
    }

    /**
     * Tablet represents a concrete classroom device that extends ClassroomDevice
     * and implements Chargeable.
     */
    public static class Tablet extends ClassroomDevice implements Chargeable {
        private final String assetTag;

        public Tablet(String assetTag) {
            if (assetTag == null || assetTag.trim().isEmpty()) {
                throw new IllegalArgumentException("Asset tag cannot be null or blank");
            }
            this.assetTag = assetTag.trim();
        }

        public String getAssetTag() {
            return assetTag;
        }

        @Override
        public String operate() {
            return "Tablet " + assetTag + " displaying lesson";
        }

        @Override
        public String charge() {
            return assetTag + " charging";
        }

        @Override
        public String charge(int minutes) {
            if (minutes < 0) {
                throw new IllegalArgumentException("Charging minutes cannot be negative");
            }
            return assetTag + " charging for " + minutes + " minutes";
        }
    }

    public static void main(String[] args) {
        try {
            Tablet tablet = new Tablet("TAB-5");
            System.out.println("Operation: " + tablet.operate());
            System.out.println("Standard Charge: " + tablet.charge());
            System.out.println("Timed Charge: " + tablet.charge(30));
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
