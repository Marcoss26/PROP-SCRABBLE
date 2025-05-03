package DomainLayer.DomainClasses;
import java.util.*;
import java.io.*;


/* 
String original = "h_e_l_ch_o_w_o_r_l_d";
System.out.println("Original String: " + original);

// Replace underscores with a space
String replaced = original.replace("_", "");
System.out.println("Replaced String: " + replaced);*/


public class Driver {
    static public Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        DomainController domainController = DomainController.getInstance();
        boolean exit = false;
        while(!exit) {
            System.out.print("\033[H\033[2J");
            System.out.flush();

            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Play game");
            System.out.println("2. Manage profiles");
            System.out.println("3. Manage dictionaries");
            System.out.println("4. Show Ranking");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    GameDriver.main(args);
                    break;
                case 2:
                    ProfileDriver.main(args);
                    break;
                case 3:
                    DictionaryDriver.main(args);
                    break;
                case 4:
                    domainController.displayRanking();
                    break;
                case 5:
                    exit = true;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();

    }
}



class ProfileDriver {
    static ProfileController profileController = ProfileController.getInstance();

    public static void printProfiles() {
        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println("\n--- All profiles ---");
        profileController.printProfiles();

        System.out.println("Press enter to go back");
        Driver.scanner.nextLine();
    }

