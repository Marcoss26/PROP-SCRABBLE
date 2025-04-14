import java.util.*;

public class Dictionary
{

    //Atributos del diccionario
    String name; // Nombre del diccionario
    String language; // Idioma del diccionario
    Dawg dawg; // Estructura de datos que contiene el DAWG

    public Dictionary()
    {
        // Create a new dictionary
    }

    public Dictionary(String language)
    {
        // Create a new dictionary with the specified language
        this.language = language;
        this.dawg = new Dawg();
    }

    public createDictionary(String language)
    {
        // Create a new dictionary with the specified language
        new Dictionary(language);
        ///////// leer el archivo de texto y añadir las palabras al DAWG
    }

    public void addWord(String word)
    {
        // Add a word to the dictionary
        dawg.addWord(word);
    }

    public void removeWord(String word)
    {
        // Remove a word from the dictionary
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