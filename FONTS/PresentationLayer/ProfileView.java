import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class ProfileView extends JFrame {

    public ProfileView(String profileName, int totalGamesPlayed, int totalGamesWon, double winRate) {
        // Configurar el marco
        setTitle("Profile View");
        setSize(900, 540);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel de fondo personalizado
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Fondo izquierdo
                g.setColor(Color.decode("#F5F6FA"));
                g.fillRect(0, 0, getWidth(), getHeight());

                // Dibuja la diagonal y el fondo derecho
                Graphics2D g2 = (Graphics2D) g.create();
                int w = getWidth();
                int h = getHeight();

                // Polígono para la parte derecha (gris)
                Polygon rightSide = new Polygon();
                rightSide.addPoint((int) (w * 0.65), 0); // Punto en la parte superior, un poco a la derecha del centro
                rightSide.addPoint(w, 0);               // Esquina superior derecha
                rightSide.addPoint(w, h);               // Esquina inferior derecha
                rightSide.addPoint((int) (w * 0.55), h); // Punto en la parte inferior, un poco a la derecha del centro

                g2.setColor(Color.decode("#C0B8B8"));
                g2.fillPolygon(rightSide);

                // Línea diagonal
                g2.setStroke(new BasicStroke(4f));
                g2.setColor(Color.decode("#807777")); // Gris oscuro para la línea
                g2.drawLine((int) (w * 0.65), 0, (int) (w * 0.55), h);

                g2.dispose();
            }
        };
        backgroundPanel.setLayout(new BorderLayout());

        // Color para los botones
        Color botonColor = Color.decode("#F7BBA9");

        // Panel principal con margen ajustado
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        profilePanel.setBackground(Color.white);
        profilePanel.setOpaque(false);
        profilePanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50)); // Márgenes superior e inferior ajustados

        // Título principal
        JLabel title = new JLabel("Profile");
        title.setFont(new Font("Dubai Medium", Font.BOLD, 48)); // Tamaño de fuente ajustado a 48
        title.setForeground(Color.decode("#181818"));
        title.setAlignmentX(Component.LEFT_ALIGNMENT); // Alinear a la izquierda
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0)); // Espaciado inferior del título

        // Panel de información del perfil con fondo y borde
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(botonColor); // Fondo del rectángulo
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 2), // Borde negro
            BorderFactory.createEmptyBorder(20, 20, 20, 20) // Espaciado interno uniforme
        ));
        infoPanel.setMaximumSize(new Dimension(600, 250)); // Hacer el rectángulo más alargado
        infoPanel.setAlignmentX(Component.LEFT_ALIGNMENT); // Alinear a la izquierda

        // Información del perfil
        JLabel profileNameLabel = new JLabel("Profile Name: " + profileName);
        JLabel totalGamesPlayedLabel = new JLabel("Total Games Played: " + totalGamesPlayed);
        JLabel totalGamesWonLabel = new JLabel("Total Games Won: " + totalGamesWon);
        JLabel winRateLabel = new JLabel("Win Rate: " + winRate + "%");

        JLabel[] infoLabels = {profileNameLabel, totalGamesPlayedLabel, totalGamesWonLabel, winRateLabel};
        for (JLabel label : infoLabels) {
            label.setFont(new Font("Dubai Medium", Font.PLAIN, 22));
            label.setAlignmentX(Component.LEFT_ALIGNMENT); // Alinear a la izquierda
            label.setForeground(Color.decode("#181818"));
            label.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0)); // Espaciado entre líneas
            infoPanel.add(label);
        }

        // Botones en la parte inferior izquierda
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);

        JPanel bottomLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomLeftPanel.setOpaque(false);
        bottomLeftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton deleteProfileBtn = new JButton("Delete Profile");
        JButton changePasswordBtn = new JButton("Change Password");

        // Configurar colores y tamaños de los botones
        deleteProfileBtn.setBackground(Color.RED); // Fondo rojo
        deleteProfileBtn.setForeground(Color.WHITE); // Texto blanco
        deleteProfileBtn.setPreferredSize(new Dimension(250, 40)); // Botón más ancho
        deleteProfileBtn.setMaximumSize(new Dimension(250, 40));
        deleteProfileBtn.setMinimumSize(new Dimension(250, 40));
        deleteProfileBtn.setFont(new Font("Dubai Medium", Font.PLAIN, 22));
        deleteProfileBtn.setFocusPainted(false); // Desactivar el enfoque visual
        deleteProfileBtn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 1), // Borde negro suave
            BorderFactory.createEmptyBorder(8, 16, 8, 16) // Espaciado interno
        ));

        changePasswordBtn.setBackground(botonColor); // Fondo del botón
        changePasswordBtn.setPreferredSize(new Dimension(250, 40)); // Botón más ancho
        changePasswordBtn.setMaximumSize(new Dimension(250, 40));
        changePasswordBtn.setMinimumSize(new Dimension(250, 40));
        changePasswordBtn.setFont(new Font("Dubai Medium", Font.PLAIN, 22));
        changePasswordBtn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 1), // Borde negro suave
            BorderFactory.createEmptyBorder(8, 16, 8, 16) // Espaciado interno
        ));

        bottomLeftPanel.add(deleteProfileBtn);
        bottomLeftPanel.add(Box.createRigidArea(new Dimension(20, 0))); // Espaciado entre botones
        bottomLeftPanel.add(changePasswordBtn);

        // Botón "Return" en la esquina inferior derecha
        JPanel returnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        returnPanel.setOpaque(false);
        returnPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton returnBtn = new JButton("Return");
        returnBtn.setFont(new Font("Dubai Medium", Font.PLAIN, 22));
        returnBtn.setFocusPainted(false);
        returnBtn.setOpaque(true);
        returnBtn.setBackground(botonColor); // Cambiar al color botonColor
        returnBtn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 1), // Borde negro suave
            BorderFactory.createEmptyBorder(8, 16, 8, 16) // Espaciado interno
        ));
        returnPanel.add(returnBtn);

        // Añadir los paneles de botones al panel inferior
        bottomPanel.add(bottomLeftPanel, BorderLayout.WEST); // Botones en la parte inferior izquierda
        bottomPanel.add(returnPanel, BorderLayout.EAST); // Botón "Return" en la parte inferior derecha

        // Añadir componentes al panel principal
        profilePanel.add(title);
        profilePanel.add(Box.createRigidArea(new Dimension(0, 20))); // Espaciado entre el título y el rectángulo
        profilePanel.add(infoPanel);

        backgroundPanel.add(profilePanel, BorderLayout.CENTER);
        backgroundPanel.add(bottomPanel, BorderLayout.SOUTH); // Panel inferior con botones

        add(backgroundPanel); // Añadir el panel de fondo al marco
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ProfileView frame = new ProfileView("DynamicProfileName", 10000, 20000, 75.5);
            frame.setVisible(true);
        });
    }
}