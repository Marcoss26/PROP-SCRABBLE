import java.util.*;

public class ProfileController {
    private Map<String, Profile> profiles = new HashMap<>();
    private static ProfileController c;

    private ProfileController() {
        
    }

    public static ProfileController getInstance() {
        if (c == null) c = new ProfileController();
        return c;
    }


    /**
     * Adds a new profile to the profiles map.
     * @param username The username of the new profile.
     * @param password The password of the new profile.
     */
    public void addProfile(String username, String password) {
        Profile profile = new Profile(username, password);
        profiles.put(profile.getUsername(), profile);
    }
    public void removeProfile(String username) {
        profiles.remove(username);
    }

    /**
     * Checks if a profile with the given username exists.
     * @param username The username of the profile to check.
     * @return true if the profile exists, false otherwise.
     */
    public Profile profileExists(String username,) {
        return profiles.get(username) != null;
    }

    /**
     * Retrieves a profile by username and password.
     * @param username The username of the profile.
     * @param password The password of the profile.
     * @return The Profile object if authentication is successful, null otherwise.
     */
    public Profile getProfile(String username, String password) {
        Profile profile = profiles.get(username);
        return profile.authenticate(password) ? profile : null;
    }

    public void updateProfile(String username, String password) {
        Profile profile = profiles.get(username);
        if (profile != null) {
            profile.changePassword(profile.getPassword(), password);
        }
    }
    void printProfiles() {
        for (Profile profile : profiles.values()) {
            profile.printProfile();
        }
    }
}