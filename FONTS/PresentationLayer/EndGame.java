package PresentationLayer;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class EndGame extends JPanel {

    private JLabel winnerLabel;
    private JButton returnButton;
    private JTable scoresTable;

    public EndGame(String winnerName, List<String[]> scores) {
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
        String[][] data = scores.toArray(new String[0][]);
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

    // Getters si necesitas acceder desde fuera
    public JButton getReturnButton() { return returnButton; }
    public JLabel getWinnerLabel() { return winnerLabel; }
    public JTable getScoresTable() { return scoresTable; }

    // Ejemplo de uso independiente
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("End Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(700, 500);
            frame.setResizable(false);

            // Ejemplo de datos
            List<String[]> scores = List.of(
                new String[]{"Player 1", "120"},
                new String[]{"Player 2", "95"},
                new String[]{"Player 3", "80"}
            );

            EndGame endGamePanel = new EndGame("Player 1", scores);
            frame.add(endGamePanel);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}