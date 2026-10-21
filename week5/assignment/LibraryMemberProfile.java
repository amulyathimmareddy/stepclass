package week5.assignment;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Problem 4: LibraryMember JavaBean & Security Answer Property
 * Compliant JavaBean modeling a library member profile with a write-once membership identifier
 * and a write-only, one-way transformed security answer.
 */
public class LibraryMemberProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    private String membershipId;
    private String name;
    private boolean premiumMember;
    private String securityAnswerHash; // Write-only one-way transformation
    private boolean membershipIdSet;   // Flag to enforce write-once contract

    /**
     * Mandatory public no-argument constructor for JavaBean compliance.
     */
    public LibraryMemberProfile() {
        this.membershipId = null;
        this.name = "";
        this.premiumMember = false;
        this.securityAnswerHash = null;
        this.membershipIdSet = false;
    }

    /**
     * Getter for membershipId.
     *
     * @return the assigned membership identifier
     */
    public String getMembershipId() {
        return membershipId;
    }

    /**
     * Write-once setter for membershipId.
     * Takes effect ONLY on the very first invocation; subsequent calls are silently ignored.
     *
     * @param id proposed membership ID
     */
    public void setMembershipId(String id) {
        if (!this.membershipIdSet && id != null && !id.trim().isEmpty()) {
            this.membershipId = id.trim();
            this.membershipIdSet = true;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = (name != null) ? name.trim() : "";
    }

    public boolean isPremiumMember() {
        return premiumMember;
    }

    public void setPremiumMember(boolean premiumMember) {
        this.premiumMember = premiumMember;
    }

    /**
     * Write-only setter for member security verification answer.
     * Stores a deterministic one-way SHA-256 hash representation.
     * Deliberately provides NO matching getter anywhere on this class.
     *
     * @param answer plaintext security answer
     */
    public void setSecurityAnswer(String answer) {
        if (answer == null || answer.trim().isEmpty()) {
            this.securityAnswerHash = null;
            return;
        }

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(answer.trim().getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            this.securityAnswerHash = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Fallback deterministic one-way transformation if algorithm unavailable
            this.securityAnswerHash = String.valueOf(answer.trim().hashCode());
        }
    }

    public static void main(String[] args) {
        try {
            LibraryMemberProfile member = new LibraryMemberProfile();

            // Set initial properties
            member.setMembershipId("LIB-8841");
            member.setName("Priya Nair");
            member.setPremiumMember(true);
            System.out.println("Membership ID: " + member.getMembershipId());

            // Attempt to overwrite membershipId (write-once enforcement)
            member.setMembershipId("FAKE-0000");
            System.out.println("Membership ID after overwrite attempt: " + member.getMembershipId());

            System.out.println("Is Premium Member: " + member.isPremiumMember());

            // Set security answer (write-only property verified)
            member.setSecurityAnswer("BlueMountain");
            System.out.println("Security answer stored as write-only property.");
        } catch (Exception e) {
            System.err.println("Error testing LibraryMemberProfile JavaBean: " + e.getMessage());
        }
    }
}
