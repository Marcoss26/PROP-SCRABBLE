import java.util.*;

public class Bag
{
    //Atributos de la bolsa
    private List<Letter> letters = new ArrayList<>(); // Conjunto de fichas que hay en la bolsa
    private int totalLetters; // Total de fichas en la 
    public Bag() {

        this.totalLetters = 0; // Inicializamos el total de fichas
    }

    public int getNumLetters(){
        return totalLetters;
    }

    public void setLetters(List<Letter> letters) {
        this.letters = letters;
        this.totalLetters = 100;
    }

    public Letter extractLetter(String playerId){
        
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

    public boolean isEmpty() {
        return totalLetters == 0; // Comprobamos si la bolsa está vacía
    }
}