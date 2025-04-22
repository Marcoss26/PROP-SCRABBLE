import java.util.*;

public class AlgorithmDriver {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Crear el tablero
        System.out.println("Introduce el tamaño del tablero (7, 15 o 25): ");
        int boardSize = scanner.nextInt();
        Board board = new Board(boardSize);

        // Crear el rack
        Rack rack = new Rack();

        // Crear el diccionario (simulado para este ejemplo)
        DictionaryController dictionaryController = DictionaryController.getInstance();
        Dictionary dictionary = null;
        // Menú interactivo
        while (true) {
            System.out.println("\n--- Menú ---");
            System.out.println("1. Introducir fichas en el rack");
            System.out.println("2. Ejecutar el algoritmo para encontrar palabras jugables");
            System.out.println("3. Añadir una palabra manualmente al tablero");
            System.out.println("4. Mostrar el tablero");
            System.out.println("5. Salir");
            System.out.print("Elige una opción: ");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    // Introducir fichas en el rack
                    System.out.println("Introduce las fichas del rack (separadas por espacios, formato: letra-valor): ");
                    scanner.nextLine(); // Limpiar el buffer
                    String rackInput = scanner.nextLine();
                    String[] rackLetters = rackInput.split(" ");
                    for (String rackLetter : rackLetters) {
                        String[] parts = rackLetter.split("-");
                        String symbol = parts[0];
                        int value = Integer.parseInt(parts[1]);
                        rack.addLetter(new Letter(symbol, value));
                    }
                    System.out.println("Fichas añadidas al rack.");
                    rack.print();
                    break;

                case 2:
                    // Ejecutar el algoritmo para encontrar palabras jugables
                    MP_Controller controller = MP_Controller.getInstance();
                    List<MP_Controller.PlayableWord> playableWords = controller.calculatePlayableWords(board, rack, dictionary);
                    System.out.println("Palabras jugables encontradas:");
                    for (MP_Controller.PlayableWord word : playableWords) {
                        System.out.println(word);
                    }
                    break;

                case 3:
                    // Añadir una palabra manualmente al tablero
                    System.out.println("Introduce la palabra que quieres añadir: ");
                    String word = scanner.next();
                    System.out.println("Introduce la posición inicial (x y): ");
                    int startX = scanner.nextInt();
                    int startY = scanner.nextInt();
                    System.out.println("Introduce la dirección (H para horizontal, V para vertical): ");
                    char direction = scanner.next().toUpperCase().charAt(0);

                    // Añadir la palabra al tablero
                    for (int i = 0; i < word.length(); i++) {
                        char letter = word.charAt(i);
                        if (direction == 'H') {
                            board.placeLetter(startX, startY + i, String.valueOf(letter), 1); // Valor por defecto: 1
                        } else if (direction == 'V') {
                            board.placeLetter(startX + i, startY, String.valueOf(letter), 1); // Valor por defecto: 1
                        }
                    }
                    System.out.println("Palabra añadida al tablero.");
                    break;

                case 4:
                    // Mostrar el tablero
                    board.printBoard();
                    break;

                case 5:
                    // Salir
                    System.out.println("Saliendo del programa...");
                    scanner.close();
                    return;

                case 6:
                    // Cargar un diccionario
                    System.out.println("Introduce el nombre del diccionario que deseas cargar: ");
                    String dictionaryName = scanner.next();
                    System.out.println("Introduce el idioma del diccionario (es, en, ca): ");
                    String language = scanner.next();

                    try {
                        dictionaryController.addDictionary(dictionaryName, language);
                        dictionaryController.addWordsToDictionary(dictionaryName, dictionaryName); // Cargar palabras desde archivo
                        dictionary = dictionaryController.getDictionary(dictionaryName);
                        System.out.println("Diccionario cargado correctamente.");
                    } catch (Exception e) {
                        System.out.println("Error al cargar el diccionario: " + e.getMessage());
                    }
                    break;

                default:
                    System.out.println("Opción no válida. Intenta de nuevo.");
            }
        }
    }
}