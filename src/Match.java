import java.util.*;

public class Match
{
    private String id;
    private int turn; // 0 for player1, 1 for player2
    private int score;
    private boolean finished = false;
    private boolean paused = true;
    private int size;
    private Map<String,Player> players = new LinkedHashMap<>();
    private Map<String,Rack> racks = new HashMap<>();
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

    public getBag() 
    {
        return bag;
    }

    public getBoard() 
    {
        return board;
    }

    public getDictionary() 
    {
        return dictionary;
    }

    public setBag(Bag bag) 
    {
        this.bag = bag;
    }

    public setBoard(Board board) 
    {
        this.board = board;
    }

    public getPlayers() 
    {
        return players;
    }

    public setDictionary(Dictionary dictionary) 
    {
        this.dictionary = dictionary;
    }

    public setSize(int size) 
    {
        this.size = size;
    }

    public void setPlayer(Player player)
    {
        players.put(player.getID(), player);
    }

    public player getPlayer(String id)
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
        System.out.println("Started: " + started);
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
        this.turn = turn;
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

    public boolean isFinished() 
    {
        return finished;
    }

    public void setFinished(boolean finished) 
    {
        this.finished = finished;
    }
}
