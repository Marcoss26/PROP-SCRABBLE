package DomainClasses;
import java.util.*;
import java.io.*;


public class BagController {
    //Los controladores son clases Singleton
    private static BagController instance; 
    private Map<String, Bag> bags = new HashMap<>(); // Bolsas del sistema, relacionadas con la partida a la que pertencen con el string de la partida

    private BagController() {
        //constructor privado porque es Singleton
    }

    public static BagController getInstance() {
        if (instance == null) {
            instance = new BagController(); 
        }
        return instance; 
    }

    public int getTotalLetters(String matchId){

        return bags.get(matchId).getNumLetters();
        
    }
    
    public Bag getBag(String matchId) {
        return bags.get(matchId);
    }

    public void removeBag(String matchId) {
        bags.remove(matchId);
    }

    public boolean bagExists(String matchId) {
        return bags.containsKey(matchId);
    }

    public void clearBags() {
        bags.clear();
    }

    public boolean bagIsEmpty(String matchId) {
        return bags.get(matchId).isEmpty();
    }

    public void displayBag(String matchId) {
        Bag bag = bags.get(matchId); 
        bag.displayBag();
    }

    public void createBag(String matchId, Map<Letter, Integer> letters, int totalLettersInTheBag) {
       
        Bag bag = new Bag(letters, totalLettersInTheBag); 
        bags.put(matchId, bag); 
    }

    //ficha: simbolo, cantidad y valor 
    //Ficheros Bolsas: LetrasCAST, LEtrasCAT, LetrasENG
    public void createBagFromFile(String matchId, String fileName) throws IOException {
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
        createBag(matchId, letters, totalLettersInTheBag);
        
    }

    public void createBagInteractively(String matchId){
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
          
        createBag(matchId, letters, numLetters);
        }

    }

    /* 
    SWITCH(CASE){
        case 1;
            System.out.println("Introduce el nombre de la bolsa: ");
            String bagName = scanner.next(); 
            createBagFromFile(bagName, "LetrasCAST"); 
            break;
    }
    system.out.println("Introduce Bolsa: " ); 
    createBagInteractively("Bolsa1");
    }*/ //Esto es para el driver principal.

    public Letter extractLetter(String matchId) {

        Bag bag = bags.get(matchId); 
        return bag.extractLetter(); 
       
    }

    public void addLetter(String matchId, Letter letter) {
        Bag bag = bags.get(matchId); 
        bag.addLetter(letter); 
    }

    

    
}
