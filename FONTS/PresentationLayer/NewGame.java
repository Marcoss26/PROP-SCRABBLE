package PresentationLayer;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

/**
 * LoginView representa la vista de inicio de sesión del juego.
 * Permite a los usuarios iniciar sesión o registrarse con un nombre de usuario y contraseña.
 * Incluye botones para iniciar sesión, registrarse y regresar al menú principal.
 * @author Alvaro Perez
 */
public class NewGame extends JPanel {
    
    /**
     * Atributos de la clase NewGame.
     * @param mainPanel Panel principal que contiene la configuración del juego.
     * @param playerPanel Panel para la configuración de jugadores.
     * @param settingsPanel Panel para la configuración adicional del juego.
     * @param startButton Botón para iniciar el juego.
     * @param returnButton Botón para regresar al menú principal.
     * @param numHumPlayers Número de jugadores humanos seleccionados.
     * @param numAIPlayers Número de jugadores IA seleccionados.
     * @param boardSize Tamaño del tablero seleccionado.
     * @param dictionary Diccionario seleccionado para el juego.
     * @param pc Controlador de presentación.
     * @param cc Controlador de creación de perfiles.
     */
    private JPanel mainPanel;
    private JPanel playerPanel;
    private JPanel settingsPanel;
    private JButton startButton;
    private JButton returnButton;
    private Integer numHumPlayers;
    private Integer numAIPlayers;
    private Integer boardSize;
    private String dictionary;
    private PresentationCtrl pc;
    private CreationCtrl cc;
    
