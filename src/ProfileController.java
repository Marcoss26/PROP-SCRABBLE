import java.util.*;

public class ProfileController {
    private Map<String, Profile> matches = new HashMap<>();
    private static ProfileController c;

    private ProfileController() {
        
    }

    public static ProfileController getInstance() {
        if (c == null) c = new ProfileController();
        return c;
    }


    public void addProfile(String username, String password) {
        Profile profile = new Profile(username, password);
        matches.put(profile.getUsername(), profile);
    }
    public void removeProfile(String username) {
        matches.remove(username);
    }
    public Profile getProfile(String username) {
        return matches.get(username);
    }
    public void updateProfile(String username, String password) {
        Profile profile = matches.get(username);
        if (profile != null) {
            profile.changePassword(profile.getPassword(), password);
        }
    }
}