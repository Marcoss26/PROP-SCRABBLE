package DomainClasses;
import java.util.*;




/* 
String original = "h_e_l_ch_o_w_o_r_l_d";
System.out.println("Original String: " + original);

// Replace underscores with a space
String replaced = original.replace("_", "");
System.out.println("Replaced String: " + replaced);*/


public class Driver {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DomainController domainController = DomainController.getInstance();
        boolean exit = false;
        while(!exit) {
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
                   
                    break;
                case 3:
            
                    break;
                
                case 4:
                    
                    domainController.displayRanking();
                    break;
                case 5:
                    exit = true;
                    System.out.println("Exiting...");
                    scanner.close();
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}



class ProfileDriver {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ProfileController profileController = ProfileController.getInstance();

        boolean exit = false;
        while (!exit) {
            System.out.println("\n--- Profile Controller Menu ---");
            System.out.println("1. Add Profile");
            System.out.println("2. Remove Profile");
            System.out.println("4. Retrieve Profile");
            System.out.println("5. Print All Profiles");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    System.out.print("Enter username: ");
                    String usernameToAdd = scanner.nextLine();
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
                    String usernameToRemove = scanner.nextLine();
                    profileController.removeProfile(usernameToRemove);
                    System.out.println("Profile removed successfully.");
                    break;

                case 3:
                    System.out.print("Enter username to check: ");
                    String usernameToCheck = scanner.nextLine();
                    boolean exists = profileController.profileExists(usernameToCheck);
                    System.out.println("Profile exists: " + exists);
                    break;

                case 4:
                    System.out.print("Enter username: ");
                    String usernameToRetrieve = scanner.nextLine();
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
                    System.out.println("All Profiles:");
                    profileController.printProfiles();
                    break;

                case 6:
                    System.out.println("Exiting Profile Controller Driver...");
                    exit = true;
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }
}






class GameDriver {
    static DomainController domainController = DomainController.getInstance();

    private static void placeWord(String matchId) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a word: ");
        String word = scanner.nextLine();
        
        System.out.print("Enter a coordinate to place the forst letter: ");
        String position = scanner.nextLine();

        System.out.print("Enter the direction (u d b l): ");
        String direction = scanner.nextLine();

        domainController.playsMatch(matchId, word, position, direction);
    }

    private static void turn(String matchId) {
        Scanner scanner = new Scanner(System.in);
        Player currentPlayer = domainController.MatchGetCurrentTurn(matchId);

        boolean exit = false;
        while(!exit) {
            System.out.println("\n--- Play Turn ---");
            System.out.println("1. Suffle rack");
            System.out.println("2. Replace rack");
            System.out.println("3. Place word");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    domainController.shuffleRack(matchId, currentPlayer.getId());
                    break;
                case 2:
                    exit = true;
                    domainController.replaceRach(matchId, currentPlayer.getId());
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
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--- New Game ---");

        System.out.print("Enter the number of players: ");
        int players = scanner.nextInt();

        System.out.print("Out of these " + players + " players, how many are human? ");
        int humanPlayers = scanner.nextInt();

        System.out.print("Log in to all " + humanPlayers + " profiles: ");
        final int MIN_HOMAN_PLAYERS = 1;
        Set<Profile> profiles = new HashSet<>();

        while(profiles.size() < humanPlayers) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            if (!domainController.profileExists(username)) {
                System.out.println("Profile does not exist");
                break;
            }

            String password = new String(System.console().readPassword("Enter password: "));
            if (username == null || profiles.size() >= MIN_HOMAN_PLAYERS) break;
            Profile p = domainController.getProfile(username, password);
            if (p == null) {
                System.out.println("Incorrect password");
                break;
            } else {
                System.out.println("Profile found: " + p.getUsername());
                profiles.add(p);
            }
        }



        System.out.print("Enter the size of the board: ");
        int boardSize = scanner.nextInt();

        System.out.print("What language do you want to play in? (es, en, ca): ");
        String lang = scanner.nextLine();

        System.out.print("Match name: ");
        String name = scanner.nextLine();

        scanner.close();
        String matchID = domainController.newMatch(players, profiles, lang, name, boardSize);

        while
        turn(matchID);
    }

    private static void continueGame() {
        Scanner scanner = new Scanner(System.in);

        boolean exit = false;
        while(!exit) {
            System.out.println("\n--- Continue Game ---");
            System.out.println("\nAvailavle games to continue:");

            System.out.print("Choose an option: ");
            List<Match> matches = domainController.getMatches();
            for (int i = 0; i < matches.size(); i++) {
                System.out.println((i + 1) + ". " + matches.get(i).getName());
            }
            System.out.println((matches.size() + 1) + ". Back");

            int option = scanner.nextInt();
            scanner.nextLine();

            if (option > 0 && option <= matches.size()) {
                Match selectedMatch = matches.get(option - 1);
                System.out.println("You selected: " + selectedMatch.getName());
                domainController.continueMatch(selectedMatch.getId());
                exit = true;
            } else if (option == matches.size() + 1) {
                exit = true;
                System.out.println("Exiting...");
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean exit = false;
        while(!exit) {
            System.out.println("\n--- Play game Menu ---");
            System.out.println("1. Play new game");
            System.out.println("2. Continue game");
            System.out.println("3. Back");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    GameDriver.newGame();
                    break;
                case 2:
                    GameDriver.continueGame();
                    break;
                case 3:
                    exit = true;
                    System.out.println("Exiting...");
                    scanner.close();
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
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the name of the dictionary: ");
        String name = scanner.nextLine();

        System.out.print("Enter the language of the dictionary: (You should enter one of this options: es/cat/en) ");
        String language = scanner.nextLine();

        System.out.print("Enter the name of the dictionary file without any format: ");
        String fileName = scanner.nextLine();

        domainController.createDictionary(name, language, fileName);
        System.out.println("Dictionary " + name + " created successfully.");
        scanner.close();
    }

    private static void removeDictionary() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the name of the dictionary to remove: ");
        String name = scanner.nextLine();

        domainController.removeDictionary(name);
        System.out.println("Dictionary " + name + " removed successfully.");
        scanner.close();
    }

    private static void addWordToDictionary() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the name of the dictionary to add a word to: ");
        String name = scanner.nextLine();

        System.out.print("Enter the word to add: ");
        String word = scanner.nextLine();

        domainController.addWordToDictionary(name, word);
        System.out.println("Word " + word + " added to dictionary " + name + " successfully.");
        scanner.close();
    }

    private static void removeWordFromDictionary() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the name of the dictionary to remove a word from: ");
        String name = scanner.nextLine();

        System.out.print("Enter the word to remove: ");
        String word = scanner.nextLine();

        domainController.removeWordFromDictionary(name, word);
        System.out.println("Word " + word + " removed from dictionary " + name + " successfully.");
        scanner.close();
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
        Scanner scanner = new Scanner(System.in);

        boolean exit = false;
        while(!exit) {
            System.out.println("\n--- Dictionary Menu ---");
            System.out.println("1. Create Dictionary");
            System.out.println("2. Remove dictionary");
            System.out.println("3. Add word to dictionary");
            System.out.println("4. Remove word from dictionary");
            System.out.println("5. Display current dictionaries");
            System.out.println("6. Back");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();
            scanner.nextLine();

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
                    scanner.close();
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
