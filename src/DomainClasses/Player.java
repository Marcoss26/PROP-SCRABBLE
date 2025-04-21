package DomainClasses;
import java.util.*;

public abstract class Player
{
    protected String id;
    protected int score;
    protected String name;
    protected Match match;
    protected Rack rack;

    public Player()
    {
    }

    public Player(String id,Match match)
    {
        this.setID(id);
        this.setScore(0);
        //this.setMatch(match);
    }


    public void displayPlayer()
    {
        System.out.println("Player username: " + name + ", Score: " + score);
    }

    public abstract boolean isHuman();

    
    public void setMatch(Match match)
    {
        this.match = match;
    }

    public void setRack(Rack rack)
    {
        this.rack = rack;
    }

    public Rack getRack()
    {
        return rack;
    }
    
    public void printRack()
    {
        rack.print();
    }

    public void setID(String id)
    {
        this.id = id;
    }

    public String getID()
    {
        return id;
    }
    public void setScore(int score)
    {
        this.score = score;
    }

    public abstract void addScore(int score);

    public int getScore()
    {
        return score;
    }
}