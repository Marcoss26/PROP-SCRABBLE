import java.util.*;

public abstract class Player
{
    protected String id;
    protected int score;
    protected String name;
    protected Match match;
    private Rack rack;

    public Player()
    {
    }

    public Player(String id,Match match)
    {
        this.setID(id);
        this.setScore(0);
        this.setMatch(match);
    }

    public void makeMove();

    public void displayPlayer()
    {
        System.out.println("Player username: " + name + ", Score: " + score);
    }

    public boolean isHuman()
    {}

    
    public void setMatch(Match match)
    {
        this.match = match;
    }

    public void setRack(Rack rack)
    {
        this.rack = rack;
    }
    
    public void setID(String id)
    {
        this.id = id;
    }

    public char getDecision()
    {
        scanner = new Scanner(System.in);
        System.out.println("r to refresh rack, 1-7 to play a tile, p to pass");
        char decision = scanner.next();
        return decision;
    }

    public String getID()
    {
        return id;
    }
    public void setScore(int score)
    {
        this.score = score;
    }

    public void addScore(int score)
    {
    }

    public int getScore()
    {
        return score;
    }

    public void makeMove()
    {}
}