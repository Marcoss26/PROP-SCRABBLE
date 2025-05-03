package DomainLayer.Drivers;

import java.util.Scanner;
import DomainLayer.DomainClasses.ProfileController;
import DomainLayer.DomainClasses.Profile;

public class ProfileControllerDriver {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ProfileController profileController = ProfileController.getInstance();

        boolean exit = false;
        while (!exit) {
            System.out.println("\n--- Profile Controller Menu ---");
            System.out.println("1. Add Profile");
            System.out.println("2. Remove Profile");
            System.out.println("3. Check Profile Existence");
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