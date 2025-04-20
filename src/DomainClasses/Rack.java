import java.util.*;

/**
 * Rack.java
 * This class represents a player's rack in a match. It contains a set of letters that the player can use to form words
 * @author Kai Knox
 */
public class Rack {
    private Map<String, Set<Letter>> letters = new HashMap<>();
    static const int NUM_LETTERS = 7;
    private static Bag bag; // The bag of letters from which letters are drawn

    /**
     * Constructor for the Rack class.
     * Initializes the rack with a set of letters.
     * @param bag The bag of letters from which letters are drawn.
     */
    public void Rack(Bag bag) {
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
        if (letters.values().stream().mapToInt(Set::size).sum() >= NUM_LETTERS) {
            throw new IllegalStateException("Rack is full");
        }

        letters.computeIfAbsent(letter.getSymbol(), k -> new HashSet<>()).add(letter);
    }

    /**
     * Gets a letter from the rack, removing it from the rack, and replacing it with a new letter from the bag.
     * @param symbol The symbol of the letter to get.
     * @return The letter that was removed, or null if no such letter exists.
     */
    public Letter getLetter(String symbol) {
        Set<Letter> letterSet = letters.get(symbol);
        if (letterSet != null && !letterSet.isEmpty()) {
            Iterator<Letter> iterator = letterSet.iterator();
            Letter letter = iterator.next();
            iterator.remove(); // Remove the letter from the set
            if (letterSet.isEmpty()) {
                letters.remove(symbol); // Remove the key if the set is empty
            }

            Letter letter2 = bag.extractLetter();
            this.addLetter(letter2);
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
    public print() {
        System.out.println("------------------------------");
        System.out.println("Rack: " + letters);
        System.out.println("------------------------------");
    }
}