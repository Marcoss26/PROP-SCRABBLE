package DomainLayer.DomainClasses;
import java.util.*;
import java.io.*;
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


    public void print(String id)
    {
        Match match = matches.get(id);
        match.printBoard();
        List<Player> players = match.getListPlayers();
        players.get(match.getTurn()).printRack();

    }

    public void printCrossChecks(int column, int row, String id)
    {
        Match match = matches.get(id);
        Board board = match.getBoard();
        Box square = board.getBox(row,column);
        Set<String> vertical = square.getCrossCheck(0);
        Set<String> horizontal = square.getCrossCheck(1);
        System.out.println("Vertical cross check: ");
        for (String word : vertical)
        {
            System.out.println(word);
        }
        System.out.println("Horizontal cross check: ");
        for (String word : horizontal)
        {
            System.out.println(word);
        }
    }

    public String createMatch(int size, Set<Profile> profiles, Dictionary dictionary,int board_size, Map<Letter,Integer> letters, int bag_size) throws IOException, IllegalArgumentException
    {
            String id = UUID.randomUUID().toString(); //Generating a random ID for the match
            while(matches.containsKey(id))  
            {
                id = UUID.randomUUID().toString(); //Generating a new ID for the match
            }
            Match match = new Match(id,size);
            matches.put(id, match);
            createBagForMatch(match,letters,bag_size);
            createPlayersForMatch(match,profiles,dictionary.getLanguage());
            createDictionaryForMatch(match,dictionary);
            createBoardForMatch(match,board_size);
            match.setPaused(false);
            System.out.println("Match created with ID: " + match.getId());
            return id;
    }

    private void createBoardForMatch(Match match, int size)
    {
        match.setBoard(new Board(size)); //Creating a new board with the size given by the user
    }

    private void createBagForMatch(Match match, Map<Letter,Integer> letters, int bag_size) throws IOException
    {
        match.setBag(new Bag(letters, bag_size));
    }

    private void createDictionaryForMatch(Match match, Dictionary dictionary)
    {
        match.setDictionary(dictionary);
    }

    private void createPlayersForMatch(Match match, Set<Profile> profiles, String language)
    {
        int match_size = match.getSize();
        int profile_size = profiles.size();
        String match_id = match.getId();
        int j = 0;
        for (Profile profile: profiles)
        {
            /*List<Letter> letters = new ArrayList<>();
            letters.add(new Letter("P",3)); //Adding the joker to the rack
            letters.add(new Letter("A",1));
            letters.add(new Letter("M",2));
            letters.add(new Letter("T",1));
            if(j == 0)  letters.add(new Letter("I",1));
            else    letters.add(new Letter("E",2));
            letters.add(new Letter("R",1));
            letters.add(new Letter("#",0));*/
            String human_id = profile.getUsername(); //Get the ID of the profile
            Player player = new Human(human_id+match_id,profile,language);    //Creating a new human player with this profile
            player.setRack(new Rack(match.getBag())); //Creating a new rack for the player
            match.setPlayer(player);  //Adding the human player to the match
            j++;
        }
        for (int i = 0; i < match_size - profile_size; i++)
        {
            String bot_id = match_id+"BOT"+i; //Creating a bot ID
            Player player = new IA(bot_id, "IA" + i); //Creating a new AI player
            player.setRack(new Rack(match.getBag())); //Creating a new rack for the AI player
            match.setPlayer(player);    //Adding the AI player to the match
        }
    }

    public boolean existMatch(String id)
    {
        return matches.containsKey(id);
    }

    public Match getMatch(String id) throws IllegalArgumentException
    {
        if (existMatch(id))
        {
            return matches.get(id);
        }
        else
        {
            throw new IllegalArgumentException("Match with ID: " + id + " does not exist.");
        }
    }

    public void displayPlayers(String match_id) throws IllegalArgumentException
    {
        if(existMatch(match_id))
        {
            Match match = matches.get(match_id);
            match.displayPlayers();
        }
        else
        {
            throw new IllegalArgumentException("Match with ID: " + match_id + " does not exist.");
        }
    }

    public void displayMatch(String id) throws IllegalArgumentException
    {
        if(existMatch(id))
        {
            Match match = matches.get(id);
            match.displayMatch();
        }
        else
        {
            throw new IllegalArgumentException("Match with ID: " + id + " does not exist.");
        }
    }

    
    
    public void startMatch(String id) throws IllegalArgumentException
    {
        if (existMatch(id))
        {
            Match match = matches.get(id);
            match.startMatch();
        }
        else
        {
            throw new IllegalArgumentException("Match with ID: " + id + " does not exist.");
        }
    }




    public boolean playsMatch(String id ,String word, int startX, int startY, int endX, int endY) throws IllegalArgumentException, IllegalStateException
    {
        boolean valid = false;
        if (existMatch(id))
        {
            Match match = matches.get(id);
            if(!match.isPaused())   valid = match.playsMatch(word, startX, startY, endX, endY);
            else
            {
                throw new IllegalStateException("Match with ID: " + id + " is paused.");
            }
        }
        else
        {
            throw new IllegalArgumentException("Match with ID: " + id + " does not exist.");
        }
        return valid;
    }

    public void modifyRack(String id, String old_letters)
    {
        if(existMatch(id))
        {
            Match match = matches.get(id);
            match.modifyRack(old_letters);
        }
        else
        {
            throw new IllegalArgumentException("Match with ID: " + id + " does not exist.");
        }
    }

    public void shuffleRack(String id)
    {
        if(existMatch(id))
        {
            Match match = matches.get(id);
        }
        else
        {
            throw new IllegalArgumentException("Match with ID: " + id + " does not exist.");
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

    public void pauseMatch(String id) throws IllegalArgumentException
    {
        if (existMatch(id))
        {
            Match match = matches.get(id);
            match.setPaused(true);
            System.out.println("Match with ID: " + id + " paused.");
        }
        else
        {
            throw new IllegalArgumentException("Match with ID: " + id + " does not exist.");
        }
    }

    public String finishMatch(String id)  throws IllegalArgumentException
    {
        if (existMatch(id))
        {
            Match match = matches.get(id);
            String winner = match.setFinished();
            matches.remove(id);
            System.out.println("Match with ID: " + id + " finished.");
            return winner;
        }
        else
        {
            throw new IllegalArgumentException("Match with ID: " + id + " does not exist.");
        }
    }

    public Map<String,Match> getUnfinishedMatches()
    {
        return matches;
    }

    public void continueMatch(String id) throws IllegalArgumentException
    {
        if (existMatch(id))
        {
            Match match = matches.get(id);
            match.setPaused(false);
            System.out.println("Match with ID: " + id + " continued.");
        }
        else
        {
            throw new IllegalArgumentException("Match with ID: " + id + " does not exist.");
        }
    }

    public Player whoseTurn(String id) throws IllegalArgumentException
    {
        if (existMatch(id))
        {
            Match match = matches.get(id);
            List<Player> list_players = match.getListPlayers();
            int turn = match.getTurn();
            return list_players.get(turn);
        }
        else
        {
            throw new IllegalArgumentException("Match with ID: " + id + " does not exist.");
        }
    }
}