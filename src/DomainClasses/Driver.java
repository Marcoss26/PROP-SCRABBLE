package DomainClasses;
import java.util.*;

public class Driver {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean exit = false;
        while(!exit) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Play game");
            System.out.println("2. Manage profoles");
            System.out.println("3. Manage dicrionaries");
            System.out.println("4. Exit");
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













class GameDriver {
    static DomainController domainController = DomainController.getInstance();

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
        domainController.newMatch(players, profiles, lang, name, boardSize);
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
