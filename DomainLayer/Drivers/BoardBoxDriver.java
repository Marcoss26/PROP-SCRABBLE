package DomainLayer.Drivers;
import DomainLayer.DomainClasses.Board;
import java.util.Scanner;

public class BoardBoxDriver {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Driver para las clases Board y Box ===");
        System.out.println("1. Crear un tablero");
        System.out.println("2. Colocar una ficha en el tablero");
        System.out.println("3. Eliminar una ficha del tablero");
        System.out.println("4. Imprimir el tablero");
        System.out.println("5. Salir");

        Board board = null;

        while (true) {
            System.out.print("\nElige una opción: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    System.out.print("Ingrese el tamaño del tablero (7, 15, 25): ");
                    int size = scanner.nextInt();
                    if (size == 7 || size == 15 || size == 25) {
                        board = new Board(size);
                        System.out.println("Tablero creado con tamaño " + size + "x" + size + ".");
                    } else {
                        System.out.println("Tamaño inválido. Solo se permiten 7, 15 o 25.");
                    }
                    break;

                case 2:
                    if (board == null) {
                        System.out.println("Primero debes crear un tablero.");
                        break;
                    }
                    System.out.print("Ingrese las coordenadas X e Y (separadas por un espacio): ");
                    int x = scanner.nextInt();
                    int y = scanner.nextInt();
                    System.out.print("Ingrese la letra: ");
                    String letter = scanner.next();
                    System.out.print("Ingrese el valor de la letra: ");
                    int value = scanner.nextInt();

                    board.placeLetter(x, y, letter, value);
                    System.out.println("Ficha colocada en (" + x + ", " + y + ").");
                    break;

                case 3:
                    if (board == null) {
                        System.out.println("Primero debes crear un tablero.");
                        break;
                    }
                    System.out.print("Ingrese las coordenadas X e Y de la ficha a eliminar (separadas por un espacio): ");
                    int xRemove = scanner.nextInt();
                    int yRemove = scanner.nextInt();

                    board.removeLetter(xRemove, yRemove);
                    System.out.println("Ficha eliminada de (" + xRemove + ", " + yRemove + ").");
                    break;

                case 4:
                    if (board == null) {
                        System.out.println("Primero debes crear un tablero.");
                        break;
                    }
                    board.printBoard();
                    break;

                case 5:
                    System.out.println("Saliendo del driver...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Opción inválida. Intenta de nuevo.");
            }
        }
    }
}