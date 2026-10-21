package week7.assignment;

/**
 * Common ringing capability interface shared across unrelated device classes.
 */
interface Ringable {
    String ring();
}

/**
 * Clock alarm device.
 */
class AlarmClock implements Ringable {
    private String time;

    public AlarmClock(String time) {
        this.time = (time != null) ? time.trim() : "12:00 PM";
    }

    @Override
    public String ring() {
        return "Alarm ringing for " + time;
    }
}

/**
 * Entrance doorbell device.
 */
class Doorbell implements Ringable {
    private String location;

    public Doorbell(String location) {
        this.location = (location != null) ? location.trim() : "Entrance";
    }

    @Override
    public String ring() {
        return "Doorbell ringing at " + location;
    }
}

/**
 * Problem 1: Morning Wake-Up Circuit
 * Implements interface abstraction decoupling functionality from class inheritance.
 */
public class MorningWakeUpCircuit {

    public static void ringAll(Ringable[] devices) {
        if (devices == null) {
            return;
        }
        for (Ringable d : devices) {
            if (d != null) {
                System.out.println(d.ring());
            }
        }
    }

    public static void main(String[] args) {
        try {
            AlarmClock a = new AlarmClock("7:00 AM");
            Doorbell d = new Doorbell("Front Door");

            ringAll(new Ringable[] {a, d});
        } catch (Exception e) {
            System.err.println("Error triggering wake-up circuit: " + e.getMessage());
        }
    }
}
