package PresentationLayer;

import javax.swing.*;

import Utils.Pair;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.*;

public class BoardView extends JPanel {
    private JFrame frame;
    private ImageIcon centericon;
    private Set<Pair<Integer, Integer>> tilesPlaced; // column = first(), row = second()
    private Set<Pair<Integer, Integer>> jokerPos; // column = first(), row = second()
    private int size;

    public BoardView(int size, RackView rackPanel) {
        // Crear una ventana


        this.setLayout(new BorderLayout());
        tilesPlaced = new LinkedHashSet<>();
        jokerPos = new LinkedHashSet<>();
        this.size = size;

        initializeBoard(size, rackPanel);


    }

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
                cell.setCoords(i,j);
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
                            if(sTile != null){
                                System.out.println("simbolo de la ficha puesta es:" + sTile.getSymbol());
                                if(sTile.getSymbol().equals("#")){
                                    System.out.println("soy un comodin");
                                    String inp = JOptionPane.showInputDialog(null, "Joker selection", "Type letter that you want: ", JOptionPane.PLAIN_MESSAGE );
                                    if(inp == null || inp.length() > 1 || inp.isEmpty()) {
                                        sTile = null;
                                        return;
                                    }
                                    inp = inp.toUpperCase();
                                    sTile.updateLabel(inp, 0);
                                    jokerPos.add(new Pair<>(cell.getColumn(), cell.getRow()));
                                }
                                sTile.removeMouseListener(sTile.getMouseListeners()[0]);
                                cell.PlaceTile(sTile);
                                tilesPlaced.add(new Pair<>(cell.getColumn(), cell.getRow()));
                                


                                rackPanel.removeSelectedTile();
                            
                            }
                             

