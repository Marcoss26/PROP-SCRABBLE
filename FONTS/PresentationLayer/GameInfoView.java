package PresentationLayer;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * GameInfoView representa la vista de información del juego, mostrando puntuaciones, estado del "bag",
 * historial de jugadas y botones de acción.
 * Permite a los jugadores ver el estado actual del juego y realizar acciones como salir o guardar.
 * @author Marcos Arroyo
 */
public class GameInfoView extends JPanel {

    /**
     * Atributos de la clase GameInfoView.
     * @param scorePanel Panel que muestra las puntuaciones de los jugadores.
     * @param bagPanel Panel que muestra el estado del "bag" (bolsa de fichas).
     * @param historyPanel Panel que muestra el historial de jugadas.
     * @param buttPanel Panel que contiene los botones de acción.
     * @param historyModel Modelo de lista para el historial de jugadas.
     */
    private JPanel scorePanel;
    private JPanel bagPanel;
    private JPanel historyPanel;
    private JPanel buttPanel;
    private DefaultListModel<String> historyModel;
    private MatchView matchView;

    /**
     * Constructor de la clase GameInfoView.
     * Inicializa el panel con un diseño vertical y agrega componentes para mostrar puntuaciones,
     * estado del "bag", historial de jugadas y botones de acción.
     * @param numPlayers Número de jugadores en el juego.
     * @param players Lista de nombres de los jugadores.
     */
    public GameInfoView(Integer numPlayers, List<String> players, MatchView matchView) {
        this.matchView = matchView;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setPreferredSize(new Dimension(200,600));
        this.setBackground(Color.decode("#D7D7D7"));

        // Create the score panel
        scorePanel = new JPanel();
        scorePanel.setLayout(new GridLayout(4,1));
        scorePanel.setBackground(Color.decode("#D7D7D7"));
        scorePanel.setBorder(BorderFactory.createTitledBorder("Scores"));
        scorePanel.setPreferredSize(new Dimension(200, 200));
        for(int i = 0; i < numPlayers; ++i) {
            JLabel label = new JLabel(players.get(i) + ": " + 0);
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

        historyModel = new DefaultListModel<>();
        JList<String> historyList = new JList<>(historyModel);
        historyList.setFont(new Font("Dubai Medium", Font.PLAIN, 20));
        historyList.setBackground(Color.decode("#D7D7D7"));
        historyList.setVisibleRowCount(10);

        JScrollPane scrollPane = new JScrollPane(historyList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("History of plays"));
        scrollPane.setBackground(Color.decode("#D7D7D7"));
        scrollPane.setPreferredSize(new Dimension(200, 300));

        buttPanel = new JPanel();
        buttPanel.setLayout(null);
        buttPanel.setBackground(Color.decode("#D7D7D7"));

        JButton exit = new JButton("Exit");
        exit.setPreferredSize(new Dimension(60, 20));
        exit.setFont(new Font("Dubai Medium", Font.PLAIN, 15));
        exit.setBounds(10, 10, 80, 30);

        exit.addActionListener(e -> {
            // Action to perform when the exit button is clicked
            int result = JOptionPane.showConfirmDialog(
            this,
            "If u exit without saving before, your match will be lost, are you sure?",
            "Confirmar salida",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
             );

            if (result == JOptionPane.YES_OPTION) {
                matchView.showView("MainMenuView");
                matchView.deleteMatch();
            }
        });

        JButton save = new JButton("Save&Exit");
        save.setPreferredSize(new Dimension(200, 50));
        save.setFont(new Font("Dubai Medium", Font.PLAIN, 15));
        save.setBounds(100, 10, 80, 30);

        save.addActionListener(e -> {
            // Action to perform when the save button is clicked
            int result = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to save and exit?",
            "Confirmar guardado y salida",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
             );

            if (result == JOptionPane.YES_OPTION) {
                matchView.showView("MainMenuView");
            }
        });

        buttPanel.add(exit);
        buttPanel.add(save);

        // Add the components to the main panel
        this.add(scorePanel);
        this.add(Box.createVerticalStrut(1));
        this.add(bagPanel);
        this.add(Box.createVerticalStrut(1));
        this.add(scrollPane);
        this.add(Box.createVerticalGlue());
        this.add(buttPanel);


    }

/**
     * Agrega un movimiento al historial de jugadas.
     * @param player El nombre del jugador que realizó el movimiento.
     * @param word La palabra jugada.
     * @param points Los puntos obtenidos por la jugada.
     */
    public void addMoveToHistory(String player, String word, int points) {
        String move = player + " - \"" + word + "\" (" + points + " puntos)";
        historyModel.addElement(move);
    }



    public void actPlayerScore(int playerIndex, int score) {
        
        JLabel label = (JLabel) scorePanel.getComponent(playerIndex);
        label.setText(label.getText().split(":")[0] + ": " + score);
        scorePanel.revalidate();
        scorePanel.repaint();
    }

    public void setBagTiles(int tiles) {
        JLabel bagLabel = (JLabel) bagPanel.getComponent(0);
        bagLabel.setText("Tiles left in the bag: " + tiles);
        bagPanel.revalidate();
        bagPanel.repaint();
    }

}
