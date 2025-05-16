import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GameInfoView extends JPanel {
    private JPanel scorePanel;
    private JPanel bagPanel;
    private JPanel historyPanel;
    private DefaultListModel<String> historyModel;

    public GameInfoView(Integer numPlayers, List<String> players) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setPreferredSize(new Dimension(200,600));
        this.setBackground(Color.decode("#332f2c"));

        // Create the score panel
        scorePanel = new JPanel();
        scorePanel.setLayout(new GridLayout(2,2));
        scorePanel.setBackground(Color.decode("#D7D7D7"));
        scorePanel.setBorder(BorderFactory.createTitledBorder("Scores"));
        scorePanel.setPreferredSize(new Dimension(200, 200));
        for(int i = 0; i < numPlayers; ++i) {
            JLabel label = new JLabel(players.get(i) + ": 15");
            label.setFont(new Font("Dubai Medium", Font.PLAIN, 15));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            scorePanel.add(label);
        }

        // Create the bag panel
        bagPanel = new JPanel();
        bagPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bagPanel.setBackground(Color.decode("#D7D7D7"));
        bagPanel.setBorder(BorderFactory.createTitledBorder("Bag"));
        bagPanel.setPreferredSize(new Dimension(200, 100));

        int tilesRemaining = 100 - (7*numPlayers); // Example value
        JLabel bagLabel = new JLabel("Tiles left in the bag: " + tilesRemaining);
        bagLabel.setFont(new Font("Dubai Medium", Font.PLAIN, 15));
        bagLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bagPanel.add(bagLabel);

        // Create the history panel
       /* historyPanel = new JPanel();
        historyPanel.setLayout(new BorderLayout());
        historyPanel.setBackground(Color.decode("#D7D7D7"));
        historyPanel.setBorder(BorderFactory.createTitledBorder("History of plays"));
        historyPanel.setPreferredSize(new Dimension(200, 300));*/

        historyModel = new DefaultListModel<>();
        JList<String> historyList = new JList<>(historyModel);
        historyList.setFont(new Font("Dubai Medium", Font.PLAIN, 20));
        historyList.setBackground(Color.decode("#D7D7D7"));
        historyList.setVisibleRowCount(10);

        JScrollPane scrollPane = new JScrollPane(historyList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("History of plays"));
        scrollPane.setBackground(Color.decode("#D7D7D7"));
        scrollPane.setPreferredSize(new Dimension(200, 300));

        // Add the components to the main panel
        this.add(scorePanel);
        this.add(Box.createVerticalStrut(1));
        this.add(bagPanel);
        this.add(Box.createVerticalStrut(1));
        this.add(scrollPane);


    }

    public void addMoveToHistory(String player, String word, int points) {
        String move = player + " - \"" + word + "\" (" + points + " puntos)";
        historyModel.addElement(move);
    }

    public static void main(String[] args) {
        // Crear un JFrame para mostrar la vista
        JFrame frame = new JFrame("Game Info View");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 700);

        // Lista de nombres de los jugadores
        List<String> players = List.of("Jugador 1", "Jugador 2", "Jugador 3", "Jugador 4");

        // Crear la vista con 4 jugadores
        GameInfoView gameInfoView = new GameInfoView(4, players);

        // Agregar un movimiento de ejemplo a la historia
        gameInfoView.addMoveToHistory("Jugador 1", "HOLA", 10);
        gameInfoView.addMoveToHistory("Jugador 2", "MUNDO", 15);
        gameInfoView.addMoveToHistory("Jugador 3", "JAVA", 20);
        gameInfoView.addMoveToHistory("Jugador 4", "SCRABBLE", 25);



        // Agregar la vista al JFrame
        frame.add(gameInfoView);

        // Hacer visible el JFrame
        frame.setVisible(true);
    }

}
