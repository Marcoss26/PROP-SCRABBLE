package PresentationLayer;
import javax.swing.*;
import java.awt.*;
import java.util.List;

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

    public MatchView createMatchView(Integer numPlayers, List<String> players) {
        matchView = new MatchView(numPlayers, players);
        return matchView;
    }

    public void updateMatchInfo(Integer numPlayers, List<String> players) {
        if (matchView != null) {
            matchView.updateInfo(numPlayers, players);
        }
    }

    public MatchView getMatchView() {
        return matchView;
    }
    
}
