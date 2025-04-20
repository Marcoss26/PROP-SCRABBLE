package Drivers;
import java.util.*;
import java.io.*;
import DomainClasses.BagController;
import DomainClasses.Letter;

public class BagDriver {
    
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        BagController bagController = BagController.getInstance();
        
        //crea todas las bolsas que quieras hasta que quieras salir;
        boolean inputDone = false;
        while(!inputDone){
            System.out.println("Para crear la bolsa de la partida puedes utilizar uno de los ficheros que te ofrecemos o establecer tu mismo el conjunto de fichas que la forman:\n");
            System.out.println("1. letrasCAST.txt" + "\n" + 
                                "2. letrasCAT.txt" + "\n" +
                                "3. letrasENG.txt" + "\n" +
                                "4. Añadir mi propia bolsa" + "\n"
                                + "5. Salir" + "\n"
                                + "Elige una opción: ");
            
            int opcion = scanner.nextInt();
            switch(opcion){
                case 1:
                    System.out.println("Introduce la partida a la que pertenece la bolsa: ");
                    String matchId = scanner.next();
                    bagController.createBagFromFile(matchId, "letrasCAST");
                    
                    break;
                case 2:
                    System.out.println("Introduce la partida a la que pertenece la bolsa: ");
                    matchId = scanner.next();
                    bagController.createBagFromFile(matchId, "letrasCAT");
                    
                    break;
                case 3:
                    System.out.println("Introduce la partida a la que pertenece la bolsa: ");
                    matchId = scanner.next();
                    bagController.createBagFromFile(matchId, "letrasENG");
                    
                    break;
                case 4:
                    System.out.println("Introduce la partida a la que pertenece la bolsa: ");
                    matchId = scanner.next();
                    bagController.createBagInteractively(matchId);
                    
                    break;
                case 5:
                    System.out.println("...");
                    inputDone = true; // Cambiamos la variable para salir del bucle
                    break;
                default:
                    System.out.println("Opción no válida. Intenta de nuevo.");
                    continue; // Volver al inicio del bucle para pedir otra opción

            }
        }
        
        System.out.print("Introduce a que partida pertenece la bolsa que quieres probar: ");
         String matchId2 = scanner.next();

        while (true) {
            System.out.println("\n--- Menú ---");
            System.out.println("1. Verificar si la bolsa existe");
            System.out.println("2. Mostrar el contenido de la bolsa");
            System.out.println("3. Extraer una ficha de la bolsa");
            System.out.println("4. Agregar una ficha a la bolsa");
            System.out.println("5. Cambiar las fichas del rack");
            System.out.println("6. Verificar si la bolsa está vacía");
            System.out.println("7. Limpiar todas las bolsas");
            System.out.println("8. Cuantas Fichas quedan en la bolsa");
            System.out.println("9. Salir");
            
            System.out.print("Elige una opción: ");
            int opcion = scanner.nextInt();
             // Cambié el nombre de la variable para evitar confusiones
            switch (opcion) {
                case 1:
                    System.out.println("¿Existe la bolsa? " + bagController.bagExists(matchId2));
                    break;
                case 2:
                    bagController.displayBag(matchId2);
                    break;
                case 3:
                    /*System.out.print("Ingresa el ID del jugador: ");
                    String playerId = scanner.next();*/
                    Letter extractedLetter = bagController.extractLetter(matchId2);
                    System.out.println("Ficha extraída: " + extractedLetter.getSymbol() + " (Valor: " + extractedLetter.getValue() + ")");
                    break;
                case 4:
                    System.out.print("Ingresa la letra de la Ficha: ");
                    String letra = scanner.next();
                    System.out.print("Ingresa el valor de la Ficha: ");
                    int valor = scanner.nextInt();
                    Letter newLetter = new Letter(letra, valor);
                    bagController.addLetter(matchId2, newLetter);
                    System.out.println("Ficha añadida: " + newLetter.getSymbol() + " (Valor: " + newLetter.getValue() + ")");
                    break;
                case 5:
                    Set<Letter> lettersToChange = new HashSet<>();
                    System.out.print("Introduce las Fichas que conforman actualmente tu rack: " + "\n");
                    
                    for (int i = 0; i < 7; i++) {
                        System.out.print("Ingresa la letra de la Ficha " + (i+1) + ": ");
                        String letraCambiar = scanner.next();
                        System.out.print("Ingresa el valor de la Ficha " + (i+1) + ": ");
                        int valorCambiar = scanner.nextInt();
                        lettersToChange.add(new Letter(letraCambiar, valorCambiar));
                    }
                    /*System.out.print("Ingresa el ID del jugador: ");
                    String playerIdChange = scanner.next();*/
                    Set<Letter> newLetters = bagController.changeLetters(matchId2, lettersToChange);
                    System.out.println("Nuevo Rack: ");
                    boolean first = true;
                    for(Letter let : newLetters) {
                        if(first) {
                            let.displayLetter();
                            first = false;
                        } else {
                            System.out.print(", ");
                            let.displayLetter();
                        }
                    }
                    break;
                case 6:
                    System.out.println("¿La bolsa está vacía? " + bagController.bagIsEmpty(matchId2));
                    break;
                case 7:
                    bagController.clearBags();
                    System.out.println("Todas las bolsas han sido eliminadas.");
                    break;
                case 8:
                    
                    System.out.println("Total de fichas en la bolsa: " + bagController.getTotalLetters(matchId2));
                    break;
                case 9:
                    System.out.println("Saliendo del programa...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opción no válida. Intenta de nuevo.");
            }
        }
    }
}