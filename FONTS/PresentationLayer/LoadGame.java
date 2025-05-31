package PresentationLayer;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * LoadGame representa la vista para cargar un juego guardado.
 * Muestra una lista de juegos guardados y permite al usuario seleccionar uno para cargar.
 * También incluye un botón para regresar al menú principal.
 * @author Alvaro Perez
 */
public class LoadGame extends JPanel {

    /**
     * Atributos de la clase LoadGame.
     * @param savedGamesListModel Modelo de la lista que contiene los juegos guardados.
     * @param savedGamesList Lista que muestra los juegos guardados.
     * @param scrollPane Panel de desplazamiento para la lista de juegos guardados.
     * @param listPanel Panel que contiene la lista y su scrollPane.
     * @param centerPanel Panel central para alinear el contenido.
     * @param returnButton Botón para regresar al menú principal.
     */
    private DefaultListModel<String> savedGamesListModel;
    private JList<String> savedGamesList;
    private JScrollPane scrollPane;
    private JPanel listPanel;
    private JPanel centerPanel;
    private JButton returnButton;

    /**
     * Constructor de la clase LoadGame.
     * Inicializa el panel con un diseño y componentes para mostrar la lista de juegos guardados
     * y un botón para regresar al menú principal.
     */
    public LoadGame() {
        // Configurar el panel principal
        setLayout(new BorderLayout(0, 0));
        Color fondoColor = new Color(245, 246, 250);
        Color botonColor = new Color(247, 187, 169);

        setBackground(fondoColor);

        // Panel para el título
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(fondoColor);
        JLabel titleLabel = new JLabel("Load Game");
        titleLabel.setFont(new Font("Dubai Medium", Font.BOLD, 48));
        titlePanel.add(titleLabel);
        titlePanel.setBorder(new EmptyBorder(20, 0, 20, 0));
        add(titlePanel, BorderLayout.NORTH);

        // Modelo de la lista
        savedGamesListModel = new DefaultListModel<>();
        savedGamesListModel.addElement("No saved games");

        // Lista de juegos guardados
        savedGamesList = new JList<>(savedGamesListModel);
        savedGamesList.setBackground(botonColor);
        savedGamesList.setSelectionBackground(new Color(255, 200, 200));
        savedGamesList.setSelectionForeground(Color.BLACK);
        savedGamesList.setFont(new Font("Dubai Medium", Font.PLAIN, 22));

        // ScrollPane para la lista
        scrollPane = new JScrollPane(savedGamesList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(botonColor);
        scrollPane.setPreferredSize(new Dimension(600, 300));
        scrollPane.setMinimumSize(new Dimension(600, 300));

        // Panel para la lista con fondo y borde
        listPanel = new JPanel(new BorderLayout());
        listPanel.setBackground(botonColor);
        listPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(Color.BLACK, 2),
            new EmptyBorder(20, 5, 20, 5)
        ));
        listPanel.setPreferredSize(new Dimension(500, 300));
        listPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel principal para centrar el rectángulo
        centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(fondoColor);
        centerPanel.add(listPanel);
        add(centerPanel, BorderLayout.CENTER);

        // Panel inferior con botón RETURN
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        returnButton = new JButton("RETURN");
        returnButton.setFont(new Font("Dubai Medium", Font.PLAIN, 22));
        returnButton.setBackground(botonColor);
        returnButton.setFocusPainted(false);
        bottomPanel.setBackground(fondoColor);
        bottomPanel.setBorder(new EmptyBorder(8, 8, 8, 8));
        bottomPanel.add(returnButton);

        // Acción para el botón RETURN
        returnButton.addActionListener(e -> {
            PresentationCtrl.getInstance().showView("MainMenuView");
        });

        add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * Agrega un juego guardado a la lista de juegos guardados.
     * Si la lista contiene "No saved games", lo elimina antes de agregar el nuevo juego.
     * @param gameName El nombre del juego guardado a agregar.
     */
    public void addSavedGame(String gameName) {
        if (savedGamesListModel.contains("No saved games")) {
            savedGamesListModel.removeElement("No saved games");
        }
        savedGamesListModel.addElement(gameName);
    }

    /**
     * Obtiene el modelo de la lista de juegos guardados.
     * @return El modelo de la lista de juegos guardados.
     */
    public JList<String> getSavedGamesList() {
        return savedGamesList;
    }

    /**
     * Obtiene el botón de retorno al menú principal.
     * @return El botón de retorno.
     */
    public JButton getReturnButton() {
        return returnButton;
    }

    static public void main(String[] args) {
        JFrame frame = new JFrame("Load Game Example");
        LoadGame loadGamePanel = new LoadGame();

        loadGamePanel.addSavedGame("Partida 1 - 12/06/2025");
        loadGamePanel.addSavedGame("Partida 2 - 13/06/2025");


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(loadGamePanel);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}