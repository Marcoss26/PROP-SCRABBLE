package PresentationLayer;
//import javax.swing.*;
//import java.awt.*;
import java.util.*;

//import javax.swing.JOptionPane;



/**
 * CreationCtrl es el controlador de la capa de presentación que gestiona la creación de partidas,
 * el inicio de sesión de jugadores y la visualización de perfiles.
 * Permite crear vistas para nuevas partidas, iniciar sesión y mostrar perfiles de jugadores.
 * 
 * @author Marcos Arroyo
 */
public class CtrlRankingView {
    private static CtrlRankingView instance;
    private RankingView rankingView;
    private PresentationCtrl pc;

    /**
     * Constructor privado para implementar el patrón Singleton.
     * Evita la creación de instancias adicionales de CtrlRankingView.
     */
    private CtrlRankingView() {
        pc = PresentationCtrl.getInstance();
    }

    /**
     * Método estático para obtener la instancia única de CtrlRankingView.
     * Si no existe una instancia, la crea.
     * 
     * @return La instancia única de CtrlRankingView.
     */
    public static CtrlRankingView getInstance() {
        if (instance == null) {
            instance = new CtrlRankingView();
        }
        return instance;
    }

    /**
     * Crea una nueva instancia de RankingView con los parámetros proporcionados.
     * 
     * @param scores Lista de puntuaciones de los jugadores, donde cada entrada es un par con el nombre del jugador y su puntuación.
     * @return La instancia creada de RankingView.
     */
    public RankingView createRankingView() {
        rankingView = new RankingView();
        return rankingView;
    }

    public void setRankingData(ArrayList<ArrayList<String>> RankingData) {
        for(int i = 0; i < RankingData.size(); i++) {
            ArrayList<String> userInfo = RankingData.get(i);
            int rank = i + 1; // El rango es el índice + 1
            String profileID = userInfo.get(0);
            String wins = userInfo.get(1);
            String winRate = userInfo.get(2);
            String totalGames = userInfo.get(3);
            String ppg = userInfo.get(4);
            String preferredDictionary = userInfo.get(5);
            rankingView.addUserToRanking(rank, profileID, wins, winRate, totalGames, ppg, preferredDictionary);

        }
    }

    public void cleanRanking() {
        rankingView.cleanRanking(); // Limpia la tabla del ranking
    }

    /**
     * Obtiene la instancia actual de RankingView.
     * 
     * @return La instancia de RankingView asociada a este controlador.
     */
    public RankingView getRankingView() {
        return rankingView;
    }
}
