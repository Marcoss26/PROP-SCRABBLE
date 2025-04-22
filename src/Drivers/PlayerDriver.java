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
        Scanner scanner = new Scanner(System.in);
        System.out.println("How many human players do you want to create?");
        int numPlayers = scanner.nextInt();
        Set<Human> humanPlayers = new HashSet<Human>(); 
        for(int i = 0; i < numPlayers; ++i)
        {
            
        }
    }
}
