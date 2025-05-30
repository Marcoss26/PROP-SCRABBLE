package PresentationLayer;
import javax.swing.*;

import DomainLayer.DomainClasses.DomainController;
//import DomainLayer.DomainClasses.Match;
import Utils.Pair;

import java.awt.*;
import java.util.*;
import java.util.ArrayList;

public class PresentationCtrl {
    private JFrame mainFrame;
	private JPanel visiblePanel;
    private int clientWidth = 1060;
    private int clientHeight = 650;
    private static PresentationCtrl instance;
    private Map<String, JPanel> createdViews;
    private CreationCtrl cc;
    private DomainController domainCtrl;
    private MatchViewCtrl matchViewCtrl;
    private int turn;
    private String matchId;

    

    // CONSTRUCTOR, its a Singleton class
    private PresentationCtrl() {

    }

    public static PresentationCtrl getInstance() {
        if (instance == null) {
            instance = new PresentationCtrl();
       }
        return instance;
    }

    //Con esta funcion se inicializa el controlador del dominio y está listo para utilizar
   
    public void initializeCD(){
        domainCtrl = DomainController.getInstance();
    }

    //Funciones relacionadas con el ctrl dominio

   
    public void createNewMatch(){
        //int numHumPlayers = cc.getHumanPlayers();
        int totalPlayers = cc.getTotalPlayers();
        int boardSize = cc.getBoardSize();
        String dictionary = cc.getDictionary();
        System.out.println(dictionary);
        Set<Pair<String,String>> playersId = cc.getPlayersId();
        matchId = null;

        try {
            matchId = domainCtrl.newMatch(totalPlayers, playersId, dictionary, boardSize);
            System.out.println("Match created successfully.");
        } catch (Exception e) {
            System.out.println("Error creating match: " + e.getMessage());
        }

        //Ahora querré crear una nueva vista de match view, por lo que llamo al controlador para que la cree
        // para hacerlo necesito: boardsize, numPlayers, players y letters
        //boardsize y numplayers los tengo, players y letters los tengo que sacar del dominio
        turn = 0;
        
        ArrayList<String> players = new ArrayList<>();
        ArrayList<String> letters = new ArrayList<>();
        if(matchId != null) {
            players = domainCtrl.getMatchplayers(matchId);
            letters = domainCtrl.getRackLetters(matchId, turn);
        }
        matchViewCtrl = MatchViewCtrl.getInstance();
        JPanel pan = matchViewCtrl.createMatchView(boardSize, totalPlayers, players, letters);
        createdViews.put("MatchView", pan );


    }

    private void actTurn() {
        ++turn;
        if(turn >= cc.getTotalPlayers()){
            turn = 0; //vuelvo al primer jugador
        }
        
    }

    public void skipTurn() {
        
        actTurn();
        startTurn();
    }

    public void submitTurn(Pair<Integer, Integer> coord_ini, Pair<Integer, Integer> coord_end, String word) {
        
        boolean valid = domainCtrl.playsMatch(matchId, word, coord_ini.first(), coord_ini.second(), coord_end.first(), coord_end.second());
        if(!valid){
            //si no es valido, muestro un mensaje de error
            showErrorDialog("Invalid move. Please try again.");
            return;
        }
        else{
            showSuccessDialog(word + " is valid, successful move.");
            int score = domainCtrl.getPlayerScore(matchId, turn);
            matchViewCtrl.actPlayerScore(turn, score);
            actTurn();
            startTurn();
        }
    }

    public void startTurn(){

        if(domainCtrl.isGameFinished(matchId)){
            //si el juego ha terminado, muestro la vista de ranking
            showView("EndGameView");
            return;
        }

        if (domainCtrl.isHumanTurn(matchId, turn)) {
            //Aqui no se hace nada, porque estoy esperando la accion del humano, que se registrará cuando le de a cierto boton
        }
        else{
           /* ArrayList<String> playData = domainCtrl.AIplayTurn(matchId, turn);
            matchViewCtrl.actBoardView(playData);
            actTurn();
            startTurn();*/


        }

    }

  

    public boolean profInSystem(String username) {
        return domainCtrl.profInSystem(username);
    }

    public boolean checkPassword(String username, String password) {
        return domainCtrl.checkPassword(username, password);
    }

