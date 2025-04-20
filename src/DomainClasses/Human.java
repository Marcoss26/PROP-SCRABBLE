public class Human extends Player
{
    private Profile profile;
    public Human()
    {
        super();
    }

    public Human(String id,Profile profile,Match match)
    {
        super(id,match); //Call the constructor of the Player class
        this.profile = profile; //Set the profile of the player
        this.name = profile.getUsername(); //Set the name of the player to the username of the profile
        this.profile.incrementGamePlayed(); //Increment the number of games played in the profile
    }

    public void addScore(int score)
    {
        this.score += score; //Add the score to the player's score
        this.profile.addScore(score); //Add the score to the profile's score
    }
    
    public boolean isHuman()
    {
        return true;
    }
}