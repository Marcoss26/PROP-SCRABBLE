import java.util.*;

public class DomainController {
    private ProfileController profileController;
    private MatchController MP_Controller;

    public DomainController() {
        this.profileController = new ProfileController();
        this.MP_Controller = new MatchController();
    }

    /* ---------------------------------------------------------------------
                            PROFILE FUNCTIONALITY
    ------------------------------------------------------------------------*/
    public void manageProfiles() {
        return this.profileController;
    }
    public void addProfile(String username, String password) {
        this.profileController.addProfile(username, password);
    }
    public void removeProfile(String username) {
        this.profileController.removeProfile(username);
    }
    public Profile getProfile(String username) {
        return this.profileController.getProfile(username);
    }

    /* ---------------------------------------------------------------------
                            MATCH FUNCTIONALITY
    ------------------------------------------------------------------------*/
    public void manageMatches() {
        return this.MP_Controller;
    }
    public void newMatch(matchID, int humanCount, int aiCount) {
        this.MP_Controller.createMatch(matchID, humanCount, aiCount);
    }
}