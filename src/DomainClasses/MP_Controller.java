package DomainClasses;
import java.util.*;
import java.io.*;
public class MP_Controller
{
    /*public static class PlayableWord {
        public String word;
        public int startX, startY;
        public int endX, endY;

        public PlayableWord(String word, int startX, int startY, int endX, int endY) {
            this.word = word;
            this.startX = startX;
            this.startY = startY;
            this.endX = endX;
            this.endY = endY;
        }

        @Override
        public String toString() {
            return "Word: " + word + " Start: (" + startX + ", " + startY + ") End: (" + endX + ", " + endY + ")";
        }
        @Override
        public boolean equals(Object obj)
        {
            if(this == obj) return true;
            if(obj == null || getClass() != obj.getClass()) return false;
            PlayableWord other = (PlayableWord) obj;
            return this.word.equals(other.word) && this.startX == other.startX && this.startY == other.startY && this.endX == other.endX && this.endY == other.endY;
        }
    }
		public List<PlayableWord> calculatePlayableWords(Board board, Rack rack, Dictionary dictionary) {
			List<PlayableWord> playableWords = new ArrayList<>();
			Set<Box> anchorSquares = board.getAnchorSquares(); // Casillas de anclaje
		
			for (Box anchor : anchorSquares) {
				int x = anchor.getX();
				int y = anchor.getY();
		
				// Generar todas las combinaciones posibles de palabras horizontales
				List<String> leftParts = generateLeftParts(anchor, rack, dictionary);
				for (String leftPart : leftParts) {
					for (String letter : rack.getLetters()) {
						String word = leftPart + letter;
						if (dictionary.isValidWord(word)) {
							// Extender hacia la derecha
							String extendedWord = extendRight(word, x, y, board, dictionary);
							int endX = x + extendedWord.length() - 1;
							int endY = y; // Horizontal por defecto
		
							// Agregar palabra jugable horizontal
							playableWords.add(new PlayableWord(extendedWord, x, y, endX, endY));
						}
					}
				}
		
				// Generar todas las combinaciones posibles de palabras verticales
				List<String> topParts = generateTopParts(anchor, rack, dictionary);
				for (String topPart : topParts) {
					for (String letter : rack.getLetters()) {
						String word = topPart + letter;
						if (dictionary.existsWord(word)) {
							// Extender hacia abajo
							String extendedWord = extendDown(word, x, y, board, dictionary);
							int endX = x; // Vertical por defecto
							int endY = y + extendedWord.length() - 1;
		
							// Agregar palabra jugable vertical
							playableWords.add(new PlayableWord(extendedWord, x, y, endX, endY));
						}
					}
				}
			}
		
			return playableWords;
		}
    
    // Generar todas las partes izquierdas posibles
    private List<String> generateLeftParts(Box anchor, Rack rack, Dictionary dictionary) {
        List<String> leftParts = new ArrayList<>();
        String partialWord = "";
        generateLeftPartsRecursive(partialWord, anchor, rack, dictionary, leftParts);
        return leftParts;
    }
    
    private void generateLeftPartsRecursive(String partialWord, Box anchor, Rack rack, Dictionary dictionary, List<String> leftParts) {
        if (anchor == null || !dictionary.isPrefix(partialWord)) {
            return;
        }
    
        leftParts.add(partialWord);
    
        for (String letter : rack.getLetters()) {
            Rack newRack = rack.clone();
            newRack.removeLetter(letter);
            generateLeftPartsRecursive(partialWord + letter, anchor.getLeftNeighbor(), newRack, dictionary, leftParts);
        }
    }

    private List<String> generateTopParts(Box anchor, Rack rack, Dictionary dictionary) {
        List<String> topParts = new ArrayList<>();
        String partialWord = "";
        generateTopPartsRecursive(partialWord, anchor, rack, dictionary, topParts);
        return topParts;
    }
    
    private void generateTopPartsRecursive(String partialWord, Box anchor, Rack rack, Dictionary dictionary, List<String> topParts) {
        if (anchor == null || !dictionary.isPrefix(partialWord)) {
            return;
        }
    
        topParts.add(partialWord);
    
        for (String letter : rack.getLetters()) {
            Rack newRack = rack.clone();
            newRack.removeLetter(letter);
            generateTopPartsRecursive(partialWord + letter, anchor.getTopNeighbor(), newRack, dictionary, topParts);
        }
    }
    
    // Extender palabra hacia la derecha
    private String extendRight(String word, int x, int y, Board board, Dictionary dictionary) {
        while (board.hasLetter(x + word.length(), y)) {
            char nextLetter = board.getLetter(x + word.length(), y);
            word += nextLetter;
            if (!dictionary.isPrefix(word)) {
                break;
            }
        }
        return word;
    }

    private String extendDown(String word, int x, int y, Board board, Dictionary dictionary) {
        while (board.hasLetter(x, y + word.length())) {
            char nextLetter = board.getLetter(x, y + word.length());
            word += nextLetter;
            if (!dictionary.isPrefix(word)) {
                break;
            }
        }
        return word;
    }*/
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

