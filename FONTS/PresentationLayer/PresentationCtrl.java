package PresentationLayer;
import javax.swing.*;

import DomainLayer.DomainClasses.DomainController;
//import DomainLayer.DomainClasses.Match;
import Utils.Pair;

import java.awt.*;
//import java.awt.List;
import java.util.*;


/**
 * PresentationCtrl es el controlador principal de la capa de presentación.
 * Se encarga de gestionar las vistas y la interacción con el usuario.
 * Utiliza el patrón Singleton para asegurar que solo haya una instancia de PresentationCtrl.
 * @author Marcos Arroyo
 */
public class PresentationCtrl {

    /**
     * Atributos de la clase PresentationCtrl.
     * @param mainFrame El marco principal de la aplicación.
     * @param visiblePanel El panel actualmente visible en el marco principal.
     * @param clientWidth Ancho del cliente de la ventana principal.
     * @param clientHeight Alto del cliente de la ventana principal.
     * @param instance Instancia única de PresentationCtrl (Singleton).
     * @param createdViews Mapa que almacena las vistas creadas por su nombre.
     * @param cc Controlador de creación de vistas (CreationCtrl).
     * @param domainCtrl Controlador del dominio (DomainController).
     * @param matchViewCtrl Controlador de la vista del juego (MatchViewCtrl).
     * @param turn Turno actual del juego.
     */
    private JFrame mainFrame;
	private JPanel visiblePanel;
    private int clientWidth = 1060;
    private int clientHeight = 650;
    private static PresentationCtrl instance;
    private Map<String, JPanel> createdViews;
    private CreationCtrl cc;
    private DomainController domainCtrl;
    private MatchViewCtrl matchViewCtrl;
    private CtrlRankingView ctrlRankingView;
    private int turn;
    private String matchId;
    private int skipCount;
    private Set<String> specialChars;
    private int totPlay;

    

    /**
     * Constructor privado para implementar el patrón Singleton.
     * Evita la creación de instancias adicionales de PresentationCtrl.
     */
    private PresentationCtrl() {
        ctrlRankingView = CtrlRankingView.getInstance();
    }

    /**
     * Método estático para obtener la instancia única de PresentationCtrl.
     * Si no existe una instancia, la crea.
     * @return La instancia única de PresentationCtrl.
     */
    public static PresentationCtrl getInstance() {
        if (instance == null) {
            instance = new PresentationCtrl();
       }
        return instance;
    }
   
    /**
     * Inicializa el controlador del dominio.
     * Este método se llama al inicio para preparar el controlador del dominio.
     */
    public void initializeCD(){
        domainCtrl = DomainController.getInstance();
        domainCtrl.createDictionary("es", "es", "es");
        domainCtrl.createDictionary("en", "en", "en");
        domainCtrl.createDictionary("ca", "ca", "ca");
        domainCtrl.loadProfiles();
        domainCtrl.loadMatches();
    }

    private void setSpecialChars(String dictionary) {
        if(dictionary.equals("es")){
            specialChars.add("CH");
            specialChars.add("LL");
            specialChars.add("RR");
            specialChars.add("Ñ");
        }
        else if(dictionary.equals("ca")){
            specialChars.add("NY");
            specialChars.add("L·L");
            specialChars.add("Ç");
        }
        //en no tiene caracteres especiales
    }
   
    /**
     * Crea un nuevo juego y muestra la vista de creación de juego.
     * Obtiene los parámetros necesarios del controlador de creación (CreationCtrl)
     * y del controlador del dominio (DomainController) para crear un nuevo juego.
     */
    public void createNewMatch(){
        //int numHumPlayers = cc.getHumanPlayers();
        for (char c = 'A'; c <= 'Z'; c++) {
            specialChars.add(String.valueOf(c));
        }
        int totalPlayers = cc.getTotalPlayers();
        int boardSize = cc.getBoardSize();
        String dictionary = cc.getDictionary(); //es, en o ca
        setSpecialChars(dictionary);
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
        totPlay = players.size();
        matchViewCtrl = MatchViewCtrl.getInstance();
        
        JPanel pan = matchViewCtrl.createMatchView(boardSize, totalPlayers, players, letters, specialChars, null, null,  false);
        createdViews.put("MatchView", pan );
        skipCount = 0; //reinicio el contador de skips


    }

    public void setPrivacity(boolean privacity, String profileName){
        //este metodo se llama desde la vista de perfil para cambiar la privacidad del perfil
        domainCtrl.updateProfile(profileName, null, null, privacity);
        
    }

