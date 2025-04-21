package Drivers;
import java.util.*;
import java.io.*;

import DomainClasses.Bag;
import DomainClasses.Letter;

public class BagDriver {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Bag bag = null ; // Crear una bolsa vacía
        //Creación de la bolsa
        boolean inputDone = false;
        boolean bagCreated = false;
        while(!inputDone && !bagCreated){
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
                    
                    bag = createBagFromFile("letrasCAST");
                    System.out.println("I Bolsa en Castellano creada correctamente.");
                    bagCreated = true;

                    break;
                case 2:
                    
                    bag = createBagFromFile("letrasCAT");
                    System.out.println("Bolsa en Catalán creada correctamente.");
                    bagCreated = true;
                    break;
                case 3:
                    bag = createBagFromFile("letrasENG");
                    System.out.println("Bolsa en Inglés creada correctamente.");
                    bagCreated = true;
                    break;
                case 4:
                    bag = createBagInteractively(); 
                    System.out.println("Bolsa creada correctamente.");
                    bagCreated = true; 
                    break;
                case 5:
                    inputDone = true;
                    System.out.println("No se ha creado ninguna bolsa. Saliendo del programa...");
                    break;

                     // Cambiamos la variable para salir del bucle
                    
                default:
                    System.out.println("Opción no válida. Intenta de nuevo.");
                    continue; // Volver al inicio del bucle para pedir otra opción

            }
        }


       
        if(bagCreated){
            while (true) {
                System.out.println("\n--- Menú Funcionalidades de la bolsa creada ---");
                System.out.println("1. Verificar si la bolsa existe");
                System.out.println("2. Mostrar el contenido de la bolsa");
                System.out.println("3. Extraer una ficha de la bolsa");
                System.out.println("4. Agregar una ficha a la bolsa");
                System.out.println("5. Cambiar las fichas del rack");
                System.out.println("6. Verificar si la bolsa está vacía");
                System.out.println("7. Obtener cuantas fichas quedan en la bolsa");
                System.out.println("8. Salir");
                
                System.out.print("Elige una opción: ");
                int opcion = scanner.nextInt();
                // Cambié el nombre de la variable para evitar confusiones
                switch (opcion) {
                    case 1:
                        System.out.println("¿Existe la bolsa? " + (bag != null));
                        break;
                    case 2:
                        bag.displayBag();
                        break;
                    case 3:
                        /*System.out.print("Ingresa el ID del jugador: ");
                        String playerId = scanner.next();*/
                        Letter extractedLetter = bag.extractLetter();
                        System.out.println("Ficha extraída: " + extractedLetter.getSymbol() + " (Valor: " + extractedLetter.getValue() + ")");
                        break;
                    case 4:
                        System.out.print("Ingresa la letra de la Ficha: ");
                        String letra = scanner.next();
                        System.out.print("Ingresa el valor de la Ficha: ");
                        int valor = scanner.nextInt();
                        Letter newLetter = new Letter(letra, valor);
                        bag.addLetter(newLetter);
                        System.out.println("Ficha añadida: " + newLetter.getSymbol() + " (Valor: " + newLetter.getValue() + ")");
                        break;
                    case 5:
                        List<Letter> lettersToChange = new ArrayList<>();
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
                        List<Letter> newLetters = bag.changeRackLetters(lettersToChange);
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
                        System.out.println("¿La bolsa está vacía? " + bag.isEmpty());
                        break;
                    
                    case 7:
                        
                        System.out.println("Total de fichas en la bolsa: " + bag.getNumLetters());
                        break;
                    case 8:
                        System.out.println("Saliendo del programa...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Opción no válida. Intenta de nuevo.");
                }
            }
        }
        else {
            System.out.println("No se ha creado ninguna bolsa. Saliendo del programa...");
            scanner.close(); // Cerramos el escáner antes de salir
            return; 
        }
    }

    //ficha: simbolo, cantidad y valor 
    //Ficheros Bolsas: LetrasCAST, LEtrasCAT, LetrasENG
    private static Bag createBagFromFile(String fileName) throws IOException {
        //en lugar de recibir un array de fichas, creo yo el mapa de letras y cantidades a partir del txt
        Map<Letter, Integer> letters = new HashMap<>();
        String file = fileName + ".txt"; 
        File filePath = new File("data/Letters/" + file); 
        int totalLettersInTheBag = 0;
        if (!filePath.exists()) {
            throw new FileNotFoundException("El archivo '" + fileName + "' no se encontró en la carpeta 'data/Letters'.");
        }
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" "); 
                if (parts.length == 3) {
                    String symbol = parts[0];
                    int quantity = Integer.parseInt(parts[1]); 
                    totalLettersInTheBag += quantity;
                    int value = Integer.parseInt(parts[2]); 
                    Letter letter = new Letter(symbol, value); 
                    letters.put(letter, quantity); 
                }
            }
        } catch (IOException e) {
            throw new IOException("Error al leer el archivo: " + e.getMessage());
        }
        Bag bag = new Bag(letters, totalLettersInTheBag); 
        return bag;
        
        
    }

    private static Bag createBagInteractively(){
        Map<Letter, Integer> letters = new HashMap<>(); 
        
        System.out.println("Introduce total letters in the bag: ");
        try( Scanner scanner = new Scanner(System.in)){ 
        int numLetters = scanner.nextInt(); 
            for (int i = 0; i < numLetters; i++) {
                System.out.println("Introduce the symbol: ");
                String symbol = scanner.next(); 
                System.out.println("Introduce the quantity of this type of letter: ");
                int quantity = scanner.nextInt(); 
                System.out.println("Introduce the value of this type of letter: ");
                int value = scanner.nextInt(); 
                Letter letter = new Letter(symbol, value); 
                letters.put(letter, quantity); 
            }
          
            Bag bag = new Bag(letters, numLetters); 
            return bag;
        }

    }

}