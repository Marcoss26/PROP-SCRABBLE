public class Human extends Player
{
    private Profile profile;
    public Human()
    {
        super();
    }
    public Human(String id,Profile profile)
    {
        super(id);
        this.profile = profile;
        this.name = profile.getUsername(); //Set the name of the player to the username of the profile
    }
    public boolean isHuman()
    {
        return true;
    }
}