    private void actTurn() {
        ++turn;
        if(turn >= totPlay){
            turn = 0; //vuelvo al primer jugador
        }
        
    }

    private void passTurn() {
        matchViewCtrl.cleanTilesplaced();
        matchViewCtrl.cleanRack();
        actTurn();
        ArrayList<String> letters = domainCtrl.getRackLetters(matchId, turn);
        matchViewCtrl.updateRack(letters);
        matchViewCtrl.setBagTiles(domainCtrl.getBagTiles(matchId));
    }

    public void skipTurn() {

        skipCount++;
        if(skipCount >= totPlay*2){
            //si se han saltado todos los turnos, el juego termina
            showSuccessDialog("All players have skipped their turns. The game is over.");
            String winner = domainCtrl.endMatch(matchId);
            showEndView("EndGame", winner);
            return;
        }
        passTurn();
        domainCtrl.setTurn(matchId);
        domainCtrl.addSkipCount(matchId);
        startTurn();
    }
    


    public void submitTurn(ArrayList<Pair<Integer, Integer>> coord_ini, ArrayList<Pair<Integer, Integer>> coord_end, ArrayList<String> word, Set<Pair<Integer, Integer>> jokers) {
        
        
        boolean valid = false;
        skipCount = 0; //reinicio el contador de skips al hacer un movimiento
        if(word.size() == 0){
            //si no hay palabra, no se puede hacer un movimiento
            showErrorDialog("Invalid move. Please try again.");
            return;
        }
        else if(word.size() == 1 || word.size() == 2) valid = domainCtrl.playsMatch(matchId, word.get(0), coord_ini.get(0).first(), coord_ini.get(0).second(), coord_end.get(0).first(), coord_end.get(0).second(), jokers);
       
        
        
        if(!valid){
            //si no es valido, muestro un mensaje de error
            showErrorDialog("Invalid move. Please try again.");
            
        }
        else{
            showSuccessDialog(word.get(0) + " is valid, successful move.");
            int score = domainCtrl.getPlayerScore(matchId, turn);
            matchViewCtrl.actPlayerScore(turn, score);
            matchViewCtrl.lockTilesPlaced();
            int nBagTiles = domainCtrl.getBagTiles(matchId);
            int nRackTiles = domainCtrl.getRackLetters(matchId, turn).size();
            if(nBagTiles <= 0 && nRackTiles <= 0){
                showSuccessDialog("The game is over, no more tiles left in the bag and rack of a player.");
                String winner = domainCtrl.endMatch(matchId);
                showEndView("EndGame", winner);
                return;
            }
            passTurn();
            startTurn();
        }
    }

    public void shuffleRack() {
        domainCtrl.shuffleRack(matchId);
        matchViewCtrl.cleanTilesplaced();
        matchViewCtrl.cleanRack();
        ArrayList<String> letters = domainCtrl.getRackLetters(matchId, turn);
        matchViewCtrl.updateRack(letters);
        
    }

    public void exchangeLetters(String letters){
        skipCount = 0; //reinicio el contador de skips al hacer un intercambio
        domainCtrl.modifyRack(matchId, letters);

        passTurn();
        
        startTurn();
    }

