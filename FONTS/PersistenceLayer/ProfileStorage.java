import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.nio.file.*;
import java.util.*;


public class ProfileStorage 
{
    private static ProfileStorage instance = null;
    private final String DATA_PATH = "./data";
    private final String PROFILES_FILE = "profiles.json";

    private ProfileStorage() {
        // Ensure the data directory exists
        File dataDir = new File(DATA_PATH);
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
    }

    public static ProfileStorage getInstance() {
        if (instance == null) {
            instance = new ProfileStorage();
        }
        return instance;
    }

    public void saveProfile(Map<String, Profile> profiles) {
        JSONObject profilesObject = new JSONObject();

        for (Map.Entry<String, Profile> entry : profiles.entrySet()) {
            JSONObject profile = new JSONObject();
            profile.put("username", entry.getKey());
            profile.put("password", entry.getValue().getPassword());
            profile.put("isPublic", entry.getValue().isPublic());
            profile.put("salt", entry.getValue().getSalt());
            profile.put("score", entry.getValue().getScore());
            profile.put("wins", entry.getValue().getWins());
            profile.put("gamesPlayed", entry.getValue().getGamesPlayed());
            profile.put("dictionaryUsage", new JSONObject(entry.getValue().getDictionaryUsage()));
            profilesObject.put(entry.getKey(), profile);
        }

        saveProfiles(profilesObject);
    }

    public JSONObject loadProfiles() {
        File file = new File(DATA_PATH + "/" + PROFILES_FILE);
        if (!file.exists()) {
            return new JSONObject();
        }

        try {
            String content = new String(Files.readAllBytes(file.toPath()));
            return new JSONObject(content);
        } catch (IOException e) {
            e.printStackTrace();
            return new JSONObject();
        }
    }

    private void saveProfilesToJSON(JSONObject profilesObject) {
        try (FileWriter fileWriter = new FileWriter(DATA_PATH + "/" + PROFILES_FILE)) {
            fileWriter.write(profilesObject.toString());
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
