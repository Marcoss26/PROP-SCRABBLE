import java.util.*;

public class DomainController {
    private ProfileController profileController;

    public DomainController() {
    }

    public void addProfile(String username, String password) {
        profileController.addProfile(username, password);
    }
    public void removeProfile(String username) {
        profileController.removeProfile(username);
    }
    public Profile getProfile(String username) {
        return profileController.getProfile(username);
    }

    public void newGame() {
        MP_Controller.createMatch("matchID", 2, 2);
    }
    
}