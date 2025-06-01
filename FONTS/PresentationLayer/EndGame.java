package PresentationLayer;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import Utils.Pair;

/**
 * EndGame representa la pantalla final del juego, mostrando al ganador y una tabla de puntuaciones.
 * Permite al usuario volver al menú principal.
 * @author ?
 */
public class EndGame extends JPanel {

    /**
     * Atributos de la clase EndGame.
     * @param winnerLabel Etiqueta que muestra el nombre del ganador.
     * @param returnButton Botón para volver al menú principal.
     * @param scoresTable Tabla que muestra las puntuaciones de los jugadores.
     */
    private JLabel winnerLabel;
    private JButton returnButton;
    private JTable scoresTable;

    /**
     * Constructor de la clase EndGame.
     * Inicializa el panel con un diseño y componentes para mostrar el ganador y la tabla de puntuaciones.
     * @param winnerName Nombre del jugador ganador.
     * @param scores Lista de puntuaciones de los jugadores, donde cada entrada es un array con el nombre del jugador y su puntuación.
     */
    public EndGame(String winnerName, List<Pair<String, Integer>> scores) {
        setLayout(new BorderLayout(10, 10));
        Color fondoColor = Color.decode("#F5F6FA");
        Color botonColor = Color.decode("#F7BBA9");

        setBackground(fondoColor);

        // Panel superior con título
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(fondoColor);
        JLabel titleLabel = new JLabel("Game Over");
        titleLabel.setFont(new Font("Dubai Medium", Font.BOLD, 48));
        titleLabel.setForeground(Color.decode("#181818"));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Panel central con ganador y tabla de puntuaciones
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(fondoColor);
        centerPanel.setBorder(new EmptyBorder(40, 80, 40, 80));

        winnerLabel = new JLabel("Winner: " + winnerName);
        winnerLabel.setFont(new Font("Dubai Medium", Font.BOLD, 32));
        winnerLabel.setForeground(Color.decode("#018FC7"));
        winnerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(winnerLabel);

        centerPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Tabla de puntuaciones
        String[] columnNames = {"Player", "Score"};
        String[][] data = new String[scores.size()][2];
        for (int i = 0; i < scores.size(); i++) {
        data[i][0] = scores.get(i).first(); // nombre del jugador
        data[i][1] = String.valueOf(scores.get(i).second()); // puntuación como string
        }
        scoresTable = new JTable(data, columnNames);
        scoresTable.setFont(new Font("Dubai Medium", Font.PLAIN, 20));
        scoresTable.setRowHeight(32);
        scoresTable.getTableHeader().setFont(new Font("Dubai Medium", Font.BOLD, 22));
        scoresTable.getTableHeader().setBackground(botonColor);
        scoresTable.getTableHeader().setForeground(Color.decode("#181818"));
        scoresTable.setBackground(botonColor);
        scoresTable.setForeground(Color.decode("#181818"));
        scoresTable.setFillsViewportHeight(true);
        scoresTable.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(scoresTable);
        scrollPane.setBorder(new LineBorder(Color.BLACK, 2));
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(scrollPane);

        add(centerPanel, BorderLayout.CENTER);

        // Panel inferior con botón RETURN
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(fondoColor);
        bottomPanel.setBorder(new EmptyBorder(8, 8, 8, 8));

        returnButton = new JButton("RETURN");
        returnButton.setFont(new Font("Dubai Medium", Font.PLAIN, 22));
        returnButton.setBackground(botonColor);
        returnButton.setFocusPainted(false);
        returnButton.setPreferredSize(new Dimension(120, 40));
        bottomPanel.add(returnButton);

        // Acción para el botón RETURN
        returnButton.addActionListener(e -> {
            PresentationCtrl.getInstance().showView("MainMenuView");
        });

        add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * Obtiene el botón de retorno al menú principal.
     * @return El botón de retorno.
     */
    public JButton getReturnButton() { return returnButton; }

    /**
     * Obtiene la etiqueta que muestra el ganador.
     * @return La etiqueta del ganador.
     */
    public JLabel getWinnerLabel() { return winnerLabel; }

    /**
     * Obtiene la tabla de puntuaciones.
     * @return La tabla de puntuaciones.
     */
    public JTable getScoresTable() { return scoresTable; }

  
}