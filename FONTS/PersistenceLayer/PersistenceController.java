package PersistenceLayer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;

import DomainLayer.DomainClasses.*;

interface Storage {
    void save();
    void load();
}

class JsonUtils implements Storage {
    // Save a JSONArray to a file
    private static final String DATA_PATH = "./data";

    public static void save(String filePath, JSONArray jsonArray) {
        try (FileWriter file = new FileWriter(DATA_PATH + filePath)) {
            file.write(jsonArray.toJSONString());
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
       
    }

    public Map<String, Profile> loadProfiles() {
      
    }





    /*
     * ---------------------------------------------------------------------
                            MATCH FUNCTIONALITY
     ------------------------------------------------------------------------
     */
    public void saveMatches(Map<String, Match> matches) {
        
    }

    public Map<String, Match> loadMatches() {
       
    }


}