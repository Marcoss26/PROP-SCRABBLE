package PresentationLayer;
import java.util.ArrayList;
//import java.util.Arrays;
import javax.swing.*;

import Utils.Pair;
import java.awt.*;
import java.util.Set;

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
    private MatchViewCtrl matchViewCtrl;
    

    /**
     * Constructor de la clase MatchView.
     * Inicializa el panel con un diseño de BorderLayout y agrega los componentes necesarios
     * para mostrar el tablero, la bandeja de fichas y la información del juego.
     * @param boardSize Tamaño del tablero de juego (número de filas/columnas).
     * @param numPlayers Número de jugadores en el juego.
     * @param players Lista de nombres de los jugadores.
     * @param letters Lista de letras disponibles en la bandeja de fichas del jugador.
     */
    public MatchView(int boardSize, int numPlayers, ArrayList<String> players, ArrayList<String> letters, Set<String> specialChars) {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.decode("#332F2C"));
        matchViewCtrl = MatchViewCtrl.getInstance();

        initializeRack(letters);
        initializeBoard(boardSize, specialChars);
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
    private void initializeBoard(int size, Set<String> specialChars) {
        boardPanel = new BoardView(size, rackPanel, specialChars);
        rackPanel.revalidate();
        rackPanel.repaint();
    }

    /**
     * Inicializa la bandeja de fichas del jugador con las letras proporcionadas.
     * @param letters Lista de letras que se mostrarán en la bandeja de fichas.
     */
    private void initializeRack(ArrayList<String> letters) {
        rackPanel = new RackView(letters, this);
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

    public void skipTurn() {
        boardPanel.returnTilesToRack(rackPanel);
        matchViewCtrl.skipTurn();
    }

    public void submitTurn() {
        ArrayList<Pair<Integer,Integer>> coords_ini, coords_end;
        coords_ini = new ArrayList<>();
        coords_end = new ArrayList<>();
        coords_ini.add(new Pair<>(0,0));
        coords_ini.add(new Pair<>(0,0));
        coords_end.add(new Pair<>(0,0));
        coords_end.add(new Pair<>(0,0));
        ArrayList<String> words = boardPanel.computeWord(coords_ini, coords_end);
        Set<Pair<Integer, Integer>> jokers = boardPanel.getJokersPos();
        matchViewCtrl.submitTurn(coords_ini, coords_end, words, jokers);


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

    public void lockTilesPlaced() {
        boardPanel.lockTilesPlaced();
    }

    public void exchangeLetters(String letters) {
        matchViewCtrl.exchangeLetters(letters);
    }

    public void setBagTiles(int numTiles) {
        gameInfoPanel.setBagTiles(numTiles);
    }

    public void actBoardView(ArrayList<String> word, Integer[] coords) {
        boardPanel.actBoardView(word, coords, rackPanel);
    }

    
}

