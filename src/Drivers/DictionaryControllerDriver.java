package Drivers;
import DomainClasses.DictionaryController;
import java.io.IOException;
import java.util.Scanner;

public class DictionaryControllerDriver {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DictionaryController controller = new DictionaryController();
        boolean exit = false;

        System.out.println("Bienvenido al driver de DictionaryController.");
        System.out.println("Opciones disponibles:");
        System.out.println("1. Crear un nuevo diccionario");
        System.out.println("2. Cargar palabras desde un archivo");
        System.out.println("3. Agregar una palabra manualmente");
        System.out.println("4. Buscar una palabra en el diccionario");
        System.out.println("5. Mostrar el idioma del diccionario");
        System.out.println("6. Salir");

        while (!exit) {
            System.out.print("\nElige una opción: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (option) {
                case 1:
                    System.out.print("Introduce el nombre del diccionario: ");
                    String dictionaryName = scanner.nextLine();
                    System.out.print("Introduce el idioma del diccionario: ");
                    String language = scanner.nextLine();
                    controller.addDictionary(dictionaryName, language);
                    System.out.println("Diccionario '" + dictionaryName + "' creado.");
                    break;

                case 2:
                    System.out.print("Introduce el nombre del diccionario: ");
                    String dictNameForFile = scanner.nextLine();
                    System.out.print("Introduce el nombre del archivo (debe estar en la carpeta 'data/dictionaries'): ");
                    String languageForFile = scanner.nextLine();
                    try {
                        controller.addWordsToDictionary(dictNameForFile, languageForFile);
                        System.out.println("Palabras cargadas desde el archivo '" + languageForFile + ".txt ' al diccionario '" + dictNameForFile + "'.");
                    } catch (IOException e) {
                        System.out.println("Error al cargar palabras desde el archivo: " + e.getMessage());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 3:
                    System.out.print("Introduce el nombre del diccionario: ");
                    String dictNameForWord = scanner.nextLine();
                    System.out.print("Introduce la palabra que deseas agregar: ");
                    String word = scanner.nextLine();
                    try {
                        controller.addWordToDictionary(dictNameForWord, word);
                        System.out.println("Palabra '" + word + "' agregada al diccionario '" + dictNameForWord + "'.");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 4:
                    System.out.print("Introduce el nombre del diccionario: ");
                    String dictNameForSearch = scanner.nextLine();
                    System.out.print("Introduce la palabra que deseas buscar: ");
                    String searchWord = scanner.nextLine();
                    boolean found = controller.searchWordInDictionary(dictNameForSearch, searchWord);
                    if (found) {
                        System.out.println("La palabra '" + searchWord + "' existe en el diccionario '" + dictNameForSearch + "'.");
                    } else {
                        System.out.println("La palabra '" + searchWord + "' no existe en el diccionario '" + dictNameForSearch + "'.");
                    }
                    break;

                case 5:
                    System.out.print("Introduce el nombre del diccionario: ");
                    String dictNameForLanguage = scanner.nextLine();
                    try {
                        String languageForLanguage = controller.getDictionaryLanguage(dictNameForLanguage);
                        System.out.println("El idioma del diccionario '" + dictNameForLanguage + "' es: " + languageForLanguage);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 6:
                    exit = true;
                    System.out.println("Saliendo del driver. ¡Adiós!");
                    break;

                default:
                    System.out.println("Opción no válida. Intenta de nuevo.");
            }
        }

        scanner.close();
    }
}