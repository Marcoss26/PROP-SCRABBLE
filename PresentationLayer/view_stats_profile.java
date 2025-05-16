import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class view_stats_profile extends JFrame {

    public view_stats_profile() {
        // Configurar el marco
        setTitle("User Profile");
        setSize(810, 540);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10)); // Cambiar a BorderLayout con márgenes
        Color fondoColor = new Color(245, 246, 250);
        Color botonColor = new Color(247, 187, 169);

        // Cambiar el color de fondo
        getContentPane().setBackground(fondoColor);

        String profileName = "profile_name"; // Nombre del perfil

        // Crear un panel para el texto de profile_name (esquina superior izquierda)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Alinear al centro
        topPanel.setBackground(fondoColor); // Fondo del panel
        JLabel profileNameLabel = new JLabel(profileName);
        profileNameLabel.setFont(new Font("Dubai Medium", Font.BOLD, 28));
        topPanel.add(profileNameLabel); // Agregar el texto al panel superior

        // Crear un panel para los textos con fondo de color botonColor
        JPanel textBackgroundPanel = new JPanel(new GridBagLayout());
        textBackgroundPanel.setBackground(botonColor); // Fondo del panel
        textBackgroundPanel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(Color.BLACK, 2), // Borde negro de 2 píxeles
        new EmptyBorder(20, 20, 20, 20)));

        // Crear panel para los datos del perfil con GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20); // Espaciado entre los textos
        gbc.anchor = GridBagConstraints.CENTER; // Centrar los textos
        gbc.fill = GridBagConstraints.NONE; // No expandir los componentes

        String number1 = "10000";
        String number2 = "20000";
        String number3 = "30000";

        // Crear y configurar los textos
        Font textFont = new Font("Dubai Medium", Font.BOLD, 24); // Fuente más grande

        JLabel totalGamesPlayedLabel = new JLabel("Total Games Played: " + number1);
        totalGamesPlayedLabel.setFont(textFont);
        gbc.gridx = 0; // Primera columna
        gbc.gridy = 0; // Primera fila
        textBackgroundPanel.add(totalGamesPlayedLabel, gbc); // Agregar al panel

        JLabel totalGamesWonLabel = new JLabel("Total Games Won: " + number2);
        totalGamesWonLabel.setFont(textFont);
        gbc.gridx = 1; // Segunda columna
        textBackgroundPanel.add(totalGamesWonLabel, gbc); // Agregar al panel

        JLabel winRateLabel = new JLabel("Win Rate: " + number3);
        winRateLabel.setFont(textFont);
        gbc.gridx = 0; // Primera columna
        gbc.gridy = 1; // Segunda fila
        gbc.gridwidth = 2; // Ocupa ambas columnas
        textBackgroundPanel.add(winRateLabel, gbc); // Agregar al panel

        // Crear un panel principal para centrar el rectángulo
        JPanel profilePanel = new JPanel(new GridBagLayout());
        profilePanel.setBackground(fondoColor); // Fondo del panel principal
        profilePanel.add(textBackgroundPanel); // Agregar el panel con el fondo de color

        // Crear un panel inferior con un botón de retorno
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton returnButton = new JButton("RETURN");
        returnButton.setBackground(botonColor); // Fondo del botón
        returnButton.setFocusPainted(false);
        bottomPanel.setBackground(fondoColor); // Fondo del panel
        bottomPanel.setBorder(new EmptyBorder(8, 8, 8, 8)); // Añadir margen al panel inferior
        bottomPanel.add(returnButton); // Agregar el botón al panel inferior

        // Agregar componentes al marco
        add(topPanel, BorderLayout.NORTH); // Agregar el panel superior al norte
        add(profilePanel, BorderLayout.CENTER); // Agregar el panel de estadísticas al centro
        add(bottomPanel, BorderLayout.SOUTH); // Agregar el panel inferior al sur

        // Hacer el marco visible
        setVisible(true);
    }

    public static void main(String[] args) {
        JFrame frame = new view_stats_profile();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}