package DomainLayer.DomainClasses;
public class Human extends Player
{
    private Profile profile;
    public Human()
    {
        super();
    }

    public Human(String id,Profile profile)
    {
        super(id); //Call the constructor of the Player class
        this.profile = profile; //Set the profile of the player
        this.name = profile.getUsername(); //Set the name of the player to the username of the profile
    }

    public Profile getProfile()
    {
        return profile; //Return the profile of the player
    }


    public void printRack()
    {
        this.rack.print(); //Call the printRack method from the Rack class
    }

    public boolean isHuman()
    {
        return true;
    }


    public void addScoreToProfile(int score)
    {
        profile.addScore(score); //Add the score to the profile
    }
}