package PresentationLayer;
//import javax.swing.*;
//import java.awt.*;
import java.util.*;


public class CreationCtrl {

    private static CreationCtrl instance;
    private NewGame newGameView;
    private LoginView loginView;
    private PresentationCtrl pc;
    private Integer humanPlayers;
    private Integer loginIndex;
    private Set<Pair<String,String>> playersId;

    private CreationCtrl(){

    }

    public static CreationCtrl getInstance() {
        if (instance == null) {
            instance = new CreationCtrl();
       }
        return instance;
    }

    public NewGame createNewGameView(){
        pc = PresentationCtrl.getInstance();

        newGameView = new NewGame();
        return newGameView;
    }
    
    public LoginView createLoginView(){
        loginView = new LoginView();
        return loginView;
    }

    public void cleanLoginView(){
        loginView.cleanFields();
    }

   
    public NewGame getNewGameView(){
        return newGameView;
    }

    public void showView(String viewName) {
        pc.showView(viewName);
    }

    public Integer getHumanPlayers() {
        return humanPlayers;
    }

    public Integer getBoardSize() {
        return newGameView.getBoardSize();
    }

    public String getDictionary() {
        return newGameView.getDictionary();
    }   

    public Set<Pair<String,String>> getPlayersId() {
        return playersId;
    }

    public Integer getTotalPlayers() {
        return newGameView.getTotalPlayers();
    }


    
    public void loginSeq(Integer numPlayers){
        humanPlayers = numPlayers;
        loginIndex = 0;
        playersId = new HashSet<>();
        showView("LoginView");

    }

    public void showNextLoginView(){
        if(loginIndex < humanPlayers){
            cleanLoginView();
            
            pc = PresentationCtrl.getInstance();
            pc.refresh();
            
        }
        else{
            
            pc.refresh();
            pc.createNewMatch();
            showView("NewGame"); //de momento pongo newgame, pero lo tengo que cambiar por matchView
        }

    }

    public void loginPlayer(String playerId, String password){

        //la parte de comprobar si un jugador ya existe y su contraseña es correcta aun no la tengo hecha

        playersId.add(new Pair<>(playerId, password));
        loginIndex++;
        showNextLoginView();
    }


    
}