    public static void main(String[] args) {

        boolean exit = false;
        while (!exit) {
            System.out.print("\033[H\033[2J");
            System.out.flush();

            System.out.println("\n--- Manage profiles menu ---");
            System.out.println("1. Add Profile");
            System.out.println("2. Remove Profile");
            System.out.println("4. Login to profile");
            System.out.println("5. Print All Profiles");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int option = Driver.scanner.nextInt();
            Driver.scanner.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    System.out.print("Enter username: ");
                    String usernameToAdd = Driver.scanner.nextLine();
                    String passwordToAdd = new String(System.console().readPassword("Enter password: "));
                    try {
                        profileController.addProfile(usernameToAdd, passwordToAdd);
                        System.out.println("Profile added successfully.");
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 2:
                    System.out.print("Enter username to remove: ");
                    String usernameToRemove = Driver.scanner.nextLine();
                    profileController.removeProfile(usernameToRemove);
                    System.out.println("Profile removed successfully.");
                    break;

                case 3:
                    System.out.print("Enter username to check: ");
                    String usernameToCheck = Driver.scanner.nextLine();
                    boolean exists = profileController.profileExists(usernameToCheck);
                    System.out.println("Profile exists: " + exists);
                    break;

                case 4:
                    System.out.print("Enter username: ");
                    String usernameToRetrieve = Driver.scanner.nextLine();
                    String passwordToRetrieve = new String(System.console().readPassword("Enter password: "));

                    Profile profile = profileController.getProfile(usernameToRetrieve, passwordToRetrieve);
                    if (profile != null) {
                        System.out.println("Retrieved Profile: " + profile.getUsername());
                        profile.printProfile();
                    } else {
                        System.out.println("Authentication failed or profile not found.");
                    }
                    break;

                case 5:
                    printProfiles();
                    break;

                case 6:
                    System.out.println("Exiting Profiles...");
                    exit = true;
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}






class GameDriver {
    static DomainController domainController = DomainController.getInstance();

    private static void placeWord(String matchId) {

        System.out.print("Enter a word (separate letters by a '_', ex: h_e_l_l_o): ");
        String word = Driver.scanner.nextLine();
        
        boolean valid = false;
        while(!valid) {
            System.out.print("Enter a coordinate to place the forst letter: ");
            int positionStartX = Driver.scanner.nextInt();
            int positionStartY = Driver.scanner.nextInt();
            Driver.scanner.nextLine();
            
            System.out.print("Enter a coordinate to place the last letter: ");
            int positionEndX = Driver.scanner.nextInt();
            int positionEndY = Driver.scanner.nextInt();
            Driver.scanner.nextLine();

            if (positionStartX == positionEndX && positionStartY == positionEndY) {
                System.out.println("Invalid coordinates. Please try again.");
                continue;
            } else if (positionStartX != positionEndX && positionStartY != positionEndY) {
                System.out.println("Invalid coordinates. Please try again.");
                continue;
            } else valid = true;
             
            if (valid) domainController.playsMatch(matchId, word, positionStartX, positionStartY, positionEndX, positionEndY);
        }
    }

    private static void playGame(String matchId) {

        boolean exit = false;
        while(!exit) {
            System.out.print("\033[H\033[2J");
            System.out.flush();

            Player currentPlayer = domainController.getPlayerTurn(matchId);

            domainController.printMatch(matchId);


            System.out.println("\n--- Play Turn "+currentPlayer.getID()+"---");
            System.out.println("1. Suffle rack");
            System.out.println("2. Replace rack letters");
            System.out.println("3. Place word");
            System.out.println("4. Pause game");
            System.out.print("Choose an option: ");

            int option = Driver.scanner.nextInt();
            Driver.scanner.nextLine();

            switch (option) {
                case 1:
                    domainController.shuffleRack(matchId);
                    break;
                case 2:
                    System.out.print("Enter the letters (separate letters by a '_', ex: h_e_l_l_o): ");
                    String word = Driver.scanner.nextLine();

                    domainController.modifyRack(matchId, word);
                    break;
                case 3:
                    exit = true;
                    placeWord(matchId);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }


    }
    private static void newGame() {
        System.out.println("\n--- New Game ---");

        System.out.print("Enter the number of players: ");
        int players = Driver.scanner.nextInt();

        System.out.print("Out of these " + players + " players, how many are human? ");
        int humanPlayers = Driver.scanner.nextInt();
        Driver.scanner.nextLine();

        System.out.println("Log in to all " + humanPlayers + " profiles:");
        Set<Profile> profiles = new HashSet<>();

        while(profiles.size() < humanPlayers) {
            System.out.print("Enter username: ");
            String username = Driver.scanner.nextLine();

            if (!domainController.profileExists(username)) {
                System.out.println("Profile does not exist");
                continue;
            }

            boolean correct = false;
            int attempts = 0;
            while (!correct && attempts < 3) {
                String password = new String(System.console().readPassword("Enter password: "));
                if (domainController.getProfile(username, password) != null) {
                    System.out.println("Profile added to match: " + username);
                    profiles.add(domainController.getProfile(username, password));
                    break;
                } else {
                    if (attempts == 2) {
                        System.out.println("Too many atempts.");
                        break;
                    } else {
                        System.out.println("Incorrect password. Try again. Attempts left: " + (3 - attempts - 1));
                        attempts++;
                    }
                }
            }
        }


        System.out.print("Enter the size of the board: ");
        int boardSize = Driver.scanner.nextInt();
        Driver.scanner.nextLine();

        System.out.println("Chose a dictionary to use for the match: ");
        System.out.println("Available dictionaries: ");
        Map<String, Dictionary> dictionaries = domainController.getDictionaries();
        for (String name : dictionaries.keySet()) {
            System.out.println("Name: " + name + ", Language: " + domainController.getDictionaryLanguage(name));
        }
        System.out.print("Enter the dictionary name: ");
        String dictName = Driver.scanner.nextLine();

        Map<Letter, Integer> letters = new HashMap<>();
        int totalLettersInTheBag = 0;
        boolean validFile = false;
        while(!validFile) {
            System.out.println("Enter the name of the letter set to use for the bag: ");
            String fileName = Driver.scanner.nextLine();
            String file = fileName + ".txt";
            File filePath = new File("data/Letters/" + file);
    
            if (!filePath.exists()) {
                System.out.println("Error: The file '" + fileName + "' was not found in the folder 'data/Letters'.");
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
        

        try {
            String matchID = domainController.newMatch(players, profiles, dictName, boardSize, letters, totalLettersInTheBag);
            playGame(matchID);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void continueGame() {
        boolean exit = false;
        while(!exit) {
            System.out.println("\n--- Continue Game ---");
            System.out.println("\nAvailavle games to continue:");

            System.out.print("Choose an option: ");
            List<String> matches = domainController.getUnfinishedMatchs();
            for (int i = 0; i < matches.size(); i++) {
                System.out.println((i + 1) + ". " + matches.get(i));
            }

            System.out.println((matches.size() + 1) + ". Back");

            int option = Driver.scanner.nextInt();
            Driver.scanner.nextLine();

            if (option > 0 && option <= matches.size()) {
                String selectedMatch = matches.get(option - 1);
                System.out.println("Continuing match " + selectedMatch);
                domainController.continueMatch(selectedMatch);
                exit = true;
                playGame(selectedMatch);
            } else if (option == matches.size() + 1) {
                exit = true;
                System.out.println("Exiting...");
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }
    public static void main(String[] args) {
        boolean exit = false;
        while(!exit) {
            System.out.print("\033[H\033[2J");
            System.out.flush();

            System.out.println("\n--- Play game Menu ---");
            System.out.println("1. Play new game");
            System.out.println("2. Continue game");
            System.out.println("3. Back");
            System.out.print("Choose an option: ");

            int option = Driver.scanner.nextInt();
            Driver.scanner.nextLine();

            switch (option) {
                case 1:
                    newGame();
                    break;
                case 2:
                    continueGame();
                    break;
                case 3:
                    exit = true;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}







class DictionaryDriver {
    static DomainController domainController = DomainController.getInstance();

    private static void createDictionary() {
        System.out.print("Enter the name of the dictionary: ");
        String name = Driver.scanner.nextLine();

        System.out.print("Enter the language of the dictionary: (You should enter one of this options: es/cat/en) ");
        String language = Driver.scanner.nextLine();

        System.out.print("Enter the name of the dictionary file without any format: ");
        String fileName = Driver.scanner.nextLine();

        domainController.createDictionary(name, language, fileName);
        System.out.println("Dictionary " + name + " created successfully.");
    }

    private static void removeDictionary() {
        System.out.print("Enter the name of the dictionary to remove: ");
        String name = Driver.scanner.nextLine();

        domainController.removeDictionary(name);
        System.out.println("Dictionary " + name + " removed successfully.");
    }

    private static void addWordToDictionary() {
        System.out.print("Enter the name of the dictionary to add a word to: ");
        String name = Driver.scanner.nextLine();

        System.out.print("Enter the word to add: ");
        String word = Driver.scanner.nextLine();

        domainController.addWordToDictionary(name, word);
        System.out.println("Word " + word + " added to dictionary " + name + " successfully.");
    }

    private static void removeWordFromDictionary() {
        System.out.print("Enter the name of the dictionary to remove a word from: ");
        String name = Driver.scanner.nextLine();

        System.out.print("Enter the word to remove: ");
        String word = Driver.scanner.nextLine();

        domainController.removeWordFromDictionary(name, word);
        System.out.println("Word " + word + " removed from dictionary " + name + " successfully.");
    }

    //Esta funcion imprimirá los nombres de los diccionarios presentes junto con su language
    private static void displayDictionaries() {
        System.out.println("Current dictionaries: ");
        Map<String, Dictionary> dictionaries = domainController.getDictionaries();
        for (String name : dictionaries.keySet()) {
            System.out.println("Name: " + name + ", Language: " + domainController.getDictionaryLanguage(name));
        }
    }

    public static void main(String[] args) {
        boolean exit = false;
        while(!exit) {
            System.out.print("\033[H\033[2J");
            System.out.flush();

            System.out.println("\n--- Dictionary Menu ---");
            System.out.println("1. Create Dictionary");
            System.out.println("2. Remove dictionary");
            System.out.println("3. Add word to dictionary");
            System.out.println("4. Remove word from dictionary");
            System.out.println("5. Display current dictionaries");
            System.out.println("6. Back");
            System.out.print("Choose an option: ");

            int option = Driver.scanner.nextInt();
            Driver.scanner.nextLine();

            switch (option) {
                case 1:
                    createDictionary();
                    break;
                case 2:
                    removeDictionary();
                    break;
                case 3:
                    addWordToDictionary();
                    break;
                case 4:
                    removeWordFromDictionary();
                    break;
                case 5:
                    displayDictionaries();
                    break;
                case 6:
                    exit = true;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
