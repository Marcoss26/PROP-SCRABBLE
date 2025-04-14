import java.util.*;
public class MP_Controller
{
    private static MP_Controller c;
    private Map<String, Match> matches = new HashMap<>();
    private Map<String,Set<Player>> MatchPlayers = new HashMap<>();
    private Map<String,Player> players = new HashMap<>();
    private MP_Controller()
    {

    }
    public static MP_Controller getInstance()
    {
        if (c == null)
        {
            c = new MP_Controller();
        }
        return c;
    }
    public void createMatch(String id, int human, int ai)
    {
        Match match = new Match(id);
        matches.put(id, match);
        MatchPlayers.put(id, new HashSet<>());
        MatchPlayers.get(id).addAll(createPlayers(id, human, ai));
        System.out.println("Match created with ID: " + match.getId());
    }

    public void deleteMatch(String id)
    {
        if (existMatch(id))
        {
            Set<Player> players_to_delete = MatchPlayers.get(id);
            for (Player player : players_to_delete)
            {
                players.remove(player.getID());
            }
            matches.remove(id);
            MatchPlayers.remove(id);
            System.out.println("Match with ID: " + id + " deleted.");
        }
        else
        {
            System.out.println("Match with ID: " + id + " does not exist.");
        }
    }
    public boolean existMatch(String id)
    {
        return matches.containsKey(id);
    }

    public Set<Player> createPlayers(String id, int num_human, int num_ai)
    {
        Set<Player> created_p = new HashSet<>();
        for (int i = 0; i < num_human; i++)
        {
            Player player = new Human();
            players.put("HumanID", player);
            created_p.add(player);
        }
        for (int i = 0; i < num_ai; i++)
        {
            Player player = new IA();
            players.put("BotID", player);
            created_p.add(player);
        }
        return created_p;
    }
    public void addPlayerToMatch(String id, Player player)
    {
        if (matches.containsKey(id))
        {
            MatchPlayers.get(id).add(player);
        }
        else
        {
            //Excepciones a implementar
        }
    }
    public void displayPlayers()
    {
        for (Map.Entry<String, Player> entry : players.entrySet())
        {
            String key = entry.getKey();
            Player value = entry.getValue();
            System.out.println("ID: " + key + ", Score: " + value.getScore());
        }
    }
    public void displayMatch(String id)
    {
        if(existMatch(id))
        {
            Match match = matches.get(id);
            System.out.println("Match ID: " + match.getId());
            System.out.println("Turn: " + match.getTurn());
            System.out.println("Score: " + match.getScore());
            System.out.println("Finished: " + match.isFinished());
        }
        else
        {
            System.out.println("Match with ID: " + id + " does not exist.");
        }
    }
    public void startMatch(String id)
    {
        if (existMatch(id))
        {
            Match match = matches.get(id);
            match.startMatch();
            System.out.println("Match with ID: " + id + " started.");
        }
        else
        {
            System.out.println("Match with ID: " + id + " does not exist.");
        }
    }
    public void playsMatch(String id)
    {
        if (existMatch(id))
        {
            Match match = matches.get(id);
            finished = match.isFinished();
            if (match.isStarted())
            {
                while(!finished)
                {
                    System.out.println("Match with ID: " + id + " is in progress.");
                    // Simulate game logic here
                    // For example, update score, change turn, etc.
                    match.setTurn((match.getTurn() + 1) % 4); // Change turn between player1 and player2
                    match.endMatch();
                }
            }
            else
            {
                System.out.println("Match with ID: " + id + " has not started yet.");
            }
        }
        else
        {
            System.out.println("Match with ID: " + id + " does not exist.");
        }
    }
    public Vector<String> getMatchIDs()
    {
        Vector<String> matchIDs = new Vector<>();
        for (String id : matches.keySet())
        {
            matchIDs.add(id);
        }
        return matchIDs;
    }
    public Vector<String> getPlayerIDs()
    {
        Vector<String> playerIDs = new Vector<>();
        for (String id : players.keySet())
        {
            playerIDs.add(id);
        }
        return playerIDs;
    }
    public void endMatch(String id)
    {
        if (existMatch(id))
        {
            Match match = matches.get(id);
            match.setFinished(true);
            System.out.println("Match with ID: " + id + " finished.");
        }
        else
        {
            System.out.println("Match with ID: " + id + " does not exist.");
        }
    }
    public void addScore(String id_player, int score)
    {
        if (players.containsKey(id_player))
        {
            Player player = players.get(id_player);
            player.addScore(score);
            System.out.println("Score added to player with ID: " + id_player + ". New score: " + player.getScore());
        }
        else
        {
            System.out.println("Player with ID: " + id_player + " does not exist.");
        }
    }
}