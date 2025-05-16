import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class LoadGame extends JFrame {

    private DefaultListModel<String> savedGamesListModel; // Modelo de la lista

    public LoadGame() {
        // Configurar el marco
        setTitle("Load Game");
        setSize(860, 540);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(0, 0)); // Sin márgenes entre los componentes
        Color fondoColor = new Color(247, 187, 169);
        Color botonColor = new Color(255, 105, 97);

        // Cambiar el color de fondo del marco
        getContentPane().setBackground(fondoColor);

        // Crear un panel para el título alineado a la izquierda
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Alinear a la izquierda
        titlePanel.setBackground(fondoColor); // Fondo del panel
        JLabel titleLabel = new JLabel("Load Game");
        titleLabel.setFont(new Font("Dubai Medium", Font.BOLD, 24));
        titlePanel.add(titleLabel); // Agregar el título al panel
        titlePanel.setBorder(new EmptyBorder(0, 0, 20, 0));
        add(titlePanel, BorderLayout.NORTH); // Agregar el panel al norte

        // Crear el modelo de la lista
        savedGamesListModel = new DefaultListModel<>();
        savedGamesListModel.addElement("No saved games"); // Mensaje inicial

        // Crear la lista de juegos guardados
        JList<String> savedGamesList = new JList<>(savedGamesListModel);
        savedGamesList.setBackground(fondoColor); // Fondo de la lista

        // Crear un JScrollPane sin borde
        JScrollPane scrollPane = new JScrollPane(savedGamesList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Eliminar el borde del JScrollPane
        scrollPane.getViewport().setBackground(fondoColor); // Fondo del área de desplazamiento
        add(scrollPane, BorderLayout.CENTER); // Agregar la lista al centro

        // Crear un panel inferior con un botón de retorno
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton returnButton = new JButton("RETURN");
        returnButton.setBackground(botonColor); // Fondo del botón
        returnButton.setFocusPainted(false);
        bottomPanel.setBackground(fondoColor); // Fondo del panel
        bottomPanel.setBorder(new EmptyBorder(8, 8, 8, 8)); // Añadir margen al panel inferior
        bottomPanel.add(returnButton); // Agregar el botón al panel inferior

        add(bottomPanel, BorderLayout.SOUTH); // Agregar el panel inferior al sur
    }

    // Método para agregar un juego guardado a la lista
    public void addSavedGame(String gameName) {
        if (savedGamesListModel.contains("No saved games")) {
            savedGamesListModel.removeElement("No saved games"); // Eliminar el mensaje inicial
        }
        savedGamesListModel.addElement(gameName); // Agregar el nuevo juego
    }

    public static void main(String[] args) {
        // Crear y mostrar la ventana
        SwingUtilities.invokeLater(() -> {
            LoadGame frame = new LoadGame();
            frame.setVisible(true);
        });
    }
}