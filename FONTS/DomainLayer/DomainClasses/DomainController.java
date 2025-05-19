package DomainLayer.DomainClasses;
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
     * @param dictionaryName The name of dictionary to be used.
     * @param boardSize The size of the board.
     * @param bagLetters The letters in the bag.
     * @param numLetters The number of letters to be used.
     * @throws IOException if an error occurs while creating the match.
     */
    public String newMatch(int players, Set<Profile> profiles, String dictionaryName, int boardSize, Map<Letter, Integer> bagLetters, int numLetters) throws IOException {
        Dictionary dictionary = dictionaryController.getDictionary(dictionaryName);
        return this.matchController.createMatch(players, profiles, dictionary, boardSize, bagLetters, numLetters);
    }

    /**
     * Continues a created match.
     * @param id The ID of the existing match to continue.
     */
    public void continueMatch(String id) {
        this.matchController.continueMatch(id);
    }

    /**
     * Retrieves the current turn player for a given match.
     * @param matchId The ID of the match get the turn from.
     * @return The player whose turn it is.
     */
    public Player getPlayerTurn(String matchId) {
        return this.matchController.whoseTurn(matchId);
    }

    /**
     * Retrieves the list of unfinished matches.
     * @return A list of unfinished match IDs.
     */
    public List<String> getUnfinishedMatchs() {
        return this.matchController.getUnfinishedMatches();
    }

    /**
     * Places some letters on the board for a given match.
     * @param matchId The ID of the match to play.
     */
    public void playsMatch(String matchId, String word, int posStartX, int posStartY, int posEndX, int posEndY) {
        this.matchController.playsMatch(matchId, word, posStartX, posStartY, posEndX, posEndY);
    }

    public void shuffleRack(String matchId) {
        this.matchController.shuffleRack(matchId);
    }

    public void modifyRack(String matchId, String letters) {
        this.matchController.modifyRack(matchId, letters);
    }

    public void printMatch(String matchId) {
        this.matchController.print(matchId);
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
        try {
            return this.dictionaryController.getDictionaryLanguage(dictionaryName);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    public Map<String, Dictionary> getDictionaries() {
        return this.dictionaryController.getDictionaries();
    }

    /* 
     * ---------------------------------------------------------------------
                            RANKING FUNCTIONALITY
     ------------------------------------------------------------------------
     */
     public void updateRanking(String idWinner) {
        //this.ranking.updateRanking(this.profileController.getProfileToUpdateRanking(idWinner));
     }

     public void displayRanking() {
        this.ranking.displayRanking();
     }
}