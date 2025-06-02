package DomainLayer.DomainClasses;
import java.util.*;

/**
 * Player class represents a player in an certain match
 * It contains the player id, score, username, and game statistics.
 * @author Ziheng Zhang
 */

public abstract class Player
{
    protected String id;
    protected int score;
    protected String name;
    protected Rack rack;

    public Player()
    {
    }

    public Player(String id)
    {
        this.setID(id);
        this.setScore(0);
    }


    public void displayPlayer()
    {
        System.out.println("Player username: " + name + ", Score: " + score);
    }

    public abstract boolean isHuman();

    public void shuffleRack()
    {
        rack.shuffle();
    }

    public void modifyRack(String old_letters) throws IllegalArgumentException
    {
        rack.replaceLetters(old_letters);
    }


    public void setRack(Rack rack)
    {
        this.rack = rack;
    }

    public Rack getRack()
    {
        return rack;
    }

    public ArrayList<String> getInfoLetters()
    {
        return rack.getInfoLetters();
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

    public void addScore(int score)
    {
        this.score += score;
    }

    public int getScore()
    {
        return score;
    }

    public String getName()
    {
        return name;
    }
}