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
        return label.getText().substring(6, 7);
    }

    /**
     * Metodo principal para probar la clase TileView.
     * Crea un JFrame y agrega una instancia de TileView con un símbolo y valor específicos.
     * Este método se utiliza para verificar que la ficha se muestre correctamente en una ventana.
     * @param args
     */
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
