package DomainClasses;
import java.util.*;
import java.io.*;

/**
 * ProfileController is a singleton class that manages user profiles.
 * It allows adding, removing, and retrieving profiles, as well as checking if a profile exists.
 * @author Kai Knox
 */
public class ProfileController {
    private Map<String, Profile> profiles = new HashMap<>();
    private static ProfileController c;

    private ProfileController() {
        
    }

    /**
     * Returns the singleton instance of ProfileController.
     * @return The instance of ProfileController.
     */
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
        if (profiles.containsKey(username)) {
            throw new IllegalArgumentException("Profile with this username already exists.");
        }
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty.");
        }
        Profile profile = new Profile(username, password);
        profiles.put(profile.getUsername(), profile);
    }

    /**
     * Removes a profile from the profiles map.
     * @param username The username of the profile to be removed.
     */
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

    /**
     * Updates the password of a profile.
     * @param username The username of the profile.
     * @param password The new password for the profile.
     */
    public void updateProfile(String username, String password) {
e1cmxwaklm
    }

    /**
     * Prints all the profiles
     */
    public void printProfiles() {
        for (Profile profile : profiles.values()) {
            profile.printProfile();
        }
    }
}