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

    /**
     * Constructor de la clase GameInfoView.
     * Inicializa el panel con un diseño vertical y agrega componentes para mostrar puntuaciones,
     * estado del "bag", historial de jugadas y botones de acción.
     * @param numPlayers Número de jugadores en el juego.
     * @param players Lista de nombres de los jugadores.
     */
    public GameInfoView(Integer numPlayers, List<String> players) {
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
            JLabel label = new JLabel(players.get(i) + ": 0");
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
        JButton save = new JButton("Save");
        save.setPreferredSize(new Dimension(100, 50));
        save.setFont(new Font("Dubai Medium", Font.PLAIN, 15));
        save.setBounds(100, 10, 80, 30);
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

    /**
     * Método principal para probar la vista GameInfoView.
     * Crea un JFrame y muestra la vista con algunos datos de ejemplo.
     * @param args Argumentos de línea de comandos (no se utilizan).
     */
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
