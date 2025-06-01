package PresentationLayer;
//import javax.swing.*;
//import java.awt.*;
import java.util.*;

//import javax.swing.JOptionPane;

import Utils.Pair;

/**
 * CreationCtrl es el controlador de la capa de presentación que gestiona la creación de partidas,
 * el inicio de sesión de jugadores y la visualización de perfiles.
 * Permite crear vistas para nuevas partidas, iniciar sesión y mostrar perfiles de jugadores.
 * 
 * @author Marcos Arroyo
 */
public class CreationCtrl {

    /**
     * Atributos de la clase CreationCtrl.
     * @param instance Instancia única del controlador (Singleton).
     * @param newGameView Vista para crear una nueva partida.
     * @param loginView Vista para iniciar sesión de jugadores.
     * @param profileView Vista para mostrar el perfil de un jugador.
     * @param pc Controlador de presentación que gestiona las vistas.
     * @param humanPlayers Número de jugadores humanos en la partida.
     * @param loginIndex Índice del jugador actual en el proceso de inicio de sesión.
     * @param playersId Conjunto de identificadores y contraseñas de los jugadores.
     * @param loginMode Modo de inicio de sesión (añadir jugador o autenticación).
     */
    private static CreationCtrl instance;
    private NewGame newGameView;
    private LoginView loginView;
    private ProfileView profileView;
    private EndGame endGameView;
    private PresentationCtrl pc;
    private Integer humanPlayers;
    private Integer loginIndex;
    private Set<Pair<String,String>> playersId;
    private enum LoginMode { ADD_PLAYER, AUTHENTICATION }
    private LoginMode loginMode;

    /**
     * Constructor privado para implementar el patrón Singleton.
     * Inicializa los atributos necesarios.
     */
    private CreationCtrl(){
        pc = PresentationCtrl.getInstance();
    }

    /**
     * Método para obtener la instancia única del controlador.
     * Si no existe, crea una nueva instancia.
     * 
     * @return La instancia única de CreationCtrl.
     */
    public static CreationCtrl getInstance() {
        if (instance == null) {
            instance = new CreationCtrl();
       }

        return instance;
    }

    /**
     * Método para crear una nueva vista de partida.
     * Inicializa el controlador de presentación y la vista de nueva partida.
     * 
     * @return La vista de nueva partida creada.
     */
    public NewGame createNewGameView(){
        //pc = PresentationCtrl.getInstance();

        newGameView = new NewGame();
        return newGameView;
    }
    
    /**
     * Método para crear una nueva vista de inicio de sesión.
     * Inicializa la vista de inicio de sesión.
     * 
     * @return La vista de inicio de sesión creada.
     */
    public LoginView createLoginView(){
        //pc = PresentationCtrl.getInstance();
        loginView = new LoginView();
        return loginView;
    }

    /**
     * Método para crear una nueva vista de perfil de jugador.
     * Inicializa la vista de perfil con los datos del jugador.
     * 
     * @param username Nombre de usuario del jugador.
     * @param totalGamesPlayed Total de partidas jugadas por el jugador.
     * @param totalGamesWon Total de partidas ganadas por el jugador.
     * @param winRate Tasa de victorias del jugador.
     * @return La vista de perfil creada.
     */
    public ProfileView createProfileView(String username, int totalGamesPlayed, int totalGamesWon, float winRate){
        //pc = PresentationCtrl.getInstance();
        profileView = new ProfileView(username, totalGamesPlayed, totalGamesWon, winRate);
        return profileView;
    }

    public EndGame createEndGameView(String winner, List<Pair<String, Integer>> scores){
        //pc = PresentationCtrl.getInstance();
        endGameView = new EndGame(winner, scores);
        return endGameView;
    }

    /**
     * Método para establecer los campos del perfil del jugador.
     * Actualiza la vista de perfil con los datos proporcionados.
     * 
     * @param username Nombre de usuario del jugador.
     * @param totalGamesPlayed Total de partidas jugadas por el jugador.
     * @param totalGamesWon Total de partidas ganadas por el jugador.
     * @param winRate Tasa de victorias del jugador.
     */
    public void setProfileFields(String username, int totalGamesPlayed, int totalGamesWon, float winRate){
        profileView.setProfileFields(username, totalGamesPlayed, totalGamesWon, winRate);
        
    }

