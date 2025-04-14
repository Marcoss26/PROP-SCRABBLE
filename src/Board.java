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
            if (i == 0 && j == 0 || i == size/2 && j == 0 || i == size-1 && j == 0 || i == 0 && j == size/2 || i == size-1 && j == size/2 || i == 0 && j == size-1 || i == size/2 && j == size-1 || i == size-1 && j == size-1)
            {
                board[i][j] = new Box.TripleWord(i, j);
            }
        }
     }
    }
    //queda pendiente el poner las casillas con potenciadores
}