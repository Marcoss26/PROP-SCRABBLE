package DomainClasses;
import java.io.*;
import java.util.*;

/** 
 * DictionaryController.java
 * @author: Ivan Alcubierre
 * Esta clase es el controlador de los diccionarios.
 * Se encarga de gestionar la creación, eliminación y acceso a los diccionarios.
 * También permite añadir palabras a los diccionarios desde archivos de texto.
 */
public class DictionaryController {

    //Atributos del controlador
    /**
     * @param dictionaries Mapa que almacena los diccionarios con su nombre como clave.
     * @param c Instancia única de la clase DictionaryController (Singleton).
     */
    private Map<String, Dictionary> dictionaries = new HashMap<>();
    private static DictionaryController c;

    /**
     * Constructor de la clase DictionaryController.
     * pre: True
     * post: crea un nuevo controlador de diccionarios.
     */
    private DictionaryController() {

        this.dictionaries = new HashMap<>();
    }

    /**
     * Devuelve la instancia única de DictionaryController (Singleton).
     * pre: True
     * post: devuelve la instancia única de DictionaryController.
     * @return La instancia única de DictionaryController.
     */
    public static DictionaryController getInstance() {
        if (c == null) c = new DictionaryController();
        return c;
    }

    /**
     * Añade un nuevo diccionario al controlador.
     * @param dictionaryName Nombre del diccionario a añadir.
     * pre: dictionaryName es el nombre del diccionario que se va a añadir
     * post: añade un nuevo diccionario al controlador o lanza una excepción si ya existe
     * @throws IllegalArgumentException si el diccionario ya existe
     */
    public void addDictionary(String dictionaryName, String language) {
        
        if (dictionaryExists(dictionaryName)) {
            throw new IllegalArgumentException("El diccionario con el nombre '" + dictionaryName + "' ya existe.");
        }

        Dictionary dictionary = new Dictionary(dictionaryName, language);
        dictionaries.put(dictionaryName, dictionary);
    }

    /**
     * Elimina un diccionario del controlador.
     * @param dictionaryName Nombre del diccionario a eliminar.
     * pre: dictionaryName es el nombre del diccionario que se va a eliminar
     * post: elimina el diccionario con el nombre especificado o lanza una excepción si no existe
     * @throws IllegalArgumentException si el diccionario no existe
     */
    public void removeDictionary(String dictionaryName) {

        if (!dictionaryExists(dictionaryName)) {
            throw new IllegalArgumentException("El diccionario con el nombre '" + dictionaryName + "' no existe.");
        }

        // Elimina el diccionario del mapa
        dictionaries.remove(dictionaryName);
    }

    /**
     * Obtiene un diccionario del controlador.
     * @param dictionaryName Nombre del diccionario a obtener.
     * pre: dictionaryName es el nombre del diccionario que se va a buscar
     * post: devuelve el diccionario con el nombre especificado o lanza una excepción si no existe
     * @throws IllegalArgumentException si el diccionario no existe
     * @return El diccionario con el nombre especificado.
     */
    public Dictionary getDictionary(String dictionaryName) {

        if (!dictionaryExists(dictionaryName)) {
            throw new IllegalArgumentException("El diccionario con el nombre '" + dictionaryName + "' no existe.");
        }

        return dictionaries.get(dictionaryName);
    }

    /**
     * Obtiene todos los diccionarios del controlador.
     * @return Un mapa con todos los diccionarios.
     */
    public Map<String, Dictionary> getDictionaries() {
        return dictionaries;
    }
    
    /**
     * Obtiene el idioma de un diccionario.
     * @param dictionaryName Nombre del diccionario a obtener.
     * pre: dictionaryName es el nombre del diccionario que se va a buscar
     * post: devuelve el idioma del diccionario con el nombre especificado o lanza una excepción si no existe
     * @throws IllegalArgumentException si el diccionario no existe
     * @return El idioma del diccionario con el nombre especificado.
     */
    public String getDictionaryLanguage(String dictionaryName) {
        // Verifica si el diccionario existe
        if (!dictionaryExists(dictionaryName)) {
            throw new IllegalArgumentException("El diccionario con el nombre '" + dictionaryName + "' no existe.");
        }

        // Obtén el diccionario
        Dictionary dictionary = getDictionary(dictionaryName);

        // Devuelve el idioma del diccionario
        return dictionary.getLanguage();
    }

