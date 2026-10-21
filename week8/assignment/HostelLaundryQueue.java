package week8.assignment;

/**
 * HostelLaundryQueue models a hostel washing machine reservation system with
 * polymorphic wash types, machine availability state tracking, and cycle management.
 */
public class HostelLaundryQueue {

    /**
     * Abstract representation of a wash cycle type.
     */
    public static abstract class WashType {
        private final String name;
        private final int durationMinutes;
        private final double charge;

        public WashType(String name, int durationMinutes, double charge) {
            this.name = name;
            this.durationMinutes = durationMinutes;
            this.charge = charge;
        }

        public String getName() {
            return name;
        }

        public int getDurationMinutes() {
            return durationMinutes;
        }

        public double getCharge() {
            return charge;
        }
    }

    public static class QuickWash extends WashType {
        public QuickWash() {
            super("Quick", 30, 20.0);
        }
    }

    public static class NormalWash extends WashType {
        public NormalWash() {
            super("Normal", 45, 30.0);
        }
    }

    public static class HeavyWash extends WashType {
        public HeavyWash() {
            super("Heavy", 60, 45.0);
        }
    }

    public static class Student {
        private final String name;

        public Student(String name) {
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Student name cannot be blank");
            }
            this.name = name.trim();
        }

        public String getName() {
            return name;
        }
    }

    public static class WashCycle {
        private final Student student;
        private final WashingMachine machine;
        private final WashType washType;

        public WashCycle(Student student, WashingMachine machine, WashType washType) {
            this.student = student;
            this.machine = machine;
            this.washType = washType;
        }

        public Student getStudent() {
            return student;
        }

        public WashingMachine getMachine() {
            return machine;
        }

        public WashType getWashType() {
            return washType;
        }
    }

    public static class WashingMachine {
        private final String id;
        private boolean busy;
        private WashCycle currentCycle;

        public WashingMachine(String id) {
            if (id == null || id.trim().isEmpty()) {
                throw new IllegalArgumentException("Machine ID cannot be blank");
            }
            this.id = id.trim();
            this.busy = false;
        }

        public String getId() {
            return id;
        }

        public boolean isBusy() {
            return busy;
        }

        public WashCycle startWash(Student student, WashType washType) {
            if (busy) {
                System.out.printf("Machine %s is currently busy.%n", id);
                return null;
            }
            this.busy = true;
            this.currentCycle = new WashCycle(student, this, washType);
            System.out.printf("%s wash started on %s for %s (%d min). Charge: \u20B9%.2f.%n",
                    washType.getName(), id, student.getName(), washType.getDurationMinutes(), washType.getCharge());
            return currentCycle;
        }

        public void completeCycle() {
            if (!busy) {
                System.out.printf("Machine %s has no active wash cycle to complete.%n", id);
                return;
            }
            this.busy = false;
            this.currentCycle = null;
            System.out.printf("%s cycle completed. %s is now free.%n", id, id);
        }
    }

    public static void main(String[] args) {
        WashingMachine m1 = new WashingMachine("M1");
        WashingMachine m2 = new WashingMachine("M2");

        Student asha = new Student("Asha");
        Student ravi = new Student("Ravi");
        Student neha = new Student("Neha");

        // Asha starts a Quick wash on Machine M1
        m1.startWash(asha, new QuickWash());

        // Ravi attempts to start a Heavy wash on Machine M1
        m1.startWash(ravi, new HeavyWash());

        // Ravi starts a Heavy wash on Machine M2
        m2.startWash(ravi, new HeavyWash());

        // Machine M1 completes its cycle
        m1.completeCycle();

        // Neha starts a Normal wash on Machine M1
        m1.startWash(neha, new NormalWash());
    }
}
