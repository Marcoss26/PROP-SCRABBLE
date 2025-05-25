package PersistenceLayer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;

import DomainLayer.DomainClasses.*;

class JsonUtils {
    // Save a JSONArray to a file
    public static void saveJsonToFile(String filePath, JSONArray jsonArray) {
        try (FileWriter file = new FileWriter(filePath)) {
            file.write(jsonArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            System.err.println("Error saving JSON to file: " + e.getMessage());
        }
    }

    // Load a JSONArray from a file
    public static JSONArray loadJsonFromFile(String filePath) {
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader(filePath)) {
            return (JSONArray) parser.parse(reader);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        } catch (IOException | ParseException e) {
            System.err.println("Error loading JSON from file: " + e.getMessage());
        }
        return new JSONArray(); // Return an empty array if the file doesn't exist or an error occurs
    }
}



public class PersistenceController {
    private static PersistenceController instance = null;
    private final String DATA_PATH = "./data";

    private PersistenceController() {
        // Ensure the data directory exists
        File dataDir = new File(DATA_PATH);
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
    }

    public static PersistenceController getInstance() {
        if (instance == null) {
            instance = new PersistenceController();
        }
        return instance;
    }


    /*
     * ---------------------------------------------------------------------
                            PROFILES FUNCTIONALITY
     ------------------------------------------------------------------------
     */
    public void saveProfiles(Map<String, Profile> profiles) {
        JSONArray profilesArray = new JSONArray();

        for (Map.Entry<String, Profile> entry : profiles.entrySet()) {
            JSONObject profileObject = new JSONObject();
            profileObject.put("username", entry.getKey());
            profileObject.put("password", entry.getValue().getPassword());
            profileObject.put("gamesPlayed", entry.getValue().getGamesPlayed());
            profileObject.put("wins", entry.getValue().getWins());
            profileObject.put("score", entry.getValue().getScore());
            profilesArray.add(profileObject);
        }

        JsonUtils.saveJsonToFile(DATA_PATH + "/profiles.json", profilesArray);
    }

    public Map<String, Profile> loadProfiles() {
        Map<String, Profile> profiles = new HashMap<>();
        JSONArray profilesArray = JsonUtils.loadJsonFromFile(DATA_PATH + "/profiles.json");

        for (Object obj : profilesArray) {
            JSONObject profileObject = (JSONObject) obj;
            String username = (String) profileObject.get("username");
            String password = (String) profileObject.get("password");
            long gamesPlayed = (long) profileObject.get("gamesPlayed");
            long wins = (long) profileObject.get("wins");
            long score = (long) profileObject.get("score");

            Profile profile = new Profile(username, password);
            profile.setGamesPlayed((int) gamesPlayed);
            profile.setWins((int) wins);
            profile.addScore((int) score);

            profiles.put(username, profile);
        }

        return profiles;
    }





    /*
     * ---------------------------------------------------------------------
                            MATCH FUNCTIONALITY
     ------------------------------------------------------------------------
     */
    public void saveMatches(List<Match> matches) {
        JSONArray matchesArray = new JSONArray();
        for (Match match : matches) {
            JSONObject matchObject = new JSONObject();
 
            matchObject.put("id", match.getId());
            matchObject.put("dictionary", match.getDictionary());
            matchObject.put("current_turn", match.getTurn());

            // Save bag
            JSONArray bagArray = new JSONArray();
            for (Map.Entry<Letter, Integer> entry : match.getBag().getLetters().entrySet()) { // Assuming the map key is a String
                Letter tile = entry.getKey();
                int count = entry.getValue();
                JSONObject tileObject = new JSONObject();
                tileObject.put("symbol", tile.getSymbol());
                tileObject.put("value", tile.getValue());
                tileObject.put("count", count);
                bagArray.add(tileObject);
            }
            matchObject.put("bag", bagArray);

            // Save players
            JSONArray playersArray = new JSONArray();
            for (Map.Entry<String,Player> entry : match.getPlayers().entrySet()) { // Assuming getPlayers() returns a List or Set of Player
                Player player = entry.getValue();
                JSONObject playerObject = new JSONObject();
                playerObject.put("id", player.getID());
                playerObject.put("name", player.getName());
                playerObject.put("score", player.getScore());
                playerObject.put("type", player.isHuman() ? "Human" : "AI");

                // Save rack
                JSONArray rackArray = new JSONArray();
                for (Letter letter : player.getRack().getLetters()) { // Assuming getTiles() returns a List or Set of Box
                    JSONObject tileObject = new JSONObject();
                    tileObject.put("symbol", letter.getSymbol());
                    tileObject.put("value", letter.getValue());
                    rackArray.add(tileObject);
                }
                playerObject.put("rack", rackArray);
                playersArray.add(playerObject);
            }
            matchObject.put("players", playersArray);


            // Save board
            JSONArray boardArray = new JSONArray();
            Board board = match.getBoard();
            for (int i = 0; i < board.getSize(); i++) {
                for (int j = 0; j < board.getSize(); j++) {
                    Box box = board.getBox(i, j);
                    if (box != null && !box.isEmpty()) {
                    JSONObject boxObject = new JSONObject();
                    boxObject.put("x", box.getRow());
                    boxObject.put("y", box.getColumn());
                    boxObject.put("symbol", box.getSymbol());
                    boxObject.put("value", box.getValue());
                    boxObject.put("crosscheck_v", box.getCrossCheck(0));
                    boxObject.put("crosscheck_h", box.getCrossCheck(1));
                    boardArray.add(boxObject);
                    }
                }
            }
            matchObject.put("board", boardArray);

            matchesArray.add(matchObject);
        }

        JsonUtils.saveJsonToFile(DATA_PATH + "/matches.json", matchesArray);
    }

