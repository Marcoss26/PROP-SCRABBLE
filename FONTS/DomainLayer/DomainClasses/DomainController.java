package DomainLayer.DomainClasses;
import java.util.*;

import PersistenceLayer.PersistenceController;

import java.io.*;
import Utils.Pair;

/**
 * ProfileController is a singleton class that manages user profiles.
 * It allows adding, removing, and retrieving profiles, as well as checking if a profile exists.
 * @author Kai Knox
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
    private PersistenceController persistenceController;
    private static DomainController c;

    private DomainController() {
        this.profileController = ProfileController.getInstance();
        this.matchController = MP_Controller.getInstance();
        this.ranking = Ranking.getInstance();
        this.dictionaryController = DictionaryController.getInstance();
        //this.persistenceController = PersistenceController.getInstance();
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
        //persistenceController.saveProfiles(this.profileController.getProfiles());
    }
    public void removeProfile(String username) {
        this.profileController.removeProfile(username);
        //persistenceController.saveProfiles(this.profileController.getProfiles());
    }
    public Profile getProfile(String username) {
        return this.profileController.getProfile(username);
    }
    public boolean authenticateProfile(String username, String password) {
        return this.profileController.authenticateProfile(username, password);
      }
    public boolean profileExists(String username) {
        return this.profileController.profileExists(username);
    }
    public void updateProfile(String username, String oldPwd, String newPwd, boolean isPublic) {
        this.profileController.updateProfile(username, oldPwd, newPwd, isPublic);
        //persistenceController.saveProfiles(this.profileController.getProfiles());
    }
   /* 
    public void loadProfiles() {
        Map<String, Profile> profiles = persistenceController.loadProfiles();
        for (Map.Entry<String, Profile> entry : profiles.entrySet()) {
            this.profileController.addProfile(entry.getValue());
        }
    }*/

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
    public String newMatch(int totalPlayers, Set<Pair<String,String>> profilesIds, String dictionaryName, int boardSize) throws IOException {
        
        //Para inicializar una partida necesito: el numero de jugadores, los perfiles creados de estos, el diccionario creado, el tamaño del tablero, la bolsa en un mapa y su tamaño.
        //createDictionary(dictionaryName, dictionaryName, dictionaryName);
        Dictionary dictionary = dictionaryController.getDictionary(dictionaryName);
        Set<Profile> profiles = new HashSet<>();
        for(Pair<String,String> profileId : profilesIds){
            Profile profile = this.profileController.getProfile(profileId.first());
            profiles.add(profile);
        }

        //creacion del mapa de la bolsa a partir del txt que nos dan

        Map<Letter, Integer> letters = new HashMap<>();
        int totalLettersInTheBag = 0;
        String fileName;
        switch(dictionaryName) {
            case "en":
                fileName = "letrasENG";
                break;
            case "es":
                fileName = "letrasCAST";
                break;
            case "ca":
                fileName = "letrasCAT";
                break;
            default:
                System.out.println("Error: Dictionary not found.");
                return null;
        }
        //boolean validFile = false;
        //while(!validFile) {
            String file = fileName + ".txt";
            File filePath = new File("data/Letters/" + file);
    
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(" ");
                    if (parts.length == 3) {
                        String symbol = parts[0];
                        int quantity = Integer.parseInt(parts[1]);
                        totalLettersInTheBag += quantity;
                        int value = Integer.parseInt(parts[2]);
                        Letter letter = new Letter(symbol, value);
                        letters.put(letter, quantity);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading the file: " + e.getMessage());
                
            }

        return this.matchController.createMatch(totalPlayers, profiles, dictionary, boardSize, letters, totalLettersInTheBag);
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
    public Map<String,Match> getUnfinishedMatchs() {
        return this.matchController.getUnfinishedMatches();
    }

    /**
     * Places some letters on the board for a given match.
     * @param matchId The ID of the match to play.
     */
    public boolean playsMatch(String matchId, String word, int posStartX, int posStartY, int posEndX, int posEndY, Set<Pair<Integer, Integer>> jokers) {
        return this.matchController.humanTurn(matchId, word, posStartX, posStartY, posEndX, posEndY, jokers);
    }

    public Pair<Boolean, String> playsMatch2(String matchId, String word1, int coordX_ini1, int coordX_end1, int coordY_ini1, int coordY_end1, String word2, int coordX_ini2, int coordX_end2, int coordY_ini2, int coordY_end2  ,Set<Pair<Integer, Integer>> jokers) {

        return this.matchController.humanTurn2(matchId, word1, coordX_ini1, coordY_ini1, coordX_end1, coordY_end1, word2, coordX_ini2, coordY_ini2, coordX_end2, coordY_end2, jokers);
    }


    public void shuffleRack(String matchId) { 
        this.matchController.shuffleRack(matchId);
    }

    public void modifyRack(String matchId, String letters) {
        this.matchController.modifyRack(matchId, letters);
    }
    
    public void loadMatches() {
        Map<String, Match> matches = persistenceController.loadMatches();
        matchController.loadMatchesFromJSON(matches);
    }
    public void saveMatches() {
        this.persistenceController.saveMatches(this.matchController.getUnfinishedMatches());
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

     public ArrayList<ArrayList<String>> getRankingInfo() {
        return this.ranking.getRankingInfo();
     }


     /*
      * ---------------------------------------------------------------------
                            PRESENTATION FUNCTIONALITY
        ------------------------------------------------------------------------
      */
      public ArrayList<String> getMatchplayers(String matchId) {
            try{
                return matchController.getMatchPlayers(matchId);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
            return null;
      }

      public ArrayList<String> getRackLetters(String matchId, int turn) {
            try{
                return matchController.getRackLetters(matchId, turn);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
            return null;
      }

      public boolean profInSystem(String username) {
        return this.profileController.profileExists(username);
      }


      public boolean checkPassword(String username, String password) {
        
        return this.profileController.getProfile(username) != null;
      }


      public ArrayList<String> getProfileStats(String username, String password) {
        Profile profile = this.profileController.getProfile(username);
        if (profile != null) {
            ArrayList<String> stats = new ArrayList<>();
            int gp = profile.getGamesPlayed();
            int wins = profile.getWins();
            float winRate = gp > 0 ? (float) wins / gp * 100 : 0; 
            stats.add(username);
            stats.add(String.valueOf(gp));
            stats.add(String.valueOf(wins));
            stats.add(String.format("%.2f", winRate));
            return stats;
        }
        return null;
      }

      public boolean isGameFinished(String matchId) {
        return this.matchController.isGameFinished(matchId);
      }

      public boolean isHumanTurn(String matchId, int turn) {
        return this.matchController.isHumanTurn(matchId, turn);
      }

      public int getPlayerScore(String matchId, int turn) {
        return this.matchController.getPlayerScore(matchId, turn);
      }

      public Integer getBagTiles(String matchId) {
        return this.matchController.getBagTiles(matchId);
      }

      public void setTurn(String matchId) {
        this.matchController.setTurn(matchId);
      }

      public String endMatch(String matchId) {
        return this.matchController.setFinished(matchId);
      }

      
}