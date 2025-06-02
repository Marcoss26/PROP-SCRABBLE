package Utils;

public class squareDTO {
    private final int row;
    private final int col;
    private final String letter;
    private final int score;

    public squareDTO(int row, int col, String letter, int score) {
        this.row = row;
        this.col = col;
        this.letter = letter;
        this.score = score;
    }

    public int getRow() { return row; }
    public int getCol() { return col; }
    public String getLetter() { return letter; }
    public int getScore() { return score; }
}