    /**
     * Verifica si un diccionario existe en el controlador.
     * @param dictionaryName Nombre del diccionario a verificar.
     * pre: dictionaryName es el nombre del diccionario que se va a buscar
     * post: devuelve true si el diccionario existe, false en caso contrario
     * @return true si el diccionario existe, false en caso contrario
     */
    public boolean dictionaryExists(String dictionaryName) {

        return dictionaries.containsKey(dictionaryName);
    }

    /**
     * Elimina todos los diccionarios del controlador.
     * pre: True
     * post: elimina todos los diccionarios del controlador
     */
    public void clearDictionaries() {

        dictionaries.clear();
    }

    
    /**
     * Añade palabras a un diccionario desde un archivo de texto.
     * @param dictionaryName Nombre del diccionario al que se van a añadir las palabras y del archivo de texto.
     * pre: dictionaryName es el nombre del diccionario donde se van a añadir las palabras y el nombre del archivo de texto
     * post: añade las palabras del archivo al diccionario especificado
     * @throws IOException si ocurre un error al leer el archivo
     * @throws IllegalArgumentException si el diccionario no existe o el archivo no se encuentra
     * @throws FileNotFoundException si el archivo no se encuentra
     */
    public void addWordsToDictionary(String dictionaryName, String wordsFile) throws IOException {
        // Verifica si el diccionario existe
        if (!dictionaryExists(dictionaryName)) {
            throw new IllegalArgumentException("El diccionario con el nombre '" + dictionaryName + "' no existe.");
        }
        
        // Obtén el diccionario
        Dictionary dictionary = getDictionary(dictionaryName);
        
        // Construye la ruta al archivo que esta en la carpeta 'data'
        String fileName = wordsFile + ".txt";
        File file = new File("data/dictionaries/" + fileName);
        if (!file.exists()) {
            throw new FileNotFoundException("El archivo '" + fileName + "' no se encontró en la carpeta 'data/dictionaries'.");
        }
        
        // Lee el archivo línea por línea y añade las palabras al diccionario
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String word;
            while ((word = br.readLine()) != null) {
                dictionary.addWord(word.trim()); // Añade cada palabra al diccionario
            }
        }
    }
    
    /**
     * Añade una palabra a un diccionario.
     * @param dictionaryName
     * @param word
     * pre: dictionaryName es el nombre del diccionario al que se va a añadir la palabra y word es la palabra a añadir
     * post: añade la palabra al diccionario especificado
     * @throws IllegalArgumentException si el diccionario no existe
     * @throws NullPointerException si la palabra es nula
     */
    public void addWordToDictionary(String dictionaryName, String word) {
        // Verifica si el diccionario existe
        if (!dictionaryExists(dictionaryName)) {
            throw new IllegalArgumentException("El diccionario con el nombre '" + dictionaryName + "' no existe.");
        }
        if (word == null || word.isEmpty()) {
            throw new IllegalArgumentException("La palabra no puede ser nula o vacía.");
        }

        // Obtén el diccionario
        Dictionary dictionary = getDictionary(dictionaryName);

        // Añade la palabra al diccionario
        dictionary.addWord(word);
    }

    /**
     * Busca una palabra en un diccionario.
     * @param dictionaryName
     * @param word
     * pre: dictionaryName es el nombre del diccionario donde se va a buscar la palabra y word es la palabra a buscar
     * post: busca la palabra en el diccionario especificado
     * @return true si la palabra existe en el diccionario, false en caso contrario
     * @throws IllegalArgumentException si el diccionario no existe
     * @throws NullPointerException si la palabra es nula
     */
    public boolean searchWordInDictionary(String dictionaryName, String word) {
        // Verifica si el diccionario existe
        if (!dictionaryExists(dictionaryName)) {
            throw new IllegalArgumentException("El diccionario con el nombre '" + dictionaryName + "' no existe.");
        }
        if (word == null || word.isEmpty()) {
            throw new IllegalArgumentException("La palabra no puede ser nula o vacía.");
        }

        // Obtén el diccionario
        Dictionary dictionary = getDictionary(dictionaryName);

        // Busca la palabra en el diccionario
        return dictionary.existsWord(word);
    }

    public void removeWordFromDictionary(String dictionaryName, String word) {
        // Verifica si el diccionario existe
        if (!dictionaryExists(dictionaryName)) {
            throw new IllegalArgumentException("El diccionario con el nombre '" + dictionaryName + "' no existe.");
        }
        if (word == null || word.isEmpty()) {
            throw new IllegalArgumentException("La palabra no puede ser nula o vacía.");
        }

        // Obtén el diccionario
        Dictionary dictionary = getDictionary(dictionaryName);

        // Elimina la palabra del diccionario
        dictionary.removeWord(word);
    }
}