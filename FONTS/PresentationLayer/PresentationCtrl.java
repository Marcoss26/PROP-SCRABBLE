package PresentationLayer;
import javax.swing.*;
//import DomainLayer.DomainClasses.DomainController;
import java.awt.*;
import java.util.*;

public class PresentationCtrl {
    private JFrame mainFrame;
	private JPanel visiblePanel;
    private static PresentationCtrl instance;
    private Map<String, JPanel> createdViews;
    //private DomainController domainCtrl;

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
   /*
    public void initializeCD(){
        domainCtrl = DomainController.getInstance();
    }
        */

    public void initializeViews() {
        mainFrame = new JFrame("Scrabble");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setSize(screenSize.width, screenSize.height);

        visiblePanel = new MainMenuView();
        mainFrame.add(visiblePanel);

        createdViews = new HashMap<>();
        createdViews.put("MainMenuView", visiblePanel);
        
        mainFrame.setVisible(true);

        
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
            /*case "NewGame":
                if(!createdViews.containsKey("NewGame")) {
                    visiblePanel = new NewGame(); // hay que cambiar el new Game a un JPanel
                    createdViews.put("NewGame", visiblePanel);
                } else {
                    visiblePanel = createdViews.get("NewGame");
                }
                break;*/
            case "LoginView":
                if(!createdViews.containsKey("LoginView")) {
                    visiblePanel = new LoginView(); 
                    createdViews.put("LoginView", visiblePanel);
                } else {
                    visiblePanel = createdViews.get("LoginView");
                }
                break;
            default:
                System.out.println("Invalid view name");
                return;
        }
    }

}
