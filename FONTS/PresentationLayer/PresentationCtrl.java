package PresentationLayer;
import javax.swing.*;
import DomainLayer.DomainClasses.DomainController;
import java.awt.*;
import java.util.*;

public class PresentationCtrl {
    private JFrame mainFrame;
	private JPanel visiblePanel;
    private static PresentationCtrl instance;
    private Map<String, JPanel> createdViews;
    private CreationCtrl cc;
    private DomainController domainCtrl;

    

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
        int numPlayers = cc.getHumanPlayers();
        int totalPlayers = cc.getTotalPlayers();
        int boardSize = cc.getBoardSize();
        String dictionary = cc.getDictionary();
        Set<Pair<String,String>> playersId = cc.getPlayersId();

        //domainCtrl.newMatch(numPlayers, totalPlayers, playersId, dictionary, boardSize);


    }

       

    public void initializeViews() {
        mainFrame = new JFrame("Scrabble");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setSize(new Dimension(1060, 650));
        mainFrame.setLocation(200, 100);

        cc = CreationCtrl.getInstance();

       visiblePanel = new MainMenuView();
       mainFrame.add(visiblePanel);

        createdViews = new HashMap<>();
        createdViews.put("MainMenuView", visiblePanel);
        
        mainFrame.setVisible(true);

        
    }

    public void refresh(){
        mainFrame.revalidate();
        mainFrame.repaint();
    }

        

    public void showView(String viewName) {
        changeView(viewName);
       mainFrame.add(visiblePanel);
       mainFrame.revalidate();
       mainFrame.repaint();
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

            default:
                System.out.println("Invalid view name: " + viewName);
                return;
        }
   }

}





