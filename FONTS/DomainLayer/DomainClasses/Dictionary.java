package DomainLayer.DomainClasses;
import java.util.*;
import java.io.*;
//import java.util.*;

/**
 * Dictionary.java
 * Author: Ziheng Zhang
 * Esta clase representa un diccionario.
 * Se encarga de gestionar la creación, eliminación y acceso a las palabras del diccionario.
 */
public class Dictionary
{

    /**
     * Atributos de la clase Dictionary.
     * @param name Nombre del diccionario.
     * @param dawg Estructura de datos que contiene el DAWG (Directed Acyclic Word Graph).
     * @param characters Conjunto de caracteres que contiene el diccionario.
     */
    private String name;                
    private Dawg dawg;                  
    private Set<String> characters;     

    /**
     * Constructor de la clase Dictionary.
     * pre: name es el nombre del diccionario que se va a crear
     * post: crea un nuevo diccionario con el nombre especificado y un DAWG vacío con el idioma correspondiente
     * @param name Nombre del diccionario.
     * @param language Idioma del diccionario (por ejemplo, "es" para español, "en" para inglés, "ca" para catalán).
     * @throws IOException Si ocurre un error al crear el DAWG.
     */
    public Dictionary(String name, String language) throws IOException
    {
        // Create a new dictionary with the specified language
        this.name = name;
        this.dawg = new Dawg(language);
        if(language == "es")
        {
            characters = new HashSet<>(Arrays.asList("A","B","C","D","E","F","G","H","I","J","K",
                    "L","M","N","Ñ","O","P","Q","R","S","T","U","V","W","X","Y","Z", "CH", "LL", "RR"));
        }
        else if(language == "en")
        {
            characters = new HashSet<>(Arrays.asList("A","B","C","D","E","F","G","H","I","J","K",
                    "L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"));
        }
        else if(language == "ca")
        {
            characters = new HashSet<>(Arrays.asList("A","B","C","D","E","F","G","H","I","J","K",
                    "L","M","N","O","P","Q","R","S","T","U","V","W","X","Z", "NY", "L·L", "Ç"));
        }
    }

    /**
     * Devuelve el nombre del diccionario.
     * pre: True
     * post: devuelve el nombre del diccionario
     * @return El nombre del diccionario, por ejemplo, "es" para español, "en" para inglés, "ca" para catalán.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Devuelve el Dawg del diccionario.
     * pre: True
     * post: devuelve el Dawg del diccionario
     * @return El Dawg asociado al diccionario, que contiene las palabras y su estructura.
     */
    public Dawg getDawg()
    {
        return dawg;
    }

    /**
     * Devuelve los caracteres del diccionario.
     * pre: True
     * post: devuelve los caracteres del diccionario
     * @return Un conjunto de caracteres que contiene los caracteres válidos en el diccionario.
     */
    public Set<String> getCharacters()
    {
        return characters;
    }

    /**
     * Elimina una palabra del diccionario.
     * pre: word es la palabra a eliminar del diccionario
     * post: elimina la palabra del diccionario
     * @param word La palabra que se desea eliminar del diccionario.
     */
    public void removeWord(String word)
    {
        dawg.removeWord(word);
    }

    /**
     * Comprueba si una palabra existe en el diccionario.
     * pre: word es la palabra a comprobar
     * post: devuelve true si la palabra existe en el diccionario, false en caso contrario
     * @param word La palabra que se desea comprobar.
     * @return true si la palabra existe en el diccionario, false en caso contrario.
     */
    public boolean existsWord(String word)
    {
        return dawg.existsWord(word);
    }

    /**
     * Comprueba si un prefijo existe en el diccionario.
     * pre: prefix es el prefijo a comprobar
     * post: devuelve true si el prefijo existe en el diccionario, false en caso contrario
     * @param prefix El prefijo que se desea comprobar.
     * @return true si el prefijo existe en el diccionario, false en caso contrario.
     */
    public boolean isPrefix(String prefix)
    {
        return dawg.isPrefix(prefix);
    }
}