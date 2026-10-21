package week4.assignment;

/**
 * Problem A3: final Method — Parking Overstay Fine Calculator
 * Demonstrates 'final' methods enforcing unalterable business calculations across vehicle categories.
 */
public class ParkingTicket {

    private String vehicleNo;
    private double ratePerMinute;

    /**
     * Initializes parking ticket record.
     *
     * @param vehicleNo     the vehicle registration plate number
     * @param ratePerMinute the penalty charge per minute of overstay
     */
    public ParkingTicket(String vehicleNo, double ratePerMinute) {
        this.vehicleNo = (vehicleNo != null) ? vehicleNo.trim() : "UNKNOWN";
        this.ratePerMinute = Math.max(0.0, ratePerMinute);
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public double getRatePerMinute() {
        return ratePerMinute;
    }

    /**
     * Final method locking the penalty calculation formula against subclass overrides.
     *
     * @param overstayMinutes number of minutes parked beyond allotted duration
     * @return total fine owed
     */
    public final double calculateFine(int overstayMinutes) {
        if (overstayMinutes <= 0) {
            return 0.0;
        }
        return overstayMinutes * this.ratePerMinute;
    }

    /**
     * Final method outputting receipt with calculated fine.
     *
     * @param overstayMinutes number of minutes parked beyond allotted duration
     */
    public final void printReceipt(int overstayMinutes) {
        double fine = calculateFine(overstayMinutes);
        System.out.printf("%s - Fine: Rs %.1f%n", this.vehicleNo, fine);
    }

    /**
     * Processes parking tickets from parallel batch arrays.
     *
     * @param vehicleNos      array of vehicle registration codes
     * @param ratesPerMinute  array of rates
     * @param overstayMinutes array of overstay minutes
     */
    public static void processParkingBatch(String[] vehicleNos, double[] ratesPerMinute, int[] overstayMinutes) {
        if (vehicleNos == null || ratesPerMinute == null || overstayMinutes == null) {
            System.out.println("Invalid input arrays.");
            return;
        }

        int count = Math.min(vehicleNos.length, Math.min(ratesPerMinute.length, overstayMinutes.length));
        for (int i = 0; i < count; i++) {
            ParkingTicket ticket = new ParkingTicket(vehicleNos[i], ratesPerMinute[i]);
            if (overstayMinutes[i] > 0) {
                ticket.printReceipt(overstayMinutes[i]);
            } else {
                System.out.printf("%s - No fine, within allotted time%n", vehicleNos[i]);
            }
        }
    }

    public static void main(String[] args) {
        try {
            String[] vehicleNos = {"TN09AB1234", "TN22CD5678", "TN09EF9012", "TN10GH3456"};
            double[] ratePerMinute = {2.0, 2.0, 3.0, 2.0};
            int[] overstayMinutes = {15, 0, -5, 8};

            processParkingBatch(vehicleNos, ratePerMinute, overstayMinutes);
        } catch (Exception e) {
            System.err.println("Error processing parking fines: " + e.getMessage());
        }
    }
}
