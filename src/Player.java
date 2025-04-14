import java.util.*;

public abstract class Player
{
    protected String id;
    protected int score;

    public Player()
    {}

    public Player(String id)
    {
        this.id = id;
        this.score = 0;
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
}