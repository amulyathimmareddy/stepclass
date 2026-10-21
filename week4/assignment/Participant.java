package week4.assignment;

/**
 * Problem A1: Overloaded Constructors for Hackathon Registration
 * Employs constructor overloading and this(...) chaining for participant self-service team allocation.
 */
public class Participant {

    private String name;
    private String teamName;
    private boolean registered;

    /**
     * Primary constructor for participants already assigned to a team.
     *
     * @param name     participant's name
     * @param teamName registered team name
     */
    public Participant(String name, String teamName) {
        this.name = (name != null) ? name.trim() : "Unknown";
        this.teamName = (teamName != null && !teamName.trim().isEmpty()) ? teamName.trim() : "Unassigned";
        this.registered = true;
    }

    /**
     * Overloaded constructor for solo participants.
     * Chains via this(...) providing default placeholder team "Unassigned".
     *
     * @param name participant's name
     */
    public Participant(String name) {
        this(name, "Unassigned");
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getTeamName() {
        return teamName;
    }

    public boolean isRegistered() {
        return registered;
    }

    /**
     * Prints the registration status line.
     * Example: "Ravi | ByteBusters | Registered: true"
     */
    public void printStatus() {
        System.out.printf("%s | %s | Registered: %b%n", name, teamName, registered);
    }

    /**
     * Batch processes registration entries from parallel arrays.
     *
     * @param names     array of applicant names
     * @param teamNames array of team names (empty string indicates solo entrant)
     */
    public static void processRegistrations(String[] names, String[] teamNames) {
        if (names == null || teamNames == null) {
            System.out.println("Invalid input arrays.");
            return;
        }

        int count = Math.min(names.length, teamNames.length);
        for (int i = 0; i < count; i++) {
            Participant participant;
            if (teamNames[i] == null || teamNames[i].trim().isEmpty()) {
                participant = new Participant(names[i]);
            } else {
                participant = new Participant(names[i], teamNames[i]);
            }
            participant.printStatus();
        }
    }

    public static void main(String[] args) {
        try {
            String[] names = {"Ravi", "Meera", "Karthik", "Divya"};
            String[] teamNames = {"ByteBusters", "", "CodeCrafters", ""};

            processRegistrations(names, teamNames);
        } catch (Exception e) {
            System.err.println("Error processing hackathon participants: " + e.getMessage());
        }
    }
}
