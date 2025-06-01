package DomainLayer.Drivers;
import DomainLayer.DomainClasses.*;
import java.io.*;
import java.util.*;
import Utils.Pair;

public class MyDriver 
{
    static public Scanner scanner = new Scanner(System.in,"UTF-8");
    public static void main(String[] args) throws IOException 
    {
        DomainController domainController = DomainController.getInstance();
        MP_Controller matchController = MP_Controller.getInstance();
        DictionaryController dictionaryController = DictionaryController.getInstance();
        Map<Letter, Integer> letters = new HashMap<>();
        int totalLettersInTheBag = 0;
        boolean validFile = false;
        while(!validFile) {
            String file = "letrasENG.txt";
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
        dictionaryController.addDictionary("en", "en");
        dictionaryController.addDictionary("es", "es");
        dictionaryController.addDictionary("ca", "ca");
        Set<Profile> profiles = new HashSet<>();
        //domainController.addProfile("ziheng","1");
        //domainController.addProfile("kai","1");
        domainController.loadProfiles();
        Profile profile = domainController.getProfile("ziheng");
        Profile profile2 = domainController.getProfile("kai");
        profiles.add(profile);
        profiles.add(profile2);
        /*String matchId = matchController.createMatch(2, profiles, dictionary, 7, letters, totalLettersInTheBag);
        String matchId2 = matchController.createMatch(2, profiles, dictionary2, 7, letters, totalLettersInTheBag);
        String matchId3 = matchController.createMatch(2, profiles, dictionary3, 7, letters, totalLettersInTheBag);*/
        while(true)
        {
            System.out.println("Choose what to do:");
            System.out.println("1. Create new match");
            System.out.println("2. Load existing matches");
            System.out.println("3. Save matches");
            System.out.println("4. Play a match");
            int option = MyDriver.scanner.nextInt();
            MyDriver.scanner.nextLine(); // Consume the newline character left by nextInt()
            switch(option)
            {
                case 1:
                System.out.println("Enter board size:");
                int boardSize = MyDriver.scanner.nextInt();
                System.out.println("Enter the dictionary name (en, es, ca):");
                String dictionaryName = MyDriver.scanner.next();
                MyDriver.scanner.nextLine(); // Consume the newline character left by nextInt()
                matchController.createMatch(2,profiles,dictionaryController.getDictionary(dictionaryName),boardSize, letters, totalLettersInTheBag);
                break;
                
                case 2:
                domainController.loadMatches();
                Map<String, Match> matches = matchController.getUnfinishedMatches();
                for(Map.Entry<String, Match> entry : matches.entrySet()) 
                {
                    Match match = entry.getValue();
                    matchController.createDictionaryForMatch(match, dictionaryController.getDictionary(match.getDictionaryName()));
                    match.displayMatch();        
                }
                break;

                case 3:
                System.out.println("Matches saved");
                domainController.saveMatches();
                //System.exit(0);
                break;
        // Menú de prueba
                case 4:
                boolean playing = true;
                System.out.println("Enter the match ID to play:");
                String matchId = MyDriver.scanner.nextLine();
                while (playing) {
                    matchController.print(matchId);
                    System.out.println("Choose what you want to do: ");
                    System.out.println("1. Place a word");
                    System.out.println("2. Replace letters in the rack");
                    System.out.println("3. Exit");
                    int option2 = MyDriver.scanner.nextInt();
                    MyDriver.scanner.nextLine(); // Consume the newline character left by nextInt()
                    switch (option2) {
                        case 1:
                            System.out.println("Enter a word: ");
                            String word = MyDriver.scanner.nextLine();
                            System.out.println("Enter the start position (x,y): ");
                            int posStartX = MyDriver.scanner.nextInt();
                            int posStartY = MyDriver.scanner.nextInt();
                            System.out.println("Enter the end position (x,y): ");
                            int posEndX = MyDriver.scanner.nextInt();
                            int posEndY = MyDriver.scanner.nextInt();
                            matchController.humanTurn(matchId,word,posStartX,posStartY,posEndX,posEndY,new HashSet<Pair<Integer, Integer>>());
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
                            playing = false;
                            break;
                    }
                }
            }
        }
    }
}