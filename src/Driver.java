public class Driver
{
    public static void main(String[] args)
    {
        Controller controller = Controller.getInstance();
        controller.createMatch("match1");
        controller.createPlayers(2, 2);
        controller.addPlayerToMatch("match1", new Human("player1"));
        controller.addPlayerToMatch("match1", new IA("player2"));
        controller.displayPlayers();
    }
}