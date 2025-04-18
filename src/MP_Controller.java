import java.util.*;
public class MP_Controller
{
    private static MP_Controller c;
    private Map<String, Match> matches = new HashMap<>();
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

    public void createMatch(String id, set<Profile> profiles)
    {
        Match match = new Match(id);
        matches.put(id, match);
        createPlayersForMatch(match,profiles);
        System.out.println("Match created with ID: " + match.getId());
    }

    public void createPlayersForMatch(Match match, set<Profile> profiles)
    {
        int match_size = match.getSize();
        int profile_size = profiles.size();
        String match_id = match.getId();
        for (int i = 0; i < profile_size; i++)
        {
            Profile profile = profiles.get(i); //Get the profile from the set
            String human_id = profiles.get(i).getID(); //Get the ID of the profile
            Player player = new Human(human_id+match_id,profile,match);    //Creating a new human player with this profile
            match.setPlayer(player);  //Adding the human player to the match
            player.setMatch(match);   //Setting the match for the player
        }
        for (int i = 0; i < match_size - profile_size; i++)
        {
            bot_id = match_id+"BOT"+i; //Creating a bot ID
            Player player = new IA(bot_id,match,i); //Creating a new AI player
            match.setPlayer(player);    //Adding the AI player to the match
            player.setMatch(match);  //Setting the match for the player
        }
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

    public void leaveMatch(String match_id, String p_id)
    {
        if(existMatch(match_id))
        {
            players.remove(p_id);
            MatchPlayer.get(match_id).remove(p_id);
            System.out.println("Player with ID: " + p_id + " left match with ID: " + match_id);
        }
        else
        {
            System.out.println("Match with ID: " + match_id + " does not exist.");
        }
    }

    public void displayPlayers(String match_id)
    {
        if(existsMatch(match_id))
        {
            match.displayPlayers();
        }
        else
        {
            System.out.println("Match with ID: " + match_id + " does not exist.");
        }
    }

    public void displayMatch(String id)
    {
        if(existMatch(id))
        {
            Match match = matches.get(id);
            match.displayMatch();
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
                Map<String, Player> players = match.getPlayers();
                while(!finished)
                {
                    for(String playerId : players.keySet())
                    {
                        Player player = players.get(playerId);
                        if (player.isHuman())
                        {
                            // Human player's turn
                            System.out.println("It's your turn, " + player.getName() + ". Enter your move:");
                            // Get move from human player (e.g., through console input)
                            // For example: String move = scanner.nextLine();
                            // player.makeMove(move);
                        }
                        else
                        {
                            // AI player's turn
                            System.out.println("AI player's turn: " + player.getName());
                            // AI makes a move automatically
                            // player.makeMove(aiMove);
                        }
                    }
                    System.out.println("Match with ID: " + id + " is in progress.");
                    
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