package PresentationLayer;
//import javax.swing.*;
//import java.awt.*;
import java.util.*;

//import javax.swing.JOptionPane;

import Utils.Pair;


public class CreationCtrl {

    private static CreationCtrl instance;
    private NewGame newGameView;
    private LoginView loginView;
    private ProfileView profileView;
    private PresentationCtrl pc;
    private Integer humanPlayers;
    private Integer loginIndex;
    private Set<Pair<String,String>> playersId;
    private enum LoginMode { ADD_PLAYER, AUTHENTICATION }
    private LoginMode loginMode;

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

    public ProfileView createProfileView(String username, int totalGamesPlayed, int totalGamesWon, float winRate){
        profileView = new ProfileView(username, totalGamesPlayed, totalGamesWon, winRate);
        return profileView;
    }

    public void setProfileFields(String username, int totalGamesPlayed, int totalGamesWon, float winRate){
        profileView.setProfileFields(username, totalGamesPlayed, totalGamesWon, winRate);
        
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
        loginMode = LoginMode.ADD_PLAYER;
        showView("LoginView");

    }

    public void setMode(String mode) {
        if (mode.equals("authentication")) {
            loginMode = LoginMode.AUTHENTICATION;
        } else {
            loginMode = LoginMode.ADD_PLAYER;
        }
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
            showView("MatchView"); //de momento pongo newgame, pero lo tengo que cambiar por matchView
            pc.startTurn();
        }

    }

    public void loginPlayer(String playerId, String password){

        //la parte de comprobar si un jugador ya existe y su contraseña es correcta aun no la tengo hecha
        if(loginMode == LoginMode.AUTHENTICATION) {
            if(!pc.profInSystem(playerId)) {
                loginView.showError("Player does not exist in the system.");
                
            }
            else if(!pc.checkPassword(playerId, password)) {
                loginView.showError("Incorrect password.");
                
            }
            else{
                pc.showProfileView(playerId, password);
                
            }
            
        }
        else{
            //lo tengo en modo add player, por lo que lo que tiene que hacer login es comprobar que las credenciales coinciden con un perfil en el sistemam y en ese caso añadir el perfil a la lista de jugadores
            if(!pc.profInSystem(playerId)) {
                loginView.showError("Player does not exist in the system, you can create the profile with Sign Up.");
                
            }
            else if(!pc.checkPassword(playerId, password)) {
                loginView.showError("Incorrect password.");
                return;
            }
            else{
                //si el jugador ya existe en el sistema, lo añado a la lista de jugadores
                playersId.add(new Pair<>(playerId, password));
                loginIndex++;
                showNextLoginView();
            }
        }
        /*pc.createProfile(playerId, password);
        playersId.add(new Pair<>(playerId, password));
        loginIndex++;
        showNextLoginView();*/
    }

    public void createProfile(String playerId, String password) {
        pc = PresentationCtrl.getInstance();

        if(pc.profInSystem(playerId)) {
            //JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            loginView.showError("Player already exists in the system.");
        }
        else{
            pc.createProfile(playerId, password);
            loginView.showSuccess("Profile created successfully.");
        }
        
        // Aquí puedes añadir la lógica para crear un perfil de jugador
        // Por ejemplo, guardar el perfil en una base de datos o en memoria
        System.out.println("Creating profile for player: " + playerId);
        // Puedes añadir más lógica aquí si es necesario
    }

    public void skipTurn(){
        pc.skipTurn();
    }


    
}
