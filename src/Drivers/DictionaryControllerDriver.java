package Drivers;
import DomainClasses.DictionaryController;
import java.io.IOException;
import java.util.Scanner;

public class DictionaryControllerDriver {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DictionaryController controller = new DictionaryController();
        boolean exit = false;

        System.out.println("\n--- Dictionary Controller Driver ---");
        System.out.println("\nBienvenido al driver de DictionaryController.");
        
        while (!exit) {
            System.out.println("\n-----------------------------------------------");
            System.out.println("Opciones disponibles:");
            System.out.println("1. Crear un nuevo diccionario");
            System.out.println("2. Cargar palabras desde un archivo");
            System.out.println("3. Agregar una palabra manualmente");
            System.out.println("4. Eliminar una palabra");
            System.out.println("5. Buscar una palabra en el diccionario");
            System.out.println("6. Mostrar el idioma del diccionario");
            System.out.println("7. Salir");
            System.out.println("-----------------------------------------------");
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
                    String dictNameForRemove = scanner.nextLine();
                    System.out.print("Introduce la palabra que deseas eliminar: ");
                    String wordToRemove = scanner.nextLine();
                    try {
                        controller.removeWordFromDictionary(dictNameForRemove, wordToRemove);
                        System.out.println("Palabra '" + wordToRemove + "' eliminada del diccionario '" + dictNameForRemove + "'.");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 5:
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

                case 6:
                    System.out.print("Introduce el nombre del diccionario: ");
                    String dictNameForLanguage = scanner.nextLine();
                    try {
                        String languageForLanguage = controller.getDictionaryLanguage(dictNameForLanguage);
                        System.out.println("El idioma del diccionario '" + dictNameForLanguage + "' es: " + languageForLanguage);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 7:
                    exit = true;
                    System.out.println("Saliendo del driver.");
                    break;

                default:
                    System.out.println("Opción no válida. Intenta de nuevo.");
            }
        }

        scanner.close();
    }
}