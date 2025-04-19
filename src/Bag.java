import java.util.*;

public class Bag
{
    //Atributos de la bolsa
    private List<Letter> letters = new ArrayList<>(); // Conjunto de fichas que hay en la bolsa
    private int totalLetters; // Total de fichas en la 
    public Bag(List<Letter> letters) {
        this.letters = letters; // Inicializamos el conjunto de letras
        this.totalLetters = letters.size(); // Inicializamos el total de fichas
    }

    public int getNumLetters(){
        return totalLetters;
    }

    public List<Letter> getLetters() {
        return letters; // Devolvemos el conjunto de letras
    }

    public void setLetters(List<Letter> letters) {
        if(letters == null) {
            throw new IllegalArgumentException("Letters cannot be null"); 
        }
        this.letters = letters;
        this.totalLetters = 100;
    }

    public Letter extractLetter(String playerId){
        if (isEmpty()) { // Comprobamos si la bolsa está vacía
            throw new IllegalStateException("The bag is empty, you cannot take more letters."); 
        }
        Random random = new Random(); 
        int index = random.nextInt(letters.size()); 
        Letter extletter = letters.get(index);
        letters.remove(index); 
        extletter.setIdPlayer(playerId); 
        totalLetters--; 
        return extletter; 
       
    }

    public void addLetter(Letter letter) {
        letters.add(letter); // Añadimos la letra a la bolsa
        totalLetters++; // Aumentamos el total de letras en la bolsa
    }

    public void addSetOfLetters(Set<Letter> letters) {
        
        for (Letter letter : letters) {
            addLetter(letter); // Añadimos cada letra al conjunto de letras
        }
    }
    //pre: La bolsa tiene al menos 7 fichas
    //post: devuelve un conjunto de letras extraídas de la bolsa
    //       y las elimina de la bolsa
    public Set<Letter> extractSetOfLetters(String playerId) {
        if(totalLetters < 7) { 
            throw new IllegalStateException("Not enough letters in the bag to extract."); 
        }
        Set<Letter> extractedLetters = new HashSet<>(); 
        for (int i = 0; i < 7 && !isEmpty(); i++) {    //aqui puedo poner una excepción que diga que la bolsa no tiene suficientes fichas para hacer el cambio 
            extractedLetters.add(extractLetter(playerId)); //añadimos al conjunto a devolver
        }
        return extractedLetters; // Devolvemos el conjunto de letras extraídas
    }

    

    public boolean isEmpty() {
        return totalLetters == 0; // Comprobamos si la bolsa está vacía
    }

    public void displayBag() {
        System.out.println("Total letters in bag: " + totalLetters + "\n");
        System.out.println("Letters inside the bag: \n"); 
        
        for (Letter letter : letters) {
            letter.displayLetter(); 
            System.out.println("\n");
        }
    }
}