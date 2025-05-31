package PresentationLayer;
import javax.swing.*;
import java.awt.*;

public class TileView extends JPanel {
    // Jpanel this
    JLabel label;
    String symbol;
    int value;
    public TileView(String symbol, int value) {
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(50, 50));
        this.setBackground(Color.decode("#EED09D"));

        this.symbol = symbol;
        this.value = value;
        label = new JLabel("<html><center>" + symbol + "<sup>" + value + "</sup></center></html>");
        label.setFont(new Font("Dubai Medium", Font.PLAIN, 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(label, BorderLayout.CENTER);
    }

    public String getSymbol() {
        return symbol;
    }

    public int getValue() {
        return value;
    }

    public void updateLabel(String symbol, int value) {
        this.symbol = symbol;
        label.setText("<html><center>" + symbol + "<sup>" + value + "</sup></center></html>");
        this.revalidate();
        this.repaint();
    }

    
}
