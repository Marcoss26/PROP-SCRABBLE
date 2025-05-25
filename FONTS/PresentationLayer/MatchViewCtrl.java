package PresentationLayer;
//import javax.swing.*;
//import java.awt.*;
import java.util.*;

public class MatchViewCtrl {
    private static MatchViewCtrl instance;
    private MatchView matchView;

    private MatchViewCtrl() {
        // Private constructor to prevent instantiation
    }

    public static MatchViewCtrl getInstance() {
        if (instance == null) {
            instance = new MatchViewCtrl();
        }
        return instance;
    }

    public MatchView createMatchView(Integer boardSize, Integer totalPlayers, ArrayList<String> players, ArrayList<String> letters) {
        matchView = new MatchView(boardSize, totalPlayers, players, letters);
        return matchView;
    }

    

    public MatchView getMatchView() {
        return matchView;
    }
    
}
