import java.util.*;

public class Profile {
    private String username;
    private String password;
    private int score;
    private int gamesPlayed;
    private boolean public;

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

    public void getUsername() {
        return this.username;
    }
    public getScore() {
        return this.score;
    }
    public getGamesPlayed() {
        return this.gamesPlayed;
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
}