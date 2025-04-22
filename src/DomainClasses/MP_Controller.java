package DomainClasses;
import java.util.*;
import java.io.*;
public class MP_Controller
{
    public static class PlayableWord {
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
//        private Rack removeUsedLetters(Rack rack, String leftPart, String anchorSymbol, int nSpecialCharacters) {

            
  //      }

        private void generateValidWords(Board board, Rack rack, Dictionary dictionary, Box anchor, String partialWord, List<String> validWords, List<Integer> validWordsEndX, List<Integer> validWordsEndY, int nSpecialCharacters) {

            if(rack.getLetters().size() == 0) return;
            if(anchor != null) return;

            if(board.getRightNeighbor(anchor) != null && dictionary.existsWord(partialWord)) {

                validWords.add(partialWord);
                validWordsEndX.add(anchor.getX());
                validWordsEndY.add(anchor.getY());
            }

            for (Letter letter : rack.getLetters()) {
                Rack newRack = rack.clone();
                newRack.removeLetter(letter);
                String aux = partialWord + letter.getSymbol();
                nSpecialCharacters += letter.getSymbol().length() - 1;

                generateValidWords(board, newRack, dictionary, board.getRightNeighbor(anchor), aux, validWords, validWordsEndX, validWordsEndY, nSpecialCharacters);
            }
        }

        public List<PlayableWord> calculatePlayableWords(Board board, Rack rack, Dictionary dictionary) {
            List<PlayableWord> playableWords = new ArrayList<>();
            Set<Box> anchorSquares = board.getLeftAnchorSquares(); // Casillas de anclaje
        
            for (Box anchor : anchorSquares) {
                int x = anchor.getX();
                int y = anchor.getY();
        
                System.out.println("Anchor at: (" + x + ", " + y + ")");
        
                // Generar todas las combinaciones posibles de palabras horizontales
                int horizontalLimit = getLeftPartLimit(board, anchor, true);
                List<String> leftParts = generateLeftParts(board, anchor, rack, dictionary, horizontalLimit, new ArrayList<>());
                
                for (String leftPart : leftParts) {
                    for (Letter letter : rack.getLetters()) {
                        String word = leftPart + letter.getSymbol();
                        if (dictionary.existsWord(word)) {
                            // Extender hacia la derecha
                            String extendedWord = extendRight(word, anchor.getX(), anchor.getY(), board, dictionary);
                            int endX = anchor.getX();
                            int endY = anchor.getY() + extendedWord.length() - 1;
                
                            // Agregar palabra jugable horizontal
                            playableWords.add(new PlayableWord(extendedWord, anchor.getX(), anchor.getY() - leftPart.length(), endX, endY));
                        }
                    }
                }
        
                // Generar todas las combinaciones posibles de palabras verticales
                int verticalLimit = getLeftPartLimit(board, anchor, false);
                List<String> topParts = generateTopParts(board, anchor, rack, dictionary, verticalLimit);
                for (String topPart : topParts) {
                    for (Letter letter : rack.getLetters()) {
                        String word = topPart + letter.getSymbol();
                        if (dictionary.existsWord(word)) {
                            // Extender hacia abajo
                            String extendedWord = extendDown(word, x, y, board, dictionary);
                            int endX = x + extendedWord.length() - 1; // Vertical por defecto
                            int endY = y;
        
                            // Agregar palabra jugable vertical
                            playableWords.add(new PlayableWord(extendedWord, x - topPart.length(), y, endX, endY));
                        }
                    }
                }
            }
        
            return playableWords;
        }

    private int getLeftPartLimit(Board board, Box anchor, boolean hor) {

        if(hor) {

            int i = 1;
            boolean stop = false;
            while(!stop) {

                if((anchor.getY() - i > 0) && (anchor.getY() - i - 1 > 0) && !board.hasLetter(anchor.getX(), anchor.getY() - i - 1)) ++i;
                else stop = true;
            }
            return i;
        }
        else {

            int i = 1;
            boolean stop = false;
            while(!stop) {

                if((anchor.getX() - i > 0) && (anchor.getX() - i - 1 > 0) && !board.hasLetter(anchor.getX() - i - 1, anchor.getY())) ++i;
                else stop = true;
            }
            return i;
        }
    }
    
