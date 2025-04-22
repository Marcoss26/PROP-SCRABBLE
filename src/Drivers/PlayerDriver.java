package Drivers;
import java.util.*;
import java.io.*;

import DomainClasses.Player;
import DomainClasses.Profile;
import DomainClasses.Human;
import DomainClasses.IA;


public class PlayerDriver {
    public static void main(String args[])
    {
        System.out.println("Welcome to the Player Driver!");
        System.out.println("This driver will create players and display their information.");
        Scanner scanner = new Scanner(System.in);
        System.out.println("How many human players do you want to create?");
        int numPlayers = scanner.nextInt();
        List<Human> humanPlayers = new ArrayList<Human>();
        for(int i = 0; i < numPlayers; ++i)
        {
            System.out.println("Enter the username of player " + (i+1) + ": ");
            String username = scanner.next();
            System.out.println("Enter the pwd of player " + (i+1) + ": ");
            String id = scanner.next();
            Profile profile = new Profile(username, id);
            Human human = new Human(id, profile,"Spanish"); // Assuming null for match for now
            humanPlayers.add(human);
        }
        System.out.println("How many IA players do you want to create?");
        int numIA = scanner.nextInt();
        List<IA> iaPlayers = new ArrayList<IA>();
        for(int i = 0; i < numIA; ++i)
        {
            System.out.println("Enter the id of IA player " + (i+1) + ": ");
            String id = scanner.next();
            IA ia = new IA(id, i); // Assuming null for match for now
            iaPlayers.add(ia);
        }
        for(int i = 0; i < numPlayers; ++i)
        {
            Human human = humanPlayers.get(i);
            human.displayPlayer();
        }
        for(int i = 0; i < numIA; ++i)
        {
            IA ia = iaPlayers.get(i);
            ia.displayPlayer();
        }
    }
}