    /**
     * Método para limpiar los campos de la vista de inicio de sesión.
     * Resetea los campos de entrada en la vista de inicio de sesión.
     */
    public void cleanLoginView(){
        loginView.cleanFields();
    }

    /**
     * Método para obtener la vista de la nueva partida.
     * @return La vista de la nueva partida.
     */
    public NewGame getNewGameView(){
        return newGameView;
    }

    /**
     * Método para mostrar la vista que se le pase por parámetro.
     * @param viewName Nombre de la vista que se desea mostrar.
     */
    public void showView(String viewName) {
        pc.showView(viewName);
    }

    /**
     * Método para obtener los jugadores humanos.
     * @return Número de jugadores humanos en la partida.
     */
    public Integer getHumanPlayers() {
        return humanPlayers;
    }

    /**
     * Método para obtener el tamaño del tablero de la nueva partida.
     * @return Tamaño del tablero de la nueva partida.
     */
    public Integer getBoardSize() {
        return newGameView.getBoardSize();
    }

    /**
     * Método para obtener el diccionario seleccionado en la nueva partida.
     * @return Diccionario seleccionado en la nueva partida.
     */
    public String getDictionary() {
        return newGameView.getDictionary();
    }   

    /**
     * Método para obtener el conjunto de identificadores y contraseñas de los jugadores.
     * @return Conjunto de pares (ID, contraseña) de los jugadores.
     */
    public Set<Pair<String,String>> getPlayersId() {
        return playersId;
    }

    /**
     * Método para obtener el número total de jugadores en la nueva partida.
     * @return Número total de jugadores en la nueva partida.
     */
    public Integer getTotalPlayers() {
        return newGameView.getTotalPlayers();
    }

    /**
     * Método para iniciar el proceso de inicio de sesión.
     * Establece el número de jugadores humanos, reinicia el índice de inicio de sesión,
     * inicializa el conjunto de identificadores y contraseñas, y establece el modo de inicio de sesión.
     * Luego muestra la vista de inicio de sesión.
     * 
     * @param numPlayers Número de jugadores humanos que se van a registrar.
     */
    public void loginSeq(Integer numPlayers){
        humanPlayers = numPlayers;
        loginIndex = 0;
        playersId = new HashSet<>();
        pc.showLoginView("ADD_PLAYER");

    }
    
    /**
     * Método para establecer el modo de inicio de sesión.
     * Si el modo es "authentication", se establece el modo de autenticación,
     * de lo contrario, se establece el modo de añadir jugador.
     * 
     * @param mode Modo de inicio de sesión ("authentication" o "add_player").
     */
    public void setMode(String mode) {
        if (mode.equals("authentication")) {
            loginMode = LoginMode.AUTHENTICATION;
        } else {
            loginMode = LoginMode.ADD_PLAYER;
        }
    }

    /**
     * Método para mostrar la siguiente vista de inicio de sesión.
     * Si el índice de inicio de sesión es menor que el número de jugadores humanos,
     * limpia la vista de inicio de sesión y refresca el controlador de presentación.
     * Si se han registrado todos los jugadores, crea una nueva partida y muestra la vista de partida.
     */
    public void showNextLoginView(){
        if(loginIndex < humanPlayers){
            cleanLoginView();
            pc.refresh();
            
        }
        else{
            
            pc.refresh();
            pc.createNewMatch();
            showView("MatchView"); 
            pc.startTurn();
        }

    }

/**
     * Método para iniciar sesión de un jugador.
     * Comprueba si el jugador ya existe en el sistema y si la contraseña es correcta.
     * Si las credenciales son válidas, muestra la vista del perfil del jugador.
     * Si no, muestra un mensaje de error.
     * 
     * @param playerId ID del jugador que intenta iniciar sesión.
     * @param password Contraseña del jugador que intenta iniciar sesión.
     */
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

    /**
     * Método para crear un perfil de jugador.
     * Comprueba si el jugador ya existe en el sistema.
     * Si no existe, crea el perfil y muestra un mensaje de éxito.
     * Si ya existe, muestra un mensaje de error.
     * 
     * @param playerId ID del jugador que se va a crear.
     * @param password Contraseña del jugador que se va a crear.
     */
    public void createProfile(String playerId, String password) {

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
