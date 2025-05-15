import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Flow;

import javax.swing.border.EmptyBorder;

public class view_stats_profile extends JFrame {

    public view_stats_profile() {
        // Configurar el marco
        setTitle("User Profile");
        setSize(810, 540);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10)); // Cambiar a BorderLayout con márgenes

        // Cambiar el color de fondo
        getContentPane().setBackground(Color.LIGHT_GRAY);

        // Crear un panel para el texto de profile_name (esquina superior izquierda)
        JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Alinear a la izquierda
        topLeftPanel.setBackground(Color.LIGHT_GRAY); // Fondo del panel
        JLabel profileNameLabel = new JLabel("profile_name");
        profileNameLabel.setFont(new Font("Serif", Font.BOLD, 28));
        topLeftPanel.add(profileNameLabel); // Agregar el texto al panel superior izquierdo

        // Crear un panel para el texto de other_users (esquina superior derecha)
        JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Alinear al centro
        topRightPanel.setBackground(Color.LIGHT_GRAY); // Fondo del panel
        topRightPanel.setBorder(new EmptyBorder(8, 0, 0, 0)); // Añadir margen superior
        JLabel otherUsersLabel = new JLabel("other_users");
        otherUsersLabel.setFont(new Font("Serif", Font.BOLD, 18));
        topRightPanel.add(otherUsersLabel); // Agregar el texto al panel superior derecho

        // Crear un panel superior que combine ambos (izquierda y derecha)
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.LIGHT_GRAY);
        topPanel.add(topLeftPanel, BorderLayout.WEST); // Panel izquierdo
        topPanel.add(topRightPanel, BorderLayout.CENTER); // Panel derecho

        // Crear panel para los datos del perfil con GridBagLayout
        JPanel profilePanel = new JPanel(new GridBagLayout());
        profilePanel.setBackground(Color.LIGHT_GRAY);
        profilePanel.setBorder(new EmptyBorder(10, 20, 10, 10)); // Añadir margen izquierdo al panel

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0); // Sin márgenes adicionales
        gbc.gridx = 0; // Columna fija
        gbc.anchor = GridBagConstraints.WEST; // Alinear a la izquierda
        gbc.fill = GridBagConstraints.HORIZONTAL; // Permitir que los componentes se expandan horizontalmente
        gbc.weightx = 1.0; // Expandir horizontalmente
        gbc.weighty = 1.0; // Expandir verticalmente para ajustar el interlineado

        // Añadir los textos al panel en el formato deseado
        JLabel totalGamesPlayedLabel = new JLabel("Total Games Played: number1");
        gbc.gridy = 0; // Fila 0
        JPanel panelGamesPlayed = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelGamesPlayed.setBackground(Color.GRAY); // Fondo del panel
        panelGamesPlayed.add(totalGamesPlayedLabel); // Agregar el texto al panel
        profilePanel.add(panelGamesPlayed, gbc);

        JLabel totalGamesWonLabel = new JLabel("Total Games Won: number2");
        gbc.gridy = 1; // Fila 1
        JPanel panelGamesWon = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelGamesWon.setBackground(Color.GRAY); // Fondo del panel
        panelGamesWon.add(totalGamesWonLabel); // Agregar el texto al panel
        profilePanel.add(panelGamesWon, gbc);

        JLabel winRateLabel = new JLabel("Win Rate: number3");
        gbc.gridy = 2; // Fila 2
        JPanel panelWinRate = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelWinRate.setBackground(Color.GRAY); // Fondo del panel
        panelWinRate.add(winRateLabel); // Agregar el texto al panel
        profilePanel.add(panelWinRate, gbc);

        // Agregar componentes al marco
        add(topPanel, BorderLayout.NORTH); // Agregar el panel superior al norte
        add(profilePanel, BorderLayout.WEST); // Agregar el panel de estadísticas al centro

        // Hacer el marco visible
        setVisible(true);
    }

    public static void main(String[] args) {
        JFrame frame = new view_stats_profile();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}