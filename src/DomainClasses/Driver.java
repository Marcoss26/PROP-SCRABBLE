package DomainClasses;
import java.util.*;

class GameDriver {
    static DomainController domainController = DomainController.getInstance();

    private static void newGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--- New Game ---");

        System.out.print("Enter the number of players: ");
        int players = scanner.nextInt();

        System.out.print("Enter the size of the board: ");
        int boardSize = scanner.nextInt();




        final int MIN_HOMAN_PLAYERS = 1;

        Set<Profile> profiles = new HashSet<>();

        while(profiles.size() < players) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            String password = new String(System.console().readPassword("Enter password: "));

            if (username == "" && profiles.size() >= MIN_HOMAN_PLAYERS) break;

            Profile p = domainController.getProfile(username, password);
            if (p == null) {
                System.out.println("Profile not found.");
                return;
            } else {
                if (p.authenticate(password)) {
                    profiles.add(p);
                } else {
                    System.out.println("Invalid password");
                    return;
                }
            }
        }



        scanner.close();
        domainController.newMatch(players, boardSize);
    }

    private static void continueGame() {
        Scanner scanner = new Scanner(System.in);

        boolean exit = false;
        while(!exit) {
            System.out.println("\n--- Continue Game ---");
            System.out.println("\nAvailavle games to continue:");

            System.out.print("Choose an option: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                   
                    break;
                case 2:
                   
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