package PresentationLayer;
import javax.swing.*;
//import DomainLayer.DomainClasses.DomainController;
import java.awt.*;
//import java.util.*;

public class PresentationCtrl {
    private JFrame mainFrame;
	private JPanel visiblePanel;
    private static PresentationCtrl instance;
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
        
        mainFrame.setVisible(true);

        
    }

}