                            System.out.println("is empty");
                            
                        }
                        else {
                            System.out.println("is not empty");
                            TileView tile = cell.getTilePlaced();
                            System.out.println("tile picked");
                            cell.removeTile();
                            tilesPlaced.remove(new Pair<>(cell.getColumn(), cell.getRow()));
                            if(tile.getValue() == 0) {
                                jokerPos.remove(new Pair<>(cell.getColumn(), cell.getRow()));
                                tile.updateLabel("#", 0);
                            }
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

    public BoardCell getBoardCell(int row, int column) {
        // Devuelve la celda del tablero en la posición especificada
        int index = row * size + column; // Calcula el índice de la celda en el GridLayout
        Component comp = this.getComponent(index);
        if (comp instanceof BoardCell) {
            return (BoardCell) comp;
        }
        return null; // Si no se encuentra la celda, devuelve null
    }

    private boolean nextCellisEmpty(int row, int column) {
        // Comprueba si la siguiente celda en la dirección especificada está vacía
        BoardCell nextCell = getBoardCell(row, column);
        return nextCell.isEmpty();
    }

    // Método que forma la palabra que el usuario ha colocado en el tablero y además calcula
    // cuales son las coordenadas iniciales y finales de la palabra. Esta información se utilizará
    // para que el algoritmo compruebe si la palabra es válida o no.
    // coords_ini.columna = first(), coords_ini.fila = second()
    // coords_end.columna = first(), coords_end.fila = second()
    public ArrayList<String> computeWord(ArrayList<Pair<Integer, Integer>> coords_ini, ArrayList<Pair<Integer, Integer>> coords_end) {
        ArrayList<String> res = new ArrayList<>();
        boolean vertical = false; 
        String word = "";
        coords_ini.get(0).setFirst(tilesPlaced.iterator().next().first());
        coords_ini.get(0).setSecond(tilesPlaced.iterator().next().second());
        coords_end.get(0).setFirst(tilesPlaced.iterator().next().first());
        coords_end.get(0).setSecond(tilesPlaced.iterator().next().second());
        Pair<Integer, Integer> firstPos = new Pair<>(tilesPlaced.iterator().next().first(), tilesPlaced.iterator().next().second()); // obtenemos la primera posicion de las fichas colocadas
 
        //System.out.println("Coordenadas iniciales: " + coords_ini.get(0).first() + ' ' + coords_ini.get(0).second() + ", Coordenadas finales: " + coords_end.get(0).first() + ' ' + coords_end.get(0).second());
        BoardCell cell = getBoardCell(coords_ini.get(0).second(), coords_ini.get(0).first());
        word = cell.getTilePlaced().getSymbol(); // se empieza a formar la palabta a partir de la primera ficha que has puesto

        if(tilesPlaced.size() > 1){ 
        vertical = computeDir();  // se calcula la direccion de la palabra, true si es vertical, false horizontal 
            if(vertical){
                //Si es vertical, recorremos las filas del tablero para obtener los simbolos de cada ficha y en total, la palabra
                //En primer lugar, extendemos la posicion actual hacia arriba 
                while((coords_ini.get(0).second() - 1 >= 0) && !nextCellisEmpty(coords_ini.get(0).second() - 1, coords_ini.get(0).first())) {
                    coords_ini.get(0).setSecond(coords_ini.get(0).second() - 1);
                    cell = getBoardCell(coords_ini.get(0).second(), coords_ini.get(0).first()); // obtenemos la nueva celda
                    word = cell.getTilePlaced().getSymbol() + word; 
                }

                // Ahora extendemos la posicion actual hacia abajo
                while((coords_end.get(0).second() + 1 < size) && !nextCellisEmpty(coords_end.get(0).second() + 1, coords_end.get(0).first())) {
                    coords_end.get(0).setSecond(coords_end.get(0).second() + 1);
                    cell = getBoardCell(coords_end.get(0).second(), coords_end.get(0).first()); // obtenemos la nueva celda
                    word += cell.getTilePlaced().getSymbol();
                }


            }
            else{

                //Si es horizontal, recorremos las columnas del tablero para obtener los simbolos de cada ficha y en total, la palabra
                //En primer lugar, extendemos la posicion actual hacia la izquierda 
                while((coords_ini.get(0).first() - 1 >= 0) && !nextCellisEmpty(coords_ini.get(0).second(), coords_ini.get(0).first() - 1)) {
                    coords_ini.get(0).setFirst(coords_ini.get(0).first() - 1);
                    cell = getBoardCell(coords_ini.get(0).second(), coords_ini.get(0).first()); // obtenemos la nueva celda
                    word = cell.getTilePlaced().getSymbol() + word; 
                }


                // Ahora extendemos la posicion actual hacia la derecha
                while((coords_end.get(0).first() + 1 < size) && !nextCellisEmpty(coords_end.get(0).second(), coords_end.get(0).first() + 1)) {
                    coords_end.get(0).setFirst(coords_end.get(0).first() + 1);
                    cell = getBoardCell(coords_end.get(0).second(), coords_end.get(0).first()); // obtenemos la nueva celda
                    word += cell.getTilePlaced().getSymbol();
                }

            
             }
             
             res.add(word);
        }   

        else{
            // Si solo se hac colocado una ficha, tengo que extender la posicion actual hacia arriba, abajo, izquierda y derecha
            String word2 = word; // palabra formada con la primera ficha colocada
            Boolean found = false;
            coords_ini.get(1).setFirst(tilesPlaced.iterator().next().first());
            coords_ini.get(1).setSecond(tilesPlaced.iterator().next().second());
            coords_end.get(1).setFirst(tilesPlaced.iterator().next().first());
            coords_end.get(1).setSecond(tilesPlaced.iterator().next().second());
            // ARRIBA
            while((coords_ini.get(1).second() - 1 >= 0) && !nextCellisEmpty(coords_ini.get(1).second() - 1, coords_ini.get(1).first())) {
                    coords_ini.get(1).setSecond(coords_ini.get(1).second() - 1);
                    cell = getBoardCell(coords_ini.get(1).second(), coords_ini.get(1).first()); // obtenemos la nueva celda
                    word2 = cell.getTilePlaced().getSymbol() + word2; 
                    if(!found) found = true;
                }
            // ABAJO
            while((coords_end.get(1).second() + 1 < size) && !nextCellisEmpty(coords_end.get(1).second() + 1, coords_end.get(1).first())) {
                coords_end.get(1).setSecond(coords_end.get(1).second() + 1);
                cell = getBoardCell(coords_end.get(1).second(), coords_end.get(1).first()); // obtenemos la nueva celda
                word2 += cell.getTilePlaced().getSymbol();
                if(!found) found = true;
            }

            if(found) { // Si la palabra formada es mayor que 1, entonces se añade a la lista de palabras formadas
                res.add(word2); // Segunda palabra formada con la primera ficha colocada
            }
            else {
                coords_ini.remove(1); // Si la palabra formada es menor o igual a 1, entonces no se añade a la lista de palabras formadas
                coords_end.remove(1);
            }

            found = false; // Reiniciamos la variable found para la segunda palabra formada

            // IZQUIERDA
            while((coords_ini.get(0).first() - 1 >= 0) && !nextCellisEmpty(coords_ini.get(0).second(), coords_ini.get(0).first() - 1)) {
                    coords_ini.get(0).setFirst(coords_ini.get(0).first() - 1);
                    cell = getBoardCell(coords_ini.get(0).second(), coords_ini.get(0).first()); // obtenemos la nueva celda
                    word = cell.getTilePlaced().getSymbol() + word; 
                    if(!found) found = true;
                }

            // DERECHA 
            while((coords_end.get(0).first() + 1 < size) && !nextCellisEmpty(coords_end.get(0).second(), coords_end.get(0).first() + 1)) {
                coords_end.get(0).setFirst(coords_end.get(0).first() + 1);
                cell = getBoardCell(coords_end.get(0).second(), coords_end.get(0).first()); // obtenemos la nueva celda
                word += cell.getTilePlaced().getSymbol();
                if(!found) found = true;
            }

            if(found) { // Si la palabra formada es mayor que 1, entonces se añade a la lista de palabras formadas
                res.add(0,word); // Segunda palabra formada con la primera ficha colocada
            }
            else {
                coords_ini.remove(0); // Si la palabra formada es menor o igual a 1, entonces no se añade a la lista de palabras formadas
                coords_end.remove(0);
            }


        }
        System.out.println("Coordenadas iniciales: " + coords_ini.get(0).first() + ' ' + coords_ini.get(0).second() + ", Coordenadas finales: " + coords_end.get(0).first() + ' ' + coords_end.get(0).second());
        if(coords_ini.size() > 1) {
            System.out.println("Coordenadas iniciales: " + coords_ini.get(1).first() + ' ' + coords_ini.get(1).second() + ", Coordenadas finales: " + coords_end.get(1).first() + ' ' + coords_end.get(1).second());
        }
        else {
            System.out.println("No hay segunda palabra formada");
        }

        System.out.println("Palabra formada: " + word);
        return res;

    }


    // Calcula la dirección de la palabra (vertical u horizontal), devuelve true si es vertical, false si es horizontal
    // En tiles placed tengo el conjunto de las coordenadas donde se han colocado las fichas, si dos fichas consecutivas
    // tienen diferentes columnas, entonces es horizontal, en cambio si tienen diferentes filas, entonces es vertical
    private boolean computeDir() {
        
        int col = tilesPlaced.iterator().next().first();
        int row = tilesPlaced.iterator().next().second();
        for(Pair<Integer, Integer> pos : tilesPlaced) {
            if(pos.first() != col) return false; // es horizontal porque las columnas son diferentes
            if(pos.second() != row) return true; // es vertical porque las filas son diferentes
        }
        return false; 
    }

    public void cleanTilesPlaced() {
        tilesPlaced.clear();
    }

    public void lockTilesPlaced() {
        for(Pair<Integer, Integer> pos : tilesPlaced) {
            BoardCell cell = getBoardCell(pos.second(), pos.first());
            cell.removeMouseListener(cell.getMouseListeners()[0]);
        }
        jokerPos.clear(); // Limpiamos las posiciones de los jokers al bloquear las fichas colocadas
        
    }

    public Set<Pair<Integer, Integer>> getJokersPos() {
        return jokerPos;
    }

    public void returnTilesToRack(RackView rackPanel) {
    for (Pair<Integer, Integer> pos : new ArrayList<>(tilesPlaced)) {
        BoardCell cell = getBoardCell(pos.second(), pos.first());
        TileView tile = cell.getTilePlaced();
        if (tile != null) {
            cell.removeTile();
            // Si es comodín, restáurale el símbolo
            if (tile.getValue() == 0) {
                tile.updateLabel("#", 0);
                jokerPos.remove(new Pair<>(cell.getColumn(), cell.getRow()));
            }
            rackPanel.addTile(tile);
            rackPanel.setSelectedTile(tile);
            rackPanel.addListener(tile);
        }
    }
    tilesPlaced.clear();
    jokerPos.clear();
}

//word es una lista de palabras y sus valores de forma: [A][S]...
    public void actBoardView(ArrayList<String> word, Integer[] coords, RackView rackPanel) {
        //Añado letra a letra en el tablero y refresco la vista cada vez que se añade
        //quiero ir pillando una a una las fichas del rack y colocarlas una a  una en el tablero
        int coordX = coords[0];
        int coordY = coords[1];
        int endX = coords[2];
        Boolean vertical = false;
        if(coordX == endX) {
            vertical = true; // Si las coordenadas iniciales y finales tienen la misma columna, es vertical
        }
        
        
        for(int i = 0; i < word.size(); ++i) {
           
            BoardCell cell = getBoardCell(coordY, coordX);
            if(cell.isEmpty()){
                TileView tile = rackPanel.removeTile(word.get(i)); // Elimina la ficha del rack
                rackPanel.refreshRack(); //Refresca el rack para que se vea en la interfaz que esa ficha se ha quitado del rack
                
                cell.PlaceTile(tile);
                //si la celda tiene algun mouste listener, lo elimino para que no se pueda quitar la ficha del tablero
                if(cell.getMouseListeners().length > 0) {
                    cell.removeMouseListener(cell.getMouseListeners()[0]);
                }
                if(tile.getMouseListeners().length > 0) {
                    tile.removeMouseListener(tile.getMouseListeners()[0]); // Elimina el mouse listener de la ficha para que no se pueda quitar del tablero
                }
                
            }
            if(vertical) {
                    coordY++; // Si es vertical, incrementamos la fila
                } else {
                    coordX++; // Si es horizontal, incrementamos la columna
                }
        }   

      
      

        
    }


}