    public String createMatch(int size, Set<Profile> profiles, String language, String name,int board_size) throws IOException, IllegalArgumentException
    {
            String id = UUID.randomUUID().toString(); //Generating a random ID for the match
            if(matches.containsKey(id))  throw new IllegalArgumentException("Match with ID: " + id + " already exists.");
            Match match = new Match(id,size);
            matches.put(id, match);
            createPlayersForMatch(match,profiles);
            createDictionaryForMatch(match,language,name);
            createBagForMatch(match,"letras"+language);
            createBoardForMatch(match,board_size);
            match.setPaused(false);
            System.out.println("Match created with ID: " + match.getId());
            return id;
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

    public Match getMatch(String id) throws IllegalArgumentException
    {
        if (existMatch(id))
        {
            return matches.get(id);
        }
        else
        {
            throw new IllegalArgumentException("Match with ID: " + id + " does not exist.");
        }
    }

    public void displayPlayers(String match_id) throws IllegalArgumentException
    {
        if(existMatch(match_id))
        {
            Match match = matches.get(match_id);
            match.displayPlayers();
        }
        else
        {
            throw new IllegalArgumentException("Match with ID: " + match_id + " does not exist.");
        }
    }

    public void displayMatch(String id) throws IllegalArgumentException
    {
        if(existMatch(id))
        {
            Match match = matches.get(id);
            match.displayMatch();
        }
        else
        {
            throw new IllegalArgumentException("Match with ID: " + id + " does not exist.");
        }
    }

    
    
    public void startMatch(String id) throws IllegalArgumentException
    {
        if (existMatch(id))
        {
            Match match = matches.get(id);
            match.startMatch();
            System.out.println("Match with ID: " + id + " started.");
        }
        else
        {
            throw new IllegalArgumentException("Match with ID: " + id + " does not exist.");
        }
    }




    public boolean playsMatch(String id/* ,String word, int startX, int startY, int endX, int endY*/) throws IllegalArgumentException, IllegalStateException
    {
        boolean validPlay = false;
        int score = 0;
        //String aux_word = word.replace("_","");
        boolean nextRound = false;
        if (existMatch(id))
        {
            Match match = matches.get(id);
            if (!match.isPaused() && !match.isFinished())
            {
                Board board = match.getBoard();
                List<Player> list_players = match.getListPlayers();
                int turn = match.getTurn();
                Player player = list_players.get(turn);
                match.printBoard();
                Rack player_rack = player.getRack();
                if(player.isHuman())
                {
                    player.printRack();
                    /*List<PlayableWord> PlayAbleWords = calculatePlayableWords(board, player_rack, match.getDictionary());
                    PlayableWord My_playablewords = new PlayableWord(word, startX, startY, endX, endY);
                    if (PlayAbleWords.contains(My_playablewords))
                    {
                        validPlay = true;
                        List<Letter> letters = player_rack.getLetters(aux_word);
                        for (Letter letter : letters)
                        {
                            if(startX == endX)
                            {
                                int addedY = startY;
                                board.placeLetter(startX, addedY, letter.getSymbol(),letter.getValue());
                                ++addedY;
                            }
                            else
                            {
                                int addedX = startX;
                                board.placeLetter(addedX, startY, letter.getSymbol(),letter.getValue());
                                ++addedX;
                            }
                            player_rack.removeLetter(letter); //Remove the letter from the rack
                            player.addScore(letter.getValue());
                        }
                        
                    }
                    else    validPlay = false;*/
                    validPlay = true;
                }
                else
                {
                     
                }
                match.setTurn(turn + 1);
                return true;
            }
            else
            {
                throw new IllegalStateException("Match with ID: " + id + " is paused.");
            }
        }
        else
        {
            throw new IllegalArgumentException("Match with ID: " + id + " does not exist.");
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

    public void pauseMatch(String id) throws IllegalArgumentException
    {
        if (existMatch(id))
        {
            Match match = matches.get(id);
            match.setPaused(true);
            System.out.println("Match with ID: " + id + " paused.");
        }
        else
        {
            throw new IllegalArgumentException("Match with ID: " + id + " does not exist.");
        }
    }

    public String finishMatch(String id)  throws IllegalArgumentException
    {
        if (existMatch(id))
        {
            Match match = matches.get(id);
            String winner = match.setFinished();
            System.out.println("Match with ID: " + id + " finished.");
            return winner;
        }
        else
        {
            throw new IllegalArgumentException("Match with ID: " + id + " does not exist.");
        }
    }

    public void continueMatch(String id) throws IllegalArgumentException
    {
        if (existMatch(id))
        {
            Match match = matches.get(id);
            match.setPaused(false);
            System.out.println("Match with ID: " + id + " continued.");
        }
        else
        {
            throw new IllegalArgumentException("Match with ID: " + id + " does not exist.");
        }
    }

    public Player whoseTurn(String id) throws IllegalArgumentException
    {
        if (existMatch(id))
        {
            Match match = matches.get(id);
            List<Player> list_players = match.getListPlayers();
            int turn = match.getTurn();
            return list_players.get(turn);
        }
        else
        {
            throw new IllegalArgumentException("Match with ID: " + id + " does not exist.");
        }
    }
}