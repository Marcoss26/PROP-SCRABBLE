package DomainLayer.DomainClasses;
import java.util.*;
import java.security.*;

/**
 * Profile class represents a user profile in the game.
 * It contains the username, password, visibility status, and game statistics.
 * @author Kai Knox
 */
public class Profile {
    private String username;
    private String password;
    private boolean isPublic;

    private int score;
    private int wins;
    private int gamesPlayed;
    private Map<String, Integer> dictionaryUsage = new HashMap<>();

    /**
     * Constructor for Profile class.
     * @param username The username of the profile.
     * @param password The password of the profile.
     */
    public Profile(String username, String password) {
        this.setUsername(username);
        this.setPassword(PasswordUtils.hashPassword(password));
        this.setVisibility(true); //true by default
    }

    /**
     * Sets the username for the profile.
     * @param username The username to be set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the password for the profile.
     * @param password The password to be set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Changes the password of the profile.
     * @param old_pw The old password.
     * @param new_pw The new password.
     */
    public void changePassword(String old_pw, String new_pw) {
        if (old_pw.equals(this.password)) {
            this.password = new_pw;
        }
    }

    /**
     * Sets the visibility of the profile.
     * @param isPublic The visibility status to be set.
     */
    public void setVisibility(boolean isPublic) {
        this.isPublic = isPublic;
    }

    /**
     * Authenticates the profile with the given password.
     * @param password The password to be authenticated.
     * @return true if authentication is successful, false otherwise.
     */
    public boolean authenticate(String password) {
        String hashedInput = PasswordUtils.hashPassword(password);
        return hashedInput.equals(this.password);
    }

    /**
     * Retrieves the username of the profile.
     * @return The username of the profile.
     */
    public String getUsername() {
        return this.username;
    }
    /**
     * Retrieves the password of the profile.
     * @return The password of the profile.
     */
    public String getPassword() {
        return this.password;
    }
    
    /**
     * Retrieves the password of the profile.
     * @return The password of the profile.
     */
    public int getWins() {
        return this.wins;
    }
    public int getScore() {
        return this.score;
    }
    public int getGamesPlayed() {
        return this.gamesPlayed;
    }
    public Map<String, Integer> getDictionaryUsage() {
        return this.dictionaryUsage;
    }
    public boolean isPublic() {
        return this.isPublic;
    }
    

    public void addScore(int score) {
        this.score += score;
    }
    public void incrementGamePlayed() {
        this.gamesPlayed++;
    }
    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed += gamesPlayed;
    }
    public void incrementWins() {
        this.wins++;
    }
    public void setWins(int wins) {
        this.wins += wins;
    }
    
    /**
     * Increments the usage count of a specific dictionary.
     * @param lang The language of the dictionary.
     */
    public void incrementDictionaryUsage(String lang) {
        if (dictionaryUsage.containsKey(lang)) {
            dictionaryUsage.put(lang, dictionaryUsage.get(lang) + 1);
        } else {
            dictionaryUsage.put(lang, 1);
        }
    }
}




class PasswordUtils {
    /**
     * Hashes a password using SHA-256 and encodes it in Base64.
     * @param password The password in plain text to be hashed.
     * @return The hashed password as a Base64 encoded string.
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedPassword = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}