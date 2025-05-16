import javax.swing.*;
import java.awt.*;

public class ManageDictionaryView extends JPanel {

    // Diccionarios por defecto
    private String[] dictionaries = {"Español", "Catalán", "Inglés"};

    public ManageDictionaryView() {
        setLayout(new BorderLayout());
        setBackground(Color.decode("#F5F6FA"));

        // Panel principal centrado
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, -80, 40, 100)); // Menos margen para ventana pequeña

        // Título
        JLabel title = new JLabel("Manage Dictionaries");
        title.setFont(new Font("Dubai Medium", Font.BOLD, 48));
        title.setForeground(Color.decode("#181818"));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(title);

        // Espacio extra bajo el título (más grande para bajarlo)
        mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));

        // Panel para subtítulo y select juntos
        JPanel subtitleSelectPanel = new JPanel();
        subtitleSelectPanel.setLayout(new BoxLayout(subtitleSelectPanel, BoxLayout.Y_AXIS));
        subtitleSelectPanel.setOpaque(false);
        subtitleSelectPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Subtítulo
        JLabel subtitle = new JLabel("Select a dictionary to manage:");
        subtitle.setFont(new Font("Dubai Medium", Font.PLAIN, 18));
        subtitle.setForeground(Color.decode("#807777"));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitleSelectPanel.add(subtitle);

        // Espacio pequeño entre subtítulo y select
        subtitleSelectPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Panel para el desplegable y botones
        JPanel selectPanel = new JPanel();
        selectPanel.setLayout(new BoxLayout(selectPanel, BoxLayout.X_AXIS));
        selectPanel.setOpaque(false);
        selectPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        selectPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 100));

        // Desplegable de diccionarios
        JComboBox<String> dictCombo = new JComboBox<>(dictionaries);
        dictCombo.setFont(new Font("Dubai Medium", Font.PLAIN, 16));
        dictCombo.setMaximumSize(new Dimension(160, 36));
        dictCombo.setPreferredSize(new Dimension(160, 36));
        dictCombo.setMinimumSize(new Dimension(160, 36));
        dictCombo.setBackground(Color.WHITE);
        dictCombo.setForeground(Color.decode("#181818"));

        Color salmon = Color.decode("#F7BBA9");

        // Botón estándar
        JButton addWordBtn = new JButton("Add words");
        addWordBtn.setFont(new Font("Dubai Medium", Font.PLAIN, 16));
        addWordBtn.setBackground(salmon);
        addWordBtn.setForeground(Color.decode("#181818"));
        addWordBtn.setFocusPainted(false);
        addWordBtn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 1),
                BorderFactory.createEmptyBorder(6, 18, 6, 18)
        ));
        addWordBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addWordBtn.setPreferredSize(new Dimension(120, 36));
        addWordBtn.addActionListener(e -> {
            String dict = (String) dictCombo.getSelectedItem();
            JOptionPane.showMessageDialog(this, "Añadir palabras a: " + dict);
        });

        JButton deleteBtn = new JButton("Delete");
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
        selectPanel.add(addWordBtn);
        selectPanel.add(Box.createRigidArea(new Dimension(12, 0)));
        selectPanel.add(deleteBtn);

        // Añade el panel combinado al mainPanel
        subtitleSelectPanel.add(selectPanel);
        mainPanel.add(subtitleSelectPanel);

        // Espacio extra
        mainPanel.add(Box.createVerticalGlue());

        // Botón Return en la parte inferior izquierda
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        JButton returnBtn = new JButton("Return");
        returnBtn.setFont(new Font("Dubai Medium", Font.PLAIN, 16));
        returnBtn.setBackground(salmon);
        returnBtn.setForeground(Color.decode("#181818"));
        returnBtn.setFocusPainted(false);
        returnBtn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 1),
                BorderFactory.createEmptyBorder(6, 18, 6, 18)
        ));
        returnBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        returnBtn.setPreferredSize(new Dimension(100, 36));
        bottomPanel.add(returnBtn, BorderLayout.WEST);

        add(mainPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Ejemplo de acción para Return
        returnBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Volver al menú principal");
        });
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
        rightSide.addPoint((int)(w*0.75), 0);      // Punto en la parte superior, un poco a la derecha del centro
        rightSide.addPoint(w, 0);                  // Esquina superior derecha
        rightSide.addPoint(w, h);                  // Esquina inferior derecha
        rightSide.addPoint((int)(w*0.65), h);      // Punto en la parte inferior, un poco a la derecha del centro

        g2.setColor(Color.decode("#C0B8B8"));
        g2.fillPolygon(rightSide);

        // Línea diagonal
        g2.setStroke(new BasicStroke(4f));
        g2.setColor(Color.decode("#807777")); // Gris oscuro para la línea
        g2.drawLine((int)(w*0.75), 0, (int)(w*0.65), h);

        g2.dispose();
    }

    // Para probar la vista de forma independiente
    public static void main(String[] args) {
        JFrame frame = new JFrame("Manage Dictionaries");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 540);
        frame.setResizable(false); // Ventana no redimensionable
        frame.add(new ManageDictionaryView());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}