package DomainClasses;
import java.util.*;
import java.io.*;
import DomainClasses.Dawg.Node;
public class MP_Controller
{
    public static class PlayableWord {
        private String word;
        private int startColumn, startRow;
        private int endColumn, endRow;

        public PlayableWord(String word, int startColumn, int startRow, int endColumn, int endRow) {
            this.word = word;
            this.startColumn = startColumn;
            this.startRow = startRow;
            this.endColumn = endColumn;
            this.endRow = endRow;
        }

        @Override
        public String toString() {
            return "Word: " + word + " Start: (" + startColumn + ", " + startRow + ") End: (" + endColumn + ", " + endRow + ")";
        }
        @Override
        public boolean equals(Object obj)
        {
            if(this == obj) return true;
            if(obj == null || getClass() != obj.getClass()) return false;
            PlayableWord other = (PlayableWord) obj;
            return this.word.equals(other.word) && this.startColumn == other.startColumn && this.startRow == other.startRow && this.endColumn == other.endColumn && this.endRow == other.endRow;
        }
    }

    public List<PlayableWord> calculatePlayableWords(Board board, Rack rack, Dictionary dictionary)
    {
        List<PlayableWord> playableWords = new ArrayList<>();
        Dawg dawg = dictionary.getDawg();
        if(!board.isEmpty())
        {
            for (int i = 0; i < board.getSize(); i++)
            {
                for (int j = 0; j < board.getSize(); j++)
                {
                    Box square = board.getBox(i,j);
                    if(board.isAnchor(i,j))
                    {
                        System.out.println("Working with anchor: " + j + " " + i);
                        int leftlimit = 0;
                        int uplimit = 0;
                        int column = j - 1;
                        int row = i - 1;
                        String leftPW = "";
                        String upPW = "";
                        while(column >= 0)
                        {
                            if(!board.isAnchor(i,column))
                            {
                                if(board.getBox(i,column).isEmpty())    ++leftlimit;
                                else    leftPW = board.getBox(i,column).getSymbol() + leftPW;
                            }
                            else break;
                            --column;
                        }
                        while(row >= 0)
                        {
                            if(!board.isAnchor(row,j))
                            {
                                if(board.getBox(row,j).isEmpty())    ++uplimit;
                                else    upPW = board.getBox(row,j).getSymbol() + upPW;
                            }
                            else break;
                            --row;
                        }
                        Node node1 = dawg.get_lastNode(dawg.getRoot(), leftPW);
                        Node node2 = dawg.get_lastNode(dawg.getRoot(), upPW);
                        LeftParts(leftPW, node1, leftlimit, rack, square, board, playableWords, 1, dawg);
                        LeftParts(upPW, node2, uplimit, rack, square, board, playableWords, 0, dawg);
                        /*System.out.println("Playable words constructed so far:");
                        for(PlayableWord playableWord : playableWords)
                        {
                            System.out.println(playableWord.toString());
                        }*/
                    }
                }
            }
        }
        else
        {
            System.out.println("Board is empty, placing letters in the middle of the board.");
            Box anchor = board.getBox(board.getSize()/2,board.getSize()/2);
            LeftParts("",dawg.getRoot(),board.getSize()/2,rack,anchor,board,playableWords,1,dawg);
            LeftParts("",dawg.getRoot(),board.getSize()/2,rack,anchor,board,playableWords,0,dawg);
        }
        return playableWords;
    }
    
