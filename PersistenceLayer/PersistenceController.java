package PersistenceLayer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;
import java.util.Base64;


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
    public void saveMatchProfiles(Map<String, Profile> profiles) {
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

    public Map<String, Profile> loadMatches() {
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



}