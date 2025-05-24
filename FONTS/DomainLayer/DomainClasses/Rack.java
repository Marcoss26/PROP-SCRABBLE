package DomainLayer.DomainClasses;
import java.util.*;

/**
 * Rack.java
 * This class represents a player's rack in a match. It contains a set of letters that the player can use to form words
 * @author Kai Knox
 */
public class Rack {
    private List<Letter> letters = new ArrayList<>();
    static final int NUM_LETTERS = 7;
    private Bag bag; // The bag of letters from which letters are drawn

    public Rack() {
    }

    /**
     * Constructor for the Rack class.
     * Initializes the rack with a set of letters.
     * @param bag The bag of letters from which letters are drawn.
     */
    public Rack(Bag bag) {
        this.bag = bag;
        this.letters = bag.extractSetOfLetters(NUM_LETTERS);
    }

    public Rack(Bag bag, List<Letter> letters) {
        this.bag = bag;
        this.letters = letters;
    }

    /**
     * Adds a letter to the rack.
     * @param letter The letter to be added.
     * @throws IllegalStateException if the rack is full.
     */
    public void addLetter(Letter letter) {
        if (letters.size() >= NUM_LETTERS) {
            throw new IllegalStateException("Rack is full");
        }
        letters.add(letter);
    }

    public ArrayList<String> getInfoLetters() {
        ArrayList<String> infoLetters = new ArrayList<>();
        for (Letter letter : letters) {
            infoLetters.add(letter.getSymbol());
            infoLetters.add(String.valueOf(letter.getValue()));
        }
        return infoLetters;
    }

    /**
     * Gets a letter from the rack, removing it from the rack, and replacing it with a new letter from the bag.
     * @param symbol The symbol of the letter to get.
     * @return The letter that was removed, or null if no such letter exists.
     */
    public Letter getLetter(String symbol) {
        //System.out.println("I'm getting the letter " + symbol + " from the rack.");
        int blank_i = -1;
        for (int i = 0; i < letters.size(); i++) {
            if (letters.get(i).getSymbol().equals(symbol)) {
                Letter letter = letters.remove(i); // Remove the letter from the list
                return letter;
            }
            if(letters.get(i).getSymbol().equals("#")) {
                blank_i = i;
            }
        }
        if(blank_i != -1) {
            Letter letter = letters.remove(blank_i); // Remove the letter from the list
            //letter.setSymbol(symbol);
            return letter;
        }
        return null; // Return null if no such letter exists
    }

    /**
     * Shuffles the letters in the rack.
     */
    public void shuffle() {
        Collections.shuffle(letters);
    }

    /**
     * Gets the size of the rack.
     * @return The size of the rack.
     */
    public int getSize() {
        return letters.size();
    }
    /**
     * Clears the rack.
     */
    public void clear() {
        letters.clear();
    }

    public void replaceLetters(String symbols) {
        String[] symbols2 = symbols.split("_");
        for (String symbol : symbols2) {
            for (int i = 0; i < letters.size(); i++) {
                if (letters.get(i).getSymbol().equals(symbol)) {
                    letters.remove(i);
                    letters.add(bag.extractLetter());
                    break;
                }
            }
        }
    }
    
    /**
     * Prints the letters in the rack.
     */
    public void print() {
        System.out.print("Rack: ");
        for (Letter letter : letters) {
            System.out.print(letter.getSymbol() + " ");
        }
        System.out.println();
        System.out.println("------------------------------");
    }

    public List<Letter> getLetters() {
        return letters;
    }

    public void removeLetter(Letter letter) {
        letters.remove(letter);
    }

    @Override
    public Rack clone() {
        Rack clonedRack = new Rack();
        for (Letter letter : this.letters) {
            clonedRack.addLetter(new Letter(letter.getSymbol(), letter.getValue())); // Asumiendo que Letter tiene un constructor de copia
        }
        return clonedRack;
    }
}