    // Generar todas las partes izquierdas posibles
    private void LeftParts(String PartialWord, Node node, int limit, Rack rack, Box anchor, Board board,List<PlayableWord> playableWords, int horizontal, Dawg dawg)
    {
        extendRight(PartialWord, node, anchor, rack,board,playableWords,horizontal, 1 - horizontal, dawg);
        if(limit > 0)
        {
            Map <String, Node> children = node.getchildren();
            for (Map.Entry<String, Node> entry : children.entrySet())
            {
                String symbol = entry.getKey();
                //System.out.println("Symbol from this node: " + symbol);
                Letter letter;
                if((letter = rack.getLetter(symbol)) != null)
                {
                    //System.out.println("Letter: " + symbol + " in my rack");
                    //rack.removeLetter(letter);
                    Node nextNode = entry.getValue();
                    LeftParts(PartialWord + symbol, nextNode, limit - 1, rack, anchor,board,playableWords,horizontal, dawg);
                    rack.addLetter(letter);
                    /*System.out.println("Enter to continue: ");
                    Scanner scanner = new Scanner(System.in);
                    scanner.nextLine();*/
                }
            }
        }
    }
    
    // Extender palabra hacia la derecha
    private void extendRight(String PartialWord, Node node, Box square, Rack rack, Board board,List<PlayableWord> playableWords,int horizontal, int vertical, Dawg dawg) 
    {
        Box nextSquare = board.getBox(square.getRow() + vertical, square.getColumn() + horizontal);
        if(square.getSymbol() == null)
        {
            if(node.isFinal())
            {
                int PWlength = dawg.getWordLength(PartialWord);
                if(board.isEmpty())
                {
                    if(square.getRow() - 1 >= board.getSize()/2 || square.getColumn() - 1 >= board.getSize()/2)
                    {
                        /*System.out.println("Current square: " + square.getColumn() + " " + square.getRow());
                        System.out.println("Partial word: " + PartialWord);
                        System.out.println("Adding words horizontally: " + horizontal);
                        PlayableWord playableWord = new PlayableWord(PartialWord, square.getColumn() - PartialWord.length()*horizontal, square.getRow() - PartialWord.length()*vertical, square.getColumn() - horizontal, square.getRow() - vertical);
                        System.out.println("Playable word: " + playableWord.toString());
                        System.out.println();*/
                        playableWords.add(new PlayableWord(PartialWord, square.getColumn() - PWlength*horizontal, square.getRow() - PWlength*vertical, square.getColumn() - horizontal, square.getRow() - vertical));
                    }
                }
                else
                {
                    /*System.out.println("Current square: " + square.getColumn() + " " + square.getRow());
                    System.out.println("Partial word: " + PartialWord);
                    System.out.println("Adding words horizontally: " + horizontal);
                    PlayableWord playableWord = new PlayableWord(PartialWord, square.getColumn() - PartialWord.length()*horizontal, square.getRow() - PartialWord.length()*vertical, square.getColumn() - horizontal, square.getRow() - vertical);
                    System.out.println("Playable word: " + playableWord.toString());
                    System.out.println();*/
                    playableWords.add(new PlayableWord(PartialWord, square.getColumn() - PWlength*horizontal, square.getRow() - PWlength*vertical, square.getColumn() - horizontal, square.getRow() - vertical));
                }
            }
            if(nextSquare == null)
            {
                return;
            }
            Map<String, Node> children = node.getchildren();
            for(Map.Entry<String, Node> entry : children.entrySet())
            {
                String edge = entry.getKey();
                Letter letter = rack.getLetter(edge);
                if(letter != null)
                {
                    if(!board.isEmpty())System.out.println("Im at square: " + square.getColumn() + " " + square.getRow() + " with letter: " + edge);
                    if(square.hasCrossCheck(edge,horizontal))
                    {
                        Node nextNode = entry.getValue();
                        extendRight(PartialWord + edge, nextNode, nextSquare, rack,board,playableWords,horizontal, vertical, dawg);
                    }
                    rack.addLetter(letter);
                }
            }
        }
        else
        {
            String character = square.getSymbol();
            Node nextNode = node.getchildren().get(character);
            if(nextNode != null)
            {
                extendRight(PartialWord + character, nextNode, nextSquare, rack,board,playableWords,horizontal, vertical, dawg);
            }
        }
    }