    public void createProfile(String username, String password){
        domainCtrl.addProfile(username, password);
    }


       

    public void initializeViews() {
        mainFrame = new JFrame("Scrabble");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setSize(new Dimension(1060, 650));
        mainFrame.setLocation(200, 100);

        cc = CreationCtrl.getInstance();
        initializeCD();

        //crear ProfileView generica y meterla en el mapa de vistas
       


       visiblePanel = new MainMenuView();
       mainFrame.add(visiblePanel);

        createdViews = new HashMap<>();
        createdViews.put("MainMenuView", visiblePanel);
        createdViews.put("ProfileView", cc.createProfileView("username", 0, 0, 0.0f));
        mainFrame.setVisible(true);

        
    }

    public void refresh(){
        mainFrame.revalidate();
        mainFrame.repaint();
    }

        

    public void showView(String viewName) {
       changeView(viewName);
       mainFrame.add(visiblePanel);
       if(viewName.equals("MatchView") && mainFrame.getWidth() <= 1060 ){
            //cojo las medidad maximas de la pantalla
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            //y las pongo como tamaño del frame
            mainFrame.setSize(screenSize.width, screenSize.height);
            //y ponho el frame en la esquina superior izquierda
            mainFrame.setLocation(0, 0);
       }
       else if(!viewName.equals("MatchView") && mainFrame.getWidth() >1060){
            //si no es matchview, vuelvo a las medidas originales
            mainFrame.setSize(new Dimension(1060, 650));
       }
       refresh();
       
   }

   public void showLoginView(String mode){

        cc.setMode(mode);
        showView("LoginView");

   }

   public void showProfileView(String username, String password){

        //lo que hara esta funcion es coger del dominio los datos del perfil, setear la vista con estos datos y entonces mostrarla por pantalla 
        ArrayList<String> stats = domainCtrl.getProfileStats(username, password);
        cc.setProfileFields(username, Integer.parseInt(stats.get(1)), Integer.parseInt(stats.get(2)), Float.parseFloat(stats.get(3).replace(',', '.')));
        showView("ProfileView");

   }

    private void changeView(String viewName){
        mainFrame.remove(visiblePanel);
        switch (viewName) {
            case "MainMenuView":
                visiblePanel = createdViews.get(viewName);
                break;

            case "NewGame":
                if (!createdViews.containsKey("NewGame")) {
                    visiblePanel = cc.createNewGameView(); // Crear la vista NewGame si no existe
                    createdViews.put("NewGame", visiblePanel);
                } else {
                    visiblePanel = cc.getNewGameView();
                }
                break;

            case "LoginView":
                if (!createdViews.containsKey("LoginView")) {
                    visiblePanel = cc.createLoginView();
                    createdViews.put("LoginView", visiblePanel);
                } else {
                    visiblePanel = createdViews.get("LoginView");
                }
                break;
            case "LoadGame":
                if (!createdViews.containsKey("LoadGame")) {
                    visiblePanel = new LoadGame(); // Crear la vista LoadGame si no existe
                    createdViews.put("LoadGame", visiblePanel);
                } else {
                    visiblePanel = createdViews.get("LoadGame");
                }
                break;
            case "ManageDictionaryView":
                if (!createdViews.containsKey("ManageDictionaryView")) {
                    visiblePanel = new ManageDictionaryView(); // Crear la vista si no existe
                    createdViews.put("ManageDictionaryView", visiblePanel);
                } else {
                    visiblePanel = createdViews.get("ManageDictionaryView");
                }
                break;

            case "RankingView":
                if (!createdViews.containsKey("RankingView")) {
                    visiblePanel = new RankingView(); // Crear la vista si no existe
                    createdViews.put("RankingView", visiblePanel);
                } else {
                    visiblePanel = createdViews.get("RankingView");
                }
                break;
            
            case "MatchView":
                visiblePanel = createdViews.get("MatchView");

            case "ProfileView":
                //esta vista es un poco diferente, porque la tengo que actualizar, cada vez que la muestro necesito actualizarla con los valores actuales
                
                    visiblePanel = createdViews.get("ProfileView");
                
                break;

            default:
                System.out.println("Invalid view name: " + viewName);
                return;
        }
   }

   public void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(mainFrame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showSuccessDialog(String message) {
        JOptionPane.showMessageDialog(mainFrame, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

}





