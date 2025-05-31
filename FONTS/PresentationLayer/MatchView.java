package PresentationLayer;
import java.util.ArrayList;
import javax.swing.*;

import Utils.Pair;
import java.awt.*;

public class MatchView extends JPanel {
    private BoardView boardPanel;
    private RackView rackPanel;
    private JPanel BoardAndRack;
    private GameInfoView gameInfoPanel;
    private MatchViewCtrl matchViewCtrl;
    

    public MatchView(int boardSize, int numPlayers, ArrayList<String> players, ArrayList<String> letters) {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.decode("#332F2C"));
        matchViewCtrl = MatchViewCtrl.getInstance();

        initializeRack(letters);
        initializeBoard(boardSize);
        CombinePanels();
        initializeGameInfo(numPlayers, players);

        this.add(BoardAndRack, BorderLayout.CENTER);
        //this.add(rackPanel, BorderLayout.SOUTH);
        this.add(gameInfoPanel, BorderLayout.EAST);
    }
    
  

    private void CombinePanels() {
        BoardAndRack = new JPanel();
        BoardAndRack.setLayout(new BorderLayout());
        BoardAndRack.setBackground(Color.decode("#332F2C"));
        BoardAndRack.add(boardPanel, BorderLayout.CENTER);
        BoardAndRack.add(rackPanel, BorderLayout.SOUTH);
    }

    private void initializeBoard(int size) {
        boardPanel = new BoardView(size, rackPanel);
        rackPanel.revalidate();
        rackPanel.repaint();
    }

    private void initializeRack(ArrayList<String> letters) {
        rackPanel = new RackView(letters, this);
    }

    private void initializeGameInfo(int numPlayers, ArrayList<String> players) {
        gameInfoPanel = new GameInfoView(numPlayers, players);
    }

    public void skipTurn() {
        matchViewCtrl.skipTurn();
    }

    public void submitTurn() {
        Pair<Integer,Integer> coord_ini, coord_end;
        coord_ini = new Pair<>(0,0);
        coord_end = new Pair<>(0,0);
        String word = boardPanel.computeWord(coord_ini, coord_end);
        matchViewCtrl.submitTurn(coord_ini, coord_end, word);


    }
    public void actPlayerScore(int turn, int score) {

        gameInfoPanel.actPlayerScore(turn, score);
    }

    public void cleanTilesPlaced() {
        boardPanel.cleanTilesPlaced();
    }

    public void cleanRack() {
        rackPanel.cleanRack();
    }

    public void updateRack(ArrayList<String> letters) {
        rackPanel.updateRack(letters);
    }

    public void shuffleRack() {
        matchViewCtrl.shuffleRack();
    }

    
}

