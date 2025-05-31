package DomainLayer.DomainClasses;
import java.io.*;
import java.util.*;

/** 
 * DictionaryController.java
 * Author: Iván Alcubierre
 * Esta clase es el controlador de los diccionarios.
 * Se encarga de gestionar la creación, eliminación y acceso a los diccionarios.
 * También permite añadir palabras a los diccionarios desde archivos de texto.
 */
public class DictionaryController {

    //Atributos del controlador

    private Map<String, Dictionary> dictionaries = new HashMap<>(); // Mapa que almacena los diccionarios con su nombre como clave
    private static DictionaryController c;                          // Instancia única de la clase DictionaryController (Singleton)

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
     * Si no existe, la crea.
     */
    public static DictionaryController getInstance() {
        if (c == null) c = new DictionaryController();
        return c;
    }

    /**
     * Añade un nuevo diccionario al controlador.
     * pre: dictionaryName es el nombre del diccionario que se va a añadir
     * post: añade un nuevo diccionario al controlador o lanza una excepción si ya existe
     * @param dictionaryName El nombre del diccionario que se va a añadir.
     * @param language El idioma del diccionario.
     * @throws IllegalArgumentException Si el diccionario ya existe.
     * @throws RuntimeException Si ocurre un error al crear el diccionario.
     */
    public void addDictionary(String dictionaryName, String language) {
        
        if (dictionaryExists(dictionaryName)) {
            throw new IllegalArgumentException("El diccionario con el nombre '" + dictionaryName + "' ya existe.");
        }

        try
        {
            Dictionary dictionary = new Dictionary(dictionaryName, language);
            dictionaries.put(dictionaryName, dictionary);
        }
        catch (IOException e)
        {
            throw new RuntimeException("Error al crear el diccionario: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene un diccionario del controlador.
     * pre: dictionaryName es el nombre del diccionario que se va a buscar
     * post: devuelve el diccionario con el nombre especificado o lanza una excepción si no existe
     * @param dictionaryName El nombre del diccionario que se va a buscar.
     * @return El diccionario con el nombre especificado.
     * @throws IllegalArgumentException Si el diccionario no existe.
     */
    public Dictionary getDictionary(String dictionaryName) {

        if (!dictionaryExists(dictionaryName)) {
            throw new IllegalArgumentException("El diccionario con el nombre '" + dictionaryName + "' no existe.");
        }

        return dictionaries.get(dictionaryName);
    }

    /**
     * Obtiene todos los diccionarios del controlador.
     * pre: True
     * post: devuelve un mapa con todos los diccionarios del controlador
     * @return Un mapa donde las claves son los nombres de los diccionarios y los valores son los objetos Dictionary.
     */
    public Map<String, Dictionary> getDictionaries() {
        return dictionaries;
    }

    /**
     * Verifica si un diccionario existe en el controlador.
     * pre: dictionaryName es el nombre del diccionario que se va a buscar
     * post: devuelve true si el diccionario existe, false en caso contrario
     * @param dictionaryName El nombre del diccionario que se va a buscar.
     * @return true si el diccionario existe, false en caso contrario.
     */
    public boolean dictionaryExists(String dictionaryName) {

        return dictionaries.containsKey(dictionaryName);
    }

    /**
     * Elimina todos los diccionarios del controlador.
     * pre: True
     * post: elimina todos los diccionarios del controlador.
     */
    public void clearDictionaries() {

        dictionaries.clear();
    }
}