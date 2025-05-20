//package PresentationLayer;
//import javax.swing.*;
////import DomainLayer.DomainClasses.DomainController;
//import java.awt.*;
//import java.util.*;
//
//public class PresentationCtrl {
//    private JFrame mainFrame;
//	private JPanel visiblePanel;
//    private static PresentationCtrl instance;
//    private Map<String, JPanel> createdViews;
//    //private DomainController domainCtrl;
//
//    // CONSTRUCTOR, its a Singleton class
//    private PresentationCtrl() {
//
//    }
//
//    public static PresentationCtrl getInstance() {
//        if (instance == null) {
//            instance = new PresentationCtrl();
//        }
//        return instance;
//    }
//
//    //Con esta funcion se inicializa el controlador del dominio y está listo para utilizar
//   /*
//    public void initializeCD(){
//        domainCtrl = DomainController.getInstance();
//    }
//        */
//
//    public void initializeViews() {
//        mainFrame = new JFrame("Scrabble");
//        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        mainFrame.setSize(screenSize.width, screenSize.height);
//
//        visiblePanel = new MainMenuView();
//        mainFrame.add(visiblePanel);
//
//        createdViews = new HashMap<>();
//        createdViews.put("MainMenuView", visiblePanel);
//        
//        mainFrame.setVisible(true);
//
//        
//    }
//
//    public void showView(String viewName) {
//        changeView(viewName);
//        mainFrame.add(visiblePanel);
//        mainFrame.revalidate();
//        mainFrame.repaint();
//    }
//
//    private void changeView(String viewName){
//        mainFrame.remove(visiblePanel);
//        switch (viewName) {
//            case "MainMenuView":  
//                visiblePanel = createdViews.get(viewName);
//                break;
//            /*case "NewGame":
//                if(!createdViews.containsKey("NewGame")) {
//                    visiblePanel = new NewGame(); // hay que cambiar el new Game a un JPanel
//                    createdViews.put("NewGame", visiblePanel);
//                } else {
//                    visiblePanel = createdViews.get("NewGame");
//                }
//                break;*/
//            case "LoginView":
//                if(!createdViews.containsKey("LoginView")) {
//                    visiblePanel = new LoginView(); 
//                    createdViews.put("LoginView", visiblePanel);
//                } else {
//                    visiblePanel = createdViews.get("LoginView");
//                }
//                break;
//            default:
//                System.out.println("Invalid view name");
//                return;
//        }
//    }
//
//}
//




package PresentationLayer;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class PresentationCtrl {
    private JFrame mainFrame;
    private JPanel visiblePanel;
    private static PresentationCtrl instance;
    private Map<String, JPanel> createdViews;

    // Constructor privado para el patrón Singleton
    private PresentationCtrl() {
        createdViews = new HashMap<>();
    }

    public static PresentationCtrl getInstance() {
        if (instance == null) {
            instance = new PresentationCtrl();
        }
        return instance;
    }

    // Inicializar las vistas y el marco principal
    public void initializeViews() {
        mainFrame = new JFrame("Scrabble");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setSize(screenSize.width, screenSize.height);

        visiblePanel = new MainMenuView();
        mainFrame.add(visiblePanel);

        createdViews.put("MainMenuView", visiblePanel);

        mainFrame.setVisible(true);
    }

    // Mostrar una vista específica
    public void showView(String viewName) {
        if (mainFrame == null) {
            throw new IllegalStateException("mainFrame no está inicializado. Llama a initializeViews primero.");
        }

        changeView(viewName);
        mainFrame.add(visiblePanel);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    // Cambiar la vista visible
    private void changeView(String viewName) {
        if (mainFrame == null) {
            throw new IllegalStateException("mainFrame no está inicializado. Llama a initializeViews primero.");
        }

        mainFrame.remove(visiblePanel);

        switch (viewName) {
            case "MainMenuView":
                visiblePanel = createdViews.get(viewName);
                break;

            case "NewGame":
                if (!createdViews.containsKey("NewGame")) {
                    visiblePanel = new NewGame(); // Crear la vista NewGame si no existe
                    createdViews.put("NewGame", visiblePanel);
                } else {
                    visiblePanel = createdViews.get("NewGame");
                }
                break;

            case "LoginView":
                if (!createdViews.containsKey("LoginView")) {
                    visiblePanel = new LoginView();
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

            default:
                System.out.println("Invalid view name: " + viewName);
                return;
        }
    }

    public static void main(String[] args) {
        PresentationCtrl pc = PresentationCtrl.getInstance();
        pc.initializeViews(); // Inicializa el marco principal y la vista inicial
    }
}