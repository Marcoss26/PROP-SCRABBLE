package DomainClasses;
import java.util.*;
import java.io.*;

/**
 * ProfileController is a singleton class that manages user profiles.
 * It allows adding, removing, and retrieving profiles, as well as checking if a profile exists.
 * @author Kai Knox
 */

 /**
  * ------------------------
  TODO
    ------------------------
    * - Creaete a new match (numbPlayers, profiles, dicctionary, bolsa, tamaño)
    * - Continue a match (id)
    * - See list of matches to continue: id[]

    * - Create dictionary (lang, name)
    * - 
  */
public class DomainController {
    /**
     * ProfileController instance to manage user profiles.
     * MP_Controller instance to manage matches.
     * Ranking instance to manage game statistics and rankings.
     * DictionaryController instance to manage the dictionary.
     * DomainController instance to implement the singleton pattern.
     */
    private ProfileController profileController;
    private MP_Controller matchController;
    private Ranking ranking;
    private DictionaryController dictionaryController;

    private static DomainController c;

    private DomainController() {
        this.profileController = ProfileController.getInstance();
        this.matchController = MP_Controller.getInstance();
        this.ranking = Ranking.getInstance();
        this.dictionaryController = DictionaryController.getInstance();
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
    public void addProfile(String username, String password) {
        this.profileController.addProfile(username, password);
    }
    public void removeProfile(String username) {
        this.profileController.removeProfile(username);
    }
    public Profile getProfile(String username, String password) {
        return this.profileController.getProfile(username, password);
    }
    public boolean profileExists(String username) {
        return this.profileController.profileExists(username);
    }

    /* ---------------------------------------------------------------------
                            MATCH FUNCTIONALITY
    ------------------------------------------------------------------------*/
    /**
     * Creates a new match with the given number of players, profiles, language, name, and size.
     * @param players The number of players in the match.
     * @param profiles The set of profiles participating in the match.
     * @param language The language of the match.
     * @param name The name of the match.
     * @param size The size of the board.
     */
    public void newMatch(int players, Set<Profile> profiles, String language, String name, int size) {
        this.matchController.createMatch(players, profiles, language, name, size);
    }

    /**
     * Continues a created match.
     * @param id The ID of the existing match to continue.
     */
    public void continueMatch(String id) {
        this.matchController.continueMatch(id);
    }
    /**
     * Retrieves a list of paused matches.
     * @return A list of matches that can be continued.
     */
    public List<Match> getMatches() {
        return this.matchController.getMatches();
    }
}