    public void startTurn(){

        if(domainCtrl.isGameFinished(matchId)){
            //si el juego ha terminado, muestro la vista de ranking
            showView("EndGameView");
            return;
        }
        ArrayList<String> players = domainCtrl.getMatchplayers(matchId);

        showSuccessDialog("Turn of " + players.get(turn) + " has started.");

        if (domainCtrl.isHumanTurn(matchId, turn)) {
            //Aqui no se hace nada, porque estoy esperando la accion del humano, que se registrará cuando le de a cierto boton
        }
        else{
            //La IA juega su turno y devuelve la palabra que ha puesto en un array de string para identificar las fichas que ha usado
            //y un array de enteros que indica las coordenadas de inicio y fin de la palabra

            Pair<ArrayList<String>, Integer[]> playData = domainCtrl.AIplayTurn(matchId);
            if(playData.first().size() != 0){
                
                if(playData.first().get(0).equals("skip")){
                    showSuccessDialog("The AI has skipped its turn.");
                    skipCount++;
                        if(skipCount >= cc.getTotalPlayers()*2){
                            //si se han saltado todos los turnos, el juego termina
                            showSuccessDialog("All players have skipped their turns. The game is over.");
                            String winner = domainCtrl.endMatch(matchId);
                            showEndView("EndGame", winner);
                            return;
                        }
                        passTurn();
                        startTurn();
                }
                else if(playData.first().get(0).equals("change")){
                    showSuccessDialog("The AI has exchanged letters.");
                    skipCount = 0;
                    passTurn();
                    startTurn();
                }
                else{
                    showSuccessDialog("The AI has played ");
                    matchViewCtrl.actBoardView(playData.first(), playData.second());
                    int score = domainCtrl.getPlayerScore(matchId, turn);
                    matchViewCtrl.actPlayerScore(turn, score);
                    int nBagTiles = domainCtrl.getBagTiles(matchId);
                    int nRackTiles = domainCtrl.getRackLetters(matchId, turn).size();
                    if(nBagTiles <= 0 && nRackTiles <= 0){
                        showSuccessDialog("The game is over, no more tiles left in the bag and rack of a player.");
                        String winner = domainCtrl.endMatch(matchId);
                        showEndView("EndGame", winner);
                        return;
                    }
                    passTurn();
                    startTurn();
                }
            }
            


        }

    }

  
  

    /**
     * Comprueba si un perfil existe en el sistema.
     * @param username Nombre de usuario del perfil a comprobar.
     * @return true si el perfil existe, false en caso contrario.
     */
    public boolean profInSystem(String username) {
        return domainCtrl.profInSystem(username);
    }

    /**
     * Comprueba si las credenciales de un usuario son válidas.
     * @param username Nombre de usuario del perfil.
     * @param password Contraseña del perfil.
     * @return true si las credenciales son correctas, false en caso contrario.
     */
    public boolean checkPassword(String username, String password) {
        return domainCtrl.authenticateProfile(username, password);
    }

    /**
     * Crea un nuevo perfil de usuario en el sistema.
     * @param username Nombre de usuario del nuevo perfil.
     * @param password Contraseña del nuevo perfil.
     */
    public void createProfile(String username, String password){
        domainCtrl.addProfile(username, password);
    }

