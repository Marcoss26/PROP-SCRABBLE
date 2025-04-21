package DomainClasses;
import java.util.*;

public class Bag
{
    //Atributos de la bolsa
    //private List<Letter> letters = new ArrayList<>(); // Conjunto de fichas que hay en la bolsa
    private Map<Letter, Integer> lettersMap = new HashMap<>(); 
    private int totalLetters; // Total de fichas en la 
    public Bag(Map<Letter, Integer> letters, int totalLetters) {
        this.lettersMap = letters; // Inicializamos el conjunto de letras
        this.totalLetters = totalLetters; // Inicializamos el total de fichas
    }

    public int getNumLetters(){
        return totalLetters;
    }

    public Map<Letter, Integer> getLetters() {
        return lettersMap; // Devolvemos el conjunto de letras
    }

    public void setLetters(Map<Letter, Integer> letters) {
        if(letters == null) {
            throw new IllegalArgumentException("Letters cannot be null"); 
        }
        this.lettersMap = letters;
        this.totalLetters = 100;
    }

    public Letter extractLetter(){
        if (isEmpty()) { // Comprobamos si la bolsa está vacía
            throw new IllegalStateException("The bag is empty, you cannot take more letters."); 
        }
        Random random = new Random(); 
        List<Letter> lettersKeys = new ArrayList<>(lettersMap.keySet()); 
        int index = random.nextInt(lettersKeys.size()); 
        Letter extletter = lettersKeys.get(index);
        lettersMap.merge(extletter, -1, Integer::sum); //Actualizo la cantidad de fichas que hay de ese tipo
        if(lettersMap.get(extletter) == 0) { 
            lettersMap.remove(extletter); 
        }
        
        totalLetters--; 
        return extletter; 
       
    }

    public void addLetter(Letter letter) {
        if(!lettersMap.containsKey(letter)) { // Si la letra no está en el mapa, la añadimos
            lettersMap.put(letter, 1); // Añadimos la letra al mapa con cantidad 1
        } else {
            lettersMap.merge(letter, 1, Integer::sum); // Si ya está, aumentamos su cantidad
        }
        
        totalLetters++; // Aumentamos el total de letras en la bolsa
    }

    public void addSetOfLetters(List<Letter> letters) {
        
        for (Letter letter : letters) {
            addLetter(letter); // Añadimos cada letra al conjunto de letras
        }
    }
    //pre: La bolsa tiene al menos 7 fichas
    //post: devuelve un conjunto de letras extraídas de la bolsa
    //       y las elimina de la bolsa
    public List<Letter> extractSetOfLetters(Integer quantity) {
        
        List<Letter> extractedLetters = new ArrayList<>(); 
        for (int i = 0; i < quantity ;++i) {    //aqui puedo poner una excepción que diga que la bolsa no tiene suficientes fichas para hacer el cambio 
            extractedLetters.add(extractLetter()); //añadimos al conjunto a devolver
        }
        return extractedLetters; // Devolvemos el conjunto de letras extraídas
    }

    //función para cambiar las 7 letras del rack cuando el jugador lo pida
    //Pre: matchId es el id de la partida a la que pertenece la bolsa
    //letters es el conjunto de letras que el jugador quiere cambiar
    //Post: devuelve un conjunto de letras que el jugador ha cambiado por las letras que ha devuelto
    //Post: el jugador ha devuelto las letras que ha cambiado a la bolsa   
    public List<Letter> changeRackLetters(List<Letter> letters) {
        //si no hay 7 letras en el rack es porque ya no quedan mas en la bolsa y por lo tanto no se pueden cambiar
        if(letters.size() != 7) { 
            throw new IllegalStateException("Not enough letters in the bag to extract."); 
        }
        addSetOfLetters(letters); 
        return extractSetOfLetters(7); // Devolvemos el conjunto de letras que se van a devolver al jugador
    }
    

    public boolean isEmpty() {
        return totalLetters == 0; // Comprobamos si la bolsa está vacía
    }

    public void displayBag() {
        System.out.println("Total letters in bag: " + totalLetters + "\n");
        System.out.println("Letters inside the bag: \n");
        
        for (Map.Entry<Letter, Integer> entry : lettersMap.entrySet()) {
            System.out.println("Letter: " ); 
            entry.getKey().displayLetter(); 
            System.out.println(", Quantity: " + entry.getValue() + "\n");
        }
        
        
    }
}