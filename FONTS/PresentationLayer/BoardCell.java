package PresentationLayer;
import javax.swing.*;
import java.awt.*;

/**
 * BoardCell representa una casilla del tablero de juego.
 * Cada casilla puede contener un TileView y tiene un color de fondo.
 * @author Marcos Arroyo
 */
public class BoardCell extends JPanel {
    /**
     * Atributos de la clase BoardCell.
     * @param isEmpty Indica si la casilla está vacía.
     * @param tilePlaced El TileView que se ha colocado en la casilla.
     * @param backColour El color de fondo de la casilla.
     * @param label Una etiqueta que se muestra en la casilla.
     * @param row La fila en la que se encuentra la casilla.
     * @param column La columna en la que se encuentra la casilla.
     */
    private boolean isEmpty;
    private TileView tilePlaced;
    private String backColour;
    private JLabel label;
    private int row;
    private int column;

    /**
     * Constructor de la clase BoardCell.
     * Inicializa la casilla con un layout de BorderLayout, establece que está vacía
     * y no tiene ningún TileView colocado.
     */
    public BoardCell() {
        super();
        this.setLayout(new BorderLayout());
        this.isEmpty = true;
        tilePlaced = null;
    }

    /**
     * Configura la casilla con un color de fondo y una etiqueta.
     * @param colour El color de fondo de la casilla.
     * @param label La etiqueta que se mostrará en la casilla.
     */
    public void setConf(String colour, JLabel label) {
        this.backColour = colour;
        this.label = label;
        this.setBackground(Color.decode(colour));
        this.add(this.label, BorderLayout.CENTER);
    }

    public void setCol(String colour) {
        this.backColour = colour;
        this.setBackground(Color.decode(colour));
    }

    /**
     * Establece las coordenadas de la casilla.
     * @param row La fila de la casilla.
     * @param column La columna de la casilla.
     */
    public void setCoords(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Obtiene la fila de la casilla.
     * @return la fila de la casilla.
     */
    public int getRow() {
        return row;
    }

    /**
     * Obtiene la columna de la casilla.
     * @return la columna de la casilla.
     */
    public int getColumn() {
        return column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    /**
     * Verifica si la casilla está vacía.
     * @return true si la casilla está vacía, false en caso contrario.
     */
    public boolean isEmpty() {
        return isEmpty;
    }

    /**
     * Obtiene el TileView colocado en la casilla.
     * @return el TileView colocado, o null si no hay ninguno.
     */
    public TileView getTilePlaced() {
        return tilePlaced;
    }

    /**
     * Coloca un TileView en la casilla.
     * Si ya hay un TileView colocado, no hace nada.
     * @param tile El TileView que se va a colocar en la casilla.
     */
    public void PlaceTile (TileView tile) {
        if(tilePlaced == null){
        this.tilePlaced = tile;
        this.removeAll();
        this.add(tilePlaced);
        this.isEmpty = false;
        this.revalidate();
        this.repaint();
        } 
    }

    /**
     * Elimina el TileView colocado en la casilla.
     * Si no hay ningún TileView, no hace nada.
     */
    public void removeTile() {
        if(tilePlaced != null){
            this.remove(tilePlaced);
            this.add(label);
            this.setBackground(Color.decode(backColour));
            tilePlaced = null;
            this.isEmpty = true;
            this.revalidate();
            this.repaint();
        }
    }
}