    // Generar todas las partes izquierdas posibles
    private List<String> generateLeftParts(Board board, Box anchor, Rack rack, Dictionary dictionary, int limit, List<Integer> nSpecialCharactersInLeftParts) {
        List<String> leftParts = new ArrayList<>();
        String partialWord = board.getRightNeighbor(anchor).getSymbol();
        System.out.println(partialWord);
        int nSpecialCharacters = partialWord.length() - 1;
        generateLeftPartsRecursive(partialWord, board, anchor, rack, dictionary, leftParts, limit, nSpecialCharactersInLeftParts,nSpecialCharacters);
        return leftParts;
    }
    
private void generateLeftPartsRecursive(String partialWord, Board board, Box anchor, Rack rack, Dictionary dictionary, List<String> leftParts, int limit, List<Integer> nSpecialCharactersInLeftParts, int nSpecialCharacters) {
    if (partialWord.length() - nSpecialCharacters >= limit + 1) return;
    if (anchor == null) return;

    leftParts.add(partialWord);
    nSpecialCharactersInLeftParts.add(nSpecialCharacters); // Asegurar sincronización

    for (Letter letter : rack.getLetters()) {
        Rack newRack = rack.clone();
        newRack.removeLetter(letter);
        String aux = letter.getSymbol() + partialWord;
        int newNSpecialCharacters = nSpecialCharacters + letter.getSymbol().length() - 1;
        generateLeftPartsRecursive(aux, board, board.getLeftNeighbor(anchor), newRack, dictionary, leftParts, limit + 1, nSpecialCharactersInLeftParts ,newNSpecialCharacters);
    }
}

private List<String> generateTopParts(Board board, Box anchor, Rack rack, Dictionary dictionary, int limit) {
    List<String> topParts = new ArrayList<>();
    String partialWord = "";
    generateTopPartsRecursive(partialWord, board, anchor, rack, dictionary, topParts, limit);
    return topParts;
}

private void generateTopPartsRecursive(String partialWord, Board board, Box anchor, Rack rack, Dictionary dictionary, List<String> topParts, int limit) {
    if (partialWord.length() >= limit + 1 || anchor == null || !dictionary.isPrefix(partialWord)) {
        return;
    }

    topParts.add(partialWord);

    for (Letter letter : rack.getLetters()) {
        Rack newRack = rack.clone();
        newRack.removeLetter(letter);
        generateTopPartsRecursive(letter.getSymbol() + partialWord, board, board.getTopNeighbor(anchor), newRack, dictionary, topParts, limit);
    }
}
    
    // Extender palabra hacia la derecha
    private String extendRight(String word, int x, int y, Board board, Dictionary dictionary) {
        while (board.hasLetter(x, y + word.length())) {
            String nextLetter = board.getLetter(x, y + word.length());
            word += nextLetter;
            if (!dictionary.isPrefix(word)) {
                break;
            }
        }
        return word;
    }


    private String extendDown(String word, int x, int y, Board board, Dictionary dictionary) {
        while (board.hasLetter(x + word.length(), y)) {
            String nextLetter = board.getLetter(x + word.length(), y);
            word += nextLetter;
            if (!dictionary.isPrefix(word)) {
                break;
            }
        }
        return word;
    }
    private static MP_Controller c;
    private Map<String, Match> matches = new HashMap<>();
    private List<String> unfinishedMatches = new ArrayList<>(); //Matches that are not finished yet
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

    public void print(String id)
    {
        Match match = matches.get(id);
        match.printBoard();
        List<Player> players = match.getListPlayers();
        players.get(match.getTurn()).printRack();

    }

    public String createMatch(int size, Set<Profile> profiles, Dictionary dictionary,int board_size, Map<Letter,Integer> letters, int bag_size) throws IOException, IllegalArgumentException
    {
            String id = UUID.randomUUID().toString(); //Generating a random ID for the match
            while(matches.containsKey(id))  
            {
                id = UUID.randomUUID().toString(); //Generating a new ID for the match
            }
            Match match = new Match(id,size);
            matches.put(id, match);
            unfinishedMatches.add(id); //Adding the match to the unfinished matches
            createBagForMatch(match,letters,bag_size);
            createPlayersForMatch(match,profiles,dictionary.getLanguage());
            createDictionaryForMatch(match,dictionary);
            createBoardForMatch(match,board_size);
            match.setPaused(false);
            System.out.println("Match created with ID: " + match.getId());
            return id;
    }

    private void createBoardForMatch(Match match, int size)
    {
        match.setBoard(new Board(size)); //Creating a new board with the size given by the user
    }

    private void createBagForMatch(Match match, Map<Letter,Integer> letters, int bag_size) throws IOException
    {
        match.setBag(new Bag(letters, bag_size));
    }

    private void createDictionaryForMatch(Match match, Dictionary dictionary)
    {
        match.setDictionary(dictionary);
    }

    private void createPlayersForMatch(Match match, Set<Profile> profiles, String language)
    {
        int match_size = match.getSize();
        int profile_size = profiles.size();
        String match_id = match.getId();
        for (Profile profile: profiles)
        {
            String human_id = profile.getUsername(); //Get the ID of the profile
            Player player = new Human(human_id+match_id,profile,language);    //Creating a new human player with this profile
            player.setRack(new Rack(match.getBag())); //Creating a new rack for the player
            match.setPlayer(player);  //Adding the human player to the match
        }
        for (int i = 0; i < match_size - profile_size; i++)
        {
            String bot_id = match_id+"BOT"+i; //Creating a bot ID
            Player player = new IA(bot_id,i); //Creating a new AI player
            match.setPlayer(player);    //Adding the AI player to the match
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




    public boolean playsMatch(String id ,String word, int startX, int startY, int endX, int endY) throws IllegalArgumentException, IllegalStateException
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

    public void modifyRack(String id, String old_letters)
    {
        Match match = matches.get(id);
        int turn = match.getTurn();
        Player player = match.getListPlayers().get(match.getTurn());
        player.getRack().replaceLetters(old_letters);
        match.setTurn(turn+1);
    }

    public void shuffleRack(String id)
    {
        Match match = matches.get(id);
        int turn = match.getTurn();
        Player player = match.getListPlayers().get(turn);
        player.getRack().shuffle();
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
            unfinishedMatches.remove(id); //Removing the match from the unfinished matches
            String winner = match.setFinished();
            System.out.println("Match with ID: " + id + " finished.");
            return winner;
        }
        else
        {
            throw new IllegalArgumentException("Match with ID: " + id + " does not exist.");
        }
    }

    public List<String> getUnfinishedMatches()
    {
        return unfinishedMatches;
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