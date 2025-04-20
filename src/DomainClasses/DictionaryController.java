import java.io.*;
import java.util.*;

public class DictionaryController {

    //Atributos del controlador
    private Map<String, Dictionary> dictionaries = new HashMap<>();

    // pre: True
    // post: crea un nuevo controlador de diccionarios
    public DictionaryController() {

        this.dictionaries = new HashMap<>();
    }

    // pre: dictionaryName es el nombre del diccionario que se va a añadir
    // post: añade un nuevo diccionario al controlador
    //       o lanza una excepción si ya existe
    public void addDictionary(String dictionaryName) {
        
        if (dictionaryExists(dictionaryName)) {
            throw new IllegalArgumentException("El diccionario con el nombre '" + dictionaryName + "' ya existe.");
        }

        Dictionary dictionary = new Dictionary(dictionaryName);
        dictionaries.put(dictionaryName, dictionary);
    }

    // pre: dictionaryName es el nombre del diccionario que se va a eliminar
    // post: elimina el diccionario con el nombre especificado
    //       o lanza una excepción si no existe
    public void removeDictionary(String dictionaryName) {

        if (!dictionaryExists(dictionaryName)) {
            throw new IllegalArgumentException("El diccionario con el nombre '" + dictionaryName + "' no existe.");
        }

        // Elimina el diccionario del mapa
        dictionaries.remove(dictionaryName);
    }

    // pre: dictionaryName es el nombre del diccionario que se va a buscar
    // post: devuelve el diccionario con el nombre especificado
    //       o lanza una excepción si no existe
    public Dictionary getDictionary(String dictionaryName) {

        if (!dictionaryExists(dictionaryName)) {
            throw new IllegalArgumentException("El diccionario con el nombre '" + dictionaryName + "' no existe.");
        }

        return dictionaries.get(dictionaryName);
    }

    // pre: dictionaryName es el nombre del diccionario que se va a buscar
    // post: devuelve true si el diccionario existe, false en caso contrario
    public boolean dictionaryExists(String dictionaryName) {

        return dictionaries.containsKey(dictionaryName);
    }

    // pre: True
    // post: elimina todos los diccionarios del controlador
    public void clearDictionaries() {

        dictionaries.clear();
    }

    // pre: dictionaryName es el nombre del diccionario donde se van a añadir las palabras
    //      fileName es el nombre del archivo .txt que contiene las palabras a añadir
    // post: añade las palabras del archivo al diccionario especificado
    public void addWordsToDictionary(String dictionaryName) throws IOException {
        // Verifica si el diccionario existe
        if (!dictionaryExists(dictionaryName)) {
            throw new IllegalArgumentException("El diccionario con el nombre '" + dictionaryName + "' no existe.");
        }

        // Obtén el diccionario
        Dictionary dictionary = getDictionary(dictionaryName);

        // Construye la ruta al archivo que esta en la carpeta 'data'
        String fileName = dictionaryName + ".txt";
        File file = new File("../data/dictionaries/" + fileName);
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
}