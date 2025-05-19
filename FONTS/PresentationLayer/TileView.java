package PresentationLayer;
import javax.swing.*;
import java.awt.*;

public class TileView extends JPanel {
    // Jpanel this
    JLabel label;
    public TileView(String symbol, int value) {
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(50, 50));
        this.setBackground(Color.decode("#EED09D"));


        label = new JLabel("<html><center>" + symbol + "<sup>" + value + "</sup></center></html>");
        label.setFont(new Font("Dubai Medium", Font.PLAIN, 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(label, BorderLayout.CENTER);
    }

    public String getSymbol() {
        return label.getText().substring(6, 7);
    }

    public static void main(String[] args) {
        // Crear un JFrame para probar el TileView
        JFrame frame = new JFrame("Tile Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        // Crear una ficha con letra y valor
        TileView tile = new TileView("A", 1);

        // Agregar la ficha al JFrame
        frame.add(tile);
        frame.setVisible(true);
    }
}
