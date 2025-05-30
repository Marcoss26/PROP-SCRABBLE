package PresentationLayer;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

/**
 * LoginView representa la vista de inicio de sesión del juego.
 * Permite a los usuarios iniciar sesión o registrarse con un nombre de usuario y contraseña.
 * Incluye botones para iniciar sesión, registrarse y regresar al menú principal.
 * @author Iván Alcubierre
 */
public class ManageDictionaryView extends JPanel {

    /**
     * Atributos de la clase ManageDictionaryView.
     * @param dictionaries Lista de diccionarios disponibles.
     * @param dictCombo ComboBox para seleccionar un diccionario.
     * @param deleteBtn Botón para eliminar el diccionario seleccionado.
     * @param returnButton Botón para regresar al menú principal.
     */
    private final String[] dictionaries = {"Español", "Catalán", "Inglés"};
    private JComboBox<String> dictCombo;
    private JButton deleteBtn;
    private JButton returnButton;

    /**
     * Constructor de la clase ManageDictionaryView.
     * Inicializa el panel con un diseño y componentes para gestionar diccionarios.
     */
    public ManageDictionaryView() {
        setLayout(new BorderLayout());
        setBackground(Color.decode("#F5F6FA"));

        // Panel principal centrado y alineado a la izquierda
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 80, 0, 0)); // Margen izquierdo para alinear el contenido

        // Título
        JLabel title = new JLabel("Manage Dictionaries");
        title.setFont(new Font("Dubai Medium", Font.BOLD, 48));
        title.setForeground(Color.decode("#181818"));
        title.setAlignmentX(Component.LEFT_ALIGNMENT); // Alinear a la izquierda

        // Panel para subtítulo y select juntos
        JPanel subtitleSelectPanel = new JPanel();
        subtitleSelectPanel.setLayout(new BoxLayout(subtitleSelectPanel, BoxLayout.Y_AXIS));
        subtitleSelectPanel.setOpaque(false);
        subtitleSelectPanel.setAlignmentX(Component.LEFT_ALIGNMENT); // Alinear a la izquierda

        // Subtítulo
        JLabel subtitle = new JLabel("Select a dictionary to manage:");
        subtitle.setFont(new Font("Dubai Medium", Font.PLAIN, 18));
        subtitle.setForeground(Color.decode("#807777"));
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT); // Alinear a la izquierda
        subtitleSelectPanel.add(subtitle);

        // Espacio pequeño entre subtítulo y select
        subtitleSelectPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Panel para el desplegable y botón Delete
        JPanel selectPanel = new JPanel();
        selectPanel.setLayout(new BoxLayout(selectPanel, BoxLayout.X_AXIS));
        selectPanel.setOpaque(false);
        selectPanel.setAlignmentX(Component.LEFT_ALIGNMENT); // Alinear a la izquierda

        // Desplegable de diccionarios
        dictCombo = new JComboBox<>(dictionaries);
        dictCombo.setFont(new Font("Dubai Medium", Font.PLAIN, 16));
        dictCombo.setMaximumSize(new Dimension(160, 36));
        dictCombo.setPreferredSize(new Dimension(160, 36));
        dictCombo.setMinimumSize(new Dimension(160, 36));
        dictCombo.setBackground(Color.WHITE);
        dictCombo.setForeground(Color.decode("#181818"));

        Color salmon = Color.decode("#F7BBA9");

        // Botón Delete
        deleteBtn = new JButton("Delete");
        deleteBtn.setFont(new Font("Dubai Medium", Font.PLAIN, 16));
        deleteBtn.setBackground(salmon);
        deleteBtn.setForeground(Color.decode("#181818"));
        deleteBtn.setFocusPainted(false);
        deleteBtn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 1),
                BorderFactory.createEmptyBorder(6, 18, 6, 18)
        ));
        deleteBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        deleteBtn.setPreferredSize(new Dimension(100, 36));
        deleteBtn.addActionListener(e -> {
            String dict = (String) dictCombo.getSelectedItem();
            JOptionPane.showMessageDialog(this, "Eliminar diccionario: " + dict);
        });

        selectPanel.add(dictCombo);
        selectPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        selectPanel.add(deleteBtn);

        // Añade el panel combinado al subtitleSelectPanel
        subtitleSelectPanel.add(selectPanel);

        // Añadir espacio flexible arriba y abajo para centrar el contenido
        mainPanel.add(Box.createVerticalGlue()); // Espacio flexible arriba
        mainPanel.add(title); // Título
        mainPanel.add(Box.createRigidArea(new Dimension(0, 50))); // Espacio bajo el título
        mainPanel.add(subtitleSelectPanel); // Subtítulo y select
        mainPanel.add(Box.createVerticalGlue()); // Espacio flexible abajo

        add(mainPanel, BorderLayout.CENTER);

        // Botón Return en la parte inferior derecha (NO TOCAR)
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setOpaque(false); // Hacer el fondo transparente
        bottomPanel.setBorder(new EmptyBorder(8, 8, 8, 8)); // Añadir margen al panel inferior

        returnButton = new JButton("RETURN");
        returnButton.setFont(new Font("Dubai Medium", Font.PLAIN, 22)); // Tamaño de fuente ajustado a 22
        returnButton.setBackground(Color.decode("#F7BBA9")); // Fondo del botón
        returnButton.setFocusPainted(false);
        returnButton.setPreferredSize(new Dimension(120, 40)); // Tamaño consistente con LoadGame
        bottomPanel.add(returnButton); // Agregar el botón al panel inferior

        // Acción para el botón RETURN
        returnButton.addActionListener(e -> {
            PresentationCtrl.getInstance().showView("MainMenuView");
        });

        add(bottomPanel, BorderLayout.SOUTH); // Agregar el panel inferior al sur
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
        rightSide.addPoint((int)(w * 0.65), 0);      // Restaurar proporciones originales
        rightSide.addPoint(w, 0);                  
        rightSide.addPoint(w, h);                  
        rightSide.addPoint((int)(w * 0.55), h);      

        g2.setColor(Color.decode("#C0B8B8"));
        g2.fillPolygon(rightSide);

        // Línea diagonal
        g2.setStroke(new BasicStroke(4f));
        g2.setColor(Color.decode("#807777")); 
        g2.drawLine((int)(w * 0.65), 0, (int)(w * 0.55), h);

        g2.dispose();
    }

    /**
     * Obtiene el JComboBox que permite seleccionar un diccionario.
     * @return El JComboBox de diccionarios.
     */
    public JComboBox<String> getDictCombo() { return dictCombo; }

    /**
     * Obtiene el botón para eliminar el diccionario seleccionado.
     * @return El botón de eliminar diccionario.
     */
    public JButton getDeleteBtn() { return deleteBtn; }

    /**
     * Obtiene el botón de retorno al menú principal.
     * @return El botón de retorno.
     */
    public JButton getReturnButton() { return returnButton; }
}