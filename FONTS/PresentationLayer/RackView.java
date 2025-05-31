package PresentationLayer;
import javax.swing.*;


import java.awt.*;
import java.awt.event.*;
//import java.util.List;
import java.util.*;


public class RackView extends JPanel {

    //el parametro letters es una lista que representa las fichas del jugador
    //el formato de la lista es el siguiente:
    //letters = [A,1, B,3, C,3, D,2, E,1, F,4, G,2] en la posición i está el simbolo
    private MatchView matchView;
    private Image rackBackground;
    private JPanel rackPanel;
    private TileView selectedTile;
    private ArrayList<String> letters; // conjunto de fichas del jugador
    @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Dibujar la imagen de fondo escalada al tamaño del panel
                g.drawImage(rackBackground, 0, 0, getWidth(), getHeight(), this);
            }

    //letters = [A,1, B,3, C,3, D,2, E,1, F,4, G,2] en la posición i está el simbolo
    //y en la posición i+1 está el valor de la ficha

    public RackView(ArrayList<String> letters, MatchView matchView) {
        //this.setLayout(new GridLayout(1, letters.size()/2));
        this.letters = new ArrayList<>(); 
        this.matchView = matchView;
        this.setLayout(new BorderLayout());

        ImageIcon rackwood = new ImageIcon("Resources/Wood.jpg");
        rackBackground = rackwood.getImage();

        rackPanel = new JPanel();
        rackPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        rackPanel.setPreferredSize(new Dimension(500, 100));
        rackPanel.setOpaque(false);
        //rackPanel.setBackground(Color.decode("#332F2C"));
        rackPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        

        

        JButton shuffleButton = new JButton("Shuffle");
        shuffleButton.setPreferredSize(new Dimension(80, 25));
        shuffleButton.setFont(new Font("Dubai Medium", Font.PLAIN, 15));

        JButton exchangeButton = new JButton("Exchange");
        exchangeButton.setPreferredSize(new Dimension(95, 25));
        exchangeButton.setFont(new Font("Dubai Medium", Font.PLAIN, 15));

        JButton submitButton = new JButton("Submit");
        submitButton.setPreferredSize(new Dimension(80, 25));
        submitButton.setFont(new Font("Dubai Medium", Font.PLAIN, 15));

        JButton skipButton = new JButton("Skip");
        skipButton.setPreferredSize(new Dimension(80, 25));
        skipButton.setFont(new Font("Dubai Medium", Font.PLAIN, 15));

        skipButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para saltar el turno
                matchView.skipTurn();
                // Aquí puedes implementar la lógica de saltar el turno
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                matchView.submitTurn();
            }
        });

        exchangeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inp = JOptionPane.showInputDialog(null, "Type what letters do you want to change in this format: A_B_C " ,  "Exchange Letters", JOptionPane.PLAIN_MESSAGE );
                if(inp == null || inp.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No letters changed" ,  "Exchange Letters", JOptionPane.INFORMATION_MESSAGE);
                    return; // Si el usuario cancela o no introduce nada, no hacemos nada
                }
                String[] lettersToExchange = inp.split("_");
                for (String letter: lettersToExchange) {
                    letter = letter.toUpperCase(); // Aseguramos que la letra esté en mayúsculas
                    if (RackView.this.letters.contains(letter)) {
                        RackView.this.letters.remove(letter); // Eliminamos la letra del rack
                    } else {
                        JOptionPane.showMessageDialog(null, "You don't have the letter: " + letter ,  "Exchange Letters", JOptionPane.ERROR_MESSAGE);
                        return; // Si alguna letra no está en el rack, no hacemos el intercambio
                    }
                }

                matchView.exchangeLetters(inp);

            }
        });

        

        shuffleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para barajar las fichas
                matchView.shuffleRack();
                // Aquí puedes implementar la lógica de barajar las fichas
            }
        });


        JPanel ButtonsPanel = new JPanel();
        ButtonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        ButtonsPanel.setOpaque(false);
        ButtonsPanel.setPreferredSize(new Dimension(200, 50));

        ButtonsPanel.add(shuffleButton);
        ButtonsPanel.add(exchangeButton);
        ButtonsPanel.add(submitButton);
        ButtonsPanel.add(skipButton);

       


        for(int i = 0; i < letters.size(); i+=2) {
            TileView tile = new TileView(letters.get(i), Integer.parseInt(letters.get(i+1)));
            addListener(tile);
            rackPanel.add(tile);
            this.letters.add(letters.get(i));
        }

        this.add(ButtonsPanel, BorderLayout.NORTH);
        this.add(rackPanel, BorderLayout.CENTER);

    }

    public TileView getSelectedTile() {
        return selectedTile;
    }

    public void addTile(TileView tile) {
        rackPanel.add(tile);
        tile.setBackground(Color.decode("#EED09D"));
        System.out.println("Tile added to rack");
        rackPanel.revalidate();
        rackPanel.repaint();
        
    }

    public void setSelectedTile(TileView tile) {
        if(selectedTile != null) {
            selectedTile.setBackground(Color.decode("#EED09D"));
        }
        selectedTile = tile;
        tile.setBackground(Color.decode("#FFCC00"));
    }

    public void removeSelectedTile() {
        
            rackPanel.remove(selectedTile);
            selectedTile.setBackground(Color.decode("#EED09D"));
            selectedTile = null;
            rackPanel.revalidate();
            rackPanel.repaint();
            
        
    }

    public void addListener(TileView tile){
        tile.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent evt) {
                    System.out.println("Me han clicado");
                    if(selectedTile == tile){
                        selectedTile = null;
                        tile.setBackground(Color.decode("#EED09D"));
                    }
                    else{
                    
                        if (selectedTile != null) {
                            selectedTile.setBackground(Color.decode("#EED09D"));
                        }
                        
                        
                        selectedTile = tile;
                        tile.setBackground(Color.decode("#FFCC00"));
                     }
                        //tile.removeMouseListener(tile.getMouseListeners()[0]);

                }
            });
    }


    public void cleanRack() {
        rackPanel.removeAll();
        selectedTile = null;
        this.letters.clear();
       
    }

    public void updateRack(ArrayList<String> letters) {
        cleanRack();
        for(int i = 0; i < letters.size(); i+=2) {
            TileView tile = new TileView(letters.get(i), Integer.parseInt(letters.get(i+1)));
            addListener(tile);
            rackPanel.add(tile);
            this.letters.add(letters.get(i));
        }
        for(int i = 0; i < this.letters.size(); i++) {
            System.out.println("Letter " + i + ": " + this.letters.get(i));
        }
        rackPanel.revalidate();
        rackPanel.repaint();
    }
}
