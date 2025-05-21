package PresentationLayer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class RankingView extends JPanel {

    private DefaultTableModel tableModel;

    public RankingView() {
        setLayout(new BorderLayout());
        setBackground(Color.decode("#F5F6FA")); // Fondo gris claro

        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80)); // Márgenes

        // Título
        JLabel title = new JLabel("Ranking");
        title.setFont(new Font("Dubai Medium", Font.BOLD, 48));
        title.setForeground(Color.decode("#181818"));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(title);

        // Espacio bajo el título
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Modelo de tabla (inicialmente vacío)
        String[] columnNames = {"#", "ProfileID", "Wins", "WR", "Total Games", "PPG (Points per Game)", "Preferred Dictionary"};
        tableModel = new DefaultTableModel(columnNames, 0); // 0 filas iniciales

        JTable rankingTable = new JTable(tableModel);
        rankingTable.setFont(new Font("Dubai Medium", Font.PLAIN, 16));
        rankingTable.setRowHeight(30);
        rankingTable.getTableHeader().setFont(new Font("Dubai Medium", Font.BOLD, 18));
        rankingTable.getTableHeader().setBackground(Color.decode("#F7BBA9"));
        rankingTable.getTableHeader().setForeground(Color.decode("#181818"));
        rankingTable.setBackground(Color.decode("#F7BBA9"));
        rankingTable.setForeground(Color.decode("#181818"));
        rankingTable.setFillsViewportHeight(true);

        // Crear un renderizador personalizado para intercalar colores
        rankingTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    if (row % 2 == 0) {
                        cell.setBackground(Color.decode("#FCE4D6")); // Salmón más pálido
                    } else {
                        cell.setBackground(Color.decode("#F7BBA9")); // Salmón original
                    }
                    cell.setForeground(Color.decode("#181818")); // Texto negro
                }
                return cell;
            }
        });

        JScrollPane scrollPane = new JScrollPane(rankingTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Borde negro de 2 píxeles
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(scrollPane);

        // Espacio flexible abajo
        mainPanel.add(Box.createVerticalGlue());

        add(mainPanel, BorderLayout.CENTER);

        // Botón Return en la parte inferior derecha
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(new EmptyBorder(8, 8, 8, 8));

        JButton returnButton = new JButton("RETURN");
        returnButton.setFont(new Font("Dubai Medium", Font.PLAIN, 22));
        returnButton.setBackground(Color.decode("#F7BBA9"));
        returnButton.setFocusPainted(false);
        returnButton.setPreferredSize(new Dimension(120, 40));
        bottomPanel.add(returnButton);

        // Acción para el botón RETURN
        returnButton.addActionListener(e -> {
            PresentationCtrl.getInstance().showView("MainMenuView");
        });

        add(bottomPanel, BorderLayout.SOUTH);
    }

    // Método para agregar un usuario al ranking
    public void addUserToRanking(String profileID, int wins, String winRate, int totalGames, double ppg, String preferredDictionary) {
        int rank = tableModel.getRowCount() + 1; // El rango es el número de filas + 1
        tableModel.addRow(new Object[]{rank, profileID, wins, winRate, totalGames, ppg, preferredDictionary});
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Ranking View");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setResizable(false);

        RankingView rankingView = new RankingView();
        frame.add(rankingView);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Caso base: agregar usuarios de ejemplo
        rankingView.addUserToRanking("Player1", 20, "55%", 52, 69.9, "English");
        rankingView.addUserToRanking("Player2", 18, "50%", 48, 65.3, "Spanish");
        rankingView.addUserToRanking("Player3", 15, "45%", 40, 60.1, "Catalan");
    }
}