package DomainClasses;
import java.util.*;

/**
 * Rack.java
 * This class represents a player's rack in a match. It contains a set of letters that the player can use to form words
 * @author Kai Knox
 */
public class Rack {
    private Map<String, List<Letter>> letters = new HashMap<>();
    static final int NUM_LETTERS = 7;
    private static Bag bag; // The bag of letters from which letters are drawn

    public Rack() {
    }
    /**
     * Constructor for the Rack class.
     * Initializes the rack with a set of letters.
     * @param bag The bag of letters from which letters are drawn.
     */
    public Rack(Bag bag) {
        this.bag = bag;
        for (int i = 0; i < NUM_LETTERS; i++) {
            Letter letter = bag.extractLetter();
            this.addLetter(letter);
        }
    }


    /**
     * Adds a letter to the rack.
     * @param letter The letter to be added.
     * @throws IllegalStateException if the rack is full.
     */
    private void addLetter(Letter letter) {
        if (letters.values().stream().mapToInt(List::size).sum() >= NUM_LETTERS) {
            throw new IllegalStateException("Rack is full");
        }

        letters.computeIfAbsent(letter.getSymbol(), k -> new ArrayList<>()).add(letter);
    }

    /**
     * Gets a letter from the rack, removing it from the rack, and replacing it with a new letter from the bag.
     * @param symbol The symbol of the letter to get.
     * @return The letter that was removed, or null if no such letter exists.
     */
    public Letter getLetter(String symbol) {
        List<Letter> letterList = letters.get(symbol);
        if (letterList != null && !letterList.isEmpty()) {
            Letter letter = letterList.remove(0); // Remove the first letter from the list
            if (letterList.isEmpty()) {
                letters.remove(symbol); // Remove the key if the list is empty
            }

            Letter newLetter = bag.extractLetter();
            this.addLetter(newLetter);
            return letter;
        }
        return null; // Return null if no such letter exists
    }



    /**
     * Checks if the rack is empty.
     * @return true if the rack is empty, false otherwise.
     */
    public void clear() {
        letters.clear();
    }

    /**
     * Returns the letters in the rack.
     * @return A set of letters in the rack.
     */
    public void print() {
        for (Map.Entry<String, List<Letter>> entry : letters.entrySet()) {
            String symbol = entry.getKey();
            List<Letter> letterList = entry.getValue();
            System.out.print("Symbol: " + symbol + " -> ");
            for (Letter letter : letterList) {
                System.out.print(letter.getSymbol() + " ");
            }
            System.out.println();
        }
        System.out.println("------------------------------");
    }
}