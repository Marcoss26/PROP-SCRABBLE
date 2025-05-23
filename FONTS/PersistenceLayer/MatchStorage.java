package PersistenceLayer;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.nio.file.*;
import java.util.*;


public class MatchStorage 
{
    public final String MATCHES_FILE = "matches.json";
    private final String DATA_PATH = "./data";
    private static MatchStorage instance = null;
    private MatchStorage() 
    {
        // Ensure the data directory exists
        File dataDir = new File(DATA_PATH);
        if (!dataDir.exists()) 
        {
            dataDir.mkdirs();
        }
    }
    public static MatchStorage getInstance()
    {
        if (instance == null) {
            instance = new MatchStorage();
        }
        return instance;
    }

    public void saveMatch()
    
}