    /**
     * Inicializa las vistas de la aplicación.
     * Configura el marco principal y crea las vistas iniciales.
     * También establece el controlador de creación de vistas (CreationCtrl).
     */
    public void initializeViews() {
        mainFrame = new JFrame("Scrabble");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setSize(new Dimension(1060, 650));
        mainFrame.setLocation(200, 100);
        mainFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                // Al cerrar la ventana, se guardan los perfiles y partidas
                domainCtrl.saveMatches();
                System.exit(0); // Cierra la aplicación
            }
        });

        cc = CreationCtrl.getInstance();
        matchViewCtrl = MatchViewCtrl.getInstance();
        specialChars = new HashSet<>();

        initializeCD();

        //crear ProfileView generica y meterla en el mapa de vistas
       


       visiblePanel = new MainMenuView();
       mainFrame.add(visiblePanel);

        createdViews = new HashMap<>();
        createdViews.put("MainMenuView", visiblePanel);
        createdViews.put("ProfileView", cc.createProfileView("username", 0, 0, 0.0f));
        mainFrame.setVisible(true);

        
    }

    /**
     * Obtiene el marco principal de la aplicación.
     * @return El JFrame principal.
     */
    public void refresh(){
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    public void deleteMatch(){
        domainCtrl.endMatch(matchId);
        matchId = null;
    }

    public void loadGame(String matchId){
        if(specialChars.size() > 0) specialChars.clear();
        specialChars = new HashSet<>();
        for (char c = 'A'; c <= 'Z'; c++) {
            specialChars.add(String.valueOf(c));
        }

        this.matchId = matchId;
        turn = domainCtrl.getMatchTurn(matchId);
        skipCount = domainCtrl.getSkipCount(matchId);
        setSpecialChars(domainCtrl.getDictionaryName(matchId));
        //specialChars = domainCtrl.getSpecialChars(matchId);
        
        ArrayList<String> players = domainCtrl.getMatchplayers(matchId);
        ArrayList<String> letters = domainCtrl.getRackLetters(matchId, turn);
        
        if(createdViews.containsKey("MatchView")) createdViews.remove("MatchView");
        totPlay = players.size();
        Pair<Set<Pair<Integer, Integer>>, Set<Pair<String, Integer>>> boardData = domainCtrl.BoardInfo(matchId);
        JPanel pan = matchViewCtrl.createMatchView(domainCtrl.getBoardSize(matchId), totPlay, players, letters, specialChars, boardData.first(), boardData.second(), true);
        createdViews.put("MatchView", pan );
        for(int i = 0; i < totPlay; ++i){
            int score = domainCtrl.getPlayerScore(matchId, i);
            matchViewCtrl.actPlayerScore(i, score);
        }
        matchViewCtrl.setBagTiles(domainCtrl.getBagTiles(matchId));

        

        showView("MatchView");
        startTurn();
    }

    /**
     * Muestra una vista específica en el marco principal.
     * Cambia la vista actual y ajusta el tamaño del marco según la vista seleccionada.
     * @param viewName Nombre de la vista a mostrar.
     */
    public void showView(String viewName) {
       changeView(viewName);
       mainFrame.add(visiblePanel);
       if((viewName.equals("MatchView") || viewName.equals("EndGame")) && mainFrame.getWidth() <= 1060 ){
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

   public void showEndView(String viewName, String winner) {
        //este metodo es para mostrar la vista de final de partida, que es un poco especial
        //porque tiene que coger los datos del dominio y mostrarlos en la vista
        
            ArrayList<String> players = domainCtrl.getMatchplayers(matchId); //nombre de los jugadores de la 
            ArrayList<Pair<String, Integer>> scoresList = new ArrayList<>();

            for(int i = 0; i <= turn; ++i){
                
                scoresList.add(new Pair<>(players.get(i), domainCtrl.getPlayerScore(matchId, i)));
                
            }
            
            JPanel endGamePanel = cc.createEndGameView(winner, scoresList);//new EndGame(winner, scoresList);
            createdViews.put("EndGame", endGamePanel);
            
        
        showView("EndGame");
    }

    /**
     * Muestra la vista del menú principal.
     * Esta vista es la primera que se muestra al iniciar la aplicación.
     * @param mode Modo de la vista (opcional, puede ser null).
     */
   public void showLoginView(String mode){

        cc.setMode(mode);
        showView("LoginView");
   }

    /**
     * Muestra la vita del perfil del usuario.
     * Esta vista muestra las estadísticas del perfil del usuario,
     * como el número de partidas jugadas, ganadas y la puntuación media.
     * @param username Nombre de usuario del perfil.
     * @param password Contraseña del perfil.
     */
   public void showProfileView(String username, String password){

        //lo que hara esta funcion es coger del dominio los datos del perfil, setear la vista con estos datos y entonces mostrarla por pantalla 
        ArrayList<String> stats = domainCtrl.getProfileStats(username, password);
        boolean isPublic = true;
        if(stats.get(4).equals("No")) isPublic = false;
        cc.setProfileFields(username, Integer.parseInt(stats.get(1)), Integer.parseInt(stats.get(2)), Float.parseFloat(stats.get(3).replace(',', '.')), isPublic);
        showView("ProfileView");
   }

    /**
    * Cambia la vista actual del marco principal.
    * Elimina el panel visible actual y añade el nuevo panel correspondiente a la vista solicitada.
    * @param viewName
    */
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
                Set<String> matchIds = domainCtrl.getUnfinishedMatchsIds();
                if (!createdViews.containsKey("LoadGame")) {
                    visiblePanel = cc.createLoadGameView(); // Crear la vista LoadGame si no existe
                    createdViews.put("LoadGame", visiblePanel);
                } else {
                    visiblePanel = createdViews.get("LoadGame");
                }
                cc.actLoadGameView(matchIds); // Actualizar la vista con los IDs de partidas no finalizadas
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
                    visiblePanel = ctrlRankingView.createRankingView(); // Crear la vista si no existe
                    createdViews.put("RankingView", visiblePanel);
                } else {
                    visiblePanel = createdViews.get("RankingView");
                    ctrlRankingView.cleanRanking(); // Limpio el ranking antes de mostrarlo
                }
                ctrlRankingView.setRankingData(domainCtrl.getRankingInfo());
                break;
            
            case "MatchView":
                visiblePanel = createdViews.get("MatchView");
                break;
            case "ProfileView":
                //esta vista es un poco diferente, porque la tengo que actualizar, cada vez que la muestro necesito actualizarla con los valores actuales
                
                    visiblePanel = createdViews.get("ProfileView");
                
                break;
            case "EndGame":
               
                    visiblePanel = createdViews.get("EndGame");
                
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





