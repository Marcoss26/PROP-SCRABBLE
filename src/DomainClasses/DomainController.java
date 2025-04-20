import java.util.*;

/**
 * ProfileController is a singleton class that manages user profiles.
 * It allows adding, removing, and retrieving profiles, as well as checking if a profile exists.
 * @author Kai Knox
 */
public class DomainController {
    private ProfileController profileController;
    private MP_Controller MP_Controller;
    private static DomainController c;

    private DomainController() {
        this.profileController = new ProfileController();
        this.MP_Controller = new MatchController();
    }

    /**
     * Returns the singleton instance of DomainController.
     * @return The instance of DomainController.
     */
    public static DomainController getInstance() {
        if (c == null) c = new DomainController();
        return c;
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

    /**
     * Creates a new match with the given number of human and AI players.
     * @param humanCount The number of human players.
     * @param aiCount The number of AI players.
     * @throws IllegalArgumentException if the total number of players is less than 2.
     * @throws IllegalArgumentException if the number of human players is less than 1.
     * @throws IllegalArgumentException if the number of AI players is less than 0.
     * @throws IllegalArgumentException if the number of human players exceeds the total number of players.
     * @throws IllegalArgumentException if the number of AI players exceeds the total number of players.
     * @throws IllegalArgumentException if the number of human players is greater than the number of AI players.
     * @throws IllegalArgumentException if the number of AI players is greater than the number of human players.
     * @throws IllegalArgumentException if the number of players is not a positive integer.
     * @throws IllegalArgumentException if the number of players is not a valid integer.
     */
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