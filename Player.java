import java.util.*;

public class Player
{
    protected int id;
    private String password;

    public Player(int id,String password)
    {
        //CheckID(id);
        setID(id);
        setPassword(password);
    }

    public void setID(int id)
    {
        this.id = id;
    }
    private void setPassword(int password)
    {
        this.password = password;
    }

    public void getID()
    {
        return id;
    }

    public void changePassword(String new_pw)
    {
        if(password.equals(new_pw))
    }
}