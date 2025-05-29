package PersistenceLayer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;

import DomainLayer.DomainClasses.*;

interface Storage<T> {
    void save(T data);
    T load();
}

class JsonUtils {
    // Save a JSONArray to a file
    private static final String DATA_PATH = "./data";
    public static void initialize() {
        File dataDir = new File(DATA_PATH);
        if (!dataDir.exists()) {
            dataDir.mkdirs(); // Create the directory if it doesn't exist
        }
    }

    public static void save(String filePath, Object jsonObject) {
        try (FileWriter file = new FileWriter(DATA_PATH + filePath)) {
            if (jsonObject instanceof JSONObject) {
                file.write(((JSONObject) jsonObject).toJSONString());
            } else if (jsonObject instanceof JSONArray) {
                file.write(((JSONArray) jsonObject).toJSONString());
            } else {
                throw new IllegalArgumentException("Input must be a JSONObject or JSONArray");
            }
            file.flush();
        } catch (IOException e) {
            System.err.println("Error saving JSON to file: " + e.getMessage());
        }
    }

    // Load a JSONArray from a file
    public static Object load(String filePath) {
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader(filePath)) {
            return (Object) parser.parse(reader);
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
    private final ProfileStorage profileStorage = new ProfileStorage();
    private final MatchStorage matchStorage = new MatchStorage();
    private final Map<String, Object> cache = new HashMap<>();

    private PersistenceController() {
        // Ensure the data directory exists
        JsonUtils.initialize();
    }

    public static PersistenceController getInstance() {
        if (instance == null) {
            instance = new PersistenceController();
        }
        return instance;
    }



    public void saveProfiles(Map<String, Profile> profiles) {
        profileStorage.save(profiles);
        cache.put("profiles", profiles); // Cache the saved profiles
    }

    public Map<String, Profile> loadProfiles() {
        if (cache.containsKey("profiles")) {
            return (Map<String, Profile>) cache.get("profiles");
        } else {
            Map<String, Profile> profiles = profileStorage.load();
            cache.put("profiles", profiles); // Cache the loaded profiles
            return profiles;
        }
    }



    public void saveMatches(Map<String, Match> matches) {
        matchStorage.save(matches);
        cache.put("matches", matches); // Cache the saved matches
    }

    public Map<String, Match> loadMatches() {
        if (cache.containsKey("matches")) {
            return (Map<String, Match>) cache.get("matches");
        } else {
            Map<String, Match> matches = matchStorage.load();
            cache.put("matches", matches); // Cache the loaded matches
            return matches;
        }
    }
}