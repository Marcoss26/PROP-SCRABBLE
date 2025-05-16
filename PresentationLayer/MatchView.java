import java.util.List;
import javax.swing.*;
import java.awt.*;

public class MatchView extends JPanel {
    private BoardView boardPanel;
    private RackView rackPanel;
    private JPanel BoardAndRack;
    private GameInfoView gameInfoPanel;

    public MatchView(int boardSize, int numPlayers, List<String> players, List<String> letters) {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.decode("#332F2C"));

        initializeBoard(boardSize);
        initializeRack(letters);
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
        boardPanel = new BoardView(size);
    }

    private void initializeRack(List<String> letters) {
        rackPanel = new RackView(letters);
    }

    private void initializeGameInfo(int numPlayers, List<String> players) {
        gameInfoPanel = new GameInfoView(numPlayers, players);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Match Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        frame.setSize(screenSize.width, screenSize.height);
        List<String> rackl = List.of("A", "1", "B", "3", "C", "3", "D", "2", "E", "1", "F", "4", "G", "2");
        MatchView matchView = new MatchView(15, 3, List.of("Player 1", "Player 2", "Player 3"), rackl);
        frame.add(matchView);

        frame.setVisible(true);
    }
}

