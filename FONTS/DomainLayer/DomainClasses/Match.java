package DomainLayer.DomainClasses;
import java.util.*;
import Utils.Pair;
import DomainLayer.DomainClasses.Dawg.Node;


public class Match
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

    private String id;
    private int turn = 0; // 0 for player1, 1 for player2
    private int score = 0;
    private boolean finished = false;
    private boolean paused = true;
    private int size;
    private Map<String,Player> players = new LinkedHashMap<>();
    private List<Player> playerList = new ArrayList<>(); //Access by index without looping through all players
    private Dictionary dictionary;
    private Bag bag;
    private Board board;

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
                        LeftParts(leftPW, node1, leftlimit, rack, square, board, playableWords, 1, dawg, i, j);
                        LeftParts(upPW, node2, uplimit, rack, square, board, playableWords, 0, dawg, i, j);
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
            int i = board.getSize()/2;
            int j = board.getSize()/2;
            LeftParts("",dawg.getRoot(),board.getSize()/2,rack,anchor,board,playableWords,1,dawg, i, j);
            LeftParts("",dawg.getRoot(),board.getSize()/2,rack,anchor,board,playableWords,0,dawg, i, j);
        }
        return playableWords;
    }
    
    // Generar todas las partes izquierdas posibles
    private void LeftParts(String PartialWord, Node node, int limit, Rack rack, Box square, Board board,List<PlayableWord> playableWords, int horizontal, Dawg dawg, int i, int j)
    {
        extendRight(PartialWord, node, square, rack,board,playableWords,horizontal, 1 - horizontal, dawg, i, j);
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
                    LeftParts(PartialWord + symbol, nextNode, limit - 1, rack, square , board,playableWords,horizontal, dawg, i, j);
                    rack.addLetter(letter);
                    /*System.out.println("Enter to continue: ");
                    Scanner scanner = new Scanner(System.in);
                    scanner.nextLine();*/
                }
            }
        }
    }
    
    // Extender palabra hacia la derecha
    private void extendRight(String PartialWord, Node node, Box square, Rack rack, Board board,List<PlayableWord> playableWords,int horizontal, int vertical, Dawg dawg, int i, int j) 
    {
        Box nextSquare = board.getBox(square.getRow() + vertical, square.getColumn() + horizontal);
        if(square.getSymbol() == null)
        {
            if(node.isFinal())
            {
                int PWlength = dawg.getWordLength(PartialWord);
                if(board.isEmpty())
                {
                    if(square.getRow() - 1>= board.getSize()/2 || square.getColumn() - 1>= board.getSize()/2)
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
                    if((i == square.getRow() && j < square.getColumn()) || (j == square.getColumn() && i < square.getRow()))
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
                    //if(!board.isEmpty())System.out.println("Im at square: " + square.getColumn() + " " + square.getRow() + " with letter: " + edge);
                    if(square.hasCrossCheck(edge,horizontal))
                    {
                        Node nextNode = entry.getValue();
                        extendRight(PartialWord + edge, nextNode, nextSquare, rack,board,playableWords,horizontal, vertical, dawg, i, j);
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
                extendRight(PartialWord + character, nextNode, nextSquare, rack,board,playableWords,horizontal, vertical, dawg, i, j);
            }
        }
    }

    public Match(String id, int size) 
    {
        this.setId(id);
        this.setSize(size);
        this.setTurn(0);
    }

    public void printBoard()
    {
        this.board.printBoard(); // Call the printBoard method from the Board class
    }

    public Bag getBag() 
    {
        return bag;
    }

    public Board getBoard() 
    {
        return board;
    }

    public Dictionary getDictionary() 
    {
        return dictionary;
    }

    public void setBag(Bag bag) 
    {
        this.bag = bag;
    }

    public void setBoard(Board board) 
    {
        this.board = board;
    }

    public Map<String, Player> getPlayers() 
    {
        return players;
    }

    public void setDictionary(Dictionary dictionary) 
    {
        this.dictionary = dictionary;
    }

    public void setSize(int size) 
    {
        this.size = size;
    }

    public int getSize() 
    {
        return size;
    }

    public void setPlayer(Player player)
    {
        players.put(player.getID(), player);
        playerList.add(player); // Add the player to the list for easy access by index
    }

    public List<Player> getListPlayers()
    {
        return playerList;
    }

    public Player getPlayer(String id)
    {
        return players.get(id);
    }

    public ArrayList<String> getPlayersname(){
        ArrayList<String> names = new ArrayList<>();
       
        for(Player player : playerList)
        {
            names.add(player.getName());
        }
        return names;
    }

    public void displayPlayers()
    {
        for (Map.Entry<String, Player> entry : players.entrySet()) 
        {
            entry.getValue().displayPlayer(); 
        }
    }

    public String decideWinner()
    {
        int maxScore = 0;
        String winnerId = null;
        for (Map.Entry<String, Player> entry : players.entrySet()) 
        {
            String key = entry.getKey();
            Player value = entry.getValue();
            if (value.getScore() > maxScore)
            {
                maxScore = value.getScore();
                winnerId = key;
            }
        }
        return winnerId;
    }


    public void displayMatch()
    {
        System.out.println("Match ID: " + id);
        System.out.println("Turn: " + turn);
        System.out.println("Score: " + score);
        System.out.println("Paused: " + paused);
        System.out.println("Size: " + size);
        displayPlayers();
    }

    public void shuffleRack()
    {
        Player player = playerList.get(turn);
        player.shuffleRack();
    }

    public String getId() 
    {
        return id;
    }
    public void setId(String id) 
    {
        this.id = id;
    }
    public int getTurn() 
    {
        return turn;
    }

    public void setTurn(int turn) 
    {
        this.turn = turn%this.size;
    }

    public int getScore() 
    {
        return this.score;
    }

    public void startMatch() 
    {
        this.paused = false;
    }
    
    public boolean isPaused() 
    {
        return this.paused;
    }

    public void setPaused(boolean paused)
    {
        this.paused = paused;
    }

    public String setFinished() 
    {
        String winner = decideWinner();
        Player player = players.get(winner);
        String winnerName = player.getName();
        if(player instanceof Human) 
        {
            Human human = (Human) player; // Cast the player to Human
            human.getProfile().incrementWins(); // Increment the number of games won in the profile
        }
        return winnerName;
    }


    public void modifyRack(String old_letters)
    {
        Player player = playerList.get(turn);
        player.displayPlayer();
        player.modifyRack(old_letters);
        player.printRack();
        System.out.println("Racked replaced, enter to continue...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        System.out.println("Continuing...");
        setTurn(turn+1);
    }

    public boolean humanTurn(String word, int startX, int startY, int endX, int endY, Set<Pair<Integer, Integer>> JokerPos) throws IllegalArgumentException, IllegalStateException
    {
        int score = 0;
        //Dictionary dictionary = this.dictionary;
        //Board board = this.board;
        Dawg dawg = dictionary.getDawg();
        board.computeCrossChecks(dictionary.getCharacters(), dawg);
        Player player = playerList.get(turn);
        Rack player_rack = player.getRack();
        List<PlayableWord> PlayAbleWords = calculatePlayableWords(board, player_rack, dictionary);
        PlayableWord My_playableword = new PlayableWord(word, startX, startY, endX, endY);
        System.out.println("All playable words: ");
        for (PlayableWord playableWord : PlayAbleWords)
        {
            System.out.println(playableWord.toString());
        }
        System.out.println("My playable word: " + My_playableword.toString());
        if (PlayAbleWords.contains(My_playableword))
        {
            System.out.println("Valid play, placing letters on the board.");
            int i = 0;
            int j = 0;
            int wholeWordBonusFactor = 1;
            while(i < word.length())
            {
                String symbol = dawg.getSpecialCharacter(word, i);
                if(symbol == null)  symbol = String.valueOf(word.charAt(i));
                if(startX == endX)
                {
                    if(board.isEmpty(startY + j,startX))
                    {
                        Letter letter;
                        if(JokerPos.contains(new Pair<>(startX, startY + j))) //If the position is a joker, we need to set the symbol to the one we are placing
                        {
                            letter = player_rack.getLetter("#");
                            letter.setSymbol(symbol);
                        }
                        else
                        {
                            letter = player_rack.getLetter(symbol); //Get the letter from the rack
                        }
                        int BonusScore = board.placeLetter(startY + j, startX, letter.getSymbol(),letter.getValue());
                        score += BonusScore;
                        wholeWordBonusFactor *= board.getWordBonus(startY + j, startX);
                        Letter letter2 = bag.extractLetter();
                        player_rack.addLetter(letter2);
                    }
                    else
                    {
                        int value = board.getBox(startX, startY + j).getValue();
                        score += value; //If the box is not empty, we add the value of the letter in the box to the score
                        wholeWordBonusFactor *= board.getWordBonus(startY + j, startX);
                    }
                }
                else
                {
                    if(board.isEmpty(startY,startX + j))
                    {
                        Letter letter;
                        if(JokerPos.contains(new Pair<>(startY, startX + j))) //If the position is a joker, we need to set the symbol to the one we are placing
                        {
                            letter = player_rack.getLetter("#");
                            letter.setSymbol(symbol);
                        }
                        else
                        {
                            letter = player_rack.getLetter(symbol); //Get the letter from the rack
                        }
                        int BonusScore = board.placeLetter(startY, startX + j, letter.getSymbol(),letter.getValue());
                        score += BonusScore;
                        wholeWordBonusFactor *= board.getWordBonus(startY, startX + j);
                        Letter letter2 = bag.extractLetter();
                        player_rack.addLetter(letter2);
                    }
                    else
                    {
                        int value = board.getBox(startY, startX + j).getValue();
                        score += value;
                        wholeWordBonusFactor *= board.getWordBonus(startY, startX + j);
                    }
                }
                i+=symbol.length();
                j++;
            }
            System.out.println("Score for this play: " + score);
            player.addScore(score * wholeWordBonusFactor); //Add the score to the player
            setTurn(turn + 1);
            return true;
        }
        player_rack.print(); //Print the rack of the player
        return false;
    }

    /*public boolean humanTurn2(String word1, int startX1, int startY1, int endX1, int endY1, String word2, int startX2, int startY2, int endX2, int endY2) throws IllegalArgumentException, IllegalStateException
    {
        PlayableWord playableWord1 = new PlayableWord(word1, startX1, startY1, endX1, endY1);
        PlayableWord playableWord2 = new PlayableWord(word2, startX2, startY2, endX2, endY2);
        Player player = playerList.get(turn);
        Dawg dawg = dictionary.getDawg();
        board.computeCrossChecks(dictionary.getCharacters(), dawg);
        Rack player_rack = player.getRack();
        List<PlayableWord> PlayAbleWords = calculatePlayableWords(board, player_rack, dictionary);
        if(PlayAbleWords.contains(playableWord1) && PlayAbleWords.contains(playableWord2))
        {
            int score1 = 0;
            int score2 = 0;
            
        }
    }*/

    public Pair <String, Integer[]> aiTurn()
    {
        Player player = playerList.get(turn);
        if(player.isHuman())
        {
            throw new IllegalStateException("Cannot call aiTurn on a human player.");
        }
        else
        {
            Dawg dawg = dictionary.getDawg();
            Rack player_rack = player.getRack();
            board.computeCrossChecks(dictionary.getCharacters(), dawg);
            List<PlayableWord> PlayAbleWords = calculatePlayableWords(board, player_rack, dictionary);
            if(PlayAbleWords.size() % 2 != 0)
            {
                System.out.println("AI is placing a word on the board.");
                int score = 0;
                int wholeWordBonusFactor = 1;
                int random = (int) (Math.random() * PlayAbleWords.size());
                PlayableWord playableword = PlayAbleWords.get(random);
                String word = playableword.word;
                int startX = playableword.startColumn;
                int startY = playableword.startRow;
                int endX = playableword.endColumn;
                int endY = playableword.endRow;
                int i = 0;
                int j = 0;
                while(i < word.length())
                {
                    String symbol = dawg.getSpecialCharacter(word, i);
                    if(symbol == null)  symbol = String.valueOf(word.charAt(i));
                    if(startX == endX)
                    {
                        if(board.isEmpty(startY + j, startX))
                        {
                            Letter letter = player_rack.getLetter(symbol); //Get the letter from the rack
                            if(letter.getSymbol().equals("#")) //If the letter is a joker, we need to set the symbol to the one we are placing
                            {
                                letter.setSymbol(symbol);
                            }
                            int bonusScore = board.placeLetter(startY + j, startX, letter.getSymbol(),letter.getValue());
                            score += bonusScore;
                            wholeWordBonusFactor *= board.getWordBonus(startY + j, startX);
                            Letter letter2 = bag.extractLetter();
                            player_rack.addLetter(letter2);
                        }
                        else
                        {
                            int value = board.getBox(startY + j, startX).getValue();
                            score += value; //If the box is not empty, we add the value of the letter in the box to the score
                            wholeWordBonusFactor *= board.getWordBonus(startY + j, startX);
                        }
                    }
                    else
                    {
                        if(board.isEmpty(startY, startX + j))
                        {
                            Letter letter = player_rack.getLetter(symbol); //Get the letter from the rack
                            if(letter.getSymbol().equals("#")) //If the letter is a joker, we need to set the symbol to the one we are placing
                            {
                                letter.setSymbol(symbol);
                            }
                            int bonusScore = board.placeLetter(startY, startX + j, letter.getSymbol(),letter.getValue());
                            score += bonusScore;
                            wholeWordBonusFactor *= board.getWordBonus(startY, startX + j);
                            Letter letter2 = bag.extractLetter();
                            player_rack.addLetter(letter2);
                        }
                        else
                        {
                            int value = board.getBox(startY, startX + j).getValue();
                            score += value;
                            wholeWordBonusFactor *= board.getWordBonus(startY, startX + j);
                        }
                    }
                    i+=symbol.length();
                    j++;
                }
                player.addScore(score * wholeWordBonusFactor);
                setTurn(turn + 1);
                return new Pair<>(word, new Integer[]{startX, startY, endX, endY});
            }
            else
            {
                int random = (int) (Math.random() * 7);
                String oldLetters = "";
                for(int i = 0; i < random - 1; ++i)
                {
                    String letter = player_rack.getLetterSymbol(i);
                    oldLetters += letter + "_";
                }
                oldLetters += player_rack.getLetterSymbol(random - 1);
                player.modifyRack(oldLetters);
            }
            return new Pair<>("", new Integer[]{});
        }
    }
}
