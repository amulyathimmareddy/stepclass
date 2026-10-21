package week10.practice;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Student member where uniqueness is dictated solely by rollNumber.
 */
class ClubStudent {
    private String rollNumber;
    private String name;

    public ClubStudent(String rollNumber, String name) {
        this.rollNumber = (rollNumber != null) ? rollNumber.trim() : "";
        this.name = (name != null) ? name.trim() : "";
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClubStudent)) return false;
        ClubStudent that = (ClubStudent) o;
        return Objects.equals(rollNumber, that.rollNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rollNumber);
    }
}

/**
 * Problem 3: Student Club Registration Management
 * Manages unique club member enrollments based strictly on student roll number.
 */
public class StudentClubRegistrationManagement {

    private Set<ClubStudent> registeredMembers = new HashSet<>();

    public void addMember(String rollNumber, String name) {
        ClubStudent student = new ClubStudent(rollNumber, name);
        if (registeredMembers.contains(student)) {
            System.out.println("duplicate rejected");
        } else {
            registeredMembers.add(student);
            System.out.println("Added");
        }
    }

    public void printMemberCount() {
        System.out.println("member count " + registeredMembers.size());
    }

    public void containsMember(String rollNumber, String name) {
        ClubStudent student = new ClubStudent(rollNumber, name);
        System.out.println("contains: " + registeredMembers.contains(student));
    }

    public static void main(String[] args) {
        try {
            StudentClubRegistrationManagement club = new StudentClubRegistrationManagement();

            // ADD operations
            club.addMember("21CS01", "Asha");
            club.addMember("21CS01", "Asha");
            club.addMember("21CS02", "Ravi");

            // Report total unique count
            club.printMemberCount();

            // CONTAINS queries
            club.containsMember("21CS02", "Ravi");
            club.containsMember("21CS03", "Priya");
        } catch (Exception e) {
            System.err.println("Error in club registration: " + e.getMessage());
        }
    }
}