    /**
     * Constructor de la clase NewGame.
     * Inicializa el panel con un diseño y componentes para configurar un nuevo juego.
     */
    public NewGame() {
        
        cc = CreationCtrl.getInstance();
        pc = PresentationCtrl.getInstance();
        //inicializar variables
        numHumPlayers = 1; // Valor por defecto
        numAIPlayers = 0; // Valor por defecto
        boardSize = 7; // Valor por defecto
        dictionary = "English"; // Valor por defecto (inglés)
        // Configurar el panel principal
        setLayout(new BorderLayout(10, 10));
        Color fondoColor = new Color(245, 246, 250);
        Color botonColor = new Color(247, 187, 169);

        // Cambiar el color de fondo
        setBackground(fondoColor);

        // Crear un panel para el título
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(fondoColor);
        JLabel titleLabel = new JLabel("New Game");
        titleLabel.setFont(new Font("Dubai Medium", Font.BOLD, 48)); // Tamaño de fuente ajustado a 48
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Crear el panel principal con GridBagLayout
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(fondoColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.NONE; // No expandir los paneles
        gbc.anchor = GridBagConstraints.CENTER; // Centrar los paneles

        // Sección 1: Configuración de jugadores
        playerPanel = new JPanel(new GridLayout(6, 1, 5, 5)); // Reducir el espacio entre filas
        playerPanel.setBackground(botonColor);
        playerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 2),
            new EmptyBorder(15, 15, 15, 15) // Márgenes internos moderados
        ));
        playerPanel.setPreferredSize(new Dimension(400, 250)); // Tamaño preferido intermedio

        JLabel playersLabel = new JLabel("How many Human players do you want?");
        playersLabel.setFont(new Font("Dubai Medium", Font.PLAIN, 22)); // Tamaño de fuente ajustado a 22
        playerPanel.add(playersLabel);

        JComboBox<String> playersDropdown = new JComboBox<>(new String[]{"1", "2", "3", "4"});
        playerPanel.add(playersDropdown);

        JLabel aiLabel = new JLabel("How many AI players do you want?");
        aiLabel.setFont(new Font("Dubai Medium", Font.PLAIN, 22)); // Tamaño de fuente ajustado a 22
        playerPanel.add(aiLabel);

        JComboBox<String> aiDropdown = new JComboBox<>(new String[]{"0", "1", "2", "3"});
        playerPanel.add(aiDropdown);

        JLabel boardSizeLabel = new JLabel("What board size?");
        boardSizeLabel.setFont(new Font("Dubai Medium", Font.PLAIN, 22)); // Tamaño de fuente ajustado a 22
        playerPanel.add(boardSizeLabel);

        JComboBox<String> boardSizeDropdown = new JComboBox<>(new String[]{"7", "15", "25"});
        playerPanel.add(boardSizeDropdown);

        boardSizeDropdown.addActionListener(e -> {
            boardSize = Integer.parseInt((String) boardSizeDropdown.getSelectedItem());
            
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(playerPanel, gbc);

        // Añadir un ActionListener para limitar el número de jugadores IA
        playersDropdown.addActionListener(e -> {
            
            numHumPlayers = Integer.parseInt((String) playersDropdown.getSelectedItem());
            /*aiDropdown.removeAllItems();
            for (int i = 0; i <= maxPlayers - numHumPlayers; i++) {
                aiDropdown.addItem(String.valueOf(i));
            }
                */
        });

        aiDropdown.addActionListener(e -> {
            System.out.println("You selected AI PLAYERS");
            numAIPlayers = Integer.parseInt((String) aiDropdown.getSelectedItem());
        });

        // Sección 2: Configuración del diccionario
        settingsPanel = new JPanel(new GridLayout(2, 1, 5, 5)); // Reducir el espacio entre filas
        settingsPanel.setBackground(botonColor);
        settingsPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 2),
            new EmptyBorder(15, 15, 15, 15) // Márgenes internos moderados
        ));
        settingsPanel.setPreferredSize(new Dimension(350, 150)); // Tamaño preferido intermedio

        JLabel dictionaryLabel = new JLabel("What dictionary?");
        dictionaryLabel.setFont(new Font("Dubai Medium", Font.PLAIN, 22)); // Tamaño de fuente ajustado a 22
        settingsPanel.add(dictionaryLabel);

        JComboBox<String> dictionaryDropdown = new JComboBox<>(new String[]{"English", "Spanish", "Catalan"});
        settingsPanel.add(dictionaryDropdown);

        dictionaryDropdown.addActionListener(e -> {
            dictionary = (String) dictionaryDropdown.getSelectedItem();
        });

        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(settingsPanel, gbc);

        add(mainPanel, BorderLayout.CENTER);

        // Crear un panel inferior con los botones "START" y "RETURN"
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(fondoColor);
        bottomPanel.setBorder(new EmptyBorder(8, 8, 8, 8));

        startButton = new JButton("START");
        startButton.setFont(new Font("Dubai Medium", Font.PLAIN, 22)); // Tamaño de fuente ajustado a 22
        startButton.setBackground(botonColor);
        startButton.setFocusPainted(false);
        bottomPanel.add(startButton);

        returnButton = new JButton("RETURN");
        returnButton.setFont(new Font("Dubai Medium", Font.PLAIN, 22)); // Tamaño de fuente ajustado a 22
        returnButton.setBackground(botonColor);
        returnButton.setFocusPainted(false);
        bottomPanel.add(returnButton);

        returnButton.addActionListener(e -> {
            cc.showView("MainMenuView");
        });

        startButton.addActionListener(e -> {
            // Aquí puedes añadir la lógica para iniciar el juego
            if (numAIPlayers + numHumPlayers > 4) {
                JOptionPane.showMessageDialog(this, "Maximum 4 players allowed", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            cc.loginSeq(numHumPlayers);
        });

        add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * Obtiene el tamaño del tablero.
     * @return El tamaño del tablero seleccionado.
     */
    public Integer getBoardSize() {
        return boardSize;
    }

    /**
     * Obtiene el diccionario seleccionado.
     * @return El identificador del diccionario seleccionado.
     */
    public String getDictionary() {
        switch(dictionary) {
            case "English":
                return "en";
            case "Spanish":
                return "es";
            case "Catalan":
                return "ca";
            default:
                return null; // O manejar el caso por defecto
        }
    }

    /**
     * Obtiene el número de jugadores.
     * @return El número de jugadores seleccionados.
     */
    public Integer getTotalPlayers() {
        return numHumPlayers + numAIPlayers;
    }

    static public void main(String[] args) {
        JFrame frame = new JFrame("New Game");
        NewGame newGamePanel = new NewGame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(newGamePanel);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}