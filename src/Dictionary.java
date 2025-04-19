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


    public Dictionary(String name)
    {
        // Create a new dictionary with the specified language
        this.name = name;
        this.dawg = new Dawg(name);
    }

    //pre: True
    //post: devuelve el lenguaje del diccionario
    public String getLanguage()
    {
        return dawg.getLanguage();
    }

    //pre: word es la palabra a añadir al diccionario
    //post: añade la palabra al diccionario
    public void addWord(String word)
    {
        dawg.addWord(word);
    }

    //pre: word existe dentro del diccionario y es la palabra a eliminar del diccionario
    //post: elimina la palabra del diccionario
    public void removeWord(String word)
    {
        dawg.removeWord(word);
    }

    public boolean existsWord(String word)
    {
        return dawg.existsWord(word);
    }
}