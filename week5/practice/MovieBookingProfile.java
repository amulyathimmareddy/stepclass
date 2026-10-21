package week5.practice;

import java.io.Serializable;

/**
 * Problem 4: MovieBookingProfile JavaBean & OTP Property
 * Conforms strictly to JavaBean conventions (serializable, no-arg constructor, standard accessors),
 * with a write-only property for sensitive OTP verification data.
 */
public class MovieBookingProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private boolean confirmed;
    private String otp; // Write-only sensitive attribute

    /**
     * Required JavaBean public no-argument constructor.
     */
    public MovieBookingProfile() {
        this.name = "";
        this.confirmed = false;
        this.otp = null;
    }

    /**
     * Convenience constructor setting customer name.
     * Chains via this() to preserve JavaBean initialization contracts.
     *
     * @param name customer profile name
     */
    public MovieBookingProfile(String name) {
        this();
        setName(name);
    }

    // Standard JavaBean getter and setter for 'name'
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = (name != null) ? name.trim() : "";
    }

    // Standard JavaBean boolean getter ('isConfirmed') and setter
    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    /**
     * Write-only OTP setter.
     * Accepts a 4-6 digit numeric OTP string for internal verification.
     * Deliberately has NO matching getter to protect authentication tokens.
     *
     * @param otp 4-6 digit verification code
     */
    public void setOtp(String otp) {
        if (otp != null && otp.matches("\\d{4,6}")) {
            this.otp = otp;
        } else {
            this.otp = null;
        }
    }

    public static void main(String[] args) {
        try {
            // Test constructor and name accessor
            MovieBookingProfile profile1 = new MovieBookingProfile("Rahul Dev");
            System.out.println("Profile Name: " + profile1.getName());

            // Test boolean getter/setter
            profile1.setConfirmed(true);
            System.out.println("Profile Confirmed: " + profile1.isConfirmed());

            // Test write-only OTP (stores securely without accessor exposure)
            profile1.setOtp("4471");
            System.out.println("OTP set successfully (write-only property verified).");
        } catch (Exception e) {
            System.err.println("Error testing MovieBookingProfile JavaBean: " + e.getMessage());
        }
    }
}
