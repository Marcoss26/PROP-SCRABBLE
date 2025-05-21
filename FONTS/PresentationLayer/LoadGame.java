package PresentationLayer;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class LoadGame extends JPanel {

    private DefaultListModel<String> savedGamesListModel; // Modelo de la lista

    public LoadGame() {
        // Configurar el panel principal
        setLayout(new BorderLayout(0, 0)); // Sin márgenes entre los componentes
        Color fondoColor = new Color(245, 246, 250);
        Color botonColor = new Color(247, 187, 169);

        // Cambiar el color de fondo del panel
        setBackground(fondoColor);

        // Crear un panel para el título centrado en la parte superior
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Alinear al centro
        titlePanel.setBackground(fondoColor); // Fondo del panel
        JLabel titleLabel = new JLabel("Load Game");
        titleLabel.setFont(new Font("Dubai Medium", Font.BOLD, 48)); // Tamaño de fuente ajustado a 48
        titlePanel.add(titleLabel); // Agregar el título al panel
        titlePanel.setBorder(new EmptyBorder(20, 0, 20, 0)); // Margen superior e inferior
        add(titlePanel, BorderLayout.NORTH); // Agregar el panel al norte

        // Crear el modelo de la lista
        savedGamesListModel = new DefaultListModel<>();
        savedGamesListModel.addElement("No saved games"); // Mensaje inicial

        // Crear la lista de juegos guardados
        JList<String> savedGamesList = new JList<>(savedGamesListModel);
        savedGamesList.setBackground(botonColor); // Fondo de la lista
        savedGamesList.setSelectionBackground(new Color(255, 200, 200)); // Fondo al seleccionar
        savedGamesList.setSelectionForeground(Color.BLACK); // Color del texto al seleccionar
        savedGamesList.setFont(new Font("Dubai Medium", Font.PLAIN, 22)); // Tamaño de fuente ajustado a 22

        // Crear un JScrollPane para la lista
        JScrollPane scrollPane = new JScrollPane(savedGamesList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Eliminar el borde del JScrollPane
        scrollPane.getViewport().setBackground(botonColor); // Fondo del área de desplazamiento
        scrollPane.setPreferredSize(new Dimension(600, 300)); // Tamaño preferido más grande
        scrollPane.setMinimumSize(new Dimension(600, 300)); // Tamaño mínimo para evitar que se colapse

        // Crear un panel para la lista con fondo y borde
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.setBackground(botonColor); // Fondo del rectángulo
        listPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(Color.BLACK, 2), // Borde negro de 2 píxeles
            new EmptyBorder(20, 5, 20, 5) // Márgenes internos: superior, izquierdo, inferior, derecho
        ));
        listPanel.setPreferredSize(new Dimension(500, 300)); // Reducir el ancho del rectángulo
        listPanel.add(scrollPane, BorderLayout.CENTER); // Agregar la lista al centro del panel

        // Crear un panel principal para centrar el rectángulo
        JPanel centerPanel = new JPanel(new GridBagLayout()); // Usar GridBagLayout para centrar
        centerPanel.setBackground(fondoColor); // Fondo del panel principal
        centerPanel.add(listPanel); // Agregar el panel con la lista centrada
        add(centerPanel, BorderLayout.CENTER); // Agregar el panel al centro

        // Crear un panel inferior con un botón de retorno
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton returnButton = new JButton("RETURN");
        returnButton.setFont(new Font("Dubai Medium", Font.PLAIN, 22)); // Tamaño de fuente ajustado a 22
        returnButton.setBackground(botonColor); // Fondo del botón
        returnButton.setFocusPainted(false);
        bottomPanel.setBackground(fondoColor); // Fondo del panel
        bottomPanel.setBorder(new EmptyBorder(8, 8, 8, 8)); // Añadir margen al panel inferior
        bottomPanel.add(returnButton); // Agregar el botón al panel inferior

        // Acción para el botón RETURN
        returnButton.addActionListener(e -> {
            PresentationCtrl.getInstance().showView("MainMenuView");
        });

        add(bottomPanel, BorderLayout.SOUTH); // Agregar el panel inferior al sur
    }

    // Método para agregar un juego guardado a la lista
    public void addSavedGame(String gameName) {
        if (savedGamesListModel.contains("No saved games")) {
            savedGamesListModel.removeElement("No saved games"); // Eliminar el mensaje inicial
        }
        savedGamesListModel.addElement(gameName); // Agregar el nuevo juego
    }
}