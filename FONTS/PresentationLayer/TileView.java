package PresentationLayer;
import javax.swing.*;
import java.awt.*;

/**
 * SignupView representa la vista de registro de un nuevo usuario en el juego Scrabble.
 * Permite a los usuarios crear un nuevo perfil ingresando un nombre de usuario y una contraseña,
 * así como configurar si el perfil será público o privado.
 * @author Marcos Arroyo
 */
public class TileView extends JPanel {

    /**
     * Atributos de la clase TileView.
     * @param label Una etiqueta que muestra el símbolo y el valor de la ficha.
     */
    JLabel label;
    String symbol;
    int value;
    /**
     * Constructor de la clase TileView.
     * Inicializa el panel con un diseño de BorderLayout y agrega una etiqueta que muestra
     * el símbolo y el valor de la ficha.
     * @param symbol La letra o símbolo de la ficha.
     * @param value El valor de la ficha (puntuación).
     */
    
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

    /**
     * Obtiene el simbolo de la ficha.
     * @return El símbolo de la ficha.
     */
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
