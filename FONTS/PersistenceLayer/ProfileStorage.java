package PersistenceLayer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

import DomainLayer.DomainClasses.*;


public class ProfileStorage implements Storage<Map<String, Profile>> {
    public void save(Map<String, Profile> profiles) {
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

        JsonUtils.save("/profiles.json", profilesArray);
    }

    public Map<String, Profile> load() {
        Map<String, Profile> profiles = new HashMap<>();
        JSONArray profilesArray = (JSONArray) JsonUtils.load( "/profiles.json");

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
