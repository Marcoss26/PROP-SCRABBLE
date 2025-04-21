import java.util.*;
public class MP_Controller
{
    private static MP_Controller c;
    private Map<String, Match> matches = new HashMap<>();
    private MP_Controller()
    {

    }

    public static MP_Controller getInstance()
    {
        if (c == null)
        {
            c = new MP_Controller();
        }
        return c;
    }

    public void createMatch(String id, Set<Profile> profiles)
    {
        Match match = new Match(id);
        matches.put(id, match);
        createPlayersForMatch(match,profiles);
        System.out.println("Match created with ID: " + match.getId());
    }

    private void createPlayersForMatch(Match match, Set<Profile> profiles)
    {
        int match_size = match.getSize();
        int profile_size = profiles.size();
        String match_id = match.getId();
        for (Profile profile: profiles)
        {
            String human_id = profile.getUsername(); //Get the ID of the profile
            Player player = new Human(human_id+match_id,profile,match);    //Creating a new human player with this profile
            match.setPlayer(player);  //Adding the human player to the match
            player.setMatch(match);   //Setting the match for the player
        }
        for (int i = 0; i < match_size - profile_size; i++)
        {
            String bot_id = match_id+"BOT"+i; //Creating a bot ID
            Player player = new IA(bot_id,match,i); //Creating a new AI player
            match.setPlayer(player);    //Adding the AI player to the match
            player.setMatch(match);  //Setting the match for the player
        }
    }

    public void deleteMatch(String id)
    {
        if (existMatch(id))
        {
            Set<Player> players_to_delete = MatchPlayers.get(id);
            for (Player player : players_to_delete)
            {
                players.remove(player.getID());
            }
            matches.remove(id);
            MatchPlayers.remove(id);
            System.out.println("Match with ID: " + id + " deleted.");
        }
        else
        {
            System.out.println("Match with ID: " + id + " does not exist.");
        }
    }
    public boolean existMatch(String id)
    {
        return matches.containsKey(id);
    }

    public void leaveMatch(String match_id, String p_id)
    {
        if(existMatch(match_id))
        {
            players.remove(p_id);
            MatchPlayer.get(match_id).remove(p_id);
            System.out.println("Player with ID: " + p_id + " left match with ID: " + match_id);
        }
        else
        {
            System.out.println("Match with ID: " + match_id + " does not exist.");
        }
    }

    public void displayPlayers(String match_id)
    {
        if(existsMatch(match_id))
        {
            match.displayPlayers();
        }
        else
        {
            System.out.println("Match with ID: " + match_id + " does not exist.");
        }
    }

    public void displayMatch(String id)
    {
        if(existMatch(id))
        {
            Match match = matches.get(id);
            match.displayMatch();
        }
        else
        {
            System.out.println("Match with ID: " + id + " does not exist.");
        }
    }
    
    public void startMatch(String id)
    {
        if (existMatch(id))
        {
            Match match = matches.get(id);
            match.startMatch();
            System.out.println("Match with ID: " + id + " started.");
        }
        else
        {
            System.out.println("Match with ID: " + id + " does not exist.");
        }
    }



    public String playsMatch(String id)
    {
        if (existMatch(id))
        {
            Match match = matches.get(id);
            if (!match.isPaused())
            {
                Board board = match.getBoard();
                List<Player> list_players = match.getListPlayers();
                int turn = match.getTurn();
                Player player = list_players.get(turn);
                match.printBoard();
                Scanner scanner = new Scanner(System.in);
                if(player.isHuman())
                {
                    Set<Letter> letters_to_add = new HashSet<>();
                    player.printRack();
                    boolean valid = false;
                    
                    System.out.println("Options:");
                    System.out.println("1. Add letter to board");
                    System.out.println("2. Delete letter");
                    System.out.println("3. Confirm play");
                    System.out.println("4. Refresh rack");
                    while(!valid)
                    {
                        System.out.println("Enter your option: ");
                        int option = scanner.nextInt();
                        switch(option)
                            case 1:
                                System.out.println("Choose a letter:");
                                Letter letter;
                                String decision = scanner.nextLine();   //Decides which letter from the rack to add
                                if((letter = player.getRack().getLetter(decision)) == null)
                                {
                                    throw new IllegalArgumentException("Invalid letter. Please enter a number between 1 and " + player.getRack().getSize() + ".");
                                }
                                {
                                    throw new IllegalArgumentException("Invalid letter number. Please enter a number between 1 and 7.");
                                }
                                System.out.println("Where do you want to add the letter?");
                                int x = scanner.nextInt(); //X coordinate
                                int y = scanner.nextInt(); //Y coordinate
                                if(board.hasLetter(x,y))
                                {
                                    throw new IllegalArgumentException("There is already a letter in that position. Please choose another position.");
                                }
                                if(!(x >= 0 && x < board.getSize() && y >= 0 && y < board.getSize()))
                                {
                                    throw new IllegalArgumentException("Invalid coordinates. Please enter coordinates between 0 and " + (board.getSize()-1) + ".");
                                }
                                board.addLetter(x,y,letter);
                                break;
                            case 2:
                                System.out.println("Give me the coordinates of the letter:");
                                int x1 = scanner.nextInt(); //X coordinate
                                int y1 = scanner.nextInt(); //Y coordinate
                                if(!(board.getBox(x1,y1).isUnchangeable()))
                                {
                                    throw new IllegalArgumentException("The letter in that position cannot be deleted. Please choose another position.");
                                }
                                board.getBox(x1,y1).setLetter(null,0); //Delete the letter from the board
                                System.out.println("Letter deleted.");
                                

                            case 3:
                                System.out.println("Confirming play...");
                                int quantity = 7 - player.getRack().getSize();
                                List<Letter> letters = match.getBag().extractSetOfLetters(quantity);
                                player.getRack().addLetters(letters);
                                break;
                            case 4:
                                System.out.println("Refreshing rack...");
                                player.refreshRack();
                                break;
                    }
                }
                
                else
                {

                }
                
                //caso 1: decison: Coge letra 1, 3 y ponlos en las casillas 4,5
                match.setTurn(turn + 1);
            }
            else
            {
                throw new IllegalStateException("Match with ID: " + id + " has not started yet.");
            }
        }
        else
        {
            System.out.println("Match with ID: " + id + " does not exist.");
        }
    }
    public Vector<String> getMatchIDs()
    {
        Vector<String> matchIDs = new Vector<>();
        for (String id : matches.keySet())
        {
            matchIDs.add(id);
        }
        return matchIDs;
    }

    public void pauseMatch(String id)
    {
        if (existMatch(id))
        {
            Match match = matches.get(id);
            match.setPaused(true);
            System.out.println("Match with ID: " + id + " finished.");
        }
        else
        {
            System.out.println("Match with ID: " + id + " does not exist.");
        }
    }
    public void addScore(String id_player, int score)
    {
        if (players.containsKey(id_player))
        {
            Player player = players.get(id_player);
            player.addScore(score);
            System.out.println("Score added to player with ID: " + id_player + ". New score: " + player.getScore());
        }
        else
        {
            System.out.println("Player with ID: " + id_player + " does not exist.");
        }
    }

    public void decideWinner(String id)

}