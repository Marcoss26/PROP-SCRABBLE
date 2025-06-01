package PersistenceLayer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;

import DomainLayer.DomainClasses.*;

/**
 * Storage is a generic interface for saving and loading data.
 * It defines methods for saving and loading data of type T.
 * @param <T> The type of data to be saved and loaded.
 * @author Kai Knox
 */
interface Storage<T> {
    /**
     * Saves the data of type T to a persistent storage.
     * @param data The data to be saved.
     */
    void save(T data);

    /**
     * Loads the data of type T from a persistent storage.
     * @return The loaded data of type T.
     */
    T load();
}

/**
 * JsonUtils is a utility class for saving and loading JSON data to and from files.
 * It provides methods to initialize the data directory, save JSON objects, and load JSON objects.
 * @author Kai Knox
 */
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
        try (FileReader reader = new FileReader(DATA_PATH + filePath)) {
            return (Object) parser.parse(reader);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + DATA_PATH + filePath);
        } catch (IOException | ParseException e) {
            System.err.println("Error loading JSON from file: " + e.getMessage());
        }
        return new JSONArray(); // Return an empty array if the file doesn't exist or an error occurs
    }
}


/**
 * PersistenceController is a singleton class that manages the persistence of profiles and matches.
 * It uses ProfileStorage and MatchStorage to save and load data from JSON files.
 * It also caches the loaded data to avoid unnecessary file I/O operations.
 * @author Kai Knox
 */
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