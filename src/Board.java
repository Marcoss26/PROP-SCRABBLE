import java.util.*;

public class Board
{
    private Box[][] board;
    private int size;
    public Board(int size)
    {
     board = new Box[size][size];

     for (int i = 0; i < size; i++)
     {
        for (int j = 0; j < size; j++)
        {
            if (i == 0 && j == 0 || i == size/2 && j == 0 || i == size-1 && j == 0 || i == 0 && j == size/2 || 
            i == size-1 && j == size/2 || i == 0 && j == size-1 || i == size/2 && j == size-1 || i == size-1 
            && j == size-1)
            {
                board[i][j] = new Box.TripleWord(i, j);
            }
            else if (size == 7) {
                if (i == j || i + j == size - 1) board[i][j] = new Box.DoubleWord(i, j);
                else if (i == size/4 && j == size/2 || i == size/2 && j == size/4 || i == size/2 && j == (3*size)/4 
                || i == (3*size)/4 && j == size/2) board[i][j] = new Box.TripleLetter(i, j);
                else if (i == size/2 + 1 && j == size/2 || i == size/2 - 1 && j == size/2 
                || i == size/2 && j == size/2 - 1 || i == size/2 && j == size/2 + 1 
                || i == 0 && j == 1 || i == 0 && j == size-2 || i == 1 && j == 0 
                || i == 1 && j == size-1 || i == size-2 && j == 0 
                || i == size-2 && j == size-1 || i == size-1 && j == 1 
                || i == size-1 && j == size-2) board[i][j] = new Box.DoubleLetter(i, j);
            }
            else if (size == 15) {
                if (i == 2 && j == size/2 - 2 || i == 2 && j == size/2 + 2 || i == size/2 - 2 && j == 1 
                || i == size/2 - 2 && j ==  size/2 - 2 || i == size/2 - 2 && j ==  size/2 + 2 
                || i == size/2 - 2 && j ==  size - 2 || i == size/2 + 2 && j == 1 
                || i == size/2 + 2 && j ==  size/2 - 2 || i == size/2 + 2 && j ==  size/2 + 2 
                || i == size/2 + 2 && j ==  size - 2 || i == 2 && j == size/2 - 2 
                || i == 2 && j == size/2 + 2) board[i][j] = new Box.TripleLetter(i, j);
                else if (i == 0 && j == 3 || i == 0 && j == size-4 
                || i == 2 && j == size/2 - 1 || i == 2 && j == size/2 + 1 
                || i == 3 && j == 0 || i == 3 && j == size/2 || i == 3 && j == size-1 
                || i == size/2 - 1 && j == 2 || i == size/2 - 1 && j == size/2 - 1 
                || i == size/2 - 1 && j == size+1 || i == size/2 -1 && j == size - 3 
                || i == size/2 && j == 3 || i == size/2 && j == size-4 
                || i == size-1 && j == 3 || i == size-1 && j == size-4 
                || i == size-3 && j == size/2 - 1 || i == size-3 && j == size/2 + 1 
                || i == size-4 && j == 0 || i == size-4 && j == size/2 || i == size-4 && j == size-1 
                || i == size/2 + 1 && j == 2 || i == size/2 + 1 && j == size/2 - 1 
                || i == size/2 + 1 && j == size+1 || i == size/2 + 1 && j == size - 3) board[i][j] = new Box.DoubleLetter(i, j);
                else if (i == j || i + j == size - 1) board[i][j] = new Box.DoubleWord(i, j);
            }
        }
     }
    }
}