package PresentationLayer;

import javax.swing.*;

import Utils.Pair;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.*;

/**
 * BoardView representa la vista del tablero de juego.
 * Contiene las casillas del tablero y gestiona la interacción con ellas.
 * @author Marcos Arroyo
 */
public class BoardView extends JPanel {

    /**
     * Atributos de la clase BoardView.
     * @param frame La ventana principal que contiene el tablero.
     * @param boardpanel El panel que representa el tablero.
     * @param centericon El icono que se muestra en el centro del tablero.
     */
    private JFrame frame;
    private JPanel boardpanel;
    private ImageIcon centericon;

    /**
     * Constructor de la clase BoardView.
     * Inicializa el tablero con un tamaño específico y un panel de rack.
     * @param size El tamaño del tablero (número de filas y columnas).
     * @param rackPanel El panel de rack donde se muestran las fichas del jugador.
     */
    public BoardView(int size, RackView rackPanel) {
        // Crear una ventana


        this.setLayout(new BorderLayout());

        initializeBoard(size, rackPanel);


    }

    /**
     * Inicializa el tablero con las casillas y sus configuraciones.
     * Dependiendo del tamaño del tablero, se establecen diferentes casillas especiales
     * (letras dobles, triples, palabras dobles y triples).
     * @param size El tamaño del tablero (número de filas y columnas).
     * @param rackPanel El panel de rack donde se muestran las fichas del jugador.
     */
    private void initializeBoard(int size, RackView rackPanel) {
        this.setLayout(new GridLayout(size, size));
        this.setBackground(Color.white);
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Set<Pair<Integer,Integer>> doubleLetter = new HashSet<>();
        Set<Pair<Integer,Integer>> tripleLetter = new HashSet<>();
        Set<Pair<Integer,Integer>> doubleWord = new HashSet<>();
        Set<Pair<Integer,Integer>> tripleWord = new HashSet<>();

        if (size == 7) {

            doubleLetter = new HashSet<>(Arrays.asList(new Pair<>(0,1), new Pair<>(0,5), new Pair<>(1,0), new Pair<>(1,6), new Pair<>(2,3), new Pair<>(3,2), new Pair<>(3,4), new Pair<>(4,3), new Pair<>(5,0), new Pair<>(5,6), new Pair<>(6,1), new Pair<>(6,5)));

            tripleLetter = new HashSet<>(Arrays.asList(new Pair<>(1,3), new Pair<>(3,1), new Pair<>(3,5), new Pair<>(5,3)));

            doubleWord = new HashSet<>(Arrays.asList(new Pair<>(1,1), new Pair<>(2,2), new Pair<>(4,4), new Pair<>(5,5), new Pair<>(1,5), new Pair<>(2,4), new Pair<>(4,2), new Pair<>(5,1)));

            tripleWord = new HashSet<>(Arrays.asList(new Pair<>(0,0), new Pair<>(0,3), new Pair<>(0,6), new Pair<>(3,0), new Pair<>(3,6), new Pair<>(6,0), new Pair<>(6,3), new Pair<>(6,6)));



        }

        else if(size == 15) {

            doubleLetter = new HashSet<>(Arrays.asList(new Pair<>(0,3), new Pair<>(0,11), new Pair<>(2,6), new Pair<>(2,8), new Pair<>(3,0), new Pair<>(3,7), new Pair<>(3,14), new Pair<>(6,2), new Pair<>(6,6), new Pair<>(6,8), new Pair<>(6,12), new Pair<>(7,3), new Pair<>(7,11), new Pair<>(14,3), new Pair<>(14,11), new Pair<>(12,6), new Pair<>(12,8), new Pair<>(11,0), new Pair<>(11,7), new Pair<>(11,14), new Pair<>(8,2), new Pair<>(8,6), new Pair<>(8,8), new Pair<>(8,12)));

            tripleLetter = new HashSet<>(Arrays.asList(new Pair<>(1,5) ,new Pair<>(1,9) ,new Pair<>(5,1) ,new Pair<>(5,5) ,new Pair<>(5,9) ,new Pair<>(5,13) ,new Pair<>(13,5) ,new Pair<>(13,9) ,new Pair<>(9,1) ,new Pair<>(9,5) ,new Pair<>(9,9) ,new Pair<>(9,13)));

            doubleWord = new HashSet<>(Arrays.asList(new Pair<> (1,1) ,new Pair<> (2,2) ,new Pair<> (3,3) ,new Pair<> (4,4)  ,new Pair<> (10,10) ,new Pair<> (11,11) ,new Pair<> (12,12) ,new Pair<> (13,13),
                            new Pair<> (1,13) ,new Pair<> (2,12) ,new Pair<> (3,11) ,new Pair<> (4,10) ,
                            new Pair<> (10,4) ,new Pair<> (11,3) ,new Pair<> (12,2),
                            new Pair<> (13,1)));

            tripleWord =  new HashSet<>(Arrays.asList(new Pair<> (0,0),
                            new Pair<> (0,7),
                            new Pair<> (0,14),
                            new Pair<> (7,0),
                            new Pair<> (7,14),
                            new Pair<> (14,0),
                            new Pair<> (14,7),
                            new Pair<> (14,14)));
        }


        else {

            doubleLetter = new HashSet<>(Arrays.asList(new Pair<>(1,8), new Pair<>(1,16), new Pair<>(3,11), new Pair<>(3,13), new Pair<>(4,12), new Pair<>(5,8), new Pair<>(5,16), new Pair<>(7,11), new Pair<>(7,13),
                            new Pair<>(8,12), new Pair<>(8,1), new Pair<>(8,5), new Pair<>(8,19), new Pair<>(8,23), new Pair<>(11,3),
                            new Pair<>(11,7), new Pair<>(11,11), new Pair<>(11,13), new Pair<>(11,17), new Pair<>(11,21), new Pair<>(12,4), new Pair<>(12,8), new Pair<>(12,16), new Pair<>(12,20),
                            new Pair<> (23,8) ,new Pair<> (23,16) ,new Pair<> (21,11) ,new Pair<> (21,13) ,new Pair<> (20,12) ,new Pair<> (19,8) ,new Pair<> (19,16) ,new Pair<> (17,11) ,new Pair<> (17,13) ,
                            new Pair<> (16,12) ,new Pair<> (16,1) ,new Pair<> (16,5) ,new Pair<> (16,19) ,new Pair<> (16,23) ,
                            new Pair<> (13,3) ,new Pair<> (13,7) ,new Pair<> (13,11) ,new Pair<> (13,13) ,new Pair<> (13,17) ,new Pair<> (13,21)));


            tripleLetter = new HashSet<>(Arrays.asList(new Pair<>(2,10), new Pair<>(2,14), new Pair<>(6,10), new Pair<>(6,14), new Pair<>(10,10), new Pair<>(10,14), new Pair<>(14,10), new Pair<>(14,14), new Pair<>(18,10), new Pair<>(18,14), new Pair<>(22,10), new Pair<>(22,14), new Pair<>(10,2), new Pair<>(10,6), new Pair<>(14,2), new Pair<>(14,6), new Pair<>(10,18), new Pair<>(10,22), new Pair<>(14,18), new Pair<>(14,22)));
            doubleWord = new HashSet<>(Arrays.asList(new Pair<>(1,1), new Pair<>(2,2), new Pair<>(3,3), new Pair<>(4,4), new Pair<>(5,5), new Pair<>(6,6), new Pair<>(7,7), new Pair<>(8,8), new Pair<>(9,9), new Pair<>(10,10), new Pair<>(11,11),  new Pair<>(13,13), new Pair<>(14,14), new Pair<>(15,15), new Pair<>(16,16), new Pair<>(17,17), new Pair<>(18,18), new Pair<>(19,19), new Pair<>(20,20), new Pair<>(21,21), new Pair<>(22,22), new Pair<>(23,23),
                            new Pair<> (1,23) ,new Pair<> (2,22) ,new Pair<> (3,21) ,new Pair<> (4,20) ,new Pair<> (5,19) ,new Pair<> (6,18) ,new Pair<> (7,17) ,new Pair<> (8,16) ,new Pair<> (9,15) ,
                            new Pair<> (15,9) ,new Pair<> (16,8) ,new Pair<> (17,7) ,new Pair<> (18,6) ,new Pair<> (19,5) ,new Pair<> (20,4) ,new Pair<> (21,3) ,new Pair<> (22,2) ,new Pair<> (23,1)));

            tripleWord = new HashSet<>(Arrays.asList(new Pair<>(0,0), new Pair<>(0,12), new Pair<>(0,24), new Pair<>(12,0), new Pair<>(12,24), new Pair<>(24,0), new Pair<>(24,12), new Pair<>(24,24)));
        }

        int sizeWords;
        if(size == 7) {
            sizeWords = 25;
        }
        else if(size == 15) {
            sizeWords = 20;
        }
        else {
            sizeWords = 15;
        }
        // Agregar casillas al tablero
        for (int i = 0; i < size ; ++i) {
            for(int j = 0 ; j < size ; ++j) {
                BoardCell cell = new BoardCell();
                if(doubleLetter.contains(new Pair<>(i,j))) {
                    JLabel label = new JLabel("DL");
                    label.setFont(new Font("Dubai Medium", Font.PLAIN, sizeWords));
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    cell.setConf("#DBE7F3", label);
                    
                }
                else if(tripleLetter.contains(new Pair<>(i,j))) {
                    JLabel label = new JLabel("TL");
                    label.setFont(new Font("Dubai Medium", Font.PLAIN, sizeWords));
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    cell.setConf("#018FC7", label);
                    
                }
                else if(doubleWord.contains(new Pair<>(i,j))) {
                    JLabel label = new JLabel("DW");
                    label.setFont(new Font("Dubai Medium", Font.PLAIN, sizeWords));
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    cell.setConf("#FFF44F", label);
                    
                }
                else if(tripleWord.contains(new Pair<>(i,j))) {
                    JLabel label = new JLabel("TW");
                    label.setFont(new Font("Dubai Medium", Font.PLAIN, sizeWords));
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    cell.setConf("#FF6961", label);
                }
                else if((i == size/2 && j == size/2)) {

                    ImageIcon star = new ImageIcon("Resources/Star.jpg");
                    Image scaledImage = star.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                    centericon = new ImageIcon(scaledImage);
                    JLabel label = new JLabel(centericon);
                    cell.setConf("#F7BBA9", label);
                    
                    cell.addComponentListener(new ComponentAdapter() {
                        @Override
                        public void componentResized(ComponentEvent e) {
                            Dimension size = cell.getSize();
                            ImageIcon scaledIcon = new ImageIcon(centericon.getImage().getScaledInstance(size.width, size.height, Image.SCALE_SMOOTH));
                            label.setIcon(scaledIcon);
                            cell.revalidate();
                            cell.repaint();
                        }
                    });
                }
                else {
                    cell.setConf("#F7BBA9", new JLabel());
                    //cell.setBackground(Color.decode("#F7BBA9"));
                }// Fondo negro de cada casilla
                cell.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.white));
                cell.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt){
                        System.out.println("clicked in cell");
                        if(cell.isEmpty() ){
                            TileView sTile = rackPanel.getSelectedTile();
                            sTile.removeMouseListener(sTile.getMouseListeners()[0]);
                            if(sTile != null){
                            cell.PlaceTile(sTile);
                            rackPanel.removeSelectedTile();
                            
                            }
                             

                            System.out.println("is empty");
                            
                        }
                        else {
                            System.out.println("is not empty");
                            TileView tile = cell.getTilePlaced();
                            System.out.println("tile picked");
                            cell.removeTile();
                            rackPanel.addTile(tile);
                            rackPanel.setSelectedTile(tile);
                            rackPanel.addListener(tile);
                            
                            
                        }
                       System.out.println("No se que hago aqui");
                    }
                    

                });
                
                this.add(cell);
            }
        }






    }

}