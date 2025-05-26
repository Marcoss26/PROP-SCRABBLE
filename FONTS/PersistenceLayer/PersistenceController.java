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

    public static void save(String filePath, Object jsonObjectOrArray) {
        try (FileWriter file = new FileWriter(DATA_PATH + filePath)) {
            if (jsonObjectOrArray instanceof JSONObject) {
                file.write(((JSONObject) jsonObjectOrArray).toJSONString());
            } else if (jsonObjectOrArray instanceof JSONArray) {
                file.write(((JSONArray) jsonObjectOrArray).toJSONString());
            } else {
                throw new IllegalArgumentException("Input must be a JSONObject or JSONArray");
            }
            file.flush();
        } catch (IOException e) {
            System.err.println("Error saving JSON to file: " + e.getMessage());
        }
    }

    // Load a JSONArray from a file
    public static JSONArray load(String filePath) {
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
    private final ProfileStorage profileStorage = new ProfileStorage();
    private final MatchStorage matchStorage = new MatchStorage();


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
    }

    public Map<String, Profile> loadProfiles() {
         return profileStorage.load();
    }



    public void saveMatches(Map<String, Match> matches) {
        matchStorage.save(matches);
    }

    public Map<String, Match> loadMatches() {
        return matchStorage.load();
    }
}