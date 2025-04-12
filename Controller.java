public class Controller
{
    private Static Controller c;
    private Static int MatchId = 0;
    private Map<Integer, Match> matches = new HashMap<>();
    private Map<Integer,set<Player>> MatchPlayers = new HashMap<>();
    private Map<String,Player> players = new HashMap<>();
    private Controller()
    {

    }
    public static Controller getInstance()
    {
        if (c == null)
        {
            c = new Controller();
        }
        return c;
    }
    public void createMatch()
    {
        Match match = new Match(MatchId);
        matches.put(MatchId, match);
        MatchId++;
        MatchPlayers.put(id, new HashSet<>());
    }

    public void createPlayers(int num_human, int num_ai))
    {
        for (int i = 0; i < num_human; i++)
        {
            Player player = new Human(i);
            players.put(player.getID(), player);
        }
        for (int i = 0; i < num_ai; i++)
        {
            Player player = new AI(i);
            players.put(player.getID(), player);
        }
    }
    public addPlayerToMatch(String id, Player player)
    {
        if (matches.containsKey(id))
        {
            players.get(id).add(player);
        }
        else
        {
            //Excepciones a implementar
        }
    }
}