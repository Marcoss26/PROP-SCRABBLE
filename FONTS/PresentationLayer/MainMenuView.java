package PresentationLayer;
import javax.swing.*;
import java.awt.*;

/**
 * MainMenuView representa la vista del menú principal del juego Scrabble.
 * Permite a los usuarios iniciar un nuevo juego, continuar uno existente,
 * gestionar perfiles, gestionar diccionarios, ver el ranking y salir del juego.
 * @author Iván Alcubierre
 */
public class MainMenuView extends JPanel {

    /**
     * Atributos de la clase MainMenuView.
     * @param newGameBtn Botón para iniciar un nuevo juego.
     * @param continueGameBtn Botón para continuar un juego existente.
     * @param manageProfilesBtn Botón para gestionar perfiles de usuario.
     * @param manageDictionariesBtn Botón para gestionar diccionarios.
     * @param rankingBtn Botón para ver el ranking de jugadores.
     * @param exitBtn Botón para salir del juego.
     * @param menuPanel Panel que contiene el menú principal.
     * @param optionsPanel Panel que contiene las opciones del menú.
     */
    private JButton newGameBtn;
    private JButton continueGameBtn;
    private JButton manageProfilesBtn;
    private JButton manageDictionariesBtn;
    private JButton rankingBtn;
    private JButton exitBtn;
    private JPanel menuPanel;
    private JPanel optionsPanel;

    /**
     * Constructor de la clase MainMenuView.
     * Inicializa el panel con un diseño y componentes para el menú principal del juego.
     */
    public MainMenuView() {
        setLayout(new GridBagLayout());
        setOpaque(false); // Para que se pinte el fondo personalizado

        menuPanel = new JPanel();
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
        optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.setOpaque(false);
        optionsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Panel envoltorio para desplazar los botones a la derecha
        JPanel optionsWrapper = new JPanel();
        optionsWrapper.setLayout(new BoxLayout(optionsWrapper, BoxLayout.X_AXIS));
        optionsWrapper.setOpaque(false);
        optionsWrapper.setBorder(BorderFactory.createEmptyBorder(0, 310, 0, 0));
        optionsWrapper.add(optionsPanel);

        // Crear botones para cada opción
        newGameBtn = new JButton("New game");
        continueGameBtn = new JButton("Continue game");
        manageProfilesBtn = new JButton("Manage profiles");
        manageDictionariesBtn = new JButton("Manage dictionaries");
        rankingBtn = new JButton("Ranking");
        exitBtn = new JButton("Exit");

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

        // Listeners de los botones
        PresentationCtrl pc = PresentationCtrl.getInstance();
        manageProfilesBtn.addActionListener(e -> pc.showLoginView("authentication") /*pc.showView("LoginView")*/);
        newGameBtn.addActionListener(e -> pc.showView("NewGame"));
        continueGameBtn.addActionListener(e -> pc.showView("LoadGame"));
        manageDictionariesBtn.addActionListener(e -> pc.showView("ManageDictionaryView"));
        rankingBtn.addActionListener(e -> pc.showView("RankingView"));
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
        rightSide.addPoint((int)(w*0.65), 0);
        rightSide.addPoint(w, 0);
        rightSide.addPoint(w, h);
        rightSide.addPoint((int)(w*0.55), h);

        g2.setColor(Color.decode("#C0B8B8"));
        g2.fillPolygon(rightSide);

        // Línea diagonal
        g2.setStroke(new BasicStroke(4f));
        g2.setColor(Color.decode("#807777"));
        g2.drawLine((int)(w*0.65), 0, (int)(w*0.55), h);

        g2.dispose();
    }

    // Getters para los botones si necesitas acceder desde fuera
    /**
     * Obtiene el botón para iniciar un nuevo juego.
     * @return El botón para iniciar un nuevo juego.
     */
    public JButton getNewGameBtn() { return newGameBtn; }

    /**
     * Obtiene el botón para continuar un juego existente.
     * @return El botón para continuar un juego.
     */
    public JButton getContinueGameBtn() { return continueGameBtn; }

    /**
     * Obtiene el botón para gestionar perfiles de usuario.
     * @return El botón para gestionar perfiles.
     */
    public JButton getManageProfilesBtn() { return manageProfilesBtn; }

    /**
     * Obtiene el botón para gestionar diccionarios.
     * @return El botón para gestionar diccionarios.
     */
    public JButton getManageDictionariesBtn() { return manageDictionariesBtn; }

    /**
     * Obtiene el botón para ver el ranking de jugadores.
     * @return El botón para ver el ranking.
     */
    public JButton getRankingBtn() { return rankingBtn; }

    /**
     * Obtiene el botón para salir del juego.
     * @return El botón para salir.
     */
    public JButton getExitBtn() { return exitBtn; }

    /**
     * Obtiene el panel del menú principal.
     * @return El panel del menú principal.
     */
    public JPanel getMenuPanel() { return menuPanel; }

    /**
     * Obtiene el panel de opciones del menú.
     * @return El panel de opciones del menú.
     */
    public JPanel getOptionsPanel() { return optionsPanel; }

    /**
     * Método principal para ejecutar la aplicación y mostrar el menú principal.
     * @param args Argumentos de línea de comandos (no se utilizan).
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Main Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setResizable(false);
        frame.add(new MainMenuView());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}