    public Map<String, Match> loadMatches() {
        Map<String, Match> matches = new HashMap<>();
        JSONArray matchesArray = JsonUtils.loadJsonFromFile(DATA_PATH + "/matches.json");

        for (Object obj : matchesArray) {
            JSONObject matchObject = (JSONObject) obj;

            String id = (String) matchObject.get("id");
            String dictionary = (String) matchObject.get("dictionary");
            long currentTurn = (long) matchObject.get("current_turn");

            // Load bag
            Map<Letter, Integer> bagMap = new HashMap<>();
            int totalLetters = 0;
            JSONArray bagArray = (JSONArray) matchObject.get("bag");
            for (Object bagObj : bagArray) {
                JSONObject tileObject = (JSONObject) bagObj;
                String symbol = (String) tileObject.get("symbol");
                long value = (long) tileObject.get("value");
                long count = (long) tileObject.get("count");
                Letter letter = new Letter(symbol, (int) value);
                bagMap.put(letter, (int) count);
                totalLetters += count;
            }

            // Load players
            List<Player> players = new ArrayList<>();
            JSONArray playersArray = (JSONArray) matchObject.get("players");
            for (Object playerObj : playersArray) {
                JSONObject playerObject = (JSONObject) playerObj;
                String playerId = (String) playerObject.get("id");
                String name = (String) playerObject.get("name");
                long score = (long) playerObject.get("score");
                String type = (String) playerObject.get("type");

                Player player;
                if (type.equals("Human")) {
                    Profile profile;
                    player = new Human(playerId, profile, );
                } else {
                    player = new IA(playerId, numb);
                }
                player.setName(name);
                player.setScore((int) score);

                // Load rack
                JSONArray rackArray = (JSONArray) playerObject.get("rack");
                Rack rack = new Rack();
                for (Object rackObj : rackArray) {
                    JSONObject tileObject = (JSONObject) rackObj;
                    String symbol = (String) tileObject.get("symbol");
                    long value = (long) tileObject.get("value");
                    rack.addTile(new Box(symbol, (int) value));
                }
                player.setRack(rack);

                players.add(player);
            }

            // Load board
            Board board = new Board((int) matchObject.get("size"));
            JSONArray boardArray = (JSONArray) matchObject.get("board");
            for (Object boardObj : boardArray) {
                JSONObject boxObject = (JSONObject) boardObj;
                int x = (int) (long) boxObject.get("x");
                int y = (int) (long) boxObject.get("y");
                String symbol = (String) boxObject.get("symbol");
                long value = (long) boxObject.get("value");

                Box box = new Box(x, y);
                box.setLetter(symbol, (int) value);
                board.placeBox(box);
            }

            Match match = new Match(id, dictionary, players, bag, board);
            match.setCurrentTurn((int) currentTurn);
            matches.put(id, match);
        }

        return matches;
    }


}