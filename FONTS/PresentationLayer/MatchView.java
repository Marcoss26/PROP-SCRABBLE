package PresentationLayer;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;
import java.awt.*;

/**
 * MatchView representa la vista del juego Scrabble, mostrando el tablero, la bandeja de fichas
 * y la información del juego como puntuaciones y estado de la bolsa.
 * Permite a los jugadores interactuar con el tablero y ver el estado actual del juego.
 * @author Marcos Arroyo
 */
public class MatchView extends JPanel {

    /**
     * Atributos de la clase MatchView.
     * @param boardPanel Panel que muestra el tablero de juego.
     * @param rackPanel Panel que muestra la bandeja de fichas del jugador.
     * @param BoardAndRack Panel que combina el tablero y la bandeja de fichas.
     * @param gameInfoPanel Panel que muestra la información del juego, como puntuaciones y estado de la bolsa.
     */
    private BoardView boardPanel;
    private RackView rackPanel;
    private JPanel BoardAndRack;
    private GameInfoView gameInfoPanel;

    /**
     * Constructor de la clase MatchView.
     * Inicializa el panel con un diseño de BorderLayout y agrega los componentes necesarios
     * para mostrar el tablero, la bandeja de fichas y la información del juego.
     * @param boardSize Tamaño del tablero de juego (número de filas/columnas).
     * @param numPlayers Número de jugadores en el juego.
     * @param players Lista de nombres de los jugadores.
     * @param letters Lista de letras disponibles en la bandeja de fichas del jugador.
     */
    public MatchView(int boardSize, int numPlayers, ArrayList<String> players, ArrayList<String> letters) {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.decode("#332F2C"));
        

        initializeRack(letters);
        initializeBoard(boardSize);
        CombinePanels();
        initializeGameInfo(numPlayers, players);

        this.add(BoardAndRack, BorderLayout.CENTER);
        //this.add(rackPanel, BorderLayout.SOUTH);
        this.add(gameInfoPanel, BorderLayout.EAST);
    }

    /**
     * Combina el panel del tablero y la bandeja de fichas en un solo panel.
     * Este panel se utiliza para mostrar el tablero y la bandeja de fichas juntos.
     */
    private void CombinePanels() {
        BoardAndRack = new JPanel();
        BoardAndRack.setLayout(new BorderLayout());
        BoardAndRack.setBackground(Color.decode("#332F2C"));
        BoardAndRack.add(boardPanel, BorderLayout.CENTER);
        BoardAndRack.add(rackPanel, BorderLayout.SOUTH);
    }

    /**
     * Inicializa el panel del tablero de juego con el tamaño especificado.
     * @param size Tamaño del tablero (número de filas/columnas).
     */
    private void initializeBoard(int size) {
        boardPanel = new BoardView(size, rackPanel);
        rackPanel.revalidate();
        rackPanel.repaint();
    }

    /**
     * Inicializa la bandeja de fichas del jugador con las letras proporcionadas.
     * @param letters Lista de letras que se mostrarán en la bandeja de fichas.
     */
    private void initializeRack(ArrayList<String> letters) {
        rackPanel = new RackView(letters);
    }

    /**
     * Inicializa el panel de información del juego con el número de jugadores y sus nombres.
     * Este panel muestra las puntuaciones, el estado de la bolsa y el historial de jugadas.
     * @param numPlayers Número de jugadores en el juego.
     * @param players Lista de nombres de los jugadores.
     */
    private void initializeGameInfo(int numPlayers, ArrayList<String> players) {
        gameInfoPanel = new GameInfoView(numPlayers, players);
    }

    /**
     * Método principal para probar la vista del juego.
     * Crea un JFrame y muestra la vista del juego con un ejemplo de datos.
     */
    /*public static void main(String[] args) {
        JFrame frame = new JFrame("Match Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        frame.setSize(screenSize.width, screenSize.height);
        List<String> rackl = List.of("A", "1", "B", "3", "C", "3", "D", "2", "E", "1", "F", "4", "G", "2");
        MatchView matchView = new MatchView(15, 3, List.of("Player 1", "Player 2", "Player 3"), rackl);
        frame.add(matchView);

        frame.setVisible(true);
    }*/ 

public static void main(String[] args) {
    JFrame frame = new JFrame("Match Example");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    frame.setSize(screenSize.width, screenSize.height);

    ArrayList<String> rackl = new ArrayList<>(Arrays.asList("A", "1", "B", "3", "C", "3", "D", "2", "E", "1", "F", "4", "G", "2"));
    ArrayList<String> players = new ArrayList<>(Arrays.asList("Player 1", "Player 2", "Player 3"));

    MatchView matchView = new MatchView(15, 3, players, rackl);
    frame.add(matchView);

    frame.setVisible(true);
}
}

