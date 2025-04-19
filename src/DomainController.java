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
    public void newMatch(int humanCount, int aiCount) {
        Set<Profile> profiles = new HashSet<>();
        const MIN_HOMAN_PLAYERS = 1;
        Scanner myObj = new Scanner(System.in);

        system.out.println("Enter the number of total players");
        int players = myObj.nextLine();
        while(profiles.size() < players) {
            system.out.println("Enter the username of the player");
            String userName = myObj.nextLine();
            if (userName == "" && profiles.size() >= MIN_HOMAN_PLAYERS) break;
            Profile p = profileController.getProfile(userName);
            if (p == null) {
                System.out.println("Profile not found.");
                return;
            } else {
                if (p.authenticate(password)) {
                    profiles.add(p);
                } else {
                    System.out.println("Invalid password");
                    return;
                }
            }
        }

        this.MP_Controller.createMatch(matchID, profiles);
    }
}