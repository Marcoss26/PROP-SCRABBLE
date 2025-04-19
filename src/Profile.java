import java.util.*;

public class Profile {
    private String username;
    private String password;
    private boolean public;

    private int score;
    private int wins;
    private int gamesPlayed;
    private static final Map<String, Int> dictionaryUsage = new HashMap<>();


    public Profile(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
        this.setVisibility(true); //true by default
    }

    public void setUsername(String username) {
        this.username = username;
    }
    private void setPassword(int password) {
        this.password = password;
    }
    public void changePassword(String old_pw, String new_pw) {
        if (old_pw.equals(this.password)) {
            this.password = new_pw;
        }
    }
    public void setVisibility(boolean isPublic) {
        this.public = isPublic;
    }
    void authenticate(String password) {
        if (this.password.equals(password)) return true;
        return false;
    }

    public void getUsername() {
        return this.username;
    }
    public getWins() {
        return this.wins;
    }
    public getScore() {
        return this.score;
    }
    public getGamesPlayed() {
        return this.gamesPlayed;
    }
    public Map<String, Int> getDictionaryUsage() {
        return this.dictionaryUsage;
    }
    public boolean isPublic() {
        return this.public;
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
    public void incrementDictionaryUsage(String lang) {
        if (dictionaryUsage.containsKey(lang)) {
            dictionaryUsage.put(lang, dictionaryUsage.get(lang) + 1);
        } else {
            dictionaryUsage.put(lang, 1);
        }
    }
    
    public printProfile() {
        System.out.println("------------------------------");
        System.out.println("Username: " + this.username);
        System.out.println("Public: " + this.public);
        System.out.println("Games Played: " + this.gamesPlayed);
        System.out.println("------------------------------");
    }
}