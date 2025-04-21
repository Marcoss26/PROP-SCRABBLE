package DomainClasses;
import java.util.*;
import java.io.*;
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

    public void createMatch(String id, Set<Profile> profiles, String language, String name)
    {
        try
        {
            Match match = new Match(id);
            matches.put(id, match);
            createPlayersForMatch(match,profiles);
            createDictionaryForMatch(match,language,name);
            createBagForMatch(match,"letras"+language);
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the size of the match (can only be 7,15 or 25): ");
            int size = scanner.nextInt();
            createBoardForMatch(match,size);
            System.out.println("Match created with ID: " + match.getId());
        }
        catch (IOException e)
        {
            System.out.println("Error creating match: " + e.getMessage());
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("Error creating match: " + e.getMessage());
        }
    }

    private void createBoardForMatch(Match match, int size)
    {
        match.setBoard(new Board(size)); //Creating a new board with the size given by the user
    }
    
    private void createBagForMatch(Match match,String fileName) throws IOException
    {
        Map<Letter, Integer> letters = new HashMap<>();
        String file = fileName + ".txt"; 
        File filePath = new File("data/Letters/" + file); 
        int totalLettersInTheBag = 0;
        if (!filePath.exists()) 
        {
            throw new FileNotFoundException("El archivo '" + fileName + "' no se encontró en la carpeta 'data/Letters'.");
        }
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = br.readLine()) != null) 
        {
            String[] parts = line.split(" "); 
            if (parts.length == 3) 
            {
                String symbol = parts[0];
                int quantity = Integer.parseInt(parts[1]); 
                totalLettersInTheBag += quantity;
                int value = Integer.parseInt(parts[2]); 
                Letter letter = new Letter(symbol, value); 
                letters.put(letter, quantity); 
            }
        }
        match.setBag(new Bag(letters, totalLettersInTheBag));
    }

    private void createDictionaryForMatch(Match match, String language, String name)
    {
        match.setDictionary(new Dictionary(name,language));
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



    public boolean existMatch(String id)
    {
        return matches.containsKey(id);
    }


    public void displayPlayers(String match_id)
    {
        if(existMatch(match_id))
        {
            Match match = matches.get(match_id);
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
                    System.out.println("1. Give a word you want to form");
                    System.out.println("2. Refresh rack");
                    while(!valid)
                    {
                        System.out.println("Enter your option: ");
                        int option = scanner.nextInt();
                        switch(option)
                        {
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
                                ++validOperation;
                                board.placeLetter(x,y,letter.getSymbol(),letter.getValue()); //Add the letter to the board
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
                                --validOperation;
                                System.out.println("Letter deleted.");
                                break;
                            case 3:
                                System.out.println("Confirming play...");
                                //valid = validBoard(board,match.getDictionary());
                                //Esto deberia de ir al tablero y que lo compruebe
                                if(!valid)
                                {
                                    System.out.println("Invalid play. Please try again.");
                                    break;
                                }
                                System.out.println("Play confirmed.");
                                int quantity = 7 - player.getRack().getSize();
                                List<Letter> letters = match.getBag().extractSetOfLetters(quantity);
                                player.getRack().addLetters(letters);
                                //int score = board.calculateScore();
                                //player.addScore(score);
                                break;
                            case 4:
                                if(validOperation == 0) 
                                {
                                    player.refreshRack();
                                    valid = true;
                                }
                                else System.out.println("You cannot refresh the rack because you already used some letters.");
                                break;
                        }
                    }
                }
                else
                {

                }
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



}