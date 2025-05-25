package PresentationLayer;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class ProfileView extends JPanel {

    private JLabel profileNameLabel;
    private JLabel totalGamesPlayedLabel;
    private JLabel totalGamesWonLabel;
    private JLabel winRateLabel;
    private JButton deleteProfileBtn;
    private JButton changePasswordBtn;
    private JButton returnBtn;

    public ProfileView(String profileName, int totalGamesPlayed, int totalGamesWon, double winRate) {
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
                rightSide.addPoint((int) (w * 0.65), 0);
                rightSide.addPoint(w, 0);
                rightSide.addPoint(w, h);
                rightSide.addPoint((int) (w * 0.55), h);

                g2.setColor(Color.decode("#C0B8B8"));
                g2.fillPolygon(rightSide);

                // Línea diagonal
                g2.setStroke(new BasicStroke(4f));
                g2.setColor(Color.decode("#807777"));
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
        profilePanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // Título principal
        JLabel title = new JLabel("Profile");
        title.setFont(new Font("Dubai Medium", Font.BOLD, 48));
        title.setForeground(Color.decode("#181818"));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Panel de información del perfil con fondo y borde
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(botonColor);
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        infoPanel.setMaximumSize(new Dimension(600, 250));
        infoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Información del perfil
        profileNameLabel = new JLabel("Profile Name: " + profileName);
        totalGamesPlayedLabel = new JLabel("Total Games Played: " + totalGamesPlayed);
        totalGamesWonLabel = new JLabel("Total Games Won: " + totalGamesWon);
        winRateLabel = new JLabel("Win Rate: " + winRate + "%");

        JLabel[] infoLabels = {profileNameLabel, totalGamesPlayedLabel, totalGamesWonLabel, winRateLabel};
        for (JLabel label : infoLabels) {
            label.setFont(new Font("Dubai Medium", Font.PLAIN, 22));
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
            label.setForeground(Color.decode("#181818"));
            label.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
            infoPanel.add(label);
        }

        // Botones en la parte inferior izquierda
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);

        JPanel bottomLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomLeftPanel.setOpaque(false);
        bottomLeftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        deleteProfileBtn = new JButton("Delete Profile");
        changePasswordBtn = new JButton("Change Password");

        deleteProfileBtn.setBackground(Color.RED);
        deleteProfileBtn.setForeground(Color.WHITE);
        deleteProfileBtn.setPreferredSize(new Dimension(250, 40));
        deleteProfileBtn.setMaximumSize(new Dimension(250, 40));
        deleteProfileBtn.setMinimumSize(new Dimension(250, 40));
        deleteProfileBtn.setFont(new Font("Dubai Medium", Font.PLAIN, 22));
        deleteProfileBtn.setFocusPainted(false);
        deleteProfileBtn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 1),
            BorderFactory.createEmptyBorder(8, 16, 8, 16)
        ));

        changePasswordBtn.setBackground(botonColor);
        changePasswordBtn.setPreferredSize(new Dimension(250, 40));
        changePasswordBtn.setMaximumSize(new Dimension(250, 40));
        changePasswordBtn.setMinimumSize(new Dimension(250, 40));
        changePasswordBtn.setFont(new Font("Dubai Medium", Font.PLAIN, 22));
        changePasswordBtn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 1),
            BorderFactory.createEmptyBorder(8, 16, 8, 16)
        ));

        bottomLeftPanel.add(deleteProfileBtn);
        bottomLeftPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        bottomLeftPanel.add(changePasswordBtn);

        // Botón "Return" en la esquina inferior derecha
        JPanel returnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        returnPanel.setOpaque(false);
        returnPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        returnBtn = new JButton("Return");
        returnBtn.setFont(new Font("Dubai Medium", Font.PLAIN, 22));
        returnBtn.setFocusPainted(false);
        returnBtn.setOpaque(true);
        returnBtn.setBackground(botonColor);
        returnBtn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 1),
            BorderFactory.createEmptyBorder(8, 16, 8, 16)
        ));
        returnPanel.add(returnBtn);

        // Añadir los paneles de botones al panel inferior
        bottomPanel.add(bottomLeftPanel, BorderLayout.WEST);
        bottomPanel.add(returnPanel, BorderLayout.EAST);

        // Añadir componentes al panel principal
        profilePanel.add(title);
        profilePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        profilePanel.add(infoPanel);

        backgroundPanel.add(profilePanel, BorderLayout.CENTER);
        backgroundPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(backgroundPanel);
    }

    // Getters para acceder a los componentes desde fuera si es necesario
    public JLabel getProfileNameLabel() { return profileNameLabel; }
    public JLabel getTotalGamesPlayedLabel() { return totalGamesPlayedLabel; }
    public JLabel getTotalGamesWonLabel() { return totalGamesWonLabel; }
    public JLabel getWinRateLabel() { return winRateLabel; }
    public JButton getDeleteProfileBtn() { return deleteProfileBtn; }
    public JButton getChangePasswordBtn() { return changePasswordBtn; }
    public JButton getReturnBtn() { return returnBtn; }

    public void setProfileFields(String profileName, int totalGamesPlayed, int totalGamesWon, double winRate) {
        profileNameLabel.setText("Profile Name: " + profileName);
        totalGamesPlayedLabel.setText("Total Games Played: " + totalGamesPlayed);
        totalGamesWonLabel.setText("Total Games Won: " + totalGamesWon);
        winRateLabel.setText("Win Rate: " + winRate + "%");
    } 

    // Si quieres probar el panel de forma independiente:
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Profile View");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 540);
            frame.setResizable(false);
            frame.add(new ProfileView("DynamicProfileName", 10000, 20000, 75.5));
            frame.setVisible(true);
        });
    }
}
