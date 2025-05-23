package DomainLayer.Drivers;
import DomainLayer.DomainClasses.*;
import java.io.*;
import java.util.*;

public class MyDriver 
{
    static public Scanner scanner = new Scanner(System.in,"UTF-8");
    public static void main(String[] args) throws IOException 
    {
        MP_Controller matchController = MP_Controller.getInstance();
        Map<Letter, Integer> letters = new HashMap<>();
        int totalLettersInTheBag = 0;
        boolean validFile = false;
        while(!validFile) {
            String file = "letrasCAT.txt";
            File filePath = new File("data/Letters/" + file);
    
            if (!filePath.exists()) {
                System.out.println("Error: The file '" + file + "' was not found in the folder 'data/Letters'.");
                continue;
            } else {
                validFile = true;
            }
    
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
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
                System.err.println("Error reading the file: " + e.getMessage());
                return;
            }
        }
        DomainLayer.DomainClasses.Dictionary dictionary = new DomainLayer.DomainClasses.Dictionary("ca","ca");
        Set<Profile> profiles = new HashSet<>();
        Profile profile = new Profile("ziheng","1");
        Profile profile2 = new Profile("ziheng2","1");
        profiles.add(profile);
        profiles.add(profile2);
        String matchId = matchController.createMatch(2, profiles, dictionary, 7, letters, totalLettersInTheBag);
        // Menú de prueba
        while (true) {
            matchController.print(matchId);
            System.out.println("Choose what you want to do: ");
            System.out.println("1. Place a word");
            System.out.println("2. Replace letters in the rack");
            System.out.println("3. Check crosschecks");
            int option = MyDriver.scanner.nextInt();
            MyDriver.scanner.nextLine(); // Consume the newline character left by nextInt()
            switch (option) {
                case 1:
                System.out.println("Enter a word: ");
                String word = MyDriver.scanner.nextLine();
                System.out.println("Enter the start position (x,y): ");
                int posStartX = MyDriver.scanner.nextInt();
                int posStartY = MyDriver.scanner.nextInt();
                System.out.println("Enter the end position (x,y): ");
                int posEndX = MyDriver.scanner.nextInt();
                int posEndY = MyDriver.scanner.nextInt();
                matchController.playsMatch(matchId,word,posStartX,posStartY,posEndX,posEndY);
                System.out.println("Enter to continue:");
                String input = MyDriver.scanner.nextLine();
                if (input.equals("\n")) {
                    continue;
                }
                    break;
            
                case 2:
                System.out.println("Enter the letters to replace (separated by '_'): ");
                String lettersToReplace = MyDriver.scanner.nextLine();
                matchController.modifyRack(matchId, lettersToReplace);
                    break;
                case 3:
                System.out.println("Check the crosschecks for a square:");
                System.out.println("Enter the coordinates (x,y): ");
                int x = MyDriver.scanner.nextInt();
                int y = MyDriver.scanner.nextInt();
                matchController.printCrossChecks(x, y, matchId);
                    break;
            }
        }
    }
}