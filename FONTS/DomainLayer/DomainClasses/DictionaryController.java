package DomainLayer.DomainClasses;
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
}