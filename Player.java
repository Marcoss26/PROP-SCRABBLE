import java.util.*;

public class Player
{
    protected String id;
    protected int score;
    private Set<String> matchIDs;
    public Player(int id)
    {
        //CheckID(id);
        //setID(id);
        setScore(0);
    }

    public void addMatch(String matchID)
    {
        matchIDs.add(matchID);
    }

    public void leaveMatch(String matchID)
    {
        matchIDs.remove(matchID);
    }

    public void saveMatch(String matchID)
    {
        //Aqui haria algo tipo save(matchIDs) para guardar los matchIDs en algun sitio.
    }

    public void setID(String id)
    {
        this.id = id;
    }

    public String getID()
    {
        return id;
    }

    public void placeLetter(char l) //De momento void, si hay que devolver cosas se cambia
    {
        //Aqui haria algo tipo board.place(l) para poner la letra en el tablero.
    }
    public void refreshRack()
    {
        //Aqui haria rack.refresh();
    }
    public int getscore()
    {
        return score;
    }
    public void setScore(int score)
    {
        this.score = score;
    }
}