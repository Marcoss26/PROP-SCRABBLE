package DomainClasses;
import java.util.*;

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
        this.setPassword(password);
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
    private void setPassword(String password) {
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
        if (this.password.equals(password)) return true;
        return false;
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
    public void incrementWins() {
        this.wins++;
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
    
    /**
     * Prints the profile information.
     */
    public void printProfile() {
        System.out.println("------------------------------");
        System.out.println("Username: " + this.username);
        System.out.println("Public: " + this.isPublic);
        System.out.println("Games Played: " + this.gamesPlayed);
        System.out.println("------------------------------");
    }
}