package DomainLayer.DomainClasses;
import java.util.*;

/**
 * Bag.java
 * @author Marcos Arroyo
 * Esta clase representa una bolsa de letras.
 * Se encarga de gestionar las letras que hay en la bolsa,
 * así como de extraer letras aleatorias, añadir letras y cambiar letras del rack.
 */
public class Bag
{
    /**
     * Atributos de la bolsa
     * @param lettersMap Mapa que almacena las letras y su cantidad en la bolsa
     * @param totalLetters Total de fichas en la bolsa
     */
    private Map<Letter, Integer> lettersMap = new HashMap<>(); 
    private int totalLetters; // Total de fichas en la 
    
    // Constructora que inicializa la bolsa con un conjunto de letras y el total de fichas
    /**
     * Constructor de la clase Bag.
     * Pre: letters es un mapa que contiene las letras y totalLetters es el total de fichas en la bolsa.
     * Post: Crea una nueva bolsa con el conjunto de letras y el total de fichas.
     * @param letters
     * @param totalLetters
     */
    public Bag(Map<Letter, Integer> letters, int totalLetters) {
        this.lettersMap = letters; // Inicializamos el conjunto de letras
        this.totalLetters = totalLetters; // Inicializamos el total de fichas
    }

    /**
     * Devuelve el total de letras en la bolsa.
     * Pre: True
     * Post: Devuelve el total de letras en la bolsa.
     * @return el total de letras en la bolsa
     */
    public int getNumLetters(){
        return totalLetters;
    }

    /**
     * Obtiene el conjunto de letras que hay en la bolsa.
     * Pre: True
     * Post: Devuelve un mapa que contiene las letras y su cantidad en la bolsa.
     * @return un mapa que contiene las letras y su cantidad en la bolsa
     */
    public Map<Letter, Integer> getLetters() {
        return lettersMap; // Devolvemos el conjunto de letras
    }

    /**
     * Establece el conjunto de letras que hay en la bolsa.
     * Pre: letters es un mapa que contiene las letras y su cantidad en la bolsa.
     * Post: Establece el conjunto de letras y el total de fichas en la bolsa.
     * @param letters
     * @exception IllegalArgumentException si letters es nulo lanzará una excepción
     */
    public void setLetters(Map<Letter, Integer> letters) {
        if(letters == null) {
            throw new IllegalArgumentException("Letters cannot be null"); 
        }
        this.lettersMap = letters;
        this.totalLetters = 100;
    }

    /**
     * Extrae una letra aleatoria de la bolsa.
     * Pre: True
     * Post: Extrae una letra aleatoria de la bolsa y la elimina de la bolsa.
     * @return la letra extraída de la bolsa
     * @exception IllegalStateException si la bolsa está vacía lanzará una excepción
     */
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

    public List<Letter> extractLetterforRack(int lettersTochange){
        List<Letter> extractedLetters = new ArrayList<>();
        if(lettersTochange > totalLetters) { // Comprobamos si hay suficientes letras en la bolsa
            extractedLetters = extractSetOfLetters(totalLetters); // Extraemos todas las letras disponibles
        }
        else{
            extractedLetters = extractSetOfLetters(lettersTochange); // Extraemos el número de letras especificado
        }
        return extractedLetters;
    }

    /**
     * Añade una letra al conjunto de letras de la bolsa.
     * Pre: letter es la letra que se va a añadir a la bolsa.
     * Post: Añade la letra al conjunto de letras de la bolsa o aumenta su cantidad si ya existe.
     * @exception IllegalArgumentException si letter es nula lanzará una excepción
     * @param letter
     */
    public void addLetter(Letter letter) {
        if(letter == null) { // Comprobamos si la letra es nula
            throw new IllegalArgumentException("letter no puede ser nula"); 
        }
        
        // Si la letra no está en el mapa, la añadimos
        // Si ya está, aumentamos su cantidad
        if(!lettersMap.containsKey(letter)) { 
            lettersMap.put(letter, 1); // Añadimos la letra al mapa con cantidad 1
        } else {
            lettersMap.merge(letter, 1, Integer::sum); // Aumentamos su cantidad
        }
        
        totalLetters++; // Aumentamos el total de letras en la bolsa
    }

    /**
     * Añade un conjunto de letras al conjunto de letras de la bolsa.
     * Pre: letters es una lista de letras que se van a añadir a la bolsa.
     * Post: Añade cada letra del conjunto al conjunto de letras de la bolsa.
     * @param letters
     */
    public void addSetOfLetters(List<Letter> letters) {
        
        for (Letter letter : letters) {
            addLetter(letter); // Añadimos cada letra al conjunto de letras
        }
    }


    /**
     * Extrae un conjunto de letras de la bolsa.
     * Pre: quantity es el número de letras que se van a extraer de la bolsa.
     * Post: Extrae un conjunto de letras de la bolsa y las elimina de la bolsa.
     * @exception IllegalStateException si no hay suficientes letras en la bolsa para extraer lanzará una excepción
     * @param quantity
     * @return una lista de letras extraídas de la bolsa
     */
    public List<Letter> extractSetOfLetters(Integer quantity) {
        
        if (quantity > totalLetters) { // Comprobamos si hay suficientes letras en la bolsa
            throw new IllegalStateException("No hay suficientes letras en la bolsa para extraer."); 
        }
        List<Letter> extractedLetters = new ArrayList<>();
        for (int i = 0; i < quantity ;++i) {    
            extractedLetters.add(extractLetter()); //añadimos al conjunto a devolver
        }
        return extractedLetters; // Devolvemos el conjunto de letras extraídas
    }

    /**
     * Cambia las letras del rack por un conjunto de letras de la bolsa.
     * Pre: letters es una lista de letras que el jugador quiere cambiar.
     * Post: Extrae un conjunto de letras de la bolsa y las añade al rack del jugador.
     * @exception IllegalStateException si no hay suficientes letras en la bolsa para extraer lanzará una excepción
     * @param letters
     * @return una lista de letras extraídas de la bolsa que se devuelven al jugador
     */
    public List<Letter> changeRackLetters(List<Letter> letters) {
        //si no hay 7 letras en el rack es porque ya no quedan mas en la bolsa y por lo tanto no se pueden cambiar
        if(letters.size() > totalLetters) { 
            throw new IllegalStateException("No hay suficientes letras en la bolsa para cambiar."); 
        }
        List<Letter> extLetters = extractSetOfLetters(letters.size()); // Extraemos las letras del rack
        addSetOfLetters(letters); 
        return extLetters;// Devolvemos el conjunto de letras que se van a devolver al jugador
    }
    
    /**
     * Comprueba si la bolsa está vacía.
     * Pre: True
     * Post: Devuelve true si la bolsa está vacía, false en caso contrario.
     * @return true si la bolsa está vacía, false en caso contrario
     */
    public boolean isEmpty() {
        return totalLetters == 0;
    }

    /**
     * Muestra el contenido de la bolsa, esta funcion es auxiliar, es prescindible para el funcionamiento del juego.
     * Pre: True
     * Post: Muestra el total de letras en la bolsa y las letras que hay en la bolsa.
     */
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