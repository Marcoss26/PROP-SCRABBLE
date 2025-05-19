package PresentationLayer;
import javax.swing.*;
import java.awt.*;

public class MainMenuView extends JPanel {

    public MainMenuView() {
        setLayout(new GridBagLayout());
        setOpaque(false); // Para que se pinte el fondo personalizado

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(Color.white);
        menuPanel.setOpaque(false);
        menuPanel.setBorder(BorderFactory.createEmptyBorder(80, -190, 80, 100));
        menuPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Título principal
        JLabel title = new JLabel("Scrabble");
        title.setFont(new Font("Dubai Medium", Font.BOLD, 72));
        title.setForeground(Color.decode("#181818"));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));

        // Panel de opciones (vertical)
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.setOpaque(false);
        optionsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Panel envoltorio para desplazar los botones a la derecha
        JPanel optionsWrapper = new JPanel();
        optionsWrapper.setLayout(new BoxLayout(optionsWrapper, BoxLayout.X_AXIS));
        optionsWrapper.setOpaque(false);
        // Ajusta este valor para alinear visualmente los botones con el centro del título
        optionsWrapper.setBorder(BorderFactory.createEmptyBorder(0, 310, 0, 0));
        optionsWrapper.add(optionsPanel);

        // Crear botones para cada opción
        JButton newGameBtn = new JButton("New game");
        JButton continueGameBtn = new JButton("Continue game");
        JButton manageProfilesBtn = new JButton("Manage profiles");
        JButton manageDictionariesBtn = new JButton("Manage dictionaries");
        JButton rankingBtn = new JButton("Ranking");
        JButton exitBtn = new JButton("Exit");

        Color salmon = Color.decode("#F7BBA9");

        JButton[] buttons = {newGameBtn, continueGameBtn, manageProfilesBtn, manageDictionariesBtn, rankingBtn, exitBtn};
        for (JButton btn : buttons) {
            btn.setFont(new Font("Dubai Medium", Font.PLAIN, 22));
            btn.setAlignmentX(Component.LEFT_ALIGNMENT);
            btn.setFocusPainted(false);
            btn.setBackground(salmon);
            btn.setOpaque(true);
            btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 1),
                BorderFactory.createEmptyBorder(8, 16, 8, 16)
            ));
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btn.setMaximumSize(new Dimension(300, 40));
            btn.setMinimumSize(new Dimension(200, 40));
            btn.setPreferredSize(new Dimension(240, 40));
            btn.setForeground(Color.decode("#181818"));
            optionsPanel.add(btn);
            optionsPanel.add(Box.createRigidArea(new Dimension(0, 18)));
        }

        // Añadir componentes al panel principal
        menuPanel.add(title);
        menuPanel.add(optionsWrapper);

        // Mover el menú a la izquierda
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(0, 150, 0, 0); // Más a la izquierda
        add(menuPanel, gbc);

        // Aquí puedes añadir los listeners para cambiar de vista
        // newGameBtn.addActionListener(e -> ...);
        // continueGameBtn.addActionListener(e -> ...);
        // etc.
/*
    continueGameBtn.addActionListener(e -> {
    JOptionPane.showMessageDialog(
        this,
        "No hay juegos cargados.",
        "Aviso",
        JOptionPane.INFORMATION_MESSAGE
    );
});

*/
        exitBtn.addActionListener(e -> System.exit(0));
}

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
        rightSide.addPoint((int)(w*0.65), 0);      // Punto en la parte superior, un poco a la derecha del centro
        rightSide.addPoint(w, 0);                  // Esquina superior derecha
        rightSide.addPoint(w, h);                  // Esquina inferior derecha
        rightSide.addPoint((int)(w*0.55), h);      // Punto en la parte inferior, un poco a la derecha del centro

        g2.setColor(Color.decode("#C0B8B8"));
        g2.fillPolygon(rightSide);

        // Línea diagonal
        g2.setStroke(new BasicStroke(4f));
        g2.setColor(Color.decode("#807777")); // Gris oscuro para la línea
        g2.drawLine((int)(w*0.65), 0, (int)(w*0.55), h);

        g2.dispose();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Main Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setResizable(false); // Ventana no redimensionable
        frame.add(new MainMenuView());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}