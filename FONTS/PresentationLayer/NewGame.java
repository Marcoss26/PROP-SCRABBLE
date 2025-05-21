package PresentationLayer;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class NewGame extends JPanel {
    
    private JPanel mainPanel;
    private JPanel playerPanel;
    private JPanel settingsPanel;
    private JButton startButton;
    private JButton returnButton;
    private Integer numHumPlayers;
    

    public NewGame() {
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

        JLabel playersLabel = new JLabel("How many players do you want?");
        playersLabel.setFont(new Font("Dubai Medium", Font.PLAIN, 22)); // Tamaño de fuente ajustado a 22
        playerPanel.add(playersLabel);

        JComboBox<String> playersDropdown = new JComboBox<>(new String[]{"1", "2", "3", "4"});
        playerPanel.add(playersDropdown);

        JLabel aiLabel = new JLabel("How many players are AI?");
        aiLabel.setFont(new Font("Dubai Medium", Font.PLAIN, 22)); // Tamaño de fuente ajustado a 22
        playerPanel.add(aiLabel);

        JComboBox<String> aiDropdown = new JComboBox<>(new String[]{"0", "1", "2", "3", "4"});
        playerPanel.add(aiDropdown);

        JLabel boardSizeLabel = new JLabel("What board size?");
        boardSizeLabel.setFont(new Font("Dubai Medium", Font.PLAIN, 22)); // Tamaño de fuente ajustado a 22
        playerPanel.add(boardSizeLabel);

        JComboBox<String> boardSizeDropdown = new JComboBox<>(new String[]{"7", "15", "25"});
        playerPanel.add(boardSizeDropdown);

        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(playerPanel, gbc);

        // Añadir un ActionListener para limitar el número de jugadores IA
        playersDropdown.addActionListener(e -> {
            int maxPlayers = 4;
            numHumPlayers = Integer.parseInt((String) playersDropdown.getSelectedItem());
            aiDropdown.removeAllItems();
            for (int i = 0; i <= maxPlayers - numHumPlayers; i++) {
                aiDropdown.addItem(String.valueOf(i));
            }
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

        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(settingsPanel, gbc);

        add(mainPanel, BorderLayout.CENTER);

        // Crear un panel inferior con los botones "START" y "RETURN"
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(fondoColor);
        bottomPanel.setBorder(new EmptyBorder(8, 8, 8, 8));

        JButton startButton = new JButton("START");
        startButton.setFont(new Font("Dubai Medium", Font.PLAIN, 22)); // Tamaño de fuente ajustado a 22
        startButton.setBackground(botonColor);
        startButton.setFocusPainted(false);
        bottomPanel.add(startButton);

        JButton returnButton = new JButton("RETURN");
        returnButton.setFont(new Font("Dubai Medium", Font.PLAIN, 22)); // Tamaño de fuente ajustado a 22
        returnButton.setBackground(botonColor);
        returnButton.setFocusPainted(false);
        bottomPanel.add(returnButton);

        returnButton.addActionListener(e -> {
            PresentationCtrl.getInstance().showView("MainMenuView");
        });

        startButton.addActionListener(e -> {
            // Aquí puedes añadir la lógica para iniciar el juego
            PresentationCtrl.getInstance().showView("");
        });

        add(bottomPanel, BorderLayout.SOUTH);
    }
}