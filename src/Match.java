import java.util.*;

public class Match
{
    private String id;
    private int turn; // 0 for player1, 1 for player2
    private int score;
    private boolean finished;

    public Match(String id) 
    {
        this.id = id;
        this.turn = 0; // Start with player1's turn
        this.finished = false;
    }
    public String getId() 
    {
        return id;
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
}
