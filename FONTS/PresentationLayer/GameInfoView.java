package PresentationLayer;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GameInfoView extends JPanel {
    private JPanel scorePanel;
    private JPanel bagPanel;
    private JPanel historyPanel;
    private JPanel buttPanel;
    private DefaultListModel<String> historyModel;

    public GameInfoView(Integer numPlayers, List<String> players) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setPreferredSize(new Dimension(200,600));
        this.setBackground(Color.decode("#D7D7D7"));

        // Create the score panel
        scorePanel = new JPanel();
        scorePanel.setLayout(new GridLayout(4,1));
        scorePanel.setBackground(Color.decode("#D7D7D7"));
        scorePanel.setBorder(BorderFactory.createTitledBorder("Scores"));
        scorePanel.setPreferredSize(new Dimension(200, 200));
        for(int i = 0; i < numPlayers; ++i) {
            JLabel label = new JLabel(players.get(i) + ": " + 0);
            label.setFont(new Font("Dubai Medium", Font.PLAIN, 15));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            scorePanel.add(label);
        }

        // Create the bag panel
        bagPanel = new JPanel();
        bagPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bagPanel.setBackground(Color.decode("#D7D7D7"));
        bagPanel.setBorder(BorderFactory.createTitledBorder("Bag"));
        bagPanel.setPreferredSize(new Dimension(200, 100));

        int tilesRemaining = 100 - (7*numPlayers); // Example value
        JLabel bagLabel = new JLabel("Tiles left in the bag: " + tilesRemaining);
        bagLabel.setFont(new Font("Dubai Medium", Font.PLAIN, 15));
        bagLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bagPanel.add(bagLabel);

        historyModel = new DefaultListModel<>();
        JList<String> historyList = new JList<>(historyModel);
        historyList.setFont(new Font("Dubai Medium", Font.PLAIN, 20));
        historyList.setBackground(Color.decode("#D7D7D7"));
        historyList.setVisibleRowCount(10);

        JScrollPane scrollPane = new JScrollPane(historyList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("History of plays"));
        scrollPane.setBackground(Color.decode("#D7D7D7"));
        scrollPane.setPreferredSize(new Dimension(200, 300));

        buttPanel = new JPanel();
        buttPanel.setLayout(null);
        buttPanel.setBackground(Color.decode("#D7D7D7"));

        JButton exit = new JButton("Exit");
        exit.setPreferredSize(new Dimension(60, 20));
        exit.setFont(new Font("Dubai Medium", Font.PLAIN, 15));
        exit.setBounds(10, 10, 80, 30);
        JButton save = new JButton("Save");
        save.setPreferredSize(new Dimension(100, 50));
        save.setFont(new Font("Dubai Medium", Font.PLAIN, 15));
        save.setBounds(100, 10, 80, 30);
        buttPanel.add(exit);
        buttPanel.add(save);

        // Add the components to the main panel
        this.add(scorePanel);
        this.add(Box.createVerticalStrut(1));
        this.add(bagPanel);
        this.add(Box.createVerticalStrut(1));
        this.add(scrollPane);
        this.add(Box.createVerticalGlue());
        this.add(buttPanel);


    }

    public void addMoveToHistory(String player, String word, int points) {
        String move = player + " - \"" + word + "\" (" + points + " puntos)";
        historyModel.addElement(move);
    }



    public void actPlayerScore(int playerIndex, int score) {
        
        JLabel label = (JLabel) scorePanel.getComponent(playerIndex);
        label.setText(label.getText().split(":")[0] + ": " + score);
        scorePanel.revalidate();
        scorePanel.repaint();
    }

}
