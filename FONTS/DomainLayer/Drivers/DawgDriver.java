package DomainLayer.Drivers;
import java.io.*;
import java.util.Scanner;
import DomainClasses.Dawg;

public class DawgDriver {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in,"UTF-8");

        // Solicita idioma y ruta del archivo
        System.out.print("Introduce el idioma (es o ca): ");
        String language = scanner.nextLine();
        Dawg dawg = new Dawg(language);

        
        // Menú de prueba
        while (true) {
            System.out.println("\n--- Menú ---");
            System.out.println("1. Comprobar si una palabra existe");
            System.out.println("2. Eliminar una palabra");
            System.out.println("3. Comprobar si un prefijo existe");
            System.out.println("4. Mostrar estructura DAWG");
            System.out.println("5. Dame una palabra con sus caracteres uno por uno para ver si existe");
            System.out.println("6. Salir");
            System.out.print("Opción: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (option) {
                case 1:
                    System.out.print("Introduce la palabra: ");
                    String word = scanner.nextLine().trim().toUpperCase();
                    System.out.println(dawg.existsWord(word) ? "Existe" : "No existe");
                    break;
                case 2:
                    System.out.print("Introduce la palabra a eliminar: ");
                    String wordToDelete = scanner.nextLine().trim().toUpperCase();
                    dawg.removeWord(wordToDelete);
                    System.out.println("Palabra eliminada si existía.");
                    break;
                case 3:
                    System.out.print("Introduce el prefijo: ");
                    String prefix = scanner.nextLine().trim().toUpperCase();
                    System.out.println(dawg.isPrefix(prefix) ? "Es un prefijo válido" : "No es un prefijo válido");
                    break;
                case 4:
                    System.out.println("Estructura DAWG:");
                    dawg.numberNodes();
                    break;
                case 5:

                case 6:
                    System.out.println("Saliendo...");
                    return;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }
}