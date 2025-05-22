package PresentationLayer;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class LoadGame extends JPanel {

    private DefaultListModel<String> savedGamesListModel;
    private JList<String> savedGamesList;
    private JScrollPane scrollPane;
    private JPanel listPanel;
    private JPanel centerPanel;
    private JButton returnButton;

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

    // Método público para agregar un juego guardado a la lista
    public void addSavedGame(String gameName) {
        if (savedGamesListModel.contains("No saved games")) {
            savedGamesListModel.removeElement("No saved games");
        }
        savedGamesListModel.addElement(gameName);
    }

    // Puedes añadir más getters si necesitas acceder a los componentes desde fuera
    public JList<String> getSavedGamesList() {
        return savedGamesList;
    }

    public JButton getReturnButton() {
        return returnButton;
    }
}