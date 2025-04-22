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
    public String newMatch(int players, Set<Profile> profiles, String language, String dictionaryName, int boardSize, BAG) {
        return this.matchController.createMatch(players, profiles, language, dictionaryName, boardSize);
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

    /*
     * ---------------------------------------------------------------------
                            DICTIONARY FUNCTIONALITY
     ------------------------------------------------------------------------
     */
    /**
     * Adds a new dictionary to the controller.
     * @param dictionaryName The name of the dictionary to add.
     * @param language The language of the dictionary.
     */
    public void createDictionary(String dictionaryName, String language, String fileName) {
        try{
            this.dictionaryController.addDictionary(dictionaryName, language);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        try{
            this.dictionaryController.addWordsToDictionary(dictionaryName, fileName);
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }

    public void removeDictionary(String dictionaryName) {
        this.dictionaryController.removeDictionary(dictionaryName);
    }

    public void addWordToDictionary(String dictionaryName, String word) {
        this.dictionaryController.addWordToDictionary(dictionaryName, word);
    }

    public void removeWordFromDictionary(String dictionaryName, String word) {
        this.dictionaryController.removeWordFromDictionary(dictionaryName, word);
    }

    public String getDictionaryLanguage(String dictionaryName) {
        try{
            return this.dictionaryController.getDictionaryLanguage(dictionaryName);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    /* 
     * ---------------------------------------------------------------------
                            RANKING FUNCTIONALITY
     ------------------------------------------------------------------------
     */

     public void updateRanking(String idWinner) {

        this.ranking.updateRanking(this.ProfileController.getProfileToUpdateRanking(idWinner));
     }

     public void displayRanking() {
        this.ranking.displayRanking();
     }
}