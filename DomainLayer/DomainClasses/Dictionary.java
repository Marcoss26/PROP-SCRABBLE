<<<<<<< HEAD:src/DomainClasses/Dictionary.java
package DomainClasses;
import java.util.*;
import java.io.*;
=======
package DomainLayer.DomainClasses;
//import java.util.*;
>>>>>>> 2a25dff64636ba3821e0499da2fccb8a543eb761:DomainLayer/DomainClasses/Dictionary.java

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
    private String name; // Nombre del diccionario
    private Dawg dawg; // Estructura de datos que contiene el DAWG
    private Set<String> characters; // Conjunto de caracteres que contiene el diccionario

    /**
     * Constructor de la clase Dictionary.
     * pre: name es el nombre del diccionario que se va a crear
     * post: crea un nuevo diccionario con el nombre especificado y un DAWG vacío con el idioma correspondiente
     * @param name Nombre del diccionario a crear
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
     * Devuelve el idioma del diccionario.
     * pre: True
     * post: devuelve el idioma del diccionario
     * @return El idioma del diccionario.
     */
    public String getLanguage()
    {
        return dawg.getLanguage();
    }

    public Dawg getDawg()
    {
        return dawg;
    }

    public Set<String> getCharacters()
    {
        return characters;
    }

    /**
     * Añade una palabra al diccionario.
     * @param word Palabra a añadir al diccionario.
     * pre: word es la palabra a añadir al diccionario
     * post: añade la palabra al diccionario
     */


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

    public boolean isPrefix(String prefix)
    {
        return dawg.isPrefix(prefix);
    }
}