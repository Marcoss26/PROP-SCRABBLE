import java.util.*;

public class Dictionary
{

    //Atributos del diccionario
    String name; // Nombre del diccionario
    Dawg dawg; // Estructura de datos que contiene el DAWG

    // pre: True
    // post: creadora por defecto
    public Dictionary()
    {
        // Create a new dictionary
    }


    public Dictionary(String name, String language)
    {
        // Create a new dictionary with the specified language
        this.name = name;
        this.dawg = new Dawg(language);
    }

    public String getLanguage()
    {
        return dawg.getLanguage();
    }

    public void addWord(String word)
    {
        dawg.addWord(word);
    }

    public void removeWord(String word)
    {
        dawg.removeWord(word);
    }

    //pre: rack es una cadena de caracteres que representa las letras disponibles
    //post: devuelve una cadena de caracteres que representa la palabra encontrada en el diccionario
    //       o null si no se encuentra ninguna palabra
    //       (en caso de que haya más de una palabra, devuelve la primera que encuentra)
    public String searchWord(String rack)
    {
        // Search for a word in the dictionary
        return dawg.findWord(rack);
    }
}