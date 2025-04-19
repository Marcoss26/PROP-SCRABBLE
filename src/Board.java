import java.util.*;

public class Board
{
    private Box[][] board;
    private int size;

    public Board(int size)
    {
        board = new Box[size][size];
        int[] doubleLetter = new int[0];
        int[] tripleLetter = new int[0];
        int[] doubleWord = new int[0];
        int[] tripleWord = new int[0];

        if (size == 7) {
            doubleLetter = new int[]{0,1, 0,5, 1,0, 1,6, 2,3, 3,2, 3,4, 4,3, 5,0, 5,6, 6,1, 6,5};
            tripleLetter = new int[]{1,3, 3,1, 3,5, 5,3};
            doubleWord = new int[]{1,1, 2,2, 3,3, 4,4, 5,5, 1,5, 2,4, 4,2, 5,1};
            tripleWord = new int[]{0,0, 0,3, 0,6, 3,0, 3,6, 6,0, 6,3, 6,6};
        }
        else if (size == 15) {
            doubleLetter = new int[]{0,3, 0,11, 2,6, 2,8, 3,0, 3,7, 3,14, 6,2, 6,6, 6,8, 6,12, 7,3, 7,11, 14,3, 14,11, 12,6, 12,8, 11,0, 11,7, 11,14, 8,2, 8,6, 8,8, 8,12};
            tripleLetter = new int[]{1,5, 1,9, 5,1, 5,5, 5,9, 5,13, 13,5, 13,9, 9,1, 9,5, 9,9, 9,13};
            doubleWord = new int[]{1,1, 2,2, 3,3, 4,4, 7,7, 10,10, 11,11, 12,12, 13,13, 1,13, 2,12, 3,11, 4,10, 10,4, 11,3, 12,2, 13,1};
            tripleWord = new int[]{0,0, 0,7, 0,14, 7,0, 7,14, 14,0, 14,7, 14,14};
        }
        else if (size == 25) {
            doubleLetter = new int[]{1,8, 1,16, 3,11, 3,13, 4,12, 5,8, 5,16, 7,11, 7,13, 8,12, 8,1, 8,5, 8,19, 8,23, 11,3, 11,7, 11,11, 11,13, 11,17, 11,21, 12,4, 12,8, 12,16, 12,20, 23,8, 23,16, 21,11, 21,13, 20,12, 19,8, 19,16, 17,11, 17,13, 16,12, 16,1, 16,5, 16,19, 16,23, 13,3, 13,7, 13,11, 13,13, 13,17, 13,21};
            tripleLetter = new int[]{2,10, 2,14, 6,10, 6,14, 10,10, 10,14, 14,10, 14,14, 18,10, 18,14, 22,10, 22,14, 10,2, 10,6, 14,2, 14,6, 10,18, 10,22, 14,18, 14,22};
            doubleWord = new int[]{1,1, 2,2, 3,3, 4,4, 5,5, 6,6, 7,7, 9,9, 12,12, 15,15, 16,16, 17,17, 18,18, 19,19, 20,20, 21,21, 22,22, 23,23, 1,23, 2,22, 3,21, 4,20, 5,19, 6,18, 7,17, 8,16, 9,15, 15,9, 16,8, 17,7, 18,6, 19,5, 20,4, 21,3, 22,2, 23,1};
            tripleWord = new int[]{0,0, 0,12, 0,24, 12,0, 12,24, 24,0, 24,12, 24,24};
        }
        
        assignSpecialBoxes(doubleLetter, "doubleLetter");
        assignSpecialBoxes(tripleLetter, "tripleLetter");
        assignSpecialBoxes(doubleWord, "doubleWord");
        assignSpecialBoxes(tripleWord, "tripleWord");

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == null) board[i][j] = new Box(i, j);
            }
        } 
    }

    private void assignSpecialBoxes(int[] posicions, String type) {
        for (int i = 0; i < posicions.length; i += 2) {
            int x = posicions[i];
            int y = posicions[i + 1];
            switch (type) {
                case "doubleLetter":
                    board[x][y] = new Box.DoubleLetter(x, y);
                    break;
                case "tripleLetter":
                    board[x][y] = new Box.TripleLetter(x, y);
                    break;
                case "doubleWord":
                    board[x][y] = new Box.DoubleWord(x, y);
                    break;
                case "tripleWord":
                    board[x][y] = new Box.TripleWord(x, y);
                    break;
            }
        }
    }

    public void printBoard() {
        // Imprimir los índices de las columnas
        System.out.print("   "); // Espacio inicial para alinear con los índices de las filas
        for (int j = 0; j < size; j++) {
            System.out.print(j + "  "); // Imprime el índice de la columna con espacio
        }
        System.out.println(); // Salto de línea después de los índices de las columnas
    
        // Imprimir las filas con sus índices
        for (int i = 0; i < size; i++) {
            System.out.print(i + " "); // Imprime el índice de la fila
            if (i < 10) System.out.print(" "); // Alineación para índices de una cifra
            for (int j = 0; j < size; j++) {
                System.out.print(board[i][j].toString() + " "); // Imprime la casilla
            }
            System.out.println(); // Salto de línea al final de cada fila
        }
    }
    
}