package PresentationLayer;
//import javax.swing.*;
//import java.awt.*;
import java.util.*;

/**
 * MatchViewCtrl es el controlador de la vista del juego Scrabble.
 * Se encarga de crear y gestionar la instancia de MatchView, que representa la vista del juego.
 * Utiliza el patrón Singleton para asegurar que solo haya una instancia de MatchViewCtrl.
 * @author Marcos Arroyo
 */
public class MatchViewCtrl {
    private static MatchViewCtrl instance;
    private MatchView matchView;

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
    
}
