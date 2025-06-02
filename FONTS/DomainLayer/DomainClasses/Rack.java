package DomainLayer.DomainClasses;
import java.util.*;

/**
 * Rack.java
 * Esta clase representa el rack de un jugador en una partida. Contiene un conjunto de letras que el jugador puede usar para formar palabras.
 * @author Kai Knox
 */

public class Rack {
    private List<Letter> letters = new ArrayList<>();
    static final int NUM_LETTERS = 7;
    private Bag bag; // La bolsa de letras de la que se extraen las letras para el rack

    /**
     * Constructora de la clase Rack
     * Pre: true
     * Post: Se ha creado una instancia de la clase Rack vacía
     */

    public Rack() {
    }

    /**
     * Constructora de la clase Rack
     * Pre: true
     * Post: Se ha creado una instancia de la clase Rack con un conjunto de letras extraídas de la bolsa
     * @param bag La bolsa de letras de la que se extraen las letras para el rack
     */

    public Rack(Bag bag, boolean fill) {
        this.bag = bag;
        if (fill) this.letters = bag.extractSetOfLetters(NUM_LETTERS);
    }

    /**
     * Constructora de la clase Rack
     * Pre: true
     * Post: Se ha creado una instancia de la clase Rack con un conjunto de letras y una bolsa
     * @param bag La bolsa de letras de la que se extraen las letras para el rack
     * @param letters El conjunto inicial de letras en el rack
     */

    public Rack(Bag bag, List<Letter> letters) {
        this.bag = bag;
        this.letters = letters;
    }

    /**
     * Añade una letra al rack
     * Pre: el rack no debe estar llena
     * Post: La letra se añade al rack
     * @param letter
     */

     public void addLetter(Letter letter) {
        if (letters.size() >= NUM_LETTERS) {
            throw new IllegalStateException("Rack is full");
        }
        letters.add(letter);
    }

        /**
     * Añade multiples letra al rack
     * Pre: el rack no debe estar llena
     * Post: Las letras se añaden al rack
     * @param letters
     */

     public void addLetters(List<Letter> letters) {
        if (letters.size() >= NUM_LETTERS) {
            throw new IllegalStateException("Rack is full");
        }
        if (this.letters.size() + letters.size() > NUM_LETTERS) {
            throw new IllegalStateException("Adding these letters would exceed the rack's capacity");
        }
        for (Letter letter : letters) {
            this.letters.add(letter);
        }
    }

    /**
     * Consultora de la información de las letras en el rack
     * Pre: El rack no debe estar vacío
     * Post: Retorna un ArrayList que contiene los símbolos y valores de las letras en el rack
     * @return
     */

    public ArrayList<String> getInfoLetters() {
        ArrayList<String> infoLetters = new ArrayList<>();
        for (Letter letter : letters) {
            infoLetters.add(letter.getSymbol());
            infoLetters.add(String.valueOf(letter.getValue()));
        }
        return infoLetters;
    }

    /**
     * Consultora que obtiene una letra de el rack, eliminándola de la misma
     * Pre: Debe existir un rack
     * Post: Retorna la letra que fue eliminada, o null si no existe tal letra
     * @param symbol
     * @return
     */

    public Letter getLetter(String symbol) {
        //System.out.println("I'm getting the letter " + symbol + " from the rack.");
        int blank_i = -1;
        for (int i = 0; i < letters.size(); i++) {
            if (letters.get(i).getSymbol().equals(symbol)) {
                System.out.println("Found letter " + symbol + " at index " + i);
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
      * Mezcla las letras del rack
      * Pre: El rack no debe estar vacío
      * Post: Las letras en el rack se mezclan aleatoriamente
      */

    public void shuffle() {
        Collections.shuffle(letters);
    }

    /**
     * Consultora del tamaño del rack
     * Pre: El rack debe existir
     * Post: Retorna el número de letras en el rack
     * @return
     */

    public int getSize() {
        return letters.size();
    }

    /**
     * Vacía el rack, eliminando todas las letras
     * Pre: El rack debe existir
     * Post: El rack está vacío
     */

    public void clear() {
        letters.clear();
    }

    /**
      * Sustituye las letras en el rack por nuevas letras de la bolsa
      * Pre: Los símbolos deben ser válidos y existir en el rack
      * Post: Las letras especificadas se sustituyen por nuevas letras de la bolsa
      * @param symbols Un String que contiene los símbolos de las letras a sustituir, separados por guiones bajos
      */

    public void replaceLetters(String symbols) throws IllegalArgumentException {
        String[] symbols2 = symbols.split("_");

        if (bag.getNumLetters() < symbols2.length) {
            throw new IllegalArgumentException("Cannot replace all letters in the rack with the same number of letters in the bag");
        }

        for (String symbol : symbols2) {
            for (int i = 0; i < letters.size(); i++) {
                if (letters.get(i).getSymbol().equals(symbol)) {
                    bag.addLetter(letters.get(i));
                    letters.remove(i);
                    letters.add(bag.extractLetter());
                    break;
                }
            }
        }
    }

    /**
      * Imprime las letras del rack
      * Pre: El rack debe existir
      * Post: Las letras en el rack se imprimen en la consola
      */

    public void print() {
        System.out.print("Rack: ");
        for (Letter letter : letters) {
            System.out.print(letter.getSymbol() + " ");
        }
        System.out.println();
        System.out.println("------------------------------");
    }

    /**
     * Consultora de las letras del rack
     * Pre: El rack debe existir
     * Post: Retorna la lista de letras del rack
     * @return La lista de letras del rack
     */

    public List<Letter> getLetters() {
        return letters;
    }

    public String getLetterSymbol(int index) {
        if (index < 0 || index >= letters.size()) {
            throw new IndexOutOfBoundsException("Index out of bounds for the rack");
        }
        return letters.get(index).getSymbol();
    }

    /**
     * Elimina una letra del rack
     * Pre: La letra debe existir en el rack
     * Post: La letra se elimina del rack
     * @param letter La letra a eliminar del rack
     */

    public void removeLetter(Letter letter) {
        letters.remove(letter);
    }

    /**
     * Clona el rack
     * Pre: El rack debe existir
     * Post: Retorna un nuevo objeto Rack que es una copia del rack actual
     * @return Un nuevo objeto Rack que es una copia del rack actual
     */

    @Override
    public Rack clone() {
        Rack clonedRack = new Rack();
        for (Letter letter : this.letters) {
            clonedRack.addLetter(new Letter(letter.getSymbol(), letter.getValue())); // Asumiendo que Letter tiene un constructor de copia
        }
        return clonedRack;
    }
}
