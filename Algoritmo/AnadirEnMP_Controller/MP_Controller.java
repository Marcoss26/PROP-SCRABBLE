import DomainClasses.Dictionary;

public class MP_Controller {
    // Clase interna para representar palabras jugables
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
    }

    public List<PlayableWord> calculatePlayableWords(Board board, Rack rack, Dictionary dictionary) {
        public List<PlayableWord> calculatePlayableWords(Board board, Rack rack, Dictionary dictionary) {
            List<PlayableWord> playableWords = new ArrayList<>();
            Set<Box> anchorSquares = board.getAnchorSquares(); // Casillas de anclaje
        
            for (Box anchor : anchorSquares) {
                int x = anchor.getX();
                int y = anchor.getY();
        
                // Generar todas las combinaciones posibles de palabras horizontales
                List<String> leftParts = generateLeftParts(anchor, rack, dictionary);
                for (String leftPart : leftParts) {
                    for (char letter : rack.getLetters()) {
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
                    for (char letter : rack.getLetters()) {
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
    
        for (char letter : rack.getLetters()) {
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
    
        for (char letter : rack.getLetters()) {
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
    }
}