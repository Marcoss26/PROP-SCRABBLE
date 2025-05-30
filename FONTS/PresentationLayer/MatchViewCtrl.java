package PresentationLayer;
//import javax.swing.*;
//import java.awt.*;
import java.util.*;
import Utils.Pair;

public class MatchViewCtrl {
    private static MatchViewCtrl instance;
    private MatchView matchView;
    private PresentationCtrl pc;

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
         pc = PresentationCtrl.getInstance();

        matchView = new MatchView(boardSize, totalPlayers, players, letters);
        return matchView;
    }

    

    public MatchView getMatchView() {
        return matchView;
    }

    public void skipTurn(){
        pc.skipTurn();
    }

    public void submitTurn(Pair<Integer, Integer> coord_ini, Pair<Integer, Integer> coord_end, String word) {
        pc.submitTurn(coord_ini, coord_end, word);
    }

    public void actPlayerScore(int turn, int score){
        matchView.actPlayerScore(turn, score);
    }
    public void cleanTilesplaced() {
        matchView.cleanTilesPlaced();
    }

    public void cleanRack() {
        matchView.cleanRack();
    }

    public void updateRack(ArrayList<String> letters) {
        matchView.updateRack(letters);
    }
}
