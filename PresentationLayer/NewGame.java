import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class NewGame extends JFrame {

    public NewGame() {
        // Configurar el marco
        setTitle("New Game");
        setSize(860, 540);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        Color fondoColor = new Color(245, 246, 250);
        Color botonColor = new Color(247, 187, 169);

        // Cambiar el color de fondo
        getContentPane().setBackground(fondoColor);

        // Crear un panel para el título
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(fondoColor);
        JLabel titleLabel = new JLabel("New Game");
        titleLabel.setFont(new Font("Dubai Medium", Font.BOLD, 28));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Crear el panel principal con GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(fondoColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.NONE; // No expandir los paneles
        gbc.anchor = GridBagConstraints.CENTER; // Centrar los paneles

        // Sección 1: Configuración de jugadores
        JPanel playerPanel = new JPanel(new GridLayout(6, 1, 5, 5)); // Reducir el espacio entre filas
        playerPanel.setBackground(botonColor);
        playerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 2),
            new EmptyBorder(15, 15, 15, 15) // Márgenes internos moderados
        ));
        playerPanel.setPreferredSize(new Dimension(350, 250)); // Tamaño preferido intermedio

        JLabel playersLabel = new JLabel("How many players do you want?");
        playersLabel.setFont(new Font("Dubai Medium", Font.PLAIN, 18));
        playerPanel.add(playersLabel);

        JComboBox<String> playersDropdown = new JComboBox<>(new String[]{"1", "2", "3", "4", "5"});
        playerPanel.add(playersDropdown);

        JLabel aiLabel = new JLabel("How many players are AI?");
        aiLabel.setFont(new Font("Dubai Medium", Font.PLAIN, 18));
        playerPanel.add(aiLabel);

        JComboBox<String> aiDropdown = new JComboBox<>(new String[]{"0", "1", "2", "3", "4", "5"});
        playerPanel.add(aiDropdown);

        JLabel boardSizeLabel = new JLabel("What board size?");
        boardSizeLabel.setFont(new Font("Dubai Medium", Font.PLAIN, 18));
        playerPanel.add(boardSizeLabel);

        JComboBox<String> boardSizeDropdown = new JComboBox<>(new String[]{"7", "15", "25"});
        playerPanel.add(boardSizeDropdown);

        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(playerPanel, gbc);

        // Añadir un ActionListener para limitar el número de jugadores IA
        playersDropdown.addActionListener(e -> {
            int maxPlayers = 5;
            int selectedHumans = Integer.parseInt((String) playersDropdown.getSelectedItem());
            aiDropdown.removeAllItems();
            for (int i = 0; i <= maxPlayers - selectedHumans; i++) {
                aiDropdown.addItem(String.valueOf(i));
            }
        });

        // Sección 2: Configuración del diccionario y bolsa
        JPanel settingsPanel = new JPanel(new GridLayout(4, 1, 5, 5)); // Reducir el espacio entre filas
        settingsPanel.setBackground(botonColor);
        settingsPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 2),
            new EmptyBorder(15, 15, 15, 15) // Márgenes internos moderados
        ));
        settingsPanel.setPreferredSize(new Dimension(350, 200)); // Tamaño preferido intermedio

        JLabel dictionaryLabel = new JLabel("What dictionary?");
        dictionaryLabel.setFont(new Font("Dubai Medium", Font.PLAIN, 18));
        settingsPanel.add(dictionaryLabel);

        JComboBox<String> dictionaryDropdown = new JComboBox<>(new String[]{"English", "Spanish", "Catalan"});
        settingsPanel.add(dictionaryDropdown);

        JLabel bagLabel = new JLabel("What bag?");
        bagLabel.setFont(new Font("Dubai Medium", Font.PLAIN, 18));
        settingsPanel.add(bagLabel);

        JComboBox<String> bagDropdown = new JComboBox<>(new String[]{"Bag 1", "Bag 2", "Bag 3"});
        settingsPanel.add(bagDropdown);

        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(settingsPanel, gbc);

        add(mainPanel, BorderLayout.CENTER);

        // Crear un panel inferior con los botones "START" y "RETURN"
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(fondoColor);
        bottomPanel.setBorder(new EmptyBorder(8, 8, 8, 8));

        JButton startButton = new JButton("START");
        startButton.setBackground(botonColor);
        startButton.setFocusPainted(false);
        bottomPanel.add(startButton);

        JButton returnButton = new JButton("RETURN");
        returnButton.setBackground(botonColor);
        returnButton.setFocusPainted(false);
        bottomPanel.add(returnButton);

        add(bottomPanel, BorderLayout.SOUTH);

        // Hacer el marco visible
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(NewGame::new);
    }
}