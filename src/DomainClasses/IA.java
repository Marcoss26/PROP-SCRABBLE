public class IA extends Player
{
    public IA()
    {
        super();
    }
    public IA(String id,Match match, int i)
    {
        super(id,match);
        this.name = "IA" + i; //Set the name of the player to IA + i
    }

    public void addScore(int score)
    {
        this.score += score; //Add the score to the player's score
    }
    
    public boolean isHuman()
    {
        return false;
    }
}