    private static MP_Controller c;
    private Map<String, Match> matches = new HashMap<>();
    private List<String> unfinishedMatches = new ArrayList<>(); //Matches that are not finished yet
    private List<PlayableWord> playableWords = new ArrayList<>();
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
            /*List<Letter> letters = new ArrayList<>();
            letters.add(new Letter("F",4)); //Adding the joker to the rack
            letters.add(new Letter("O",1));
            letters.add(new Letter("G",1));
            letters.add(new Letter("P",5));
            letters.add(new Letter("U",3));
            letters.add(new Letter("R",4));
            letters.add(new Letter("#",0));*/
            String human_id = profile.getUsername(); //Get the ID of the profile
            Player player = new Human(human_id+match_id,profile,language);    //Creating a new human player with this profile
            player.setRack(new Rack(match.getBag())); //Creating a new rack for the player
            match.setPlayer(player);  //Adding the human player to the match
        }
        for (int i = 0; i < match_size - profile_size; i++)
        {
            String bot_id = match_id+"BOT"+i; //Creating a bot ID
            Player player = new IA(bot_id,i); //Creating a new AI player
            player.setRack(new Rack(match.getBag())); //Creating a new rack for the AI player
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
                Dictionary dictionary = match.getDictionary();
                Board board = match.getBoard();
                Dawg dawg = dictionary.getDawg();
                board.computeCrossChecks(dictionary.getCharacters(),dictionary.getDawg());
                //board.printCrossChecks();
                List<Player> list_players = match.getListPlayers();
                int turn = match.getTurn();
                Player player = list_players.get(turn);
                //print(id);
                Rack player_rack = player.getRack();
                Bag bag = match.getBag();
                List<PlayableWord> PlayAbleWords = calculatePlayableWords(board, player_rack, dictionary);
                if(player.isHuman())
                {
                    PlayableWord My_playableword = new PlayableWord(word, startX, startY, endX, endY);
                    System.out.println("All playable words: ");
                    for (PlayableWord playableWord : PlayAbleWords)
                    {
                        System.out.println(playableWord.toString());
                    }
                    System.out.println("My playable word: " + My_playableword.toString());
                    if (PlayAbleWords.contains(My_playableword))
                    {
                        validPlay = true;
                        System.out.println("Valid play, placing letters on the board.");
                        int i = 0;
                        int j = 0;
                        while(i < word.length())
                        {
                            String symbol = dawg.getSpecialCharacter(word, i);
                            if(symbol == null)  symbol = String.valueOf(word.charAt(i));
                            if(startX == endX)
                            {
                                if(board.isEmpty(startY + j,startX))
                                {
                                    Letter letter = player_rack.getLetter(symbol); //Get the letter from the rack
                                    if(letter.getSymbol().equals("#")) //If the letter is a joker, we need to set the symbol to the one we are placing
                                    {
                                        letter.setSymbol(symbol);
                                    }
                                    board.placeLetter(startY + j, startX, letter.getSymbol(),letter.getValue());
                                    player.addScore(letter.getValue());
                                    Letter letter2 = bag.extractLetter();
                                    player_rack.addLetter(letter2);
                                }
                            }
                            else
                            {
                                if(board.isEmpty(startY,startX + j))
                                {
                                    Letter letter = player_rack.getLetter(symbol); //Get the letter from the rack
                                    if(letter.getSymbol().equals("#")) //If the letter is a joker, we need to set the symbol to the one we are placing
                                    {
                                        letter.setSymbol(symbol);
                                    }
                                    board.placeLetter(startY, startX + j, letter.getSymbol(),letter.getValue());
                                    player.addScore(letter.getValue());
                                    Letter letter2 = bag.extractLetter();
                                    player_rack.addLetter(letter2);
                                }
                            }
                            i+=symbol.length();
                            j++;
                        }
                        
                    }
                    else    validPlay = false;
                    player_rack.print(); //Print the rack of the player
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
        Player player = match.getListPlayers().get(turn);
        player.displayPlayer();
        player.getRack().replaceLetters(old_letters);
        player.printRack();
        System.out.println("Racked replaced, enter to continue...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        System.out.println("Continuing...");
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