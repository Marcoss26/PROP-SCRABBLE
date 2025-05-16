import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

public class RackView extends JPanel {

    //el parametro letters es una lista que representa las fichas del jugador
    //el formato de la lista es el siguiente:
    //letters = [A,1, B,3, C,3, D,2, E,1, F,4, G,2] en la posición i está el simbolo
    //y en la posición i+1 está el valor de la ficha
    private Image rackBackground;
    private JPanel rackPanel;
    private JPanel ButtonsPanel;

    public RackView(List<String> letters) {
        //this.setLayout(new GridLayout(1, letters.size()/2));

        this.setLayout(new BorderLayout());

        ImageIcon rackwood = new ImageIcon("resources/Wood.jpg");
        rackBackground = rackwood.getImage();

        rackPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Dibujar la imagen de fondo escalada al tamaño del panel
                g.drawImage(rackBackground, 0, 0, getWidth(), getHeight(), this);
            }
        };
        rackPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        rackPanel.setPreferredSize(new Dimension(500, 100));
        //rackPanel.setBackground(Color.decode("#332F2C"));
        rackPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        

        //cell.setBackground(Color.decode("#F7BBA9"));
        ButtonsPanel = new JPanel();
        ButtonsPanel.setLayout(new GridLayout(2,1));
        ButtonsPanel.setPreferredSize(new Dimension(100, 100));

        JButton shuffleButton = new JButton("Shuffle");
        shuffleButton.setPreferredSize(new Dimension(100, 50));
        JButton exchangeButton = new JButton("Exchange");
        exchangeButton.setPreferredSize(new Dimension(100, 50));
        ButtonsPanel.add(shuffleButton);
        ButtonsPanel.add(exchangeButton);

        rackPanel.add(shuffleButton);
        rackPanel.add(exchangeButton);


        for(int i = 0; i < letters.size(); i+=2) {
            TileView tile = new TileView(letters.get(i), Integer.parseInt(letters.get(i+1)));
            rackPanel.add(tile);
        }

        this.add(rackPanel, BorderLayout.CENTER);
        //this.add(ButtonsPanel, BorderLayout.WEST);

    }


    public static void main(String[] args) {
        // Crear un JFrame para probar el RackView
        JFrame frame = new JFrame("Rack Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 125);

        // Crear una lista de fichas
        List<String> letters = List.of("A", "1", "B", "3", "C", "3", "D", "2", "E", "1", "F", "4", "G", "2");

        // Crear el RackView y agregarlo al JFrame
        RackView rack = new RackView(letters);
        frame.add(rack);

        frame.setVisible(true);
    }
}
