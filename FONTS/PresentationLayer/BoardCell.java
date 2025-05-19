package PresentationLayer;
import javax.swing.*;
import java.awt.*;


public class BoardCell extends JPanel {
    private boolean isEmpty;
    private TileView tilePlaced;
    private String backColour;
    private JLabel label;

    public BoardCell() {
        super();
        this.setLayout(new BorderLayout());
        this.isEmpty = true;
        tilePlaced = null;
    }

    public void setConf(String colour, JLabel label) {
        this.backColour = colour;
        this.label = label;
        this.setBackground(Color.decode(colour));
        this.add(this.label, BorderLayout.CENTER);
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public TileView getTilePlaced() {
        return tilePlaced;
    }

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
