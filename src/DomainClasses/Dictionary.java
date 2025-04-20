import java.util.*;

/**
 * Dictionary.java
 * @author: Ivan Alcubierre
 * Esta clase representa un diccionario.
 * Se encarga de gestionar la creación, eliminación y acceso a las palabras del diccionario.
 */
public class Dictionary
{

    //Atributos del diccionario
    /**
     * @param name Nombre del diccionario.
     * @param dawg Estructura de datos que contiene el DAWG.
     */
    String name; // Nombre del diccionario
    Dawg dawg; // Estructura de datos que contiene el DAWG

    /**
     * Constructor de la clase Dictionary.
     * pre: True
     * post: crea un nuevo diccionario.
     */
    public Dictionary()
    {
        // Create a new dictionary
    }

    /**
     * Constructor de la clase Dictionary.
     * pre: name es el nombre del diccionario que se va a crear
     * post: crea un nuevo diccionario con el nombre especificado y un DAWG vacío con el idioma correspondiente
     * @param name Nombre del diccionario a crear
     */
    public Dictionary(String name)
    {
        // Create a new dictionary with the specified language
        this.name = name;
        this.dawg = new Dawg(name);
    }

    /**
     * Devuelve el idioma del diccionario.
     * pre: True
     * post: devuelve el idioma del diccionario
     * @return El idioma del diccionario.
     */
    public String getLanguage()
    {
        return dawg.getLanguage();
    }

    /**
     * Añade una palabra al diccionario.
     * @param word Palabra a añadir al diccionario.
     * pre: word es la palabra a añadir al diccionario
     * post: añade la palabra al diccionario
     */
    public void addWord(String word)
    {
        dawg.addWord(word);
    }

    /**
     * Elimina una palabra del diccionario.
     * @param word Palabra a eliminar del diccionario.
     * pre: word es la palabra a eliminar del diccionario
     * post: elimina la palabra del diccionario
     */
    public void removeWord(String word)
    {
        dawg.removeWord(word);
    }

    /**
     * Comprueba si una palabra existe en el diccionario.
     * @param word Palabra a comprobar.
     * pre: word es la palabra a comprobar
     * post: devuelve true si la palabra existe en el diccionario, false en caso contrario
     * @return true si la palabra existe, false en caso contrario
     */
    public boolean existsWord(String word)
    {
        return dawg.existsWord(word);
    }
}