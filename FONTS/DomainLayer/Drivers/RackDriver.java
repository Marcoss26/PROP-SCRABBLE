package DomainLayer.Drivers;

import java.util.*;
import java.io.IOException;
import DomainLayer.DomainClasses.Rack;
import DomainLayer.DomainClasses.Letter;

public class RackDriver {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        BagController bagController = BagController.getInstance();
        bagController.createBagFromFile("1", "letrasCAST");


        // Create a Rack instance
        Rack rack = new Rack(bagController.getBag("1"));

        boolean exit = false;
        while (!exit) {
            System.out.println("\n--- Rack Driver Menu ---");
            System.out.println("1. Display Rack");
            System.out.println("2. Shuffle Rack");
            System.out.println("3. Get a Letter from Rack");
            System.out.println("4. Replace rack letters");
            System.out.println("5. Clear Rack");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline


            switch (option) {
                case 1:
                    System.out.println("Current Rack:");
                    rack.print();
                    break;
                case 2:
                    rack.shuffle();
                    System.out.println("Current Rack:");
                    rack.print();
                    break;

                case 3:
                    System.out.print("Enter the letter symbol to get: ");
                    String letterSymbol = scanner.nextLine();
                    Letter retrievedLetter = rack.getLetter(letterSymbol);
                    if (retrievedLetter != null) {
                        System.out.println("Retrieved Letter: " + retrievedLetter.getSymbol() + " (Value: " + retrievedLetter.getValue() + ")");
                    } else {
                        System.out.println("No letter with the symbol '" + letterSymbol + "' found in the rack.");
                    }
                    break;

                case 4:
                    System.out.print("Enter the letters (separate letters by a '_', ex: h_e_l_l_o): ");
                    String word = scanner.nextLine();
                    
                    rack.replaceLetters(word);
                    break;

                case 5:
                    rack.clear();
                    System.out.println("Rack cleared.");
                    break;

                case 6:
                    System.out.println("Exiting Rack Driver...");
                    exit = true;
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }
}