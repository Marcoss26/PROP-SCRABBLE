package PresentationLayer;
//import javax.swing.*;
//import java.awt.*;
import java.util.*;
import Utils.Pair;

/**
 * MatchViewCtrl es el controlador de la vista del juego Scrabble.
 * Se encarga de crear y gestionar la instancia de MatchView, que representa la vista del juego.
 * Utiliza el patrón Singleton para asegurar que solo haya una instancia de MatchViewCtrl.
 * @author Marcos Arroyo
 */
public class MatchViewCtrl {
    private static MatchViewCtrl instance;
    private MatchView matchView;
    private PresentationCtrl pc;

    /**
     * Constructor privado para implementar el patrón Singleton.
     * Evita la creación de instancias adicionales de MatchViewCtrl.
     */
    private MatchViewCtrl() {

    }

    /**
     * Método estático para obtener la instancia única de MatchViewCtrl.
     * Si no existe una instancia, la crea.
     * @return La instancia única de MatchViewCtrl.
     */
    public static MatchViewCtrl getInstance() {
        if (instance == null) {
            instance = new MatchViewCtrl();
        }
        return instance;
    }

    /**
     * Crea una nueva instancia de MatchView con los parámetros proporcionados.
     * @param boardSize Tamaño del tablero de juego (número de filas/columnas).
     * @param totalPlayers Número total de jugadores en el juego.
     * @param players Lista de nombres de los jugadores.
     * @param letters Lista de letras disponibles en la bandeja de fichas del jugador.
     * @return La instancia creada de MatchView.
     */
    public MatchView createMatchView(Integer boardSize, Integer totalPlayers, ArrayList<String> players, ArrayList<String> letters) {
         pc = PresentationCtrl.getInstance();

        matchView = new MatchView(boardSize, totalPlayers, players, letters);
        return matchView;
    }

    /**
     * Obtiene la instancia actual de MatchView.
     * @return La instancia de MatchView asociada a este controlador.
     */
    public MatchView getMatchView() {
        return matchView;
    }

    public void skipTurn(){
        pc.skipTurn();
    }

    public void submitTurn(ArrayList<Pair<Integer, Integer>> coord_ini, ArrayList<Pair<Integer, Integer>> coord_end, ArrayList<String> word, Set<Pair<Integer, Integer>> jokers) {
        pc.submitTurn(coord_ini, coord_end, word, jokers);
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

    public void shuffleRack() {
        pc.shuffleRack();
    }

    public void lockTilesPlaced() {
        matchView.lockTilesPlaced();
    }

    public void exchangeLetters(String letters) {
        pc.exchangeLetters(letters);
    }

    public void setBagTiles(Integer numTiles) {
        matchView.setBagTiles(numTiles);
    }

    public void actBoardView(ArrayList<String> word, Integer[] coords){
        matchView.actBoardView(word, coords);
    }
}
