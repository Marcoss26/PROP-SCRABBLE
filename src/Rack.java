import java.util.*;

public class Rack {
    private Set<Letter> letters = new Set<>();
    static const int NUM_LETTERS = 7;

    public void Rack() {
        for (int i = 0; i < NUM_LETTERS; i++) {
            letters.add(new Letter());
        }
    }

    public void addLetter(Letter letter) {
        letters.add(letter);
    }
    public void removeLetter(Letter letter) {
        letters.remove(letter);
    }
    public void clearRack() {
        letters.clear();
    }
}