package DomainClasses;
import java.util.*;

public class Match
{
    private String id;
    private int turn = 0; // 0 for player1, 1 for player2
    private int score;
    private boolean finished = false;
    private boolean paused = true;
    private int size;
    private Map<String,Player> players = new LinkedHashMap<>();
    private List<Player> playerList = new ArrayList<>(); //Access by index without looping through all players
    private Dictionary dictionary;
    private Bag bag;
    private Board board;

    public Match(String id) 
    {
        this.turn = 0; // Start with player1's turn
    }

    public Match(String id, int size) 
    {
        this.setId(id);
        this.setTurn(0);
        this.setSize(size);
    }

    public void printBoard()
    {
        this.board.printBoard(); // Call the printBoard method from the Board class
    }

    public Bag getBag() 
    {
        return bag;
    }

    public Board getBoard() 
    {
        return board;
    }

    public Dictionary getDictionary() 
    {
        return dictionary;
    }

    public void setBag(Bag bag) 
    {
        this.bag = bag;
    }

    public void setBoard(Board board) 
    {
        this.board = board;
    }

    public Map<String, Player> getPlayers() 
    {
        return players;
    }

    public void setDictionary(Dictionary dictionary) 
    {
        this.dictionary = dictionary;
    }

    public void setSize(int size) 
    {
        this.size = size;
    }

    public int getSize() 
    {
        return size;
    }

    public void setPlayer(Player player)
    {
        players.put(player.getID(), player);
        playerList.add(player); // Add the player to the list for easy access by index
    }

    public List<Player> getListPlayers()
    {
        return playerList;
    }

    public Player getPlayer(String id)
    {
        return players.get(id);
    }

    public void displayPlayers()
    {
        for (Map.Entry<String, Player> entry : players.entrySet()) 
        {
            entry.getValue().displayPlayer(); 
        }
    }

    public String decideWinner()
    {
        int maxScore = 0;
        String winnerId = null;
        for (Map.Entry<String, Player> entry : players.entrySet()) 
        {
            String key = entry.getKey();
            Player value = entry.getValue();
            if (value.getScore() > maxScore)
            {
                maxScore = value.getScore();
                winnerId = key;
            }
        }
        return winnerId;
    }

    public void displayMatch()
    {
        System.out.println("Match ID: " + id);
        System.out.println("Turn: " + turn);
        System.out.println("Score: " + score);
        System.out.println("Finished: " + finished);
        System.out.println("Paused: " + paused);
        System.out.println("Size: " + size);
        displayPlayers();
    }


    public String getId() 
    {
        return id;
    }
    public void setId(String id) 
    {
        this.id = id;
    }
    public int getTurn() 
    {
        return turn;
    }

    public void setTurn(int turn) 
    {
        this.turn = turn%size;
    }

    public int getScore() 
    {
        return score;
    }

    public void startMatch() 
    {
        this.paused = false;
    }
    
    public boolean isPaused() 
    {
        return paused;
    }

    public void setPaused(boolean paused)
    {
        this.paused = paused;
    }

    public boolean isFinished() 
    {
        return finished;
    }

    public void setFinished(boolean finished) 
    {
        this.finished = finished;
    }
}
