package Drivers;

import DomainClasses.Ranking;
import DomainClasses.Profile;
import DomainClasses.ProfileController;

import java.util.*;

public class RankingDriver {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Ranking ranking = Ranking.getInstance(); // Acceso al Singleton de Ranking
        ProfileController profileController = ProfileController.getInstance(); // Acceso al Singleton de ProfileController

        // Bucle para crear perfiles y configurar sus datos
        boolean inputDone = true;
        while (inputDone) {
            System.out.println("\n--- Menú de Creación de Perfiles ---");
            System.out.println("1. Crear un nuevo perfil");
            System.out.println("2. Mostrar todos los perfiles");
            System.out.println("3. Salir de la creación de perfiles");
            System.out.print("Elige una opción: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    System.out.print("Introduce el nombre de usuario: ");
                    String username = scanner.next();
                    System.out.print("Introduce la contraseña: ");
                    String password = scanner.next();

                    try {
                        profileController.addProfile(username, password);
                        Profile profile = profileController.getProfile(username, password);

                        System.out.print("Introduce el número de victorias: ");
                        int wins = scanner.nextInt();
                        profile.setWins(wins);

                        System.out.print("Introduce el número de partidas jugadas: ");
                        int gamesPlayed = scanner.nextInt();
                        profile.setGamesPlayed(gamesPlayed);

                        System.out.println("Introduce los diccionarios utilizados (formato: nombreDiccionario cantidad). Escribe 'fin' para terminar:");
                        while (true) {
                            System.out.print("Nombre Diccionario: ");
                            String dictionary = scanner.next();
                            if (dictionary.equalsIgnoreCase("fin")) break;

                            System.out.print("Cantidad de partidas: ");
                            int usage = scanner.nextInt();
                            for (int i = 0; i < usage; i++) {
                                profile.incrementDictionaryUsage(dictionary);
                            }
                        }

                        System.out.println("Perfil creado y configurado correctamente.");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 2:
                    System.out.println("\n--- Perfiles Actuales ---");
                    profileController.printProfiles();
                    break;

                case 3:
                    inputDone = false;
                    break;

                default:
                    System.out.println("Opción no válida. Intenta de nuevo.");
            }
        }

        // Bucle para probar las funcionalidades de Ranking
        
        while (true) {
            System.out.println("\n--- Menú de Funcionalidades de Ranking ---");
            System.out.println("1. Actualizar el ranking con un perfil");
            System.out.println("2. Mostrar el ranking");
            System.out.println("3. Salir del programa");
            System.out.print("Elige una opción: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    System.out.print("Introduce el nombre de usuario del perfil a actualizar en el ranking: ");
                    String username = scanner.next();
                    System.out.print("Introduce la contraseña del perfil: ");
                    String password = scanner.next();

                    Profile profile = profileController.getProfile(username, password);
                    if (profile != null) {
                        ranking.updateRanking(profile);
                        System.out.println("Ranking actualizado con el perfil: " + username);
                    } else {
                        System.out.println("Perfil no encontrado o contraseña incorrecta.");
                    }
                    break;

                case 2:
                    try {
                        ranking.displayRanking();
                    } catch (IllegalStateException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

               

                case 3:
                    System.out.println("Saliendo del programa...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Opción no válida. Intenta de nuevo.");
            }
        }

        
